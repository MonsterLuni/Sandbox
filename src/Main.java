import javax.swing.*;

import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

public class Main {
    public static void main(String[] args) {
        JFrame window = new JFrame("2D_Miner");
        window.setDefaultCloseOperation(EXIT_ON_CLOSE);
        new UI();
    }
}