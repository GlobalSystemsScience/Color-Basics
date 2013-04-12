package colorbasics;
import javax.swing.*;
//import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;

// Limited to be used by ColorSpace, MakeColors, PlayWithColors and TestYourself
// class. Caller must also implement and updateColorSpace method.
public class ColorSpaceSelector extends JPanel {
  
  final int RGB = 0;
  final int CMY = 1;
  final int HSV = 2;
  final String RGB_S = "RGB";
  final String CMY_S = "CMY";
  final String HSV_S = "HSV";
  final String[] COLOR_SPACE_NAMES = {RGB_S, CMY_S, HSV_S};
  static ColorSpace colorSpaceO = null;
  static MakeColors makeColorsO = null;
  static PlayWithColors playWithColorsO = null;
  static TestYourself testYourselfO = null;
//  ColorSpace colorSpaceO = null;
//  MakeColors makeColorsO = null;
//  PlayWithColors playWithColorsO = null;
//  TestYourself testYourselfO = null;
  JRadioButton[] b = new JRadioButton[3];
  
  ColorSpaceSelector () {
    ButtonGroup bGroup = new ButtonGroup();
    JPanel radioButtonSelector = new JPanel();
    radioButtonSelector.setLayout(new GridLayout(0, 3));
    ActionListener colorSpaceSelectorListener = new ColorSpaceSelectorListener();
    for (int i = 0; i < COLOR_SPACE_NAMES.length; i++) {
      b[i] = new JRadioButton(COLOR_SPACE_NAMES[i]);
      if (i == 0) {
        b[i].setSelected(true);
      }
      b[i].addActionListener(colorSpaceSelectorListener);
      bGroup.add(b[i]);
      radioButtonSelector.add(b[i]);
    }
    
    radioButtonSelector.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Color Space"));
    radioButtonSelector.setVisible(true);
    radioButtonSelector.setBackground(Color.white);
    add(radioButtonSelector);
    }

  public void setColorSpace(ColorSpace obj) {
    colorSpaceO = obj;
  }
  
  public void setMakeColors(MakeColors obj) {
    makeColorsO = obj;
  }
  
  public void setPlayWithColors(PlayWithColors obj) {
    playWithColorsO = obj;
  }
  
  public void selectButton(int index) {
    b[index].setSelected(true);
  }
  
  public void setTestYourself(TestYourself obj) {
    testYourselfO = obj;
  }

  public class ColorSpaceSelectorListener implements ActionListener {

    public void actionPerformed(ActionEvent e) {
      String colorSpace = e.getActionCommand();
      int c = RGB;
//      System.out.println(colorSpace);
      if (colorSpace.compareTo(RGB_S) == 0) {
        c = RGB;
      } else if (colorSpace.compareTo(CMY_S) == 0) {
        c = CMY;
      } else if (colorSpace.compareTo(HSV_S) == 0) {
        c = HSV;
      }
      if (colorSpaceO != null) {
        colorSpaceO.updateColorSpace(c);
      }
      if (makeColorsO != null) {
        makeColorsO.updateColorSpace(c);
      }
      if (playWithColorsO != null) {
        playWithColorsO.updateColorSpace(c);
      }
      if (testYourselfO != null) {
        testYourselfO.updateColorSpace(c);
      }
    }
  }

}