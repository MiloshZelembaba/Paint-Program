
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

/**
 * Created by miloshzelembaba on 2017-02-08.
 */
public class Canvas extends JPanel implements MouseListener,MouseMotionListener {

    int currX,currY,prevX,prevY;
    Graphics2D g2;
    Image image;
    LineComponent currentLineComponent = null;
    Line currentLine = null;
    Model model;
    View parentView;
    Image baseImage;
    private boolean undoing = false;



    public Canvas(Model model, View parentView){
        this.parentView = parentView;
        this.model = model;

    }
    @Override
    public void mouseClicked(MouseEvent e){
        currX = e.getX();
        currY = e.getY();


    }

    @Override
    public void mousePressed(MouseEvent e) {
        int upto = parentView.scrubber.getValue();
        int numLines = (int) Math.ceil((double)upto/10.0);
        if (model.getLines() != null && numLines != 0) {
            int drawUpto = model.getLines().get(numLines - 1).size();
            if (upto%10 != 0){
                drawUpto = ((int) (((double)model.getLines().get(numLines-1).size())*(((double)upto%10)/10.0)));
            }
            model.removeFutureWork(numLines, drawUpto);
            parentView.changingMax = true;
//            System.out.println(numLines);
//            System.out.println(model.getLines().size());
            parentView.scrubber.setMaximum((numLines-1)*10);
            parentView.scrubber.setValue((numLines-1)*10);
            parentView.changingMax = false;
        }
        if (undoing){
            undo();
            undoing = false;
        }
        currX = e.getX();
        currY = e.getY();
        currentLine = new Line();
        currentLineComponent = new LineComponent(currX,currY,currX,currY);
        currentLineComponent.setColor(model.color);
        currentLineComponent.setSize(model.size);
        savePreviousImage();
        currentLineComponent.paintLine(g2);
        repaint();
    }

    public void savePreviousImage(){
        Image tmp = image;
        image = createImage(getWidth(), getHeight());
        g2 = (Graphics2D) image.getGraphics();
        g2.setBackground(Color.white);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.drawImage(tmp, 0, 0, null);
        currentLine.add(currentLineComponent, tmp);
    }

    @Override
    public void mouseReleased(MouseEvent e){
        model.add(currentLine);
    }

    @Override
    public void mouseEntered(MouseEvent e){} // TODO: create new line here too?

    @Override
    public void mouseExited(MouseEvent e){} // TODO: create new line here too?

    @Override
    public void mouseDragged(MouseEvent e){
        prevX = currX;
        prevY = currY;

        currX = e.getX();
        currY = e.getY();

        currentLineComponent = new LineComponent(currX,currY,prevX,prevY);
        currentLineComponent.setColor(model.color);
        currentLineComponent.setSize(model.size);
        savePreviousImage();
        currentLineComponent.paintLine(g2);
        repaint();

    }

    @Override
    public void mouseMoved(MouseEvent e){

    }

    public void clear(){
        g2.setColor(Color.WHITE);
        g2.fillRect(0, 0, getWidth(), getHeight());
        g2.setColor(model.color);
        repaint();
    }

    public void onResize() {
        Image oldImage = image;
        image = createImage(getWidth(), getHeight());
        g2 = (Graphics2D) image.getGraphics();
        g2.setBackground(Color.white);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        //clear();
        if (oldImage != null){
            g2.drawImage(oldImage.getScaledInstance(getWidth(), getHeight(), Image.SCALE_FAST), 0, 0, null);
        }
    }

    public void setImage(Image img) {
        g2.drawImage(img,0,0,null);
        baseImage = img;
    }

    public void undo(){

    }

    public void draw(Image image){
        this.image = createImage(getWidth(), getHeight());
        g2 = (Graphics2D) this.image.getGraphics();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.drawImage(image.getScaledInstance(getWidth(), getHeight(), Image.SCALE_FAST), 0, 0, null);
    }

    public void redrawPortion(int upto) {
        //clear();
        undoing = true;
        image = createImage(getWidth(), getHeight());
        g2 = (Graphics2D) image.getGraphics();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        if (upto == 0) {
            clear();
            return;
        }
        int numLines = (int) Math.ceil((double) upto / 10.0);
        int drawUpto = model.getLines().get(numLines - 1).size();
        if (upto % 10 != 0) {
            drawUpto = ((int) (((double) model.getLines().get(numLines - 1).size()) * (((double) upto % 10) / 10.0)));
        }
        clear();
        if (drawUpto == 0) {
            drawUpto = 1;
        }
        Image tmp = model.getLines().get(numLines - 1).get(drawUpto - 1).getValue();
        if (tmp != null) {
            g2.drawImage(tmp.getScaledInstance(getWidth(), getHeight(), Image.SCALE_FAST), 0, 0, null);
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (image == null){
            image = createImage(getWidth(), getHeight());
            g2 = (Graphics2D) image.getGraphics();
            g2.setBackground(Color.white);
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            //clear();
        }
        g.drawImage(image, 0,0,null);
    }

}


