package colorbasics;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;

public class MakeColors extends JPanel implements ActionListener {
  
  JComponent mc;
  
  final int RGB = 0;
  final int CMY = 1;
  final int HSV = 2;
  final int MAX_INTENSITY = 255;
  final String RGB_S = "RGB";
  final String CMY_S = "CMY";
  final String HSV_S = "HSV";
      
  final ShowImage pictureRGB = new ShowImage("docs/cb/images/8as.jpg", 150, 150);
  final ShowImage pictureCMY = new ShowImage("docs/cb/images/8bs.jpg", 150, 150);
  final ShowImage pictureHSV = new ShowImage("docs/cb/images/8cs.jpg", 150, 150);
    
  final String headline = "<html><center><font size = +1>" +
    "Explore how millions of colors are created by adjusting the intensities of" +
    "<br>Red/Green/Blue(RGB), Cyan/Magenta/Yellow (CMY), or Hue/Saturation/Value (HSV) below." +
    "</font></center>";
  
  final String midComment = "<html><left><font size = +0><dl>" +
    "<dt>To create colors, type the intensity of each color component in the three white boxes below, or use the arrow buttons to change intensities.  " +
    "Intensities range from 0 to 100%.  " +
    "<dd>0% means that there is no contribution of that color component." +
    "<dd>100% means that there is maximum contbution fo the color component.  " +
    "</dl></font></left><html>";  
  final String applyS = "<html><center><b>Apply New Intensities</center><html>";
  final String saveS = "<html><center><b>Save Color</center><html>";
  final String explanationS = "<html><center>Save colors that are interesting," +
    "<br>surprising, a favorite, or least" +
    "<br>favorite. Save up to 10 colors.</center></html>";
  final String clearS = "<html><center><b>Clear Colors</center><html>";
  final String yourSavedColorS = "<html><center><b>Your Saved Colors</b></center></html>";
  final String resultingColorS = "<html><center><b>Resulting<br>Color</b></center></html>";

  final int COLUMNS = 12;
  final int ROWS = 15;
  final int columns[] = {0, 20, 100, 190, 240, 590, 610, 690, 770, 850, 930, 1010};
  int columnW[] = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
  final int rows[] = {130, 170, 200, 230, 280, 330, 380, 430, 480, 530, 580, 630, 680, 730, 780};
  int rowH[] = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
  
  // To be change when color space changes
  int colorSpace = RGB;
  JLabel r1Label = new JLabel("Red", SwingConstants.CENTER);
  JLabel r2Label = new JLabel("Green", SwingConstants.CENTER);
  JLabel r3Label = new JLabel("Blue", SwingConstants.CENTER);
  
  final int MAX_SAVED_INDEX = 10;
  int savedIndex = 0;
  JButton c1JButton[] = new JButton[MAX_SAVED_INDEX];
  JButton c2JButton[] = new JButton[MAX_SAVED_INDEX];
  JButton c3JButton[] = new JButton[MAX_SAVED_INDEX];
  JButton resultColor[] = new JButton[MAX_SAVED_INDEX];
  
  JLabel resultDisplay = new JLabel();
  ColorSpaceSelector spaceSelector;
  ColorComposer colorComposer;
  
