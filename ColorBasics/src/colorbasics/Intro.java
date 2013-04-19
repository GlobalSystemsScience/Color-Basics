package colorbasics;
//import javax.swing.JPanel;
import javax.swing.*;
//import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
//import java.io.*;
//import java.util.*;

public class Intro extends JPanel{
  private JTabbedPane tabPane = null;

  public Intro(JTabbedPane tabbedPane, JComponent intro, int width, int height){
    //JComponent intro = makeTextPanel("Intro");
    System.out.println("In Intro");
    tabPane = tabbedPane;
    tabbedPane.addTab("Intro", intro);
    tabbedPane.setMnemonicAt(0, KeyEvent.VK_1);
    JLabel introLabel = new JLabel();
    JButton introButton = new JButton("Information About Color Spaces");
    
    intro.setBackground(Color.white);
    
    ShowImage picture = new ShowImage("docs/cb/images/1a.jpg", 265, 265);
    picture.setBounds(600, 0, width - 250, 265);
    picture.setVisible(true);
    intro.add(picture);
    
    ShowImage picture2 = new ShowImage("docs/cb/images/1b.jpg", 265, 265);
    picture2.setBounds(600, 265, width - 250, 265);
    picture2.setVisible(true);
    intro.add(picture2);
    
    intro.add(introLabel);
    introLabel.add(introButton);
    introButton.setVisible(true);
    
    JTextArea introTextArea = new JTextArea();
    introTextArea.setColumns(20);
    introTextArea.setLineWrap(true);
    introTextArea.setRows(5);
    introTextArea.setWrapStyleWord(true);
    introTextArea.setText("Color is everywhere, and we use it in so many ways.  But how well do you understand it?");
    Font font = new Font("Arial", Font.BOLD, 16);
    introTextArea.setFont(font);
    introTextArea.setEditable(false);
    introTextArea.setBounds(50, 20, 450, 50);
    
    JTextArea introTextArea2 = new JTextArea();
    introTextArea2.setColumns(20);
    introTextArea2.setLineWrap(true);
    introTextArea2.setRows(5);
    introTextArea2.setWrapStyleWord(true);
    introTextArea2.setText("Have you noticed that when you mixed all of the paints in a paint set you get a very dark color?  Yet mix a rainbow of colored light beams and you get white light. \n\n Explore the concepts of color and develop and refine your skills to create and identify color. \n\n Begin with Compare Colors to see how colors mix with paint pigments and light beams. \n\n Then select a color space to explore: \n     Red - Green - Blue (RGB), \n     Cyan - Magenta - Yellow (CMY), or \n     Hue - Saturation - Value (HSV). \n\n Make Colors in the color space to look for patterns, Play with Colors to develop your skills, and finally Test Yourself to see how well you can manipulate the color space. \n\n Details about color spaces are available at Color Info.  If you like to know where you are going before you do it, read this first.  If you like to see how well you figured out what you've explored, read this last.");
    Font font2 = new Font("Arial", Font.PLAIN, 14);
    introTextArea2.setFont(font2);
    introTextArea2.setEditable(false);
    introTextArea2.setBounds(50, 70, 450, 530);
    
    introButton.setBounds(0, height - 110, width, 25);
    intro.add(introButton);
    intro.add(introTextArea);
    intro.add(introTextArea2);
    introButton.addActionListener(new IntroButton());
    
  }
    public class IntroButton implements ActionListener{

      public void actionPerformed(ActionEvent e){
        System.out.println(e.getSource());
        e.getSource();
        tabPane.setSelectedIndex(5);
      }
    }
   
}

