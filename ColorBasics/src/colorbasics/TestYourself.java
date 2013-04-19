package colorbasics;
import javax.swing.*;
//import javax.swing.event.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.Calendar;
import java.text.SimpleDateFormat;
import java.awt.Component;
import java.io.*;

public class TestYourself extends JPanel implements ActionListener {
  
  final String headline = "<html><font size = +1><left>" +
    "Test your ability to identify 10 randomly selected colors.<left></font></html>";

  final String steps = "<html><font size = +0><ol>" +
    "<li>Select a color space: Red/Green/Blue, Cyan/Magenta/Yellow. or Hue/Saturation/Value." +
    "<li>Press the 'Start' button." +
    "<li>Set your guess color by entering intensity values for the three color components." +
    "<li>Press 'Try Your Guess' to see if you matched the secret color.  A match occurs " +
    "when the three guessed intensities are within 15% of the secret color's." +
    "<li>Repeat until the colors match." +
    "<li>After correctly matching 10 colors, examine your performance in the displayed report." +
    "</ol></font></html>";
  
  final String testGuessS = "<html><font size = +0>Test your guess after setting color intensities<font></html>";
  final String setGuessS = "<html>Set Your Guess</html>";
  final String startS= "Start";
  final String tryYourGuessS = "Try Your Guess";
  final String secretColorS = "Secret Color";
  final String guessedColorS = "Guessed Color";
  final String COLOR_SPACE_S [][] = {{"Red", "Green", "Blue"}, {"Cyan", "Magenta", "Yellow"},
    {"Hue", "Saturation", "Intensity"}};
  
  final int COLUMNS = 10;
  final int ROWS = 10;
  final int MAX_TEST_COUNT = 10;
  final int COLOR_COMPONENTS = 3;
  final int columns[] = {0, 80, 300, 520, 540, 710, 790, 800, 900, 1000};
  int columnW[] = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
  final int rows[] = {10, 30, 170, 200, 240, 440, 480, 500, 550, 580, 700};
  int rowH[] = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
  ColorSpaceSelector spaceSelector;
  SecretResultDisplay secretResultDisplay;
  ColorComposer colorComposer;
  ScoreCard scoreCard;
  JLabel testGuessL;
  int colorSpace;
  int secretColorC1;
  int secretColorC2;
  int secretColorC3;
  int correct = 0;
  int guesses = 0;
  boolean secretColorSet = false;
  boolean newSecretColor = true;
  int difficultyLevel = 15;
//  int testCount;
  int secretColorHistory[][] = new int[MAX_TEST_COUNT][COLOR_COMPONENTS];
  int firstGuessHistory[][] = new int[MAX_TEST_COUNT][COLOR_COMPONENTS];
  String playerName;
  String testLogFileName;
  File testLogFile;
  
  // Used for stats
  final int COMPONENT_COUNT = 3;
  final int STATS_COUNT = 4;
  final int AVERAGE_ERROR = 0;
  final int UNDER = 1;
  final int CORRECT = 2;
  final int OVER = 3;
  final int DOMINANT = 1;
  final int INTERMEDIATE = 2;
  final int LEAST = 3;

  PrintWriter printWriter;
  Calendar cal;
  SimpleDateFormat formatter;
  String currentDateTime;
  int currentGuessCount;
  int componentStats[][] = new int[COMPONENT_COUNT][STATS_COUNT];
  int averageError;
  int correctProportions;
  int incorrectProportions;
  int correctDomIntensity;
  int incorrectDomIntensity;
  int correctIntIntensity;
  int incorrectIntIntensity;
  int correctLstIntensity;
  int incorrectLstIntensity;
  
