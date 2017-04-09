



import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

/**
 * Created by miloshzelembaba on 2017-02-07.
 */
public class Tools extends Box{

    ColourPalette colourPalette;
    public int width = 150;
    public int height = 450;

    public Tools(View v){
        super(BoxLayout.Y_AXIS);

        //this.setSize(width, height);
        this.setMinimumSize(new Dimension(125, 180));
        this.setMaximumSize(new Dimension(125, 180));

        this.add(Box.createVerticalStrut(10));
        Label label = new Label("Color", Label.CENTER);
        label.setMaximumSize(new Dimension(150,20));
        this.add(label);
        this.add(Box.createVerticalStrut(5));
        colourPalette = new ColourPalette(v);
        colourPalette.setBackground(Color.DARK_GRAY);
        Box bl = new Box(BoxLayout.X_AXIS);
        bl.add(colourPalette);
        this.add(bl);

        setVisible(true);
    }


    public void onResize(int width, int height){

    }
}
