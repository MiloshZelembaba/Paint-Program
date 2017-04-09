

import javax.swing.*;
import java.awt.*;

/**
 * Created by miloshzelembaba on 2017-02-08.
 */
public class Thickness extends JPanel {

    public int size = 50;
    public Color color = Color.BLACK;


    public Thickness(){
        super();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(color);
        g.fillOval(25 - size/2, 25 -size/2, size, size);
    }
}
