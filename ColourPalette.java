import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Created by miloshzelembaba on 2017-02-07.
 */
public class ColourPalette extends JPanel {

    private final int rows = 5;
    private final int cols = 3;
    private ArrayList<Color> colors;
    private JButton previousColour = null;
    private JButton currentColour = null;
    private View mainView;

    public ColourPalette(View v){
        super();
        mainView = v;
        setMinimumSize(new Dimension(80,135));
        setMaximumSize(new Dimension(80,135));
        GridLayout gl = new GridLayout(rows,cols);
        gl.setHgap(5);
        gl.setVgap(5);
        this.setLayout(gl);
        this.add(createColorSelector(Color.BLUE));
        this.add(createColorSelector(Color.CYAN));
        this.add(createColorSelector(Color.GREEN));
        this.add(createColorSelector(Color.MAGENTA));
        this.add(createColorSelector(Color.PINK));
        this.add(createColorSelector(Color.ORANGE));
        this.add(createColorSelector(Color.RED));
        this.add(createColorSelector(Color.YELLOW));
        this.add(createColorSelector(Color.CYAN));
        this.add(createColorSelector(Color.BLACK));
        this.add(createColorSelector(Color.DARK_GRAY));
        this.add(createColorSelector(Color.GRAY));
        this.add(createColorSelector(Color.GRAY));
        this.add(createColorSelector(Color.LIGHT_GRAY));
        this.add(createColorSelector(Color.WHITE));

    }

    private JButton createColorSelector(Color color){
        JButton button = new JButton();
        button.setBackground(color);
        button.setBorderPainted(false);
        button.setOpaque(true);
        button.setBorderPainted(true);
        button.setBorder(new LineBorder(Color.DARK_GRAY));

        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                previousColour = currentColour;
                currentColour = button;
                mainView.notifyColorChange(color);
                currentColour.setBorder(new LineBorder(Color.BLACK,2));

                if (previousColour != null){
                    previousColour.setBorder(new LineBorder(Color.DARK_GRAY));
                }
            }
        });

        return button;
    }
}
