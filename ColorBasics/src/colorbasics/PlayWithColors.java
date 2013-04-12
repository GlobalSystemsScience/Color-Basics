package colorbasics;
import javax.swing.*;
//import javax.swing.event.*;
import javax.swing.JOptionPane;
import javax.swing.JDialog;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class PlayWithColors extends JPanel implements ActionListener {
  
  final String headline = "<html><center><font size = +1>" +
    "Develop your ability to identify colors!" +
    "</font></center>";
  
  final String steps = "<html><left><font size = +0><ol>" +
    "<li>Select a color space: Red/Green/Blue, Cyan/Magenta/Yellow. or Hue/Saturation/Value." +
    "<li>Select level of difficulty: Easy (intensities match within 15%); Medium (within 10%); or Hard (within 5%)." +
    "<li>Select opponent: 'Person' or 'Computer'." +
    "<li>Press the 'Set Secret Color' button." +
    "<li>Set your guess color by entering intensity values." +
    "<li>Press 'Try Your Guess' button to see if you matched the secret color." +
    "<li>Repeat until colors match." +
    "</ol></font></left>";
  
  final String instructionS = "<html><left><font size =+0>" +
    "Before playing, select the level of difficulty and opponent.";
  
  final String selectDifficultyS = "Select level of Difficulty";
  final String easyS = "Easy - match within 15%";
  final String mediumS = "Medium - match within 10%";
  final String hardS = "Hard - match within 5%";
  final String[] difficultyListS = {selectDifficultyS, easyS, mediumS, hardS};
  final String selectOpponentS = "Select Opponent";
  final String personS = "Play a person";
  final String computerS = "Play the Computer";
  final String[] opponentListS = {selectOpponentS, personS, computerS};
  final String setSecretColorS = "Set Secret Color";
  final String setYourGuessS = "Set Your Guess";
  final String tryYourGuessS = "Try Your Guess";
  final String showAnswerS = "Show Answer";
  final String resetScoreS = "Reset Score";
  final String secretColorS = "Secret Color";
  final String guessedColorS = "Guessed Color";
  final String correctS ="<html><center>Number of colors<br>guessed correctly</center></html>";
  final String guessesS ="<html><center>Number of<br>guesses</center></html>";
  final String comboBoxChangedS = "comboBoxChanged";
  final String TEST_GUESS_S = "Test guess after setting the color intensities";
  final String PLAYER_GUESS_S[] = {"Player 1 is guessing the Secret Color", "Player 2 is guessing the Secret Color"};
  final String PLAYER_SET_COLOR_S[] = {"Player 1 sets the secret color", "Player 2 sets the secret color"};
  final String COLOR_SPACE_S [][] = {{"Red", "Green", "Blue"}, {"Cyan", "Magenta", "Yellow"},
    {"Hue", "Saturation", "Intensity"}};
  final int PERSON = 1;
  final int COMPUTER = 2;
  final int RGB = 0;
  final int CMY = 1;
  final int HSV = 2;
  final int MAX_INTENSITY = 255;
  
  final int COLUMNS = 10;
  final int ROWS = 10;
  final int columns[] = {0, 50, 60, 270, 300, 500, 650, 800, 950, 980, 1000};
  int columnW[] = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
  final int rows[] = {10, 40, 160, 210, 250, 290, 330, 600, 630, 700};
//  final int rows[] = {10, 40, 160, 210, 250, 290, 330, 600, 630, 700};
  int rowH[] = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
  ColorSpaceSelector spaceSelector;
  ColorComposer colorComposer;
  SecretResultDisplay secretResultDisplay;
  ScoreCard scoreCard;
  JLabel playerInfoL;
  JLabel playerSetColorL;
  int colorSpace = RGB;
  int difficultyLevel = 0;
  int opponent = 0; // 0 for person, 1 for computer
  int playerID = 0;
  int correct[] = {0, 0};
  int guesses[] = {0, 0};
  boolean secretColorSet = false;
  int secretColorC1 = 0;
  int secretColorC2 = 0;
  int secretColorC3 = 0;
  
  public PlayWithColors(JTabbedPane tabbedPane, JComponent playWithColors, int width, int height){
//    frame = playWithColors;
    System.out.println("In PlayWithColors");
    tabbedPane.addTab("Play With Colors", playWithColors);
    tabbedPane.setMnemonicAt(3, KeyEvent.VK_2);
    
    for (int i = 0; i < COLUMNS - 1; i++) {
      columnW[i] = columns[i + 1] - columns[i];
    }
    
    for (int i = 0; i < ROWS - 1; i++) {
      rowH[i] = rows[i + 1] - rows[i];
    }
    
    JLabel playColorHeadline =  new JLabel(headline);
    playColorHeadline.setBounds(columns[1], rows[0], width - 20, rowH[0]);
    playWithColors.add(playColorHeadline);
    
    JLabel playColorSteps =  new JLabel(steps);
    playColorSteps.setBounds(columns[2], rows[1], width - columnW[2], rowH[1]);
    playWithColors.add(playColorSteps);

    spaceSelector = new ColorSpaceSelector();
    spaceSelector.setPlayWithColors(this);
    spaceSelector.setBounds(columns[1], rows[2], 160, rowH[2]);
    playWithColors.add(spaceSelector);
    
    JLabel instructionL = new JLabel(instructionS, SwingConstants.LEFT);
    instructionL.setForeground(Color.RED);
    instructionL.setBounds(columns[1], rows[3], width - 20, rowH[3]);
    playWithColors.add(instructionL);
    
    //Create the combo boxes for difficulty and opponent selection.
    //Indices start at 0, index 0 is combo box type
    JComboBox difficultyList = new JComboBox(difficultyListS);
    difficultyList.setSelectedIndex(0);
    difficultyList.addActionListener(this);
    difficultyList.setBounds(columns[1], rows[4], 200, rowH[4] - 10);
    difficultyList.addActionListener(this);
    playWithColors.add(difficultyList);
    
    JComboBox opponentList = new JComboBox(opponentListS);
    opponentList.setSelectedIndex(0);
    opponentList.addActionListener(this);
    opponentList.setBounds(columns[3], rows[4], 200, rowH[4] - 10);
    opponentList.addActionListener(this);
    playWithColors.add(opponentList);

    JButton setSecretColorB = new JButton(setSecretColorS);
    setSecretColorB.setBounds(columns[1], rows[5], 200, rowH[5] - 10);
    setSecretColorB.setHorizontalAlignment(SwingConstants.LEFT);
    setSecretColorB.addActionListener(this);
    playWithColors.add(setSecretColorB);
    
    playerSetColorL = new JLabel("");
    playerSetColorL.setBounds( columns[1] + 220, rows[5], 200, rowH[5] -10);
    playerSetColorL.setForeground(Color.BLUE);
    playWithColors.add(playerSetColorL);
    
    int w = (columns[3] - columns[1]) * 2;
    secretResultDisplay = new SecretResultDisplay(w, rowH[6] - 10);
    secretResultDisplay.setBounds(columns[1], rows[6], w, rowH[6] - 10);
    playWithColors.add(secretResultDisplay);
    
    JLabel secretColorL = new JLabel(secretColorS, SwingConstants.LEFT);
    secretColorL.setBounds(columns[1], rows[7] - 10, 220, rowH[7] - 10);
    playWithColors.add(secretColorL);
    JLabel guessedColorL = new JLabel(guessedColorS, SwingConstants.RIGHT);
    guessedColorL.setBounds(columns[1] + 220, rows[7] - 10, 220, rowH[7] - 10);
    playWithColors.add(guessedColorL);

    playerInfoL = new JLabel("");
    playerInfoL.setForeground(Color.RED);
    playerInfoL.setBounds(columns[5], rows[2], 300, rowH[2]);
    playWithColors.add(playerInfoL);
    
    JLabel setYourGuessL = new JLabel(setYourGuessS, SwingConstants.CENTER);
    setYourGuessL.setBounds(columns[5], rows[3], 250, rowH[4]);
    playWithColors.add(setYourGuessL);
    
    colorComposer = new ColorComposer(secretResultDisplay.getResultDisplay());
    colorComposer.addAdvice();
    colorComposer.setBounds(columns[5], rows[4], 350, 200);
//    colorComposer.setBounds(columns[5], rows[4], 250, 200);
    colorComposer.showResultColor(false);
    playWithColors.add(colorComposer);
    
    JButton tryYourGuessB = new JButton(tryYourGuessS);
    tryYourGuessB.setBounds(columns[5], 450, columnW[5] - 10, 30);
    tryYourGuessB.addActionListener(this);
    playWithColors.add(tryYourGuessB);
    
    JButton showAnswerB = new JButton(showAnswerS);
    showAnswerB.setBounds(columns[6], 450, columnW[6] - 10, 30);
    showAnswerB.addActionListener(this);
    playWithColors.add(showAnswerB);
    
    JButton resetScoreB = new JButton(resetScoreS);
    resetScoreB.setBounds(columns[7], 450, columnW[7] - 10, 30);
    resetScoreB.addActionListener(this);
    playWithColors.add(resetScoreB);
    
    scoreCard = new ScoreCard();
    scoreCard.setBounds(columns[5], 480, 360, 110);
    playWithColors.add(scoreCard);
    
  }
  
  // Listens to the combo box.
  public void actionPerformed(ActionEvent e) {
    String action = e.getActionCommand();
    System.out.println(action);
    if (action.equals(comboBoxChangedS)) {
      JComboBox cb = (JComboBox)e.getSource();
//      System.out.println(cb);
      String name = (String)cb.getSelectedItem();
      if (name.equals(selectDifficultyS)) {
        difficultyLevel = 0;
      } if (name.equals(easyS)) {
        difficultyLevel = 15;
      } else if (name.equals(mediumS)) {
        difficultyLevel = 10;
      } else if (name.equals(hardS)) {
        difficultyLevel = 5;
      } else if (name.equals(selectOpponentS)) {
        opponent = 0;
      } else if (name.equals(personS)) {
        // Set up to play person
        opponent = PERSON;
        playerID = 0;
//        playerInfoL.setText(PLAYER_SET_COLOR_S[playerID]);
        playerInfoL.setText("");
        playerSetColorL.setText(PLAYER_SET_COLOR_S[playerID]);
        initializeDisplay();
        scoreCard.setPerson();
      } else if (name.equals(computerS)) {
        // Set up to play computer
        opponent = COMPUTER;
        playerID = 0;
        playerInfoL.setText(TEST_GUESS_S);
        playerSetColorL.setText("");
        initializeDisplay();
        scoreCard.setComputer();
      }
//      System.out.println(name + " " + difficultyLevel + " " + opponent);
    } else if (action.equals(setSecretColorS)) {
      doSetSecretColor();
    } else if (action.equals(tryYourGuessS)) {
      doTryYourGuess();
    } else if (action.equals(showAnswerS)) {
      doShowAnswer();
    } else if (action.equals(resetScoreS)) {
      for (int i = 0; i < 2; i++) {
        correct[i] = 0;
        guesses[i] = 0;
      }
      scoreCard.update();
    }
  }
  
  public void doSetSecretColor() {
//    System.out.println("Set Secret Color");
    if (difficultyLevel == 0) {
      JFrame frame = new JFrame();
      JOptionPane.showMessageDialog(frame, "Select level of Difficulty: Beginner, Intermediate, or Expert");
    } else if (opponent == 0) {
      JFrame frame = new JFrame();
      JOptionPane.showMessageDialog(frame, "Select Opponent: Person or Computer");
    } else {
      initializeDisplay();
      colorComposer.initialize();
//      colorComposer.setMinMax(100, 0, 100, 0, 100, 0); 
      if (opponent == COMPUTER) {
        Random generator = new Random();
        
//        initializeDisplay();
//        colorComposer.setMinMax(100, 0, 100, 0, 100, 0); 
        secretColorC1 = generator.nextInt(101);
        secretColorC2 = generator.nextInt(101);
        secretColorC3 = generator.nextInt(101);
        paintSecretColor();
        playerID = 0;
        if (opponent == PERSON) {
          playerInfoL.setText(PLAYER_GUESS_S[playerID]);
        }
        secretColorSet = true;
      } else {
        //Person opponent code here
        new SetSecretColor(playerID);
        alternatePlayer();
        playerInfoL.setText(PLAYER_GUESS_S[playerID]);
        playerSetColorL.setText("");
      }
    }
  }
  
  public void paintSecretColor() {
    int c1;
    int c2;
    int c3;
    
    switch (colorSpace) {
      case RGB:
        c1 = secretColorC1 * MAX_INTENSITY / 100;
        c2 = secretColorC2 * MAX_INTENSITY / 100;
        c3 = secretColorC3 * MAX_INTENSITY / 100;
        secretResultDisplay.setSecretColor(new Color(c1, c2, c3));
        break;
      case CMY:
        // Convert CMY to RGB
        c1 = MAX_INTENSITY - secretColorC1 * MAX_INTENSITY / 100;
        c2 = MAX_INTENSITY - secretColorC2 * MAX_INTENSITY / 100;
        c3 = MAX_INTENSITY - secretColorC3 * MAX_INTENSITY / 100;
        secretResultDisplay.setSecretColor(new Color(c1, c2, c3));
        break;
      case HSV:
        // Convert HSV to RGB
        HsvToRgb c = new HsvToRgb(secretColorC1, secretColorC2, secretColorC3);
        secretResultDisplay.setSecretColor(new Color(c.getRed(), c.getGreen(), c.getBlue()));
    }
  }
  
  public void doTryYourGuess() {
    System.out.println("Try Your Guess");
    if (!secretColorSet) {
      JFrame frame = new JFrame();
      JOptionPane.showMessageDialog(frame, "Please set secret color first");
    }
    if ((difficultyLevel == 0) || (opponent == 0)) {
      JFrame frame = new JFrame();
      JOptionPane.showMessageDialog(frame, "Please set level of Difficulty, choose your Opponent, and set a secret Color before making your guess");
    } else {
      if (colorComposer.getAdvice(difficultyLevel, secretColorC1, secretColorC2, secretColorC3)) {
        correct[playerID]++;
        guesses[playerID]++;
        new Congratulations();
      } else {
        guesses[playerID]++;
      }
      scoreCard.update();
    }
  }
  
  public void doShowAnswer() {
    if (!secretColorSet || (difficultyLevel == 0) || (opponent == 0)) {
      JFrame frame = new JFrame();
      JOptionPane.showMessageDialog(frame, "Please set level of Difficulty, choose your Opponent, and set a secret Color before selecting this option");
      return;
    } 
    if (guesses[playerID] == 0) {
      JFrame frame = new JFrame();
      JOptionPane.showMessageDialog(frame, "Please make at least one guess. If you are playing the computer and you don't like the current color, select another Secret Color to guess");
      return;
    }
    new ConfirmShowSecretColor();
  }
  
  public void updateColorSpace(int cs) {
    colorSpace = cs;
    spaceSelector.selectButton(cs);
    colorComposer.updateColorSpace(cs);
  }

  private void alternatePlayer() {
    if (playerID == 0) {
      playerID = 1;
    } else {
      playerID = 0;
    }
  }
  
  private void initializeDisplay()
  {
    colorComposer.initialize();
    colorComposer.showResultColor(false);
    secretResultDisplay.initialize();
  }
    
  class ScoreCard extends JPanel {
    // size 330 x 110
    final String correctS = "<html><center>Number of colors<br>guessed correctly</center></html>";
    final String guessesS = "<html><center>Number of<br>guesses</center></html>";
    final String player1S = "<html><left>Player 1</left><html>";
    final String player2S = "<html><left>Player 2</left><html>";
    final String yourScoreS = "<html><left>Your Score</left><html>";
    final int LEFT_BORDER = 20;
    final int LABELH = 50;
    final int LINEH = 30;
    final int COL1W = 80;
    final int COL2W = 130;
    final int COL3W = 130;
    final int COL1X = LEFT_BORDER;
    final int COL2X = COL1X + COL1W;
    final int COL3X = COL2X + COL2W;
    final int ROW2Y = LABELH;
    final int ROW3Y = ROW2Y + LINEH;
    
    JLabel player1L;
    JLabel player2L;
    JLabel correctCount1L;
    JLabel guessCount1L;
    JLabel correctCount2L;
    JLabel guessCount2L;
    boolean isComputer;
    
    public ScoreCard () {
      setLayout(null);
      JLabel correctL = new JLabel(correctS, SwingConstants.CENTER);
      correctL.setBounds(COL2X, 0, COL2W, LABELH);
      add(correctL);
      
      JLabel guessesL = new JLabel(guessesS, SwingConstants.CENTER);
      guessesL.setBounds(COL3X, 0, COL3W, LABELH);
      add(guessesL);
      
      player1L = new JLabel(player1S, SwingConstants.LEFT);
      player1L.setBounds(COL1X, ROW2Y, COL1W, LINEH);
      add(player1L);
      
      player2L = new JLabel(player2S, SwingConstants.LEFT);
      player2L.setBounds(COL1X, ROW3Y, COL1W, LINEH);
      add(player2L);
      
      correctCount1L = new JLabel(Integer.toString(correct[0]), SwingConstants.CENTER);
      correctCount1L.setBounds(COL2X, ROW2Y, COL2W, LINEH);
      add(correctCount1L);
      
      guessCount1L = new JLabel(Integer.toString(guesses[0]), SwingConstants.CENTER);
      guessCount1L.setBounds(COL3X, ROW2Y, COL3W, LINEH);
      add(guessCount1L);

      correctCount2L = new JLabel(Integer.toString(correct[1]), SwingConstants.CENTER);
      correctCount2L.setBounds(COL2X, ROW3Y, COL2W, LINEH);
      add(correctCount2L);
      
      guessCount2L = new JLabel(Integer.toString(guesses[1]), SwingConstants.CENTER);
      guessCount2L.setBounds(COL3X, ROW3Y, COL3W, LINEH);
      add(guessCount2L);

    }
    
    public void setComputer() {
      isComputer = true;
      player1L.setText(yourScoreS);
      player2L.setText("");
      update();
    }
    
    public void setPerson() {
      isComputer = false;
      player1L.setText(player1S);
      player2L.setText(player2S);
      update();
    }
    
    public void update() {
      correctCount1L.setText(Integer.toString(correct[0]));
      guessCount1L.setText(Integer.toString(guesses[0]));
      if (isComputer) {
        correctCount2L.setText("");
        guessCount2L.setText("");
      } else {   
        correctCount2L.setText(Integer.toString(correct[1]));
        guessCount2L.setText(Integer.toString(guesses[1]));
      }
    }
    
  }

  // Class that produces the Congratuation frame
  public class Congratulations extends JPanel{
    
    final static int WIDTH = 600;
    final static int HEIGHT = 650;
       
    public JFrame frame = new JFrame("Congratulations!");
    
    final String headline = "<html><center><b><font size = +0>" +
      "Congratulations! You matched the secret color!" +
      "</font></b></center>";
    final String comments = "<html><left><font size = +0>" +
      "Click 'Explore Matched Colors' to visualize the complete set of colors that match the secret color." +
      "<p><br>When done, click 'Set Secret Color' to continue playing the game." +
      "<p><br>To skip the exploration, click 'Play Next Color'." +
      "</font></left>";
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
      congratsHeadline.setBounds(WIDTH/6, 10, WIDTH-200, 20);
      frame.add(congratsHeadline);
      
      JLabel secretColor = new JLabel(secretColorStats, JLabel.CENTER);
      secretColor.setOpaque(true);
      secretColor.setBackground(secretResultDisplay.getSecretColor());
      secretColor.setBounds((WIDTH/6)-50, 50, 250, HEIGHT/3+100);
      secretColor.setVisible(true);
      frame.add(secretColor);
      
      JLabel guessColor = new JLabel(guessColorStats, JLabel.CENTER);
      guessColor.setBounds(WIDTH/2, 50, 250, HEIGHT/3+100);
      guessColor.setOpaque(true);
      guessColor.setBackground(colorComposer.getColorResult());
      guessColor.setVisible(true);
      frame.add(guessColor);
      
      JLabel congratsComments =  new JLabel(comments);
      congratsComments.setBounds((WIDTH/6)-50, (HEIGHT/3)+175, WIDTH-100, 125);
      frame.add(congratsComments);
      
      JButton exploreColor = new JButton();
      exploreColor.setBounds((WIDTH/6)-50, (HEIGHT/3)+325, 200, 20);
      exploreColor.setEnabled(true);
      exploreColor.setText("Explore Matched Colors");
      frame.add(exploreColor);
      exploreColor.addActionListener(new ExploreColorButton());
      
      JButton nextColor = new JButton();
      nextColor.setBounds(350, (HEIGHT/3)+325, 200, 20);
      nextColor.setEnabled(true);
      nextColor.setText("Play Next Color");
      frame.add(nextColor);
      nextColor.addActionListener(new NextColorButton());
      
      JLabel jlbempty = new JLabel("");
      jlbempty.setPreferredSize(new Dimension(WIDTH, HEIGHT));
      frame.getContentPane().add(jlbempty, BorderLayout.CENTER);
      frame.pack();
      frame.setVisible(true);
    }

    public class ExploreColorButton implements ActionListener{
      
      public void actionPerformed(ActionEvent e){
        secretResultDisplay.setSecretText(secretColorStats);
        secretResultDisplay.setResultText(guessColorStats);
        colorComposer.setValue(secretColorC1, secretColorC2, secretColorC3, difficultyLevel);
        colorComposer.showResultColor(true);
        frame.dispose();
      }
    }
    
    public class NextColorButton implements ActionListener{
      
      public void actionPerformed(ActionEvent e){
        doSetSecretColor();
        frame.dispose();
      }
    }
  }
  
  public class SetSecretColor extends JPanel{
    
    final int WIDTH = 600;
    final int HEIGHT = 650;
       
    public JFrame frame = new JFrame("Set Your Secret Color");
    
    final String comments = "<html><left><font size = +0>" +
      "To create your secret color, type the intensity of each color component in the three white boxes above. " +
      "You may also use the small up and down arrows to incrementally change intensities." +
      "<p><br>Intensities range from 0 to 100%." +
      "<p><br>0% means that there is no contribution from that color component." +
      "<p>100% means that there is maximum contribution of that color component." +
      "</font></left>";
    
    int playerNumber;
    
    ColorComposer colorComposer;
    
    public SetSecretColor(int n){    
      
      JLabel commentsL =  new JLabel(comments);
      commentsL.setBounds((WIDTH/6)-50, 10, WIDTH-100, 180);
      frame.add(commentsL);
      // row = 190
      
      JLabel playerSetColorL = new JLabel(PLAYER_SET_COLOR_S[n]);
      playerSetColorL.setBounds((WIDTH/6)-50, 200, WIDTH-100, 30);
      playerSetColorL.setForeground(Color.BLUE);
      frame.add(playerSetColorL);
      // row = 230
      
      JButton secretColor = new JButton();
      secretColor.setBounds(275, 250, 315, 315);
      secretColor.setEnabled(false);
      frame.add(secretColor);
      // row = 250 - 565
      
      colorComposer = new ColorComposer(secretColor);
      colorComposer.setBounds(0, 250, 250, 200);
      colorComposer.setBackground(Color.white);
      frame.add(colorComposer);
      // row = 250 - 450
      
      JButton testColor = new JButton();
      testColor.setBounds((WIDTH/6)-50, 480, 200, 20);
      testColor.setEnabled(true);
      testColor.setText("Test Colors");
      frame.add(testColor);
      testColor.addActionListener(new TestColorButton());
      
      JButton done = new JButton();
      done.setBounds((WIDTH/6)-50, 520, 200, 20);
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
    
    public class TestColorButton implements ActionListener{
      
      public void actionPerformed(ActionEvent e){
        colorComposer.applyColor();
      }
    }
    
    public class DoneButton implements ActionListener{
      
      public void actionPerformed(ActionEvent e){
        secretColorC1 = colorComposer.getColor1();
        secretColorC2 = colorComposer.getColor2();
        secretColorC3 = colorComposer.getColor3();
        secretColorSet = true;
        paintSecretColor();
        frame.dispose();
      }
    }
    
  }

  public class ConfirmShowSecretColor extends JPanel{
    
    final int WIDTH = 400;
    final int HEIGHT = 200;
    
    public JFrame frame = new JFrame("Show Secret Color");
    
    final String comments = "<html><center><font size = +0>" +
      "Are You Sure?" +
      "<p><br>It counts as 10 guesses." +
      "</font></center>";
    final String KEEP_GUESSING_S = "Keep Guessing";
    final String SHOW_SECRET_COLOR_S = "Show Secret Color";
    
    public ConfirmShowSecretColor(){
      
      JLabel confirmComments =  new JLabel(comments, JLabel.CENTER);
      confirmComments.setBounds(50, 20, WIDTH-100, 100);
      frame.add(confirmComments);
      
      JButton keepGuess = new JButton(KEEP_GUESSING_S);
      keepGuess.setBounds(20, 150, 175, 25);
      keepGuess.setEnabled(true);
      frame.add(keepGuess);
      keepGuess.addActionListener(new DoneButton());
      
      JButton showColor = new JButton(SHOW_SECRET_COLOR_S);
      showColor.setBounds(205, 150, 175, 25);
      showColor.setEnabled(true);
      frame.add(showColor);
      showColor.addActionListener(new DoneButton());
      
      JLabel jlbempty = new JLabel("");
      jlbempty.setPreferredSize(new Dimension(WIDTH, HEIGHT));
      frame.getContentPane().add(jlbempty, BorderLayout.CENTER);
      frame.pack();
      frame.setVisible(true);
    }
    
    public class DoneButton implements ActionListener{
      
      public void actionPerformed(ActionEvent e){
        String action = e.getActionCommand();
        if (action.equals(KEEP_GUESSING_S)) {
          frame.dispose();
        } else if (action.equals(SHOW_SECRET_COLOR_S)) {
          guesses[playerID] += 10;
          scoreCard.update();
          new ShowSecretColor();
          frame.dispose();
        }
      }
    }
    
  }

  public class ShowSecretColor extends JPanel{
    
    final int WIDTH = 600;
    final int HEIGHT = 650;
    
    public JFrame frame = new JFrame("Secret Color");
    
    final String headline = "<html><center><b><font size = +0>" +
      "Examine the colors that match the secret color!" +
      "</font></b></center>";
    final String comments = "<html><left><font size = +0>" +
      "Click 'Explore Matched Colors' to go back and visualize all of the possible colors that match the secret color." +
      "You may change the intensities tosee all of the variations that match the selected level of difficulty." +
      "<br><center><table>" +
      "<tr><td><center>Level of Difficulty</center></td><td><center>Number of Colors</center></td></tr>" +
      "<tr><td><center>Easy</center></td><td><center>29,791</center></td></tr>" +
      "<tr><td><center>Medium</center></td><td><center>9,261</center></td></tr>" +
      "<tr><td><center>Hard</center></td><td><center>1,331</center></td></tr>" +
      "</table></center>" +
      "<p><br>When you are done, click 'Set Secret Color' to continue playing the game." +
      "<p><br>If you want to skip the exploration, click 'Play Next Color' below." +
      "</font></left>";
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
    
    public ShowSecretColor(){
      JLabel headlineL =  new JLabel(headline);
      headlineL.setBounds(WIDTH/6, 20, WIDTH-200, 20);
      frame.add(headlineL);
      
      SecretResultDisplay display = new SecretResultDisplay(500, HEIGHT/3+50);
      display.setBounds((WIDTH/6)-50, 70, 500, HEIGHT/3+50);
      frame.add(display);
      display.setSecretColor(secretResultDisplay.getSecretColor());
      display.setResultColor(colorComposer.getColorResult());
      display.setSecretText(secretColorStats);
      display.setResultText(guessColorStats);
      
      JLabel commentsL =  new JLabel(comments);
      commentsL.setBounds(50, 360, WIDTH-50, 250);
      frame.add(commentsL);
      
      JButton exploreColor = new JButton();
      exploreColor.setBounds((WIDTH/6)-50, 615, 200, 20);
      exploreColor.setEnabled(true);
      exploreColor.setText("Explore Matched Colors");
      frame.add(exploreColor);
      exploreColor.addActionListener(new ExploreColorButton());
      
      JButton nextColor = new JButton();
      nextColor.setBounds(350, 615, 200, 20);
      nextColor.setEnabled(true);
      nextColor.setText("Play Next Color");
      frame.add(nextColor);
      nextColor.addActionListener(new NextColorButton());
      
      JLabel jlbempty = new JLabel("");
      jlbempty.setPreferredSize(new Dimension(WIDTH, HEIGHT));
      frame.getContentPane().add(jlbempty, BorderLayout.CENTER);
      frame.pack();
      frame.setVisible(true);
    }
    
    public class ExploreColorButton implements ActionListener{
      
      public void actionPerformed(ActionEvent e){
        secretResultDisplay.setSecretText(secretColorStats);
        secretResultDisplay.setResultText(guessColorStats);
        colorComposer.setValue(secretColorC1, secretColorC2, secretColorC3, difficultyLevel);
        frame.dispose();
      }
    }
    
    public class NextColorButton implements ActionListener{
      
      public void actionPerformed(ActionEvent e){
        doSetSecretColor();
        frame.dispose();
      }
    }
    
  }

}