import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by miloshzelembaba on 2017-02-16.
 */
public class FileOpener {

    public static Image openFile(String fileName){
        Path path = Paths.get(fileName);
        byte[] data;
        Image image = new BufferedImage(1,1,BufferedImage.TYPE_3BYTE_BGR);
        try {
            data = Files.readAllBytes(path);
            ImageIcon imageIcon = new ImageIcon(data);
            image = imageIcon.getImage();

        }catch (Exception e){
            System.out.println("Errerr");
        }

        return image;
    }
}
