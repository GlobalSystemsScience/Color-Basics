package colorbasics;
import javax.swing.*;
//import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;

public class CompareColors extends JPanel{
  
  final String headline = "<html><center><font size = +1>" +
    "Explore the differences in mixing colors when using light and pigment." +
    "</font></center>";
  
  final String midComment = "<html><left><font size = +0>" +
    "Select two colors by clicking a color in each of the color palettes.  " +
    "The mixed color appears between the two selected colors.  " +
    "Equal portions or intensities of each color are mixed.  " +
    "<p><br>The upper box shows mixing pigments, and the lower box represents mixing light.  " +
    "</font></left>";
  
  final String sideComment = "<html><left><font size = -1.5>" +
    "What is the differenece between pigment and paint?  " +
    "<p><br>Pigment is added to a volume of white paint to create colored paint.  " +
    "Adding pigments creates the colors seen below - mixing colored paints may not produce these colors since the volume of white paint must be considered.  " +
    "</font></left>";
  
  public CompareColors(JTabbedPane tabbedPane, JComponent compareColors, int width, int height){
//    JComponent compareColors = makeTextPanel("Compare Colors");
    System.out.println("In CompareColors");
    compareColors.setLayout(null);
    compareColors.setBackground(Color.white);
    tabbedPane.addTab("Compare Colors", compareColors);
    tabbedPane.setMnemonicAt(1, KeyEvent.VK_2);
    
    JLabel compareColorHeadline =  new JLabel(headline);
//    compareColorHeadline.setBounds(width/4 - 100, 0, width/2 + 100, 40);
    compareColorHeadline.setBounds(100, 0, 600, 40);
    compareColors.add(compareColorHeadline);
    
    JLabel compareColorMidComment =  new JLabel(midComment);
//    compareColorMidComment.setBounds(width/4 - 110, 35, width/2 + 110, 75);
    compareColorMidComment.setBounds(80, 50, 640, 70);
    compareColors.add(compareColorMidComment);
    
    JLabel compareColorSideComment =  new JLabel(sideComment);
    compareColorSideComment.setBounds((width*3)/4, 35, width/4 - 30, 150);
    compareColors.add(compareColorSideComment);
    
    CompareCMYRGB compareCMYRGB = new CompareCMYRGB();
//    compareCMYRGB.setBounds(120, 120, 700, 500);
    compareCMYRGB.setBounds(80, 120, 700, 500);
    compareColors.add(compareCMYRGB);

  }
}