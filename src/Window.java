import java.awt.*;

import javax.swing.*;

public class Window {

    JFrame frame;
    MainCanvas canvas;


    public Window() {
        frame = new JFrame("Window");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.add(frame);
        frame.setLayout(null);
        frame.setPreferredSize(new Dimension(800, 600));
        frame.setResizable(false);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);


        //canvas = new MainCanvas();
        //frame.add(canvas);

    }


}
