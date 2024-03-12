import java.awt.*;
import java.awt.event.MouseEvent;

public class MouseListener implements java.awt.event.MouseListener {
    public UI ui;
    public boolean leftPressed, rightPressed;
    public MouseListener(UI ui){
        this.ui = ui;
    }

    @Override
    public void mouseClicked(MouseEvent e) {}

    @Override
    public void mousePressed(MouseEvent e) {
        if(e.getButton() == 1){
            leftPressed = true;
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
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
