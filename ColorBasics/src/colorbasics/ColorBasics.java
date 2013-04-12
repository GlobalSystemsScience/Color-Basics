package colorbasics;

import javax.swing.*;
import java.awt.*;

//public class TabbedPaneDemo extends JPanel{
public class ColorBasics extends JPanel{
  
  static final int FRAME_WIDTH = 1000;
  private static final int FRAME_HEIGHT = 700;
  private JTabbedPane tabbedPane = new JTabbedPane();
  public static JFrame frame = new JFrame("Basics of Color");
  
//  private BufferedImage image;
  
  public ColorBasics() {
    super(new BorderLayout());
    
    new Intro(tabbedPane, makeTextPanel("Intro"), FRAME_WIDTH, FRAME_HEIGHT);
    new CompareColors(tabbedPane, makeTextPanel("Compare Colors"), FRAME_WIDTH, FRAME_HEIGHT);
    new MakeColors(tabbedPane, makeTextPanel("Make Colors"), FRAME_WIDTH, FRAME_HEIGHT);
    new PlayWithColors(tabbedPane, makeTextPanel("Play With Colors"), FRAME_WIDTH, FRAME_HEIGHT);
    new TestYourself(tabbedPane, makeTextPanel("Test Yourself"), FRAME_WIDTH, FRAME_HEIGHT);
    new ColorSpace(tabbedPane, makeTextPanel("Color Space"), FRAME_WIDTH, FRAME_HEIGHT);
    new CheckColor(tabbedPane, makeTextPanel("Check Color"), FRAME_WIDTH, FRAME_HEIGHT);
    new About(tabbedPane, makeTextPanel("About"), FRAME_WIDTH, FRAME_HEIGHT);
    //Add the tabbed pane to this panel.
    add(tabbedPane);
    
    //The following line enables to use scrolling tabs.
    tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
    
  }
  
  public JComponent makeTextPanel(String text) {
    JPanel panel = new JPanel(false);
    JLabel filler = new JLabel(text);
    filler.setHorizontalAlignment(JLabel.CENTER);
    panel.setLayout(null);
    panel.add(filler);
    return panel;
  }
  
  /**
   * Create the GUI and show it.  For thread safety,
   * this method should be invoked from
   * the event dispatch thread.
   */
  private static void createAndShowGUI() {
    //Create and set up the window.
    //JFrame frame = new JFrame("Basics of Color");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    
    //Add content to the window.
    frame.add(new ColorBasics(), BorderLayout.CENTER);
    Menu menu = new Menu();
    frame.setJMenuBar(menu.createMenuBar());
    
    //Display the window.
    frame.pack();
    frame.setVisible(true);
    frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
  }
  
  public static void main(String[] args) {
    //Schedule a job for the event dispatch thread:
    //creating and showing this application's GUI.
    SwingUtilities.invokeLater(new Runnable() {
      public void run() {
        //Turn off metal's use of bold fonts
        UIManager.put("swing.boldMetal", Boolean.FALSE);
        createAndShowGUI();
      }
    });
  }
}
