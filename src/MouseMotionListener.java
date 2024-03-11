import java.awt.*;
import java.awt.event.MouseEvent;

public class MouseMotionListener implements java.awt.event.MouseMotionListener {
    UI ui;
    public MouseMotionListener(UI ui){
        this.ui = ui;
    }
    public void getMouseBlockHover(Point coordinates){
        if(coordinates.x >= ui.findStartingXforFieldCenter() && coordinates.x < ui.fieldWidth + ui.findStartingXforFieldCenter() && coordinates.y >= 0 && coordinates.y < ui.fieldHeight){
            ui.g.drawRect(((coordinates.x - ui.findStartingXforFieldCenter())/ui.pixelSize)*ui.pixelSize + ui.findStartingXforFieldCenter(),(coordinates.y/ui.pixelSize)*ui.pixelSize, ui.pixelSize, ui.pixelSize);
        }
    }
    @Override
    public void mouseMoved(MouseEvent e) {
        getMouseBlockHover(e.getPoint());
    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }
}
