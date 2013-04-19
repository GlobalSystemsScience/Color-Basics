package colorbasics;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;

public class ColorComposer extends JPanel{
  // Object is 255 by 200
  final int RGB = 0;
  final int CMY = 1;
  final int HSV = 2;
  final int MAX_INTENSITY = 255;
  
  final String colorComponentS = "<html><center><b>Color<br>Component</b></center></html>";
  final String intensityS = "<html><center><b>Intensity<br>[%]<b><center></html>";
  final String redS = "<html><center><b>Red</b></center></html>";
  final String greenS = "<html><center><b>Green</b></center></html>";
  final String blueS = "<html><center><b>Blue</b></center></html>";
  final String cyanS = "<html><b>Cyan</b></html>";
  final String magentaS = "<html><b>Magenta</b></html>";
  final String yellowS = "<html><b>Yellow</html>";
  final String hueS = "<html><b>Hue</b></html>";
  final String saturationS = "<html><b>Saturation</b></html>";
  final String valueS = "<html><b>Value</b></html>";
  final String[][][] colorAdvice = {
    { {"Red is fine", "Decrease Red", "Increase Red"},
      {"Green is fine", "Decrease Green", "Increase Green"},
      {"Blue is fine", "Decrease Blue", "Increase Blue"}
    },
    { {"Cyan is fine", "Decrease Cyan", "Increase Cyan"},
      {"Magenta is fine", "Decrease Magenta", "Increase Magenta"},
      {"Yellow is fine", "Decrease Yellow", "Increase Yellow"}
    },
    { {"Hue is fine", "Decrease Hue", "Increase Hue"},
      {"Saturation is fine", "Decrease Saturation", "Increase Saturation"},
      {"Value is fine", "Decrease Value", "Increase Value"}
    }
  };
  final int ADVICE_FINE = 0;
  final int ADVICE_DECREASE = 1;
  final int ADVICE_INCREASE = 2;

  final int COLUMNS = 7;
  final int ROWS = 6;
  final int columns[] = {0, 0, 80, 170, 220, 240, 360};
  final int rows[] = {0, 0, 50, 100, 150, 200};
  int columnW[] = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
  int rowH[] = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
  
  // To be change when color space changes
  int colorSpace = RGB;
  JLabel c1Label = new JLabel(redS, SwingConstants.CENTER);
  JLabel c2Label = new JLabel(greenS, SwingConstants.CENTER);
  JLabel c3Label = new JLabel(blueS, SwingConstants.CENTER);
  
  int color1;
  int color2;
  int color3;
  JLabel c1Display = new JLabel();
  JLabel c2Display = new JLabel();
  JLabel c3Display = new JLabel();
//  JButton resultDisplay = new JButton("");
  Color savedColor;
  boolean isAdvice = false;
  JLabel c1Advice;
  JLabel c2Advice;
  JLabel c3Advice;
  boolean colorIsVisible = true;
  
  final Integer value = new Integer(0); 
  final Integer min = new Integer(0);
  final Integer max = new Integer(100); 
  final Integer step = new Integer(1); 
  final SpinnerNumberModel c1Model = new SpinnerNumberModel(value, min, max, step); 
  final SpinnerNumberModel c2Model = new SpinnerNumberModel(value, min, max, step); 
  final SpinnerNumberModel c3Model = new SpinnerNumberModel(value, min, max, step);
  
  JComponent result;
  
