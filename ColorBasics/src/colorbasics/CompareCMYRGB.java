package colorbasics;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.lang.*;

public class CompareCMYRGB extends JPanel {
  
    final String COLOR1 = "<html><center>Color 1<br>palette</center>";
    final String COLOR2 = "<html><center>Color 2<br>palette</center>";
    final String MIXED_PIGMENTS = "<html><center>Mixed Pigments</center>";
    final String MIXED_LIGHT = "<html><cente>Mixed Lights</center>";
    final String USES_CMY = "Uses Cyan, Magenta and Yellow (CMY) color space";
    final String USES_RGB = "Uses Red, Green and Blue (RGB) color space:";
    final int CMY_MIXER = 0;
    final int RGB_MIXER = 1;
    final int MAX_INTENSITY = 255;
    
    boolean firstTimeColor1 = true;
    boolean firstTimeColor2 = true;
    Color leftColor = new Color(222, 222, 222);
    Color cmyColor = leftColor;
    Color rgbColor = leftColor;
    Color rightColor = leftColor;
    ColorBar cmyColorBar;
    ColorBar rgbColorBar;

    public CompareCMYRGB() {
        setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
        
        JPanel leftPanel = new ColorButtons(COLOR1, Color.RED);
        add(leftPanel);

        JPanel centerPanel = new ColorMixer(MIXED_PIGMENTS, USES_CMY, MIXED_LIGHT, USES_RGB);
        add(centerPanel);

        JPanel rightPanel = new ColorButtons(COLOR2, Color.BLUE);
        add(rightPanel);     
    }

    public class ColorButtons extends JPanel {
      final String[] buttonColorNames = {"Red", "Yellow", "Green", "Cyan", "Blue", "Magenta"};
      final Color[] buttonColors = {Color.red, Color.yellow, Color.green, Color.cyan, Color.blue, Color.magenta};
      
      JLabel colorButtonsLabel;
      
