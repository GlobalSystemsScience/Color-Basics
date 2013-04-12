package colorbasics;
import javax.swing.*;
//import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;

public class CheckColor extends JPanel{
  
  final String topText = "<html><center><font size = +1>" +
    "You should see 81 distinct color tiles in the image below --" +
    "<br>9 distinct rows and 9 distinct columns." +
    "</font></center>";
  final String bottomText = "<html><font size = +0>" +
    "If not, you won't be able to see all of the color's of the activities, and" +
    "<br>the activities may be misleading or confusing.  Try to find a computer" +
    "<br>display that has the needed color reproduction." +
    "</font>";
  
  public CheckColor(JTabbedPane tabbedPane, JComponent checkColor, int width, int height){
    System.out.println("In CheckColor");
    tabbedPane.addTab("Check Display's Color", checkColor);
    tabbedPane.setMnemonicAt(6, KeyEvent.VK_7);
    checkColor.setLayout(new BorderLayout());
    checkColor.setBackground(Color.white);
    
    JLabel checkText1 =  new JLabel(topText, JLabel.CENTER);
    checkColor.add(checkText1, BorderLayout.NORTH);
    
    ColorPalette colorBoard = new ColorPalette(270, 0, 50, 50);
    colorBoard.setBounds(50, 50, 50, 50);
    checkColor.add(colorBoard, BorderLayout.CENTER);
    
    JLabel checkText2 =  new JLabel(bottomText, JLabel.CENTER);
    checkColor.add(checkText2, BorderLayout.SOUTH);
  }
}