  public ColorComposer(JComponent d) {
    result = d;
    setLayout(null);
    
    for (int i = 0; i < COLUMNS - 1; i++) {
      columnW[i] = columns[i + 1] - columns[i];
    }
    
    for (int i = 0; i < ROWS - 1; i++) {
      rowH[i] = rows[i + 1] - rows[i];
    }

    JLabel colorComponentL = new JLabel(colorComponentS, SwingConstants.CENTER);
    colorComponentL.setBounds(columns[1], rows[1], columnW[1], rowH[1]);
    add(colorComponentL);
    
    JLabel intensityL = new JLabel(intensityS, SwingConstants.CENTER);
    intensityL.setBounds(columns[2], rows[1], columnW[2], rowH[1]);
    add(intensityL);

    c1Label.setBounds(columns[1], rows[2], columnW[1], rowH[2]);
    add(c1Label);
    c2Label.setBounds(columns[1], rows[3], columnW[1], rowH[3]);
    add(c2Label);
    c3Label.setBounds(columns[1], rows[4], columnW[1], rowH[4]);
    add(c3Label);
  
    Integer value = new Integer(0); 
    Integer min = new Integer(0);
    Integer max = new Integer(100); 
    Integer step = new Integer(1); 
    JSpinner c1Spinner = new JSpinner(c1Model);
    JSpinner c2Spinner = new JSpinner(c2Model);
    JSpinner c3Spinner = new JSpinner(c3Model);
    c1Model.addChangeListener(new ChangeListener() {
      public void stateChanged(ChangeEvent e) {
        color1 = c1Model.getNumber().intValue();
        updateColor();
      }
    });
    c2Model.addChangeListener(new ChangeListener() {
      public void stateChanged(ChangeEvent e) {
        color2 = c2Model.getNumber().intValue();
        updateColor();
      }
    });
    c3Model.addChangeListener(new ChangeListener() {
      public void stateChanged(ChangeEvent e) {
        color3 = c3Model.getNumber().intValue();
        updateColor();
      }
    });
    
    c1Spinner.setBounds(columns[2], rows[2] + 10, columnW[2], rowH[2] - 20);
    add(c1Spinner);
    c2Spinner.setBounds(columns[2], rows[3] + 10, columnW[2], rowH[3] - 20);
    add(c2Spinner);
    c3Spinner.setBounds(columns[2], rows[4] + 10, columnW[2], rowH[4] - 20);
    add(c3Spinner);
    
    // Use c1Display, c2Display, c3Display
    // Use disabled button to draw primary color rectangles
    c1Display.setBackground(Color.BLACK);
    c1Display.setOpaque(true);
    c2Display.setBackground(Color.BLACK);
    c2Display.setOpaque(true);
    c3Display.setBackground(Color.BLACK);
    c3Display.setOpaque(true);
    c1Display.setEnabled(false);
    c2Display.setEnabled(false);
    c3Display.setEnabled(false);
    c1Display.setBounds(columns[3] + 20, rows[2] + 10, columnW[3] - 20, rowH[2] - 20);
    add(c1Display);
    c2Display.setBounds(columns[3] + 20, rows[3] + 10, columnW[3] - 20, rowH[3] - 20);
    add(c2Display);
    c3Display.setBounds(columns[3] + 20, rows[4] + 10, columnW[3] - 20, rowH[4] - 20);
    add(c3Display);
  }
  
  public void initialize() {
    color1 = 0;
    color2 = 0;
    color3 = 0;
    if (isAdvice) {
      c1Advice.setText("");
      c2Advice.setText("");
      c3Advice.setText("");
    }
    c1Model.setValue(new Integer(0));
    c2Model.setValue(new Integer(0));
    c3Model.setValue(new Integer(0));
    c1Model.setMaximum(100);
    c2Model.setMaximum(100);
    c3Model.setMaximum(100);
    c1Model.setMinimum(0);
    c2Model.setMinimum(0);
    c3Model.setMinimum(0);
    updateColor();
  }
  
  public void updateColorSpace(int space) {
    if ((space >= RGB) && (space <= HSV) && (space != colorSpace)) {
      colorSpace = space;
      color1 = 0;
      color2 = 0;
      color3 = 0;
      c1Model.setValue(0);
      c2Model.setValue(0);
      c3Model.setValue(0);
      switch (colorSpace) {
        case RGB:
          c1Label.setText(redS);
          c2Label.setText(greenS);
          c3Label.setText(blueS);
          break;
        case CMY:
          c1Label.setText(cyanS);
          c2Label.setText(magentaS);
          c3Label.setText(yellowS);
          break;
        case HSV:
          c1Label.setText(hueS);
          c2Label.setText(saturationS);
          c3Label.setText(valueS);
          break;
      }
      updateColor();
    }
  }
    
  public void updateColor() {
    int c1;
    int c2;
    int c3;
    switch (colorSpace) {
      case RGB:
        c1 = color1 * MAX_INTENSITY / 100;
        c2 = color2 * MAX_INTENSITY / 100;
        c3 = color3 * MAX_INTENSITY / 100;
        c1Display.setBackground(new Color(c1, 0, 0));
        c2Display.setBackground(new Color(0, c2, 0));
        c3Display.setBackground(new Color(0, 0, c3));
        savedColor = new Color(c1, c2, c3);
        if (colorIsVisible) {
          result.setBackground(savedColor);
          result.repaint();
        }
        break;
      case CMY:
        // Convert CMY to RGB
        c1 = MAX_INTENSITY - color1 * MAX_INTENSITY / 100;
        c2 = MAX_INTENSITY - color2 * MAX_INTENSITY / 100;
        c3 = MAX_INTENSITY - color3 * MAX_INTENSITY / 100;
        c1Display.setBackground(new Color(c1, MAX_INTENSITY, MAX_INTENSITY));
        c2Display.setBackground(new Color(MAX_INTENSITY, c2, MAX_INTENSITY));
        c3Display.setBackground(new Color(MAX_INTENSITY, MAX_INTENSITY, c3));
        savedColor = new Color(c1, c2, c3);
        if (colorIsVisible) {
          result.setBackground(savedColor);
        }
        break;
      case HSV:
        // Convert HSV to RGB
        HsvToRgb c = new HsvToRgb(color1, 0, 0);
        c1Display.setBackground(new Color(c.getRed(), c.getGreen(), c.getBlue()));
        c = new HsvToRgb(0, color2, 0);
        c2Display.setBackground(new Color(c.getRed(), c.getGreen(), c.getBlue()));
        c = new HsvToRgb(0, 0, color3);
        c3Display.setBackground(new Color(c.getRed(), c.getGreen(), c.getBlue()));
        c = new HsvToRgb(color1, color2, color3);
        savedColor = new Color(c.getRed(), c.getGreen(), c.getBlue());
        if (colorIsVisible) {
          result.setBackground(savedColor);
        }
        break;
    }
    
  }
  