      ColorButtons(String label, Color c) {
        Dimension size = new Dimension(100, 40);
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        colorButtonsLabel = new JLabel(label, SwingConstants.CENTER);
        colorButtonsLabel.setForeground(c);
        add(colorButtonsLabel);
        
        JButton b = null;
        for (int i = 0; i < buttonColorNames.length; i++) {
          b = new JButton(buttonColorNames[i]);
          b.setPreferredSize(size);
          b.setMinimumSize(size);
          b.setMaximumSize(size);
          b.setBackground(buttonColors[i]);
          b.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
              JButton b = (JButton) e.getSource();
              
              if (colorButtonsLabel.getText().compareTo(COLOR1) == 0) {
                firstTimeColor1 = false;
                leftColor = b.getBackground();  // Get button color
              } else {
                firstTimeColor2 = false;
                rightColor = b.getBackground();
              }

              if (firstTimeColor1 || firstTimeColor2) {
                leftColor = b.getBackground();
                rightColor = b.getBackground();
              }

              // Update CMY cmyColorBar
              // Convert RGB to CMY
              int lc = MAX_INTENSITY - leftColor.getRed();
              int lm = MAX_INTENSITY - leftColor.getGreen();
              int ly = MAX_INTENSITY - leftColor.getBlue();
              int rc = MAX_INTENSITY - rightColor.getRed();
              int rm = MAX_INTENSITY - rightColor.getGreen();
              int ry = MAX_INTENSITY - rightColor.getBlue();
              // Mix colors
              int c = lc + rc;
              int m = lm + rm;
              int y = ly + ry;
              int maxPigment = Math.max(c, Math.max(m, y));
              c = c * MAX_INTENSITY / maxPigment;
              m = m * MAX_INTENSITY / maxPigment;
              y = y * MAX_INTENSITY / maxPigment;
              // Convert CMY to RGB
              int red = MAX_INTENSITY - c;
              int green = MAX_INTENSITY - m;
              int blue = MAX_INTENSITY - y;
              cmyColor = new Color(red, green, blue);
              cmyColorBar.repaint();

              // Update RGB rgbColorBar
              red = leftColor.getRed() + rightColor.getRed();
              green = leftColor.getGreen() + rightColor.getGreen();
              blue = leftColor.getBlue() + rightColor.getBlue();
              int maxLight = Math.max(red, Math.max(green, blue));
              red = red * MAX_INTENSITY / maxLight;
              green = green * MAX_INTENSITY / maxLight;
              blue = blue * MAX_INTENSITY / maxLight;
              rgbColor = new Color(red, green, blue);
              rgbColorBar.repaint();
            }
          });
          add(b);
        }
      }
      
    }
          
    public class ColorBar extends JPanel {
      final int VERTICAL_BORDER = 20;
      final int HORIZONTAL_BORDER = 5;
      int xPos;
      int yPos;
      int width;
      int height;
      int mixer;
   
      ColorBar(int xPos, int yPos, int w, int h, int m) {
//      System.out.println("ColorPalette constructor is called");
        this.xPos = xPos;
        this.yPos = yPos;
        width = w;
        height = h;
        mixer = m;
        int panelWidth = (2 * VERTICAL_BORDER) + (3 * width);
        int panelHeight = (2 * HORIZONTAL_BORDER) + height;
        Dimension size = new Dimension(panelWidth, panelHeight);
        setPreferredSize(size);
        setMinimumSize(size);
        setMaximumSize(size);
        setBounds(xPos, yPos, panelWidth, panelHeight);
      }
  
      public void paint( Graphics g ) {
        g.setColor(Color.BLACK);
        g.drawRect(VERTICAL_BORDER - 1, HORIZONTAL_BORDER - 1, width * 3 + 1, height + 1);

        g.setColor(leftColor);        
        g.fillRect(VERTICAL_BORDER, HORIZONTAL_BORDER, width, height);
        
        if (mixer == CMY_MIXER) {
          g.setColor(cmyColor);
        } else {
          g.setColor(rgbColor);
        }
        g.fillRect(width + VERTICAL_BORDER, HORIZONTAL_BORDER, width, height);
        
        g.setColor(rightColor);
        g.fillRect(width * 2 + VERTICAL_BORDER, HORIZONTAL_BORDER, width, height);        
      }
      
    }
    
    public class ColorMixer extends JPanel {
      
      ColorMixer(String cmyString, String cmyText, String rgbString, String rgbText) {
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        JPanel cmyLabel = new JPanel();
        cmyLabel.setLayout(new BorderLayout());
        JLabel cmyWest = new JLabel("Color 1");
        JLabel cmyCenter = new JLabel(cmyString, SwingConstants.CENTER);
        JLabel cmyEast = new JLabel("Color 2");
        cmyCenter.setFont(new Font("Arial", Font.BOLD, 16));
        cmyWest.setForeground(Color.RED);
        cmyEast.setForeground(Color.BLUE);
        cmyLabel.add(cmyWest, BorderLayout.WEST);
        cmyLabel.add(cmyCenter, BorderLayout.CENTER);
        cmyLabel.add(cmyEast, BorderLayout.EAST);
        add(cmyLabel);

        cmyColorBar = new ColorBar(0, 0, 150, 150, CMY_MIXER);
        add(cmyColorBar);
       
        JLabel bl1 = new JLabel(cmyText);
        bl1.setFont(new Font("Arial", Font.PLAIN, 10));
        bl1.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(bl1);

        add(Box.createRigidArea(new Dimension(20, 20)));
        
        JPanel rgbLabel = new JPanel();
        rgbLabel.setLayout(new BorderLayout());
        JLabel rgbWest = new JLabel("Color 1");
        JLabel rgbCenter = new JLabel(rgbString, SwingConstants.CENTER);
        JLabel rgbEast = new JLabel("Color 2");
        rgbCenter.setFont(new Font("Arial", Font.BOLD, 16));
        rgbWest.setForeground(Color.RED);
        rgbEast.setForeground(Color.BLUE);
        rgbLabel.add(rgbWest, BorderLayout.WEST);
        rgbLabel.add(rgbCenter, BorderLayout.CENTER);
        rgbLabel.add(rgbEast, BorderLayout.EAST);
        add(rgbLabel);

        rgbColorBar = new ColorBar(0, 0, 150, 150, RGB_MIXER);
        add(rgbColorBar);
       
        JLabel bl2 = new JLabel(rgbText, SwingConstants.LEFT);
        bl2.setFont(new Font("Arial", Font.PLAIN, 10));
        bl2.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(bl2);
      }
    }
}

