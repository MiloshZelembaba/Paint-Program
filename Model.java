
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;
import java.util.*;
import java.util.List;

public class Model {
    /** The observers that are watching this model for changes. */
    private List<Observer> observers;
    private ArrayList<Line> lines = new ArrayList<>();
    private boolean unsavedChanges = false;
    public Color color = Color.BLACK;
    public int size = 50;


    public Image getLastImage(){
        Line tmp = lines.get(lines.size() - 1);
        return tmp.get(tmp.size() - 1).getValue();
    }

    public void reset(){
        lines = new ArrayList<>();
    }

    public void fileSaved(){
        unsavedChanges = false;
    }

    public boolean hasUnsavedChanges(){
        return unsavedChanges;
    }

    /**
     * Create a new model.
     */
    public Model() {
        this.observers = new ArrayList();
    }

    /**
     * Add an observer to be notified when this model changes.
     */
    public void addObserver(Observer observer) {
        this.observers.add(observer);
    }

    /**
     * Remove an observer from this model.
     */
    public void removeObserver(Observer observer) {
        this.observers.remove(observer);
    }

    public void redrawPortion(int upto) {
        //undoing = true;
        if (upto == 0) {
            for (Observer observer: observers){
                observer.clear();
            }
            return;
        }
        int numLines = (int) Math.ceil((double) upto / 10.0);
        int drawUpto = getLines().get(numLines - 1).size();
        if (upto % 10 != 0) {
            drawUpto = ((int) (((double) getLines().get(numLines - 1).size()) * (((double) upto % 10) / 10.0)));
        }
        for (Observer observer: observers){
            observer.clear();
        }
        if (drawUpto == 0) {
            drawUpto = 1;
        }
        Image tmp = getLines().get(numLines - 1).get(drawUpto - 1).getValue();
        for (Observer observer: observers){
            observer.draw(tmp);
        }
    }


    public void removeFutureWork(int numLines, int drawUpTo){
        int size = lines.size();
        for (int i = size - 1; i >= numLines; --i){
            lines.remove(i);
        }
        Line tmp = lines.get(numLines - 1);
        Line newLine = new Line();
        for (int i = 0; i < drawUpTo; ++i){
            newLine.add(tmp.get(i).getKey(), tmp.get(i).getValue());
        }

        lines.remove(numLines - 1);
        lines.add(newLine);

    }

    public void add(Line line){
        lines.add(line);
        unsavedChanges = true;
        for (Observer observer: observers){
            observer.lineAddition(lines.size());
        }
    }

    public ArrayList<Line> getLines(){
        return lines;
    }

    public void saveFile(String fileName){
        FileSaver.saveFile(fileName,this);
    }

    public void saveChanges(View tmpView, JFileChooser fileChooser){
        fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new FileNameExtensionFilter("milosh file","milosh"));
        File workingDirectory = new File(System.getProperty("user.dir"));
        fileChooser.setCurrentDirectory(workingDirectory);
        int retrival = fileChooser.showSaveDialog(tmpView);
        if (retrival == JFileChooser.APPROVE_OPTION) {
            try {
                String path = fileChooser.getSelectedFile().getAbsolutePath();
                if (!path.endsWith(".milosh")){
                    path+=".milosh";
                }
                saveFile(path);
                fileSaved();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

}
