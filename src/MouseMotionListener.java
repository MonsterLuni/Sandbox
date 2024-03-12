import java.awt.*;
import java.awt.event.MouseEvent;

public class MouseMotionListener implements java.awt.event.MouseMotionListener {
    UI ui;
    public Point mousePoint = new Point(-1,-1);
    public MouseMotionListener(UI ui){
        this.ui = ui;
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        mousePoint = e.getPoint();
    }
    @Override
    public void mouseDragged(MouseEvent e) {
        mousePoint = e.getPoint();
        if(ui.ml.leftPressed){
            Point coordinates = e.getPoint();
            if(coordinates.x >= ui.findStartingXforFieldCenter() && coordinates.x < ui.fieldWidth + ui.findStartingXforFieldCenter() && coordinates.y >= 0 && coordinates.y < ui.fieldHeight){
                for (int i = 0; i < ui.drawSizeX; i++){
                    for (int l = 0; l < ui.drawSizeY; l++){
                        ui.createPieceOfSand((((coordinates.x - ui.findStartingXforFieldCenter())/ui.pixelSize)*ui.pixelSize)/ui.pixelSize + i,((coordinates.y/ui.pixelSize)*ui.pixelSize)/ui.pixelSize + l);
                    }
                }
            }
        } else if (ui.ml.rightPressed){
            Point coordinates = e.getPoint();
            if(coordinates.x >= ui.findStartingXforFieldCenter() && coordinates.x < ui.fieldWidth + ui.findStartingXforFieldCenter() && coordinates.y >= 0 && coordinates.y < ui.fieldHeight){
                for (int i = 0; i < ui.drawSizeX; i++){
                    for (int l = 0; l < ui.drawSizeY; l++){
                        ui.destroyPieceOfSand((((coordinates.x - ui.findStartingXforFieldCenter())/ui.pixelSize)*ui.pixelSize)/ui.pixelSize + i,((coordinates.y/ui.pixelSize)*ui.pixelSize)/ui.pixelSize + l);
                    }
                }
            }
        }
    }
}
