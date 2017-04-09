import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by miloshzelembaba on 2017-02-13.
 */
public class FileSaver {

    public static void saveFile(String fileName,Model model){
        BufferedImage image = toBufferedImage(model.getLastImage());
        try {
            FileOutputStream fos = new FileOutputStream(fileName);
            fos.write(toByteArray(image));
            fos.close();
        } catch(Exception e){}
    }

//    public static void main(String[] args) throws IOException {
//        //String path = "images/cougar.jpg";
//        //BufferedImage src = ImageIO.read(new File(path));
//        // Convert Image to BufferedImage if required.
//        BufferedImage image = toBufferedImage(model.getLastImage());
//        save(image, "txt", fileName);  // png okay, j2se 1.4+
//        //save(image, "bmp");  // j2se 1.5+
//        // gif okay in j2se 1.6+
//    }

    public static byte[] toByteArray(BufferedImage img){
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        try {
            ImageIO.write(img, "png", stream);
        } catch (Exception e){
            //do nothing
        }
        return stream.toByteArray();

    }

    private static void save(BufferedImage image, String ext, String fileName) {
        File file = new File("~/cs349/mzelemba/A2/" + fileName + "." + ext);
        try {
            boolean tmp = ImageIO.write(image, ext, file);  // ignore returned boolean
            System.out.println(tmp);
        } catch(IOException e) {
            System.out.println("Write error for " + file.getPath() +
                    ": " + e.getMessage());
        }
    }

    private static BufferedImage toBufferedImage(Image src) {
        int w = src.getWidth(null);
        int h = src.getHeight(null);
        int type = BufferedImage.TYPE_INT_RGB;  // other options
        BufferedImage dest = new BufferedImage(w, h, type);
        Graphics2D g2 = dest.createGraphics();
        g2.drawImage(src, 0, 0, null);
        g2.dispose();
        return dest;
    }

}