  public MakeColors(JTabbedPane tabbedPane, JComponent makeColors, int width, int height) {
    System.out.println("In MakeColors");
    
    mc = makeColors;
  
    tabbedPane.addTab("Make Colors", makeColors);
    tabbedPane.setMnemonicAt(2, KeyEvent.VK_2);
    
    
    JLabel makeColorHeadline =  new JLabel(headline);
//    makeColorHeadline.setBounds(width/4 - 200, 10, width/2 + 250, 50);
    makeColorHeadline.setBounds(20, 10, width * 3 / 4 - 20, 60);
    makeColors.add(makeColorHeadline);

    JLabel makeColorMidComment =  new JLabel(midComment);
//    makeColorMidComment.setBounds(width/4 - 200, 60, width/2 + 250, 100);
    makeColorMidComment.setBounds(40, 70, width * 3 / 4 - 20, 100);
    makeColors.add(makeColorMidComment);
    
    pictureHSV.setBounds(width * 3 / 4 + 20, 10, 150, 150);
    pictureHSV.setVisible(false);
    makeColors.add(pictureHSV);
    pictureCMY.setBounds(width * 3 / 4 + 20, 10, 150, 150);
    pictureCMY.setVisible(false);
    makeColors.add(pictureCMY);
    pictureRGB.setBounds(width * 3 / 4 + 20, 10, 150, 150);
    makeColors.add(pictureRGB);
    
    for (int i = 0; i < COLUMNS - 1; i++) {
      columnW[i] = columns[i + 1] - columns[i];
    }
    
    for (int i = 0; i < ROWS - 1; i++) {
      rowH[i] = rows[i + 1] - rows[i];
    }
    
    spaceSelector = new ColorSpaceSelector();
    spaceSelector.setMakeColors(this);
    spaceSelector.setBounds(columns[4], rows[1], columnW[4], rowH[0] + rowH[1]);
    makeColors.add(spaceSelector);
    
    // Uses JLabel c1Label, c2Label, c3Label
    JButton b1 = new JButton(applyS);
    JButton b2 = new JButton(saveS);
    JLabel e1 = new JLabel(explanationS, SwingConstants.CENTER);
    JButton b3 = new JButton(clearS);
    
    b1.setBounds(columns[1], rows[6] + 10, columnW[1] + columnW[2] + columnW[3], rowH[6] - 20);
    b1.addActionListener(this);
    makeColors.add(b1);
    b2.setBounds(columns[1], rows[7] + 10, columnW[1] + columnW[2] + columnW[3], rowH[7] - 20);
    b2.addActionListener(this);
    makeColors.add(b2);
    e1.setBounds(columns[1], rows[8], columnW[1] + columnW[2] + columnW[3], rowH[8]);
    makeColors.add(e1);
    b3.setBounds(columns[1], rows[9] + 10, columnW[1] + columnW[2] + columnW[3], rowH[9] - 20);
    b3.addActionListener(this);
    makeColors.add(b3);
    
    // Use resultDisplay
    // Use disabled button to draw result color rectangles
//    JButton resultDisplay = new JButton("");
    resultDisplay.setOpaque(true);
    resultDisplay.setBackground(Color.WHITE);
    resultDisplay.setBounds(columns[4] + 10, rows[3] + 10, columnW[4], columnW[4]);
    makeColors.add(resultDisplay);
    
    colorComposer = new ColorComposer(resultDisplay);
    colorComposer.setBounds(columns[1], rows[1], 250, 200);
    makeColors.add(colorComposer);
    
    JLabel yourSavedColorL = new JLabel(yourSavedColorS, SwingConstants.CENTER);
    int w = columnW[5] + columnW[6] + columnW[7] + columnW[8] + columnW[9];
    yourSavedColorL.setBounds(columns[5], rows[1], w, rowH[1]);
    makeColors.add(yourSavedColorL);
    
    // Uses JLabel r1Label, r2Label, r3Label
    JLabel resultingColorL = new JLabel(resultingColorS, SwingConstants.CENTER);
    r1Label.setBounds(columns[6], rows[2], columnW[6], rowH[2]);
    r2Label.setBounds(columns[7], rows[2], columnW[7], rowH[2]);
    r3Label.setBounds(columns[8], rows[2], columnW[8], rowH[2]);
    resultingColorL.setBounds(columns[9], rows[2], columnW[9], rowH[2]);
    makeColors.add(r1Label);
    makeColors.add(r2Label);
    makeColors.add(r3Label);
    makeColors.add(resultingColorL);
    
    initSavedResult();
    updateColorSpace(RGB);
//    updateColor();
  }
  
