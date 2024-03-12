import element.ELEM_AIR;
import element.ELEM_SAND;
import element.ELEM_STONE;
import element.Element;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Objects;

public class UI extends JFrame {
    public Element selectedElement;
    public Element[] elements = new Element[3];
    public boolean running = true;
    public double fps;
    public long lastTime;
    public int maxFps = 60;
    public KeyListener kl;
    public MouseMotionListener mml;
    public MouseListener ml;
    public int screenWidth = 500;
    public int screenHeight = 500;
    public int fieldWidth = 300;
    public int fieldHeight = 300;
    public int pixelSize = 5;
    public int tickRate = 1;
    public int drawSizeX = 10;
    public int drawSizeY = 10;
    int i = 0;
    public HashMap<Point, Element> field = new HashMap<>((fieldWidth/pixelSize)*(fieldHeight/pixelSize));
    public HashMap<Point,Element> fieldTemporary;
    public BufferedImage image = new BufferedImage(screenWidth,screenHeight,BufferedImage.TYPE_INT_RGB);
    public Graphics g = image.getGraphics();
    public UI(){
        fillElements();
        selectedElement = new ELEM_SAND();
        kl  = new KeyListener(this);
        mml = new MouseMotionListener(this);
        ml = new MouseListener(this);
        setSize(screenWidth, screenHeight);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setUndecorated(true);
        setIconImage(new ImageIcon("assets/logo.png").getImage());
        setVisible(true);
        addKeyListener(kl);
        addMouseMotionListener(mml);
        addMouseListener(ml);
        setFocusTraversalKeysEnabled(false);
        lastTime = System.currentTimeMillis();
        makeField();
        fpsLimiter();
    }
    public void fpsLimiter() {
        long targetTime = System.currentTimeMillis() + (long) (1000 / maxFps);
        while (running) {
            long now = System.currentTimeMillis();
            fps = 1000 / (double)(now - lastTime);
            update();
            lastTime = now;
            long sleepTime = targetTime - System.currentTimeMillis();
            if (sleepTime > 0) {
                try {
                    Thread.sleep(sleepTime);
                } catch (InterruptedException e) {
                    e.getStackTrace();
                }
            }
            targetTime += 1000 / maxFps;
        }
        dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
    }
    private void clearWindow(Color col){
        g.clearRect(0,0,screenWidth,screenHeight);
        g.setColor(col);
        g.fillRect(0,0,screenWidth,screenHeight);
    }
    public void update(){
        clearWindow(Color.black);
        drawSelectableElements();
        drawPieceOfSand();
        getMouseBlockHover(mml.mousePoint);
        drawFieldCircumference();
        drawToImage();
        if(i == tickRate){
            updateField();
            i = 0;
        }
        i++;
    }
    public void fillElements(){
        elements[0] = new ELEM_AIR();
        elements[1] = new ELEM_SAND();
        elements[2] = new ELEM_STONE();
    }
    private void drawFieldCircumference(){
        g.setColor(Color.white);
        g.drawRect(findStartingXFieldCenter(),0,fieldWidth,fieldHeight);
    }
    private void drawPieceOfSand(){
        for (int i = 0; i < fieldWidth/pixelSize; i++){
            for (int l = 0; l < fieldHeight/pixelSize; l++){
                if(!field.get(new Point(i, l)).invisible){
                    g.setColor(field.get(new Point(i, l)).color);
                    g.fillRect(findStartingXFieldCenter() + (i*pixelSize),l*pixelSize,pixelSize,pixelSize);
                }
            }
        }
    }
    public void drawSelectableElements(){
        int i = 0;
        for (Element element: elements){
            g.setColor(element.color);
            g.fillRect(findStartingXFieldCenter() + (i*50),fieldHeight + 50,50,25);
            g.setColor(Color.black);
            g.drawString(element.name,findStartingXFieldCenter() + (i*50) + 12,fieldHeight + 62);
            if(Objects.equals(element.name, selectedElement.name)){
                g.setColor(Color.red);
                g.drawRect(findStartingXFieldCenter() + (i*50),fieldHeight + 50,50,25);
            }
            i++;
        }
    }
    private void makeField(){
        for (int i = 0; i < fieldWidth/pixelSize; i++){
            for (int l = 0; l < fieldHeight/pixelSize; l++){
                field.put(new Point(i,l),new ELEM_AIR());
            }
        }
    }
    private void updateField(){
        fieldTemporary = new HashMap<>((fieldWidth/pixelSize)*(fieldHeight/pixelSize));
        for (int i = 0; i < fieldWidth/pixelSize; i++){
            for (int l = 0; l < fieldHeight/pixelSize; l++){
                fieldTemporary.put(new Point(i,l),new ELEM_AIR());
            }
        }
        for (int i = 0; i < fieldWidth/pixelSize; i++){
            for (int l = 0; l < fieldHeight/pixelSize; l++){
                if(field.get(new Point(i,l)).solid){
                    try{
                        if(field.get(new Point(i,l + 1)).solid){
                            try{
                                field.get(new Point(i,l + 1));
                                field.get(new Point(i - 1,l + 1));
                                field.get(new Point(i + 1,l + 1));
                                if(Math.random() > 0.5){
                                    // left
                                    if(!field.get(new Point(i - 1,l)).solid && !field.get(new Point(i - 1,l + 1)).solid){
                                        setSandToTrue(i - 1,l,field.get(new Point(i,l)));
                                    }
                                    else{
                                        setSandToTrue(i,l,field.get(new Point(i,l)));
                                    }
                                }
                                else{
                                    // right
                                    if(!field.get(new Point(i + 1,l)).solid && !field.get(new Point(i + 1,l + 1)).solid){
                                        setSandToTrue(i + 1,l,field.get(new Point(i,l)));
                                    }
                                    else{
                                        setSandToTrue(i,l,field.get(new Point(i,l)));
                                    }
                                }
                            } catch (NullPointerException e){
                                // left
                                try {
                                    if(!field.get(new Point(i - 1,l)).solid && !field.get(new Point(i - 1,l + 1)).solid){
                                        setSandToTrue(i - 1,l,field.get(new Point(i,l)));
                                    }
                                    else{
                                        setSandToTrue(i,l,field.get(new Point(i,l)));
                                    }
                                } catch (NullPointerException f){
                                    setSandToTrue(i,l,field.get(new Point(i,l)));
                                }
                                // right
                                try {
                                    if(!field.get(new Point(i + 1,l)).solid && !field.get(new Point(i + 1,l + 1)).solid){
                                        setSandToTrue(i + 1,l,field.get(new Point(i,l)));
                                    }
                                    else{
                                        setSandToTrue(i,l,field.get(new Point(i,l)));
                                    }
                                } catch (NullPointerException f){
                                    setSandToTrue(i,l,field.get(new Point(i,l)));
                                }
                            }
                        }
                        else{
                            setSandToTrue(i,l + 1,field.get(new Point(i,l)));
                        }
                    } catch (NullPointerException e){
                        setSandToTrue(i,l,field.get(new Point(i,l)));
                    }

                }
            }
        }
        field = fieldTemporary;
    }
    public void setSandToTrue(int x,int y, Element change){
        fieldTemporary.replace(new Point(x,y), fieldTemporary.get(new Point(x,y)),change);
    }
    public void createPieceOfSand(int x, int y){
        field.replace(new Point(x,y),field.get(new Point(x,y)),selectedElement);
    }
    public void destroyPieceOfSand(int x, int y){
        field.replace(new Point(x,y),field.get(new Point(x,y)),new ELEM_AIR());
    }
    public void getMouseBlockHover(Point coordinates){
        if(coordinates.x >= findStartingXFieldCenter() && coordinates.x < fieldWidth + findStartingXFieldCenter() && coordinates.y >= 0 && coordinates.y < fieldHeight){
            g.setColor(Color.blue);
            g.drawRect(((coordinates.x - findStartingXFieldCenter())/pixelSize)*pixelSize + findStartingXFieldCenter(),(coordinates.y/pixelSize)*pixelSize, pixelSize, pixelSize);
        }
    }
    public int findStartingXFieldCenter(){
        return (screenWidth - fieldWidth)/2;
    }
    private void drawToImage(){
        super.getGraphics().drawImage(image,0,0,null);
    }
}

