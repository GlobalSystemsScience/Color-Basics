package colorbasics;
import javax.swing.*;
//import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;

public class About extends JPanel {
  
//  private JTabbedPane tabPane = null;
  
  public About(JTabbedPane tabbedPane, JComponent about, int width, int height){
    System.out.println("In About");
//    JComponent about = makeTextPanel("About");
//    tabPane = tabbedPane;
    tabbedPane.addTab("About", about);
    tabbedPane.setMnemonicAt(7, KeyEvent.VK_8);
    
    JTextArea aboutTextArea = new JTextArea();
    aboutTextArea.setColumns(20);
    aboutTextArea.setLineWrap(true);
    aboutTextArea.setRows(5);
    aboutTextArea.setWrapStyleWord(true);
//    aboutTextArea.setText("The original software components were created by John Pickle and Jacqueline Kirtley, Museum of Science, Boston MA in support of the Lawrence Hall of Science's Global Systems Science student series in 2002. \n\n The revisions were created to support the NASA-funded project, Digital Earth Watch, DEW, (originally named Measuring Vegetation Health).  This educational project is a collaboration between seven institutions (logos have rollover urls) to develop learning activities, technologies, and software to measure environmental health by monitoring plants: \n\n -Museum of Science, Boston, MA (lead institution) \n-Global Systems Science, Lawrence Hall of Science, Berkeley, CA (co-lead)\n-Forest Watch, University of New Hampshire, Durham, NH (co-lead)\n-EOS-Webster, University of New Hampshire, Durham, NH-Remote Sensing and GIS Laboratory, Indiana State University, Terre Haute, IN \n-Blue Hill Observatory, Milton, MA-College of Education and Human Development, University of Southern Maine, Portland, ME \n\nJohn Pickle programmed these revisions, which reflect invaluable feedback and input from the DEW team and years of working with teachers and informal science educators. \n\nThis software may be freely copied and used for all educational applications. \n\nCopyright 2003, 2009 by the Regents of the University of California \nCopyright 2007, 2008 Museum of Science, Boston, MA. \nVersion 3.0.1 created October 22, 2009 using Real Basic 2009 Release 2.1");
    aboutTextArea.setText("The original software components were created by John Pickle and " +
      "Jacqueline Kirtley, Museum of Science, Boston MA in support of the Lawrence Hall of " +
      "Science's Global Systems Science student series in 2002. \n\nThe revisions were created " +
      "to support the NASA-funded project, Digital Earth Watch, DEW, (originally named Measuring " +
      "Vegetation Health). This educational project is a collaboration between seven institutions " +
      "(logos have rollover urls) to develop learning activities, technologies, and software " +
      "to measure environmental health by monitoring plants: \n\n -Museum of Science, Boston, " +
      "MA (lead institution) \n-Global Systems Science, Lawrence Hall of Science, Berkeley, " +
      "CA (co-lead)\n-Forest Watch, University of New Hampshire, Durham, NH (co-lead)\n" +
      "-EOS-Webster, University of New Hampshire, Durham, NH-Remote Sensing and GIS " +
      "Laboratory, Indiana State University, Terre Haute, IN \n-Blue Hill Observatory, Milton, " +
      "MA-College of Education and Human Development, University of Southern Maine, Portland, " +
      "ME \n\nJohn Pickle programmed these revisions, which reflect invaluable feedback and " +
      "input from the DEW team and years of working with teachers and informal science educators." +
      "\n\nThis software may be freely copied and used for all educational applications." +
      "\n\nCopyright 2003, 2009 by the Regents of the University of California \nCopyright " +
      "2007, 2008 Museum of Science, Boston, MA. \nVersion 3.0.1 created October 22, 2009 " +
      "using Real Basic 2009 Release 2.1");
    Font font = new Font("Arial", Font.PLAIN, 14);
    aboutTextArea.setFont(font);
    aboutTextArea.setEditable(false);
//    aboutTextArea.setBounds(0, 0, 500, 600);
    aboutTextArea.setBounds(20, 20, 480, 600);
    about.add(aboutTextArea);
    
    about.setBackground(Color.white);
    
    ShowImage pictureMVH = new ShowImage("docs/cb/images/MVH.bmp", 200, 100);
    pictureMVH.setBounds(width*2/3, 0, 200, 100);
    pictureMVH.setVisible(true);
    about.add(pictureMVH);
    
    ShowImage pictureMoS = new ShowImage("docs/cb/images/MoS.jpg", 200, 100);
    pictureMoS.setBounds(width/2 + 10, 130, 200, 100);
    pictureMoS.setVisible(true);
    about.add(pictureMoS);
    
    ShowImage pictureFW = new ShowImage("docs/cb/images/fw.gif", 125, 125);
    pictureFW.setBounds(width/2 + 40, 240, 125, 125);
    pictureFW.setVisible(true);
    about.add(pictureFW);
    
    ShowImage pictureISU = new ShowImage("docs/cb/images/isu.bmp", 155, 55);
    pictureISU.setBounds(width/2 + 25, 370, 155, 55);
    pictureISU.setVisible(true);
    about.add(pictureISU);
    
    ShowImage pictureUSM = new ShowImage("docs/cb/images/usm.bmp", 78, 96);
    pictureUSM.setBounds(width/2 + 60, 440, 78, 96);
    pictureUSM.setVisible(true);
    about.add(pictureUSM);
    
    ShowImage pictureGSS = new ShowImage("docs/cb/images/gss.gif", 155, 145);
    pictureGSS.setBounds(width/2 + 255, 110, 155, 145);
    pictureGSS.setVisible(true);
    about.add(pictureGSS);
    
    ShowImage pictureEOS = new ShowImage("docs/cb/images/eos.gif", 168, 137);
    pictureEOS.setBounds(width/2 + 255, 260, 168, 137);
    pictureEOS.setVisible(true);
    about.add(pictureEOS);
    
    ShowImage pictureBHO= new ShowImage("docs/cb/images/bho.jpg", 150, 150);
    pictureBHO.setBounds(width/2 + 255, 400, 150, 150);
    pictureBHO.setVisible(true);
    about.add(pictureBHO);
  }
}