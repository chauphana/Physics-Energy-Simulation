import java.awt.*;
import javax.swing.*;

public class MainCanvas extends Canvas {

    public MainCanvas() {

    }

    @Override
    public void paint(Graphics g) {
        System.out.println("i am drawing!");
        g.drawOval(50,50,10,10);
        g.setColor(Color.RED);
    }

}
