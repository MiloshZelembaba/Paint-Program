import javax.swing.*;
import java.awt.*;

/**
 * Created by miloshzelembaba on 2017-02-10.
 */
public class LineComponent {

    Color color = Color.BLACK;
    int currX, currY, prevX, prevY;
    int size;

    public LineComponent(int currX, int currY, int prevX, int prevY){
        this.currX = currX;
        this.currY = currY;
        this.prevX = prevX;
        this.prevY = prevY;
    }

    public void setColor(Color color){
        this.color = color;
    }

    public Color getColor(){
        return color;
    }

    public void setSize(int size){
        this.size = size;
    }

    public void paintLine(Graphics2D g){
        g.setColor(getColor());

        g.fillOval(currX - size/2,currY - size/2, size, size);

        int iterations = 100;
        double dy = (double)(currY - prevY)/iterations;
        double dx = (double)(currX - prevX)/iterations;

        for (int i=0; i < iterations; ++i){
            double tmpX = prevX + i*dx;
            double tmpY = prevY + i*dy;
            g.fillOval((int)tmpX - size/2,(int)tmpY - size/2, size, size);
        }
    }
}
