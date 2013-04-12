package colorbasics;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.ImageIO;
//import javax.swing.JFrame;
import javax.swing.JPanel; // DT added
public class ShowImage extends JPanel { // Changing Panel to JPanel
    BufferedImage  image;
    int width, height;
  public ShowImage(String imageName, int w, int h) {
    width = w;
    height = h;
    try {
//  System.out.println("Enter image name\n");
//  BufferedReader bf=new BufferedReader(new InputStreamReader(System.in));
//   String imageName=bf.readLine();
  File input = new File(imageName);
      image = ImageIO.read(input);
    } catch (IOException ie) {
      System.out.println("Error:"+ie.getMessage());
    }
  }

  public void paint(Graphics g) {
    g.drawImage( image, 0, 0, width, height, null);
  }

}