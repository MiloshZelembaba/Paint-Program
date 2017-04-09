import java.awt.*;

/**
 * An interface that allows an object to receive updates from the object
 * they listen to.
 */
interface Observer {
    void update(Object observable);
    void lineAddition(int numLines);
    void clear();
    void draw(Image tmp);
}