  public TestYourself(JTabbedPane tabbedPane, JComponent testYourself, int width, int height){
    System.out.println("In TestYourself");
    tabbedPane.addTab("Test Yourself", testYourself);
    tabbedPane.setMnemonicAt(4, KeyEvent.VK_5);
    
    for (int i = 0; i < COLUMNS - 1; i++) {
      columnW[i] = columns[i + 1] - columns[i];
    }
    
    for (int i = 0; i < ROWS - 1; i++) {
      rowH[i] = rows[i + 1] - rows[i];
    }
    
    JLabel testSelfHeadline =  new JLabel(headline, SwingConstants.LEFT);
    testSelfHeadline.setBounds(columns[1], rows[0], width - 200, rowH[0]);
    testYourself.add(testSelfHeadline);
    
    JLabel testSelfSteps = new JLabel(steps);
    testSelfSteps.setBounds(columns[1], rows[1], width - 200, rowH[1]);
    testYourself.add(testSelfSteps);
    
    spaceSelector = new ColorSpaceSelector();
    spaceSelector.setTestYourself(this);
    spaceSelector.setBounds(columns[1], rows[2], 200, rowH[2] + rowH[3]);
    testYourself.add(spaceSelector);
    
    JButton startB = new JButton(startS);
    startB.setBounds(columns[0] + 5, rows[4], columnW[0] - 10, 40);
    startB.addActionListener(this);
    testYourself.add(startB);
    
    int h = rowH[3] + rowH[4] + rowH[5] + rowH[6];
    secretResultDisplay = new SecretResultDisplay(columnW[1] + columnW[2], h);
    secretResultDisplay.setBounds(columns[1], rows[4], columnW[1] + columnW[2], h);
    testYourself.add(secretResultDisplay);
    
    JLabel secretColorL = new JLabel(secretColorS, SwingConstants.LEFT);
    secretColorL.setBounds(columns[1], rows[8], columnW[1], rowH[8]);
    testYourself.add(secretColorL);
    
    JLabel guessedColorL = new JLabel(guessedColorS, SwingConstants.RIGHT);
    guessedColorL.setBounds(columns[2], rows[8], columnW[2], rowH[8]);
    testYourself.add(guessedColorL);
    
    testGuessL = new JLabel("");
    testGuessL.setBounds(columns[4], rows[2], width - columns[4] - 20, rowH[2]);
    testGuessL.setForeground(Color.RED);
    testYourself.add(testGuessL);
    
    JLabel setGuessL = new JLabel(setGuessS);
    setGuessL.setBounds(columns[4], rows[3], columnW[4] + columnW[5], rowH[3]);
    testYourself.add(setGuessL);
    
    colorComposer = new ColorComposer(secretResultDisplay.getResultDisplay());
    int w = columnW[4] + columnW[5] + columnW[6] + columnW[7];
    colorComposer.setBounds(columns[4], rows[4], w, rowH[4]);
    colorComposer.addAdvice();
    colorComposer.showComponentColors(false);
    colorComposer.showResultColor(false);
    testYourself.add(colorComposer);
    
    JButton tryYourGuessB = new JButton(tryYourGuessS);
    tryYourGuessB.setBounds(columns[4], rows[5], columnW[4], rowH[5]);
    tryYourGuessB.addActionListener(this);
    testYourself.add(tryYourGuessB);
    
    scoreCard = new ScoreCard();
    scoreCard.setBounds(columns[4], rows[7], columnW[4] + columnW[5], rowH[7] + rowH[8]);
    testYourself.add(scoreCard);

  }

  public void actionPerformed(ActionEvent e) {
    String action = e.getActionCommand();
    if (action.equals(startS)) {
      new EnterName();
    } else if (action.equals(tryYourGuessS)) {
      doTryYourGuess();
    }
  }
  