  private void initSavedResult() { 
    final int h = 30;
    final int indent = 5;
    final int space = 2 * indent;
    int rowY;
    Font f = new Font("Arial", Font.BOLD, 10);
    
    for (int i = 0; i < MAX_SAVED_INDEX; i++) {
      rowY = rows[3] + (h + space) * i;
      c1JButton[i] = new JButton("0%");
      c1JButton[i].setFont(f);
      c1JButton[i].setBounds(columns[6] + indent, rowY, columnW[6] - space, h);
      c1JButton[i].setBackground(Color.RED);
      c1JButton[i].setForeground(Color.WHITE);
      c1JButton[i].setEnabled(false);
      c1JButton[i].setVisible(false);
      mc.add(c1JButton[i]);
      c2JButton[i] = new JButton("0%");
      c2JButton[i].setFont(f);
      c2JButton[i].setBounds(columns[7] + indent, rowY, columnW[7] - space, h);
      c2JButton[i].setBackground(Color.BLACK);
      c2JButton[i].setForeground(Color.WHITE);
      c2JButton[i].setEnabled(false);
      c2JButton[i].setVisible(false);
      mc.add(c2JButton[i]);
      c3JButton[i] = new JButton("0%");
      c3JButton[i].setFont(f);
      c3JButton[i].setBounds(columns[8] + indent, rowY, columnW[8] - space, h);
      c3JButton[i].setBackground(Color.BLACK);
      c3JButton[i].setForeground(Color.WHITE);
      c3JButton[i].setEnabled(false);
      c3JButton[i].setVisible(false);
      mc.add(c3JButton[i]);
      resultColor[i] = new JButton("");       
      resultColor[i].setBounds(columns[9] + indent, rowY, columnW[9] - space, h);
      resultColor[i].setBackground(Color.BLACK);
      resultColor[i].setEnabled(false);
      resultColor[i].setVisible(false);
      mc.add(resultColor[i]);
    }
  }
  
  private void displaySavedResult(int index) {
    String percent;
    
    c1JButton[index].setBackground(colorComposer.getColorC1());
    percent = Integer.toString(colorComposer.getColor1());
    percent = percent.concat("%");
    c1JButton[index].setText(percent);
    c1JButton[index].setVisible(true);
    
    c2JButton[index].setBackground(colorComposer.getColorC2());
    percent = Integer.toString(colorComposer.getColor2());
    percent = percent.concat("%");
    c2JButton[index].setText(percent);
    c2JButton[index].setVisible(true);
    
    c3JButton[index].setBackground(colorComposer.getColorC3());
    percent = Integer.toString(colorComposer.getColor3());
    percent = percent.concat("%");
    c3JButton[index].setText(percent);
    c3JButton[index].setVisible(true);
    
    resultColor[index].setBackground(resultDisplay.getBackground());
    resultColor[index].setVisible(true);
  }
  
  public void updateColorSpace(int cs) {
    spaceSelector.selectButton(cs);
    colorComposer.updateColorSpace(cs);
    colorSpace = cs;
    switch (cs) {
      case RGB:
        pictureCMY.setVisible(false);
        pictureHSV.setVisible(false);
        pictureRGB.setVisible(true);
        break;
      case CMY:
        pictureRGB.setVisible(false);
        pictureHSV.setVisible(false);
        pictureCMY.setVisible(true);
        break;
      case HSV:
        pictureRGB.setVisible(false);
        pictureCMY.setVisible(false);
        pictureHSV.setVisible(true);
        break;
    }
//    updateColor();
  }
  
  public void actionPerformed(ActionEvent e) {
    JButton b = (JButton) e.getSource();
    String text = b.getText();
    if (text.indexOf("Apply", 0) != -1) {
      // This automatically invoke JSpinner ChangeListener
      colorComposer.applyColor();
    } else if (text.indexOf("Save", 0) != -1) {
      displaySavedResult(savedIndex);
      if (savedIndex < MAX_SAVED_INDEX - 1) {
        savedIndex++;
      }
    } else if (text.indexOf("Clear", 0) != -1) {
      for (int i = 0; i < MAX_SAVED_INDEX; i++) {
        c1JButton[i].setVisible(false);
        c2JButton[i].setVisible(false);
        c3JButton[i].setVisible(false);
        resultColor[i].setVisible(false);
      }
      savedIndex = 0;
    }
  }
  
}