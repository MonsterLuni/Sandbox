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
        ui.ml.getPosition(e);
    }
}