  public Color getColorC1() {
    return(c1Display.getBackground());
  }
  
  public Color getColorC2() {
    return(c2Display.getBackground());
  }
  
  public Color getColorC3() {
    return(c3Display.getBackground());
  }
  
  public Color getColorResult() {
    return(savedColor);
  }
  
  public int getColor1() {
    return(color1);
  }
  
  public int getColor2() {
    return(color2);
  }
  
  public int getColor3() {
    return(color3);
  }
  
  public void applyColor() {
    color1 = c1Model.getNumber().intValue();
    color2 = c2Model.getNumber().intValue();
    color3 = c3Model.getNumber().intValue();
  }
  
  public void showComponentColors(boolean show) {
//    colorIsVisible = show;
    c1Display.setVisible(colorIsVisible);
    c2Display.setVisible(colorIsVisible);
    c3Display.setVisible(colorIsVisible);
  }
 
  public void showResultColor(boolean show) {
    colorIsVisible = show;
    result.setBackground(savedColor);
  }
  
  public void addAdvice() {
    c1Advice = new JLabel("");
    c1Advice.setBounds(columns[5], rows[2], columnW[5], rowH[2]);
    add(c1Advice);

    c2Advice = new JLabel("");
    c2Advice.setBounds(columns[5], rows[3], columnW[5], rowH[3]);
    add(c2Advice);

    c3Advice = new JLabel("");
    c3Advice.setBounds(columns[5], rows[4], columnW[5], rowH[4]);
    add(c3Advice);
    isAdvice = true;
  }
  
  public boolean getAdvice(int difficultyLevel, int sc1, int sc2, int sc3) {
    boolean goodGuess = true;
    if (Math.abs(sc1 - color1) <= difficultyLevel) {
      c1Advice.setText(colorAdvice[colorSpace][0][ADVICE_FINE]);
    } else {
      goodGuess = false;
      if (sc1 < color1) {
        c1Advice.setText(colorAdvice[colorSpace][0][ADVICE_DECREASE]);
      } else {
        c1Advice.setText(colorAdvice[colorSpace][0][ADVICE_INCREASE]);
      }
    }
    
    if (Math.abs(sc2 - color2) <= difficultyLevel) {
      c2Advice.setText(colorAdvice[colorSpace][1][ADVICE_FINE]);
    } else {
      goodGuess = false;
      if (sc2 < color2) {
        c2Advice.setText(colorAdvice[colorSpace][1][ADVICE_DECREASE]);
      } else {
        c2Advice.setText(colorAdvice[colorSpace][1][ADVICE_INCREASE]);
      }
    }
    
    if (Math.abs(sc3 - color3) <= difficultyLevel) {
      c3Advice.setText(colorAdvice[colorSpace][2][ADVICE_FINE]);
    } else {
      goodGuess = false;
      if (sc3 < color3) {
        c3Advice.setText(colorAdvice[colorSpace][2][ADVICE_DECREASE]);
      } else {
        c3Advice.setText(colorAdvice[colorSpace][2][ADVICE_INCREASE]);
      }
    }
        
    return(goodGuess);
  }
     
  public void setValue(int c1, int c2, int c3, int d) {
    int c1X = Math.min(100, c1 + d);
    int c1M = Math.max(0, c1 - d);
    int c2X = Math.min(100, c2 + d);
    int c2M = Math.max(0, c2 - d);
    int c3X = Math.min(100, c3 + d);
    int c3M = Math.max(0, c3 - d);
    c1Model.setMaximum(c1X);
    c1Model.setMinimum(c1M);
    c1Model.setValue(c1);
    c1Advice.setText("");
    c2Model.setMaximum(c2X);
    c2Model.setMinimum(c2M);
    c2Model.setValue(c2);
    c2Advice.setText("");
    c3Model.setMaximum(c3X);
    c3Model.setMinimum(c3M);
    c3Model.setValue(c3);
    c3Advice.setText("");
  }
}