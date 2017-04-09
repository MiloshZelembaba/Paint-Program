import javafx.util.Pair;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by miloshzelembaba on 2017-02-10.
 */
public class Line {

    private ArrayList<Pair<LineComponent,Image>> lineSegments = new ArrayList<>();

    public void add(LineComponent segment, Image image){
        lineSegments.add(new Pair<>(segment,image));
    }

    public int size(){
        return lineSegments.size();
    }

    public Pair<LineComponent,Image> get(int pos){
        return lineSegments.get(pos);
    }

    public void removeComponent(int index){
        lineSegments.remove(index);
    }


}
