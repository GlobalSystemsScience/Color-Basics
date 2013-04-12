package colorbasics;
import javax.swing.*;
//import javax.swing.event.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;

public class SecretResultDisplay extends JPanel {
 
  final int MAX_INTENSITY = 255;
  final int RGB = 0;
  final int CMY = 1;
  final int HSV = 2;
  private JLabel secret = new JLabel("", JLabel.CENTER);
  private JLabel result = new JLabel("", JLabel.CENTER);
  
  SecretResultDisplay(int w, int h) {
    setLayout(null);
    secret.setBounds(1, 1, w / 2 - 1, h - 2);
    secret.setOpaque(true);
    secret.setBackground(Color.WHITE);
    add(secret);
    result.setBounds(w / 2, 1, w / 2 - 1, h - 2);
    result.setOpaque(true);
    result.setBackground(Color.WHITE);
    add(result);
    setBorder(BorderFactory.createLineBorder(Color.BLACK));
  }
  
  public void initialize() {
    secret.setText("");
    result.setText("");
    secret.setBackground(Color.WHITE);
    result.setBackground(Color.WHITE);
  }
  
  public JComponent getResultDisplay() {
    return(result);
  }
  
  public void setSecretColor(Color c) {
    secret.setBackground(c);
  }
  
  public void setResultColor(Color c) {
    result.setBackground(c);
  }
  
  public Color getSecretColor() {
    return(secret.getBackground());
  }
  
  public void setSecretText(String text) {
    secret.setText(text);
  }
  
  public void setResultText(String text) {
    result.setText(text);
  }
  
  public void paintSecretColor(int colorSpace, int sc1, int sc2, int sc3) {
    int c1;
    int c2;
    int c3;
    
    switch (colorSpace) {
      case RGB:
        c1 = sc1 * MAX_INTENSITY / 100;
        c2 = sc2 * MAX_INTENSITY / 100;
        c3 = sc3 * MAX_INTENSITY / 100;
        secret.setBackground(new Color(c1, c2, c3));
        break;
      case CMY:
        // Convert CMY to RGB
        c1 = MAX_INTENSITY - sc1 * MAX_INTENSITY / 100;
        c2 = MAX_INTENSITY - sc2 * MAX_INTENSITY / 100;
        c3 = MAX_INTENSITY - sc3 * MAX_INTENSITY / 100;
        secret.setBackground(new Color(c1, c2, c3));
        break;
      case HSV:
        // Convert HSV to RGB
        HsvToRgb c = new HsvToRgb(sc1, sc2, sc3);
        secret.setBackground(new Color(c.getRed(), c.getGreen(), c.getBlue()));
    }
  }
}
