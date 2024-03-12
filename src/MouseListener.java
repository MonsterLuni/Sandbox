import java.awt.*;
import java.awt.event.MouseEvent;

public class MouseListener implements java.awt.event.MouseListener {
    public UI ui;
    public boolean leftPressed, rightPressed;
    public MouseListener(UI ui){
        this.ui = ui;
    }
    @Override
    public void mouseClicked(MouseEvent e) {
        ui.mml.mousePoint = e.getPoint();
        getPosition(e);
    }
    public void getPosition(MouseEvent e){
        if(leftPressed){
            Point coordinates = e.getPoint();
            if(coordinates.x >= ui.findStartingXFieldCenter() && coordinates.x < ui.fieldWidth + ui.findStartingXFieldCenter() && coordinates.y >= 0 && coordinates.y < ui.fieldHeight){
                for (int i = 0; i < ui.drawSizeX; i++){
                    for (int l = 0; l < ui.drawSizeY; l++){
                        ui.createPieceOfSand((((coordinates.x - ui.findStartingXFieldCenter())/ui.pixelSize)*ui.pixelSize)/ui.pixelSize + i - (ui.drawSizeX/2),((coordinates.y/ui.pixelSize)*ui.pixelSize)/ui.pixelSize + l - (ui.drawSizeY/2));
                    }
                }
            } else if (coordinates.x >= ui.findStartingXFieldCenter() && coordinates.x < ui.findStartingXFieldCenter() + (ui.elements.length*50)){
                ui.selectedElement = ui.elements[(coordinates.x - ui.findStartingXFieldCenter())/50];
            }
        } else if (rightPressed){
            Point coordinates = e.getPoint();
            if(coordinates.x >= ui.findStartingXFieldCenter() && coordinates.x < ui.fieldWidth + ui.findStartingXFieldCenter() && coordinates.y >= 0 && coordinates.y < ui.fieldHeight){
                for (int i = 0; i < ui.drawSizeX; i++){
                    for (int l = 0; l < ui.drawSizeY; l++){
                        ui.destroyPieceOfSand((((coordinates.x - ui.findStartingXFieldCenter())/ui.pixelSize)*ui.pixelSize)/ui.pixelSize + i - (ui.drawSizeX/2),((coordinates.y/ui.pixelSize)*ui.pixelSize)/ui.pixelSize + l - (ui.drawSizeY/2));
                    }
                }
            }
        }
    }
    @Override
    public void mousePressed(MouseEvent e) {
        if(e.getButton() == 1){
            leftPressed = true;
            getPosition(e);
        }
        if(e.getButton() == 3){
            rightPressed = true;
        }
    }
    @Override
    public void mouseReleased(MouseEvent e) {
        if(e.getButton() == 1){
            leftPressed = false;
        }
        if(e.getButton() == 3){
            rightPressed = false;
        }
    }
    @Override
    public void mouseEntered(MouseEvent e) {}
    @Override
    public void mouseExited(MouseEvent e) {

    }
}