  public void doTryYourGuess() {
    if (!secretColorSet) {
      JFrame frame = new JFrame();
      JOptionPane.showMessageDialog(frame, "Please set secret color first");
    }
    if (newSecretColor) {
      newSecretColor = false;
      firstGuessHistory[correct][0] = colorComposer.getColor1();
      firstGuessHistory[correct][1] = colorComposer.getColor2();
      firstGuessHistory[correct][2] = colorComposer.getColor3();
    }
    // Change result color
    secretResultDisplay.setResultColor(colorComposer.getColorResult());
    if (colorComposer.getAdvice(difficultyLevel, secretColorC1, secretColorC2, secretColorC3)) {
      correct++;
      guesses++;
      new Congratulations();
    } else {
      guesses++;
    }
    printWriter.println("Guess Color " + currentGuessCount + " \t" +
                        COLOR_SPACE_S[colorSpace][0] + ": " + colorComposer.getColor1() + "% \t" +
                        COLOR_SPACE_S[colorSpace][1] + ": " + colorComposer.getColor2() + "% \t" +
                        COLOR_SPACE_S[colorSpace][2] + ": " + colorComposer.getColor3() + "%");
    currentGuessCount++;
    scoreCard.update();
  }

  public void updateColorSpace(int cs) {
    colorSpace = cs;
    spaceSelector.selectButton(cs); 
    colorComposer.updateColorSpace(cs);
  }

  public void doSetSecretColor() {
    colorComposer.initialize();
    secretResultDisplay.initialize();
    Random generator = new Random();

    secretColorC1 = generator.nextInt(101);
    secretColorC2 = generator.nextInt(101);
    secretColorC3 = generator.nextInt(101);
    secretColorHistory[correct][0] = secretColorC1;
    secretColorHistory[correct][1] = secretColorC2;
    secretColorHistory[correct][2] = secretColorC3;
    secretResultDisplay.paintSecretColor(colorSpace, secretColorC1, secretColorC2, secretColorC3);
    testGuessL.setText(testGuessS);
    int count = correct + 1;
    printWriter.println("/nSecret Color " + count + " \t" +
                        COLOR_SPACE_S[colorSpace][0] + ": " + secretColorC1 + "% \t" +
                        COLOR_SPACE_S[colorSpace][1] + ": " + secretColorC2 + "% \t" +
                        COLOR_SPACE_S[colorSpace][2] + ": " + secretColorC3 + "%");
    currentGuessCount = 1;
    secretColorSet = true;
    newSecretColor = true;
  }
  
