import java.awt.*;
import java.awt.event.MouseEvent;

public class MouseListener implements java.awt.event.MouseListener {
    public UI ui;
    public MouseListener(UI ui){
        this.ui = ui;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if(e.getButton() == 1){
            Point coordinates = e.getPoint();
            if(coordinates.x >= ui.findStartingXforFieldCenter() && coordinates.x < ui.fieldWidth + ui.findStartingXforFieldCenter() && coordinates.y >= 0 && coordinates.y < ui.fieldHeight){
                ui.createPieceOfSand((((coordinates.x - ui.findStartingXforFieldCenter())/ui.pixelSize)*ui.pixelSize)/ui.pixelSize,((coordinates.y/ui.pixelSize)*ui.pixelSize)/ui.pixelSize);
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
