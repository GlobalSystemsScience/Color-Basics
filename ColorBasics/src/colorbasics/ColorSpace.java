package colorbasics;
import javax.swing.*;
//import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;

public class ColorSpace extends JPanel{
  int w;
  int h;
  JComponent cs;

  final int RGB = 0;
  final int CMY = 1;
  final int HSV = 2;
  final String RGB_S = "RGB";
  final String CMY_S = "CMY";
  final String HSV_S = "HSV";
//  final ShowImage pictureRGB = new ShowImage("docs/cb/images/8a.jpg", 450, 450);
  final ShowImage pictureRGB = new ShowImage("docs/cb/images/8a.jpg", 450, 450);
  final ShowImage pictureCMY = new ShowImage("docs/cb/images/8b.jpg", 450, 450);
  final ShowImage pictureHSV = new ShowImage("docs/cb/images/8c.jpg", 450, 450);
  
  final String colorSpaceText = "<html><left><font size = +0 color = navy>" +
    "Color spaces are numerical maps of color that help us use color " +
    "efficiently: digital photography, television, computers, vision " +
    "(human and robotic), printing, painting, theater lighting, " +
    "information technology, scanners, etc.  Because of the wide range " +
    "of uses of color, there are many color spaces.  Three common " +
    "color spaces may be explored with this program." +
    "<p><br>Use the buttons on the right to read about each color space and " +
    "to see a map of the color space." +
    "</font></left>";
    
  final String colorSpaceRGBText = "<html><left><font size = +0>" +
    "Red/Green/Blue, or RGB, is the common color space of television screens, computer, screens, digital cameras, and our eyes.  " +
    "It is the color space of additive color: when colors are mixed, the resulting color is lighter than either of the separate colors.  " +
    "The eye is seeing two or more sources of light." +
    "<p><br>With digital technologies, such as computers, intensity value of red, green, blue are integers from 0 to 255.  " + 
    "An intensity value of 50% would be 127 on a computer." +
    "<p><br>On a computer screen, the RGB color space may display 256 x 256 x 256, or 16.78 million colors!  " +
    "But few color spaces can create all colors that the human eye perceives.  " +
    "What colors cannot be created with the RGB color space?" +
    "</font></left>";
  
    final String colorSpaceCMYText = "<html><left><font size= -1>" +
      "Cyan/Magenta/Yellow, or CMY, is often the color space used by printing technologies.  " +
      "It is the color space of subtractive color: when colors are mixed, the resulting color is darker than either of the separate colors.  " +
      "The observed color depends on the color of light reflected from the surface of the dye, pigment, or paint.  " +
      "The other colors are absorbed or subtracted from the light illuminating the dye." +
      "<p><br>Computer printers don't mix the dyes before printing on papers, but rather print small dots of varying intensities of cyan, magenta, and yellow very close each other.  " +
      "Human vision creates one color from the three colored dots." +
      "<p><br> Mixing paints is more complicated than mixing pure dyes since most paints contain a significant volume of white paint.  " +
      "Adding yellow and blue paints won't produce black, as shown in this color space.  " +
      "Some of your experienceswith mixing paint don't carry over to the world of printing." +
      "<p><br>Note that most colors on a computer are based on values from 0-255, and the above values need to be translated to this scale when working with colors on a computer directly." +
      "</font></left>";
    
    final String colorSpaceHSVText = "<html><left><font size = -1>" +
      "Hue/Saturation/Value, or HSV, is a color space that is similar to how a painter mixes black and white to pure pigments to create color." +
      "<p><br>Hue represents color, with each value representing a distinct color along the spectrum, or color wheel, of" +
      "<br>red-yellow-green-cyan-blue-magenta where:" +
      "<p><br><blockquote><font size = -1>Red = 0%, Yellow = 16.7%, Green = 33.3%, " +
      "<br>  Cyan = 50%, Blue = 66.7%, and Magenta = 83.3%." +
      "</blockquote></font><p><br>Saturation is the intensity of the color (0% = no color," +
      "<br><blockquote><font size = -1>100% = maximum intensity of color)." +
      "</blockquote></font><p><br>Value is the range from black (0%) to white (100%)." +
      "<p><br>Notice black and white can be created in any ways with this color space." +
      "<p><br>HSV is the same as Hue/Saturation/Brightness (HSB)." +
      "<p><br>Note that most colors on a computer are based on values from 0-255, and the above values need to be translated to this scale when working with colors on a computer directly." +
      "</font></left>";
    
  JLabel colorSpaceText2 = null;
//  boolean initialSetup = true; 
  ColorSpaceSelector spaceSelector;
  
  public ColorSpace(JTabbedPane tabbedPane, JComponent colorSpace, int width, int height){
    System.out.println("In ColorSpace");
    w = width;
    h = height;
    cs = colorSpace;
    
    tabbedPane.addTab("Color Space", colorSpace);
    tabbedPane.setMnemonicAt(5, KeyEvent.VK_6);
    
    pictureHSV.setBounds(width / 2, 80, width /2, height - 80);
    pictureHSV.setVisible(false);
    colorSpace.add(pictureHSV);
    pictureCMY.setBounds(width / 2, 80, width /2, height - 80);
    pictureCMY.setVisible(false);
    colorSpace.add(pictureCMY);
    pictureRGB.setBounds(width / 2, 80, width /2, height - 80);
    colorSpace.add(pictureRGB);
    
    spaceSelector = new ColorSpaceSelector();
    spaceSelector.setColorSpace(this);
    spaceSelector.setBounds(width / 2, 20, 200, 60);
    colorSpace.add(spaceSelector);
    
    JLabel colorSpaceText1 =  new JLabel(colorSpaceText);
    colorSpaceText1.setBounds(40, 20, width/2 - 80, height/4);
    colorSpace.add(colorSpaceText1);
    colorSpace.setBackground(Color.white);
    
    colorSpaceText2 = new JLabel(colorSpaceRGBText);
    colorSpaceText2.setText(colorSpaceRGBText);
    colorSpaceText2.setBounds(40, 35, w/2 - 80, h);
    colorSpaceText2.setVisible(true);
    cs.add(colorSpaceText2);
  }
  
  public void updateColorSpace(int cs) {
    spaceSelector.selectButton(cs);
    switch (cs) {
      case RGB:
        pictureCMY.setVisible(false);
        pictureHSV.setVisible(false);
        pictureRGB.setVisible(true);
        colorSpaceText2.setText(colorSpaceRGBText);
        break;
      case CMY:
        pictureRGB.setVisible(false);
        pictureHSV.setVisible(false);
        pictureCMY.setVisible(true);
        colorSpaceText2.setText(colorSpaceCMYText);
        break;
      case HSV:
        pictureRGB.setVisible(false);
        pictureCMY.setVisible(false);
        pictureHSV.setVisible(true);
        colorSpaceText2.setText(colorSpaceHSVText);
        break;
    }
  }
  
}