  private void processStats() {
    // This is done in EnterName doneButton actionPerformed method
    // cal = Calendar.getInstance();
    formatter = new SimpleDateFormat("EEEE, MMMMM d, yyyy hh:mm a");
    currentDateTime =  formatter.format(cal.getTime());
    
    printWriter.println("\nSummary Statistics");
    printWriter.println(playerName + " matched " + correct + " colors in " + guesses + " guesses.");
                  
    for (int i = 0; i < MAX_TEST_COUNT; i++) {
      for (int j = 0; j < COMPONENT_COUNT; j++) {
        int secret = secretColorHistory[i][j];
        int guess = firstGuessHistory[i][j];
        componentStats[j][AVERAGE_ERROR] = Math.abs(secret - guess);
        if (Math.abs(secret - guess) <= difficultyLevel) {
          componentStats[j][CORRECT]++;
        } else if (secret > guess) {
          componentStats[j][UNDER]++;
        } else {
          componentStats[j][OVER]++;
        }          
      }
    } 
    
    for (int j = 0; j < COMPONENT_COUNT; j++) {
      componentStats[j][AVERAGE_ERROR] = componentStats[j][AVERAGE_ERROR] / MAX_TEST_COUNT;
      averageError += componentStats[j][AVERAGE_ERROR];
      
      printWriter.println("Average " + COLOR_SPACE_S[colorSpace][j] + " Intensity Error of First Guess = " +
                  componentStats[j][AVERAGE_ERROR] + "%");
      printWriter.println("\tNumber of Intensities underestimated = " + componentStats[j][UNDER]);
      printWriter.println("\tNumber of Intensities estimated correctly = " + componentStats[j][CORRECT]);
      printWriter.println("\tNumber of Intensities overestimated = " + componentStats[j][OVER] + "\n");
    }

    printWriter.println("Average Intensity Errot of First Guess = " + averageError + "%\n");   
    
    for (int i = 0; i < MAX_TEST_COUNT; i++) {
      if (componentOrder(i, secretColorHistory) == componentOrder(i, firstGuessHistory)) {
        correctProportions++;
      } else {
        incorrectProportions++;
      }
      if (getIndex(DOMINANT, i, secretColorHistory) == getIndex(DOMINANT, i, firstGuessHistory)) {
        correctDomIntensity++;
      } else {
        incorrectDomIntensity++;
      }
      if (getIndex(INTERMEDIATE, i, secretColorHistory) == getIndex(INTERMEDIATE, i, firstGuessHistory)) {
        correctIntIntensity++;
      } else {
        incorrectIntIntensity++;
      }
      if (getIndex(LEAST, i, secretColorHistory) == getIndex(LEAST, i, firstGuessHistory)) {
        correctLstIntensity++;
      } else {
        incorrectLstIntensity++;
      }
    }
    
    printWriter.println("Number of first guesses with the color proportions correct = " + correctProportions);
    printWriter.println("Number of first guesses with the color proportions incorrect = " + incorrectProportions + "\n");
    printWriter.println("Number of first guesses with the DOMINANT intensity color correct = " + correctDomIntensity);
    printWriter.println("Number of first guesses with the DOMINANT intensity color incorrect = " + incorrectDomIntensity + "\n");
    printWriter.println("Number of first guesses with the INTERMEDIATE intensity color correct = " + correctIntIntensity);
    printWriter.println("Number of first guesses with the INTERMEDIATE intensity color incorrect = " + incorrectIntIntensity + "\n");
    printWriter.println("Number of first guesses with the LEAST intensity color correct = " + correctLstIntensity);
    printWriter.println("Number of first guesses with the LEAST intensity color incorrect = " + incorrectLstIntensity + "\n");
    printWriter.close();
    
  }
    
  int componentOrder(int testIndex, int history[][]) {
    if (history[testIndex][0] > history[testIndex][1] &&
        history[testIndex][1] > history[testIndex][2]) {
      return(1);
    } else if (history[testIndex][0] > history[testIndex][2] &&
        history[testIndex][2] > history[testIndex][1]) {
      return(2);
    } else if (history[testIndex][1] > history[testIndex][0] &&
        history[testIndex][0] > history[testIndex][2]) {
      return(3);
    } else if (history[testIndex][1] > history[testIndex][2] &&
        history[testIndex][2] > history[testIndex][0]) {
      return(4);
    } else if (history[testIndex][2] > history[testIndex][0] &&
        history[testIndex][0] > history[testIndex][1]) {
      return(5);
    } else {
      return(6);
    }
  }
  
  int getIndex(int type, int testIndex, int history[][]) {
    int index = 0;
    
    switch (type) {
      case DOMINANT:
        if (history[testIndex][0] > history[testIndex][1]) {
          index = 0;
        } else {
          index = 1;
        }
        if (history[testIndex][index] < history[testIndex][2]) {
          index = 2;
        }
        break;
      case INTERMEDIATE:
        if (((history[testIndex][0] > history[testIndex][1]) &&
             (history[testIndex][0] < history[testIndex][2])) ||
            ((history[testIndex][0] < history[testIndex][1]) &&
             (history[testIndex][0] > history[testIndex][2]))) {
          index = 0;
        } else if (((history[testIndex][1] > history[testIndex][0]) &&
             (history[testIndex][1] < history[testIndex][2])) ||
            ((history[testIndex][1] < history[testIndex][0]) &&
             (history[testIndex][1] > history[testIndex][2]))) {
          index = 1;
        } else {
          index = 2;
        }
        break;
      case LEAST:
        if (history[testIndex][0] > history[testIndex][1]) {
          index = 1;
        } else {
          index = 0;
        }
        if (history[testIndex][index] > history[testIndex][2]) {
          index = 2;
        }
        break;
    }
    return(index);
  }
  
