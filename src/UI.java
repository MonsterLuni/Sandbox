import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.util.HashMap;

public class UI extends JFrame {
    public boolean running = true;
    public double fps;
    public long lastTime;
    public int maxFps = 60;
    public KeyListener kl;
    public MouseMotionListener mml;
    public MouseListener ml;
    public int screenWidth = 500;
    public int screenHeight = 500;
    public int fieldWidth = 400;
    public int fieldHeight = 400;
    public int pixelSize = 5;
    public int tickrate = 1;
    public int drawSizeX = 5;
    public int drawSizeY = 5;
    int i = 0;
    public HashMap<Point, Boolean> field = new HashMap<>((fieldWidth/pixelSize)*(fieldHeight/pixelSize));
    public HashMap<Point,Boolean> fieldtemporary;
    public BufferedImage image = new BufferedImage(screenWidth,screenHeight,BufferedImage.TYPE_INT_RGB);
    public Graphics g = image.getGraphics();
    public UI(){
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
        drawPieceOfSand();
        drawFieldCircumference();
        drawToImage();
        if(i == tickrate){
            updateField();
            i = 0;
        }
        i++;
    }
    private void drawFieldCircumference(){
        g.setColor(Color.white);
        g.drawRect(findStartingXforFieldCenter(),0,fieldWidth,fieldHeight);
    }
    private void drawPieceOfSand(){
        for (int i = 0; i < fieldWidth/pixelSize; i++){
            for (int l = 0; l < fieldHeight/pixelSize; l++){
                if(field.get(new Point(i, l))){
                    g.setColor(Color.white);
                    g.fillRect(findStartingXforFieldCenter() + (i*pixelSize),l*pixelSize,pixelSize,pixelSize);
                }
            }
        }
    }
    private void makeField(){
        for (int i = 0; i < fieldWidth/pixelSize; i++){
            for (int l = 0; l < fieldHeight/pixelSize; l++){
                field.put(new Point(i,l),false);
            }
        }
    }
    private void updateField(){
        fieldtemporary = new HashMap<>((fieldWidth/pixelSize)*(fieldHeight/pixelSize));
        for (int i = 0; i < fieldWidth/pixelSize; i++){
            for (int l = 0; l < fieldHeight/pixelSize; l++){
                fieldtemporary.put(new Point(i,l),false);
            }
        }
        for (int i = 0; i < fieldWidth/pixelSize; i++){
            for (int l = 0; l < fieldHeight/pixelSize; l++){
                if(field.get(new Point(i,l))){
                    try{
                        if(field.get(new Point(i,l + 1))){
                            try{
                                boolean bottom = field.get(new Point(i,l + 1));
                                boolean bottomleft = field.get(new Point(i - 1,l + 1));
                                boolean bottomright = field.get(new Point(i + 1,l + 1));
                                if(Math.random() > 0.5){
                                    // links
                                    if(!field.get(new Point(i - 1,l)) && !field.get(new Point(i - 1,l + 1))){
                                        setSandToTrue(i - 1,l);
                                    }
                                    else{
                                        setSandToTrue(i,l);
                                    }
                                }
                                else{
                                    // rechts
                                    if(!field.get(new Point(i + 1,l)) && !field.get(new Point(i + 1,l + 1))){
                                        setSandToTrue(i + 1,l);
                                    }
                                    else{
                                        setSandToTrue(i,l);
                                    }
                                }
                            } catch (NullPointerException e){
                                // Links
                                try {
                                    if(!field.get(new Point(i - 1,l)) && !field.get(new Point(i - 1,l + 1))){
                                        setSandToTrue(i - 1,l);
                                    }
                                    else{
                                        setSandToTrue(i,l);
                                    }
                                } catch (NullPointerException f){
                                    setSandToTrue(i,l);
                                }
                                // Rechts
                                try {
                                    if(!field.get(new Point(i + 1,l)) && !field.get(new Point(i + 1,l + 1))){
                                        setSandToTrue(i + 1,l);
                                    }
                                    else{
                                        setSandToTrue(i,l);
                                    }
                                } catch (NullPointerException f){
                                    setSandToTrue(i,l);
                                }
                            }
                        }
                        else{
                            setSandToTrue(i,l + 1);
                        }
                    } catch (NullPointerException e){
                        setSandToTrue(i,l);
                    }

                }
                /*
                if(l + 1 < fieldHeight/pixelSize && i + 1 < fieldWidth/pixelSize){
                    boolean bottom = field.get(new Point(i,l + 1));
                    if(!bottom && field.get(new Point(i,l))){
                        fieldtemporary.replace(new Point(i,l + 1),true);
                    }
                    if(bottom && field.get(new Point(i,l))){
                        if(i - 1 > 0){
                            boolean bottomLeft = field.get(new Point(i - 1,l + 1));
                            if(!bottomLeft){
                                fieldtemporary.replace(new Point(i - 1,l),true);
                            } else{
                                fieldtemporary.replace(new Point(i,l),true);
                            }
                        }else if (i + 1 < fieldWidth/pixelSize){
                            boolean bottomRight = field.get(new Point(i + 1,l + 1));
                            if (!bottomRight){
                                fieldtemporary.replace(new Point(i + 1,l),true);
                            }else{
                                fieldtemporary.replace(new Point(i,l),true);
                            }
                        }
                    }
                } else if (l + 1 == fieldHeight/pixelSize) {
                    if(field.get(new Point(i,l))){
                        fieldtemporary.replace(new Point(i,l),true);
                    }
                }
                */
            }
        }
        field = fieldtemporary;
    }
    public void setSandToTrue(int x,int y){
        fieldtemporary.replace(new Point(x,y),fieldtemporary.get(new Point(x,y)),true);
    }
    public void createPieceOfSand(int x, int y){
        field.replace(new Point(x,y),field.get(new Point(x,y)),true);
    }
    public void destroyPieceOfSand(int x, int y){
        field.replace(new Point(x,y),field.get(new Point(x,y)),false);
    }
    public int findStartingXforFieldCenter(){
        return (screenWidth - fieldWidth)/2;
    }
    private void drawToImage(){
        super.getGraphics().drawImage(image,0,0,null);
    }
}

