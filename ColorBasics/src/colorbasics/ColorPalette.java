package colorbasics;
//import javax.swing.*;
import java.awt.*;
//import javax.swing.JFrame;
import javax.swing.JPanel; // DT added

public class ColorPalette extends JPanel {
  final int STEPS = 9;
  final int MAXINTENSITY = 255;
  int xPos;
  int yPos;
  int width;
  int height;
  
  public ColorPalette(int xPos, int yPos, int w, int h) {
//     System.out.println("ColorPalette constructor is called");
    this.xPos = xPos;
    this.yPos = yPos;
    width = w;
    height = h;
    
    setLayout(null);
  }
  
  public void paint( Graphics g ) {
//    System.out.println("ColorPalette paint is called");
    
    int x = xPos;
    int y = yPos + 20;
    
    // Do Black
    colorColumn(g, x, y, 0, 0, 0, MAXINTENSITY, MAXINTENSITY, MAXINTENSITY); 
    
    // Do Purple
    x = x + width;
    colorColumn(g, x, y, MAXINTENSITY / 2, 0, MAXINTENSITY / 2, MAXINTENSITY, 0, MAXINTENSITY);
    
    // Do Red
    x = x + width;
    colorColumn(g, x, y, MAXINTENSITY / 2, 0, 0, MAXINTENSITY, 0, 0);
    
    // Do Yellow
    x = x + width;
    colorColumn(g, x, y, MAXINTENSITY / 2, MAXINTENSITY / 2, 0, MAXINTENSITY, MAXINTENSITY, 0);
    
    // Do Green
    x = x + width;
    colorColumn(g, x, y, 0, MAXINTENSITY / 2, 0, 0, MAXINTENSITY, 0);
    
    // Do Teal
    x = x + width;
    colorColumn(g, x, y, 0, MAXINTENSITY / 2, MAXINTENSITY / 2, 0, MAXINTENSITY, MAXINTENSITY);
    
    // Do Blue
    x = x + width;
    colorColumn(g, x, y, 0, 0, MAXINTENSITY / 2, 0, 0, MAXINTENSITY);
    
    // Do Brown
    x = x + width;
    colorColumn(g, x, y,
                MAXINTENSITY / 2, MAXINTENSITY / 4, MAXINTENSITY / 8,
                MAXINTENSITY, MAXINTENSITY / 2, MAXINTENSITY / 2);
    
    // Do Grey
    x = x + width;
    colorColumn(g, x, y,
                MAXINTENSITY / 2, MAXINTENSITY / 2, MAXINTENSITY / 2,
                MAXINTENSITY, MAXINTENSITY, MAXINTENSITY);
  }
  
  
  public void colorColumn(Graphics g, int x, int y, 
                          int redStart, int greenStart, int blueStart,
                          int redEnd, int greenEnd, int blueEnd)
  {
    int red = redStart;
    int green = greenStart;
    int blue = blueStart;
    int redStep = (redEnd - redStart) / STEPS;
    int greenStep = (greenEnd - greenStart) / STEPS;
    int blueStep = (blueEnd - blueStart) / STEPS;
    
    for (int i =  0; i < STEPS; i++) {
      red = red + redStep;
      green = green + greenStep;
      blue = blue + blueStep;
      g.setColor(new Color(red, green, blue));
      g.fillRect(x, y + i * height, width, height);
    }
  }
  
}