  class ScoreCard extends JPanel {
    // size = 280 x 80
    
    final String correctS = "<html><center>Number of colors<br>guessed correctly</center></html>";
    final String guessesS = "<html><center>Number of<br>guesses</center></html>";
    final int LEFT_BORDER = 20;
    final int LABELH = 50;
    final int LINEH = 30;
    final int COL1W = 130;
    final int COL2W = 130;
    final int COL1X = LEFT_BORDER;
    final int COL2X = COL1X + COL1W;
    final int ROW2Y = LABELH;
    JLabel correctCountL;
    JLabel guessCountL;
   
    public ScoreCard () {
      setLayout(null);
      JLabel correctL = new JLabel(correctS, SwingConstants.CENTER);
      correctL.setBounds(COL1X, 0, COL1W, LABELH);
      add(correctL);
      
      JLabel guessesL = new JLabel(guessesS, SwingConstants.CENTER);
      guessesL.setBounds(COL2X, 0, COL2W, LABELH);
      add(guessesL);
      
      correctCountL = new JLabel(Integer.toString(correct), SwingConstants.CENTER);
      correctCountL.setBounds(COL1X, ROW2Y, COL1W, LINEH);
      add(correctCountL);
      
      guessCountL = new JLabel(Integer.toString(guesses), SwingConstants.CENTER);
      guessCountL.setBounds(COL2X, ROW2Y, COL2W, LINEH);
      add(guessCountL);
    }

    public void update() {
      correctCountL.setText(Integer.toString(correct));
      guessCountL.setText(Integer.toString(guesses));
    }

  }
  
  public class EnterName extends JPanel{
    
    final int WIDTH = 450;
    final int HEIGHT = 350;
    
    
    public JFrame frame = new JFrame("Enter Name");
    
    final static String comments = "<html><left><font size = +0>" +
      "Test your ability to make a wide range of colors using varying intensities of only three color components: " +
      "red/green/blue, cyan/magenta/yellow, or hue/saturation/value." +
      "<p><br>After matching 10 randomly generated colors, a report is produced to help measure your understanding and skill." +
      "<p><br>To start, type your name (First and Last) or assigned code into the white box below.  " +
      "Click 'Done' when finished, and the main window will appear." +
      "</font></left>";
    
    JTextField name;
    
    public EnterName(){
      
      JLabel commentsL =  new JLabel(comments);
      commentsL.setBounds((WIDTH/6)-50, 20, WIDTH-50, 230);
      frame.add(commentsL);
      
      name = new JTextField("", 10);
      name.setEditable(true);
      name.setBounds((WIDTH/6)-50, 260, 400, 25);
      name.setVisible(true);
      frame.add(name);
      
      JButton done = new JButton();
      done.setBounds(WIDTH-100, 300, 75, 25);
      done.setEnabled(true);
      done.setText("Done");
      frame.add(done);
      done.addActionListener(new DoneButton());
      
      JLabel jlbempty = new JLabel("");
      jlbempty.setPreferredSize(new Dimension(WIDTH, HEIGHT));
      frame.getContentPane().add(jlbempty, BorderLayout.CENTER);
      frame.pack();
      frame.setVisible(true);
    }
    
    public class DoneButton implements ActionListener{
      
      public void actionPerformed(ActionEvent e){
        playerName = name.getText();
        cal = Calendar.getInstance();
        formatter = new SimpleDateFormat("yyyyMMddhhmm");
        // Combine player name and date and time to make filename
        testLogFileName =  playerName + formatter.format(cal.getTime());
        System.out.println(testLogFileName);
        frame.dispose();
        new StatsJFileChooser();
      }
    }
    
  }
  
  public class StatsJFileChooser extends JPanel {
    
    JFrame frame = new JFrame("");
    JFileChooser chooser;
    String choosertitle = "Color Basic Test Yourself Statistic File";
    
