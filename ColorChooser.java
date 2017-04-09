import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * Created by miloshzelembaba on 2017-02-13.
 */
public class ColorChooser extends JFrame {

    JColorChooser colorChooser = new JColorChooser();
    View v;

    public ColorChooser(View v){
        super("Color Chooser");
        this.v = v;
        //this.setLocation(xPos, yPos);
        this.add(colorChooser);
        //this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(300,300);
        this.setVisible(true);
        setOnClickListener();
    }

    private void setOnClickListener() {
        colorChooser.getSelectionModel().addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                v.notifyColorChange(colorChooser.getColor());
            }
        });
    }
}