    public StatsJFileChooser () {
      int result;
      
      testLogFile = new File(testLogFileName);
      chooser = new JFileChooser(); 
      chooser.setCurrentDirectory(new java.io.File("."));
      chooser.setDialogTitle(choosertitle);
      chooser.setSelectedFile(testLogFile);
      chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
      //
      // disable the "All files" option.
      //
      chooser.setAcceptAllFileFilterUsed(false);
      //    
      if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
        try {
          printWriter = new PrintWriter (new BufferedWriter
                                           (new FileWriter (chooser.getSelectedFile())), true);
        } catch (IOException e) {
            e.printStackTrace();
          //System.out.println (e);
        } // catch
        
        formatter = new SimpleDateFormat("EEEE, MMMMM d, yyyy hh:mm a");
        printWriter.println(playerName + " " + formatter.format(cal.getTime()));
      }
      else {
        System.out.println("No Selection ");
      }
    frame.getContentPane().add(chooser,"Center");
    frame.setSize(chooser.getPreferredSize());
    frame.setVisible(true);
    frame.dispose();
    doSetSecretColor();
    }
  }
  
  // Class that produces the Congratuation frame
  public class Congratulations extends JPanel{
    
    final static int WIDTH = 650;
    final static int HEIGHT = 450;
       
    public JFrame frame = new JFrame("Congratulations!");
    
    final String headline = "<html><center><b><font size = +0>" +
      "Congratulations! You matched the secret color!" +
      "</font></b></center>";
    final String secretColorStats = "<html><left><font size = +0>" +
      "Secret Color" +
      "<p><br>" + COLOR_SPACE_S[colorSpace][0] + " = " + secretColorC1 +
      "<p><br>" + COLOR_SPACE_S[colorSpace][1] + " = " + secretColorC2 +
      "<p><br>" + COLOR_SPACE_S[colorSpace][2] + " = " + secretColorC3 +
      "</font></left>";
    final String guessColorStats = "<html><left><font size = +0>" +
      "Guess Color" +
      "<p><br>" + COLOR_SPACE_S[colorSpace][0] + " = " + colorComposer.getColor1() +
      "<p><br>" + COLOR_SPACE_S[colorSpace][1] + " = " + colorComposer.getColor2() +
      "<p><br>" + COLOR_SPACE_S[colorSpace][2] + " = " + colorComposer.getColor3() +
      "</font></left>";
    
    public Congratulations(){
      JLabel congratsHeadline =  new JLabel(headline);
      congratsHeadline.setBounds(WIDTH/6, 20, WIDTH-200, 20);
      frame.add(congratsHeadline);
      
      SecretResultDisplay display = new SecretResultDisplay(500, 300);
      display.setBounds((WIDTH/6)-50, 70, 500, 300);
      frame.add(display);
      display.setSecretColor(secretResultDisplay.getSecretColor());
      display.setResultColor(colorComposer.getColorResult());
      display.setSecretText(secretColorStats);
      display.setResultText(guessColorStats);
      frame.add(display);
      
      JButton playNextColorB = new JButton("Play Next Color");
      playNextColorB.setBounds(WIDTH/3, 400, 200, 20);
      playNextColorB.setEnabled(true);
      frame.add(playNextColorB);
      playNextColorB.addActionListener(new PlayNextColorButton());
     
      JLabel jlbempty = new JLabel("");
      jlbempty.setPreferredSize(new Dimension(WIDTH, HEIGHT));
      frame.getContentPane().add(jlbempty, BorderLayout.CENTER);
      frame.pack();
      frame.setVisible(true);
    }

    public class PlayNextColorButton implements ActionListener{
      
      public void actionPerformed(ActionEvent e){
        if (correct >= MAX_TEST_COUNT) {
          processStats();
          new ReportStatistics();
        } else {
          frame.dispose();
          doSetSecretColor();
        }
      }
    }
  }
  
  public class ReportStatistics {
    
    final int WIDTH = 560;
    final int HEIGHT = 650;
  
    public JFrame frame = new JFrame("Report Statistics");
    
    final String REPORTS = "<html>" + playerName + " " + currentDateTime +
      "<br><br>Summary Statistics" +
      "<br><br>" + playerName + " matched " + correct + " colors in " + guesses + " guesess." +
      "<br><br>Average " + COLOR_SPACE_S[colorSpace][0] + " Intensity Error of First Guess = " + componentStats[0][AVERAGE_ERROR] + "%" +
      "<br>Number of Intensities underestimated = " + componentStats[0][UNDER] +
      "<br>Number of Intensities estimated correctly = " + componentStats[0][CORRECT] + 
      "<br>Number of Intensities overestimated = " + componentStats[0][OVER] +
      "<br><br>Average " + COLOR_SPACE_S[colorSpace][1] + " Intensity Error of First Guess = " + componentStats[1][AVERAGE_ERROR] + "%" +
      "<br>Number of Intensities underestimated = " + componentStats[1][UNDER] + 
      "<br>Number of Intensities estimated correctly = " + componentStats[1][CORRECT] + 
      "<br>Number of Intensities overestimated = " + componentStats[1][OVER] +
      "<br><br>Average " + COLOR_SPACE_S[colorSpace][2] + " Intensity Error of First Guess = " + componentStats[2][AVERAGE_ERROR] + "%" +
      "<br>Number of Intensities underestimated = " + componentStats[2][UNDER] +
      "<br>Number of Intensities estimated correctly = " + componentStats[2][CORRECT] +
      "<br>Number of Intensities overestimated = " + componentStats[2][OVER] +
      "<br><br>Average Intensity Error of First Guess = " + averageError + "%" +
      "<br><br>Number of first guesses with the color proportion correct = " + correctProportions +
      "<br>Number of first guesses with the color proportion incorrect = " + incorrectProportions +
      "<br><br>Number of first guesses with the DOMINANT intensity color correct = " + correctDomIntensity +
      "<br>Number of first guesses with the DOMINANT intensity color incorrect = " + incorrectDomIntensity +
      "<br><br>Number of first guesses with the INTERMEDIATE intensity color correct = " + correctIntIntensity +
      "<br>Number of first guesses with the INTERMEDIATE intensity color incorrect = " + incorrectIntIntensity +
      "<br><br>Number of first guesses with the LEAST intensity color correct = " + correctLstIntensity +
      "<br>Number of first guesses with the LEAST intensity incorrect = " + incorrectLstIntensity;
    
    JLabel reportL;
      
    ReportStatistics() {
      reportL =  new JLabel(REPORTS);
      reportL.setBounds(20, 20, WIDTH-40, HEIGHT-100);
      frame.add(reportL);     
      
      JButton printB = new JButton("Print");
      printB.setBounds(WIDTH/6, HEIGHT-70, 100, 20);
      printB.setEnabled(true);
      frame.add(printB);
      printB.addActionListener(new printButton());

      JButton doneB = new JButton("Done");
      doneB.setBounds(WIDTH/6*4, HEIGHT-70, 100, 20);
      doneB.setEnabled(true);
      frame.add(doneB);
      doneB.addActionListener(new doneButton());
     
      JLabel jlbempty = new JLabel("");
      jlbempty.setPreferredSize(new Dimension(WIDTH, HEIGHT));
      frame.getContentPane().add(jlbempty, BorderLayout.CENTER);
      frame.pack();
      frame.setVisible(true);
    }

    private class doneButton implements ActionListener{
      
      public void actionPerformed(ActionEvent e){
        printWriter.close();
        System.exit(0);
      }
    }
    
    private class printButton implements ActionListener{
      
      public void actionPerformed(ActionEvent e){
        PrintUtilities printer = new PrintUtilities((Component)reportL);
        printer.print();
      }
    }
    
  }
}