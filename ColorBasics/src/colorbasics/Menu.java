package colorbasics;
/*
 * Copyright (c) 1995, 2008, Oracle and/or its affiliates. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *   - Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *
 *   - Redistributions in binary form must reproduce the above copyright
 *     notice, this list of conditions and the following disclaimer in the
 *     documentation and/or other materials provided with the distribution.
 *
 *   - Neither the name of Oracle or the names of its
 *     contributors may be used to endorse or promote products derived
 *     from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
 * IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */ 

//package components;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.JMenuItem;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.ButtonGroup;
import javax.swing.JMenuBar;
import javax.swing.KeyStroke;
import javax.swing.ImageIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ButtonGroup;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.JFrame;

/* MenuDemo.java requires images/middle.gif. */

/*
 * This class is just like MenuLookDemo, except the menu items
 * actually do something, thanks to event listeners.
 */
public class Menu extends JFrame implements ActionListener, ItemListener {
    JTextArea output;
    JScrollPane scrollPane;
    String newline = "\n";

    public JMenuBar createMenuBar() {
        JMenuBar menuBar;
        JMenu menu, submenu, helpmenu;
        JMenuItem exit, undo, cut, copy, paste, delete, selectAll,
          openPDF, openBrowser, indivSel, introPanel, compPanel,
          makePanel, playPanel, testPanel, colSpacPanel,
          checkPanel, aboutPanel, helpPanel;
//        JRadioButtonMenuItem rbMenuItem;
//        JCheckBoxMenuItem cbMenuItem;

        //Create the menu bar.
        menuBar = new JMenuBar();

        //Build the first menu.
        menu = new JMenu("File");
        menu.setMnemonic(KeyEvent.VK_A);
//        menu.getAccessibleContext().setAccessibleDescription(
//                "The only menu in this program that has menu items");
        menuBar.add(menu);

        //a group of JMenuItems
        exit = new JMenuItem("Exit",
                                 KeyEvent.VK_T);
        //menuItem.setMnemonic(KeyEvent.VK_T); //used constructor instead
        exit.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_1, ActionEvent.ALT_MASK));
//        exit.getAccessibleContext().setAccessibleDescription(
//                "This doesn't really do anything");
//        exit.addActionListener(this);
        menu.add(exit);
        
        exit.addActionListener(
                               new ActionListener(){
          public void actionPerformed(ActionEvent e)
          {
            System.out.println("Exit is pressed");
            System.exit(0);
            
          }
        }
        );
        
        //Build second menu in the menu bar.
        submenu = new JMenu("Edit");
        submenu.setMnemonic(KeyEvent.VK_N);
        submenu.getAccessibleContext().setAccessibleDescription(
                "This menu does nothing");
        menuBar.add(submenu);

        undo = new JMenuItem("Undo",
                                 KeyEvent.VK_T);
        undo.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_Z, ActionEvent.CTRL_MASK));
        undo.getAccessibleContext().setAccessibleDescription(
                "This doesn't really do anything");
        undo.setEnabled(false);
        undo.addActionListener(this);
        submenu.add(undo);
        
//        menuItem = new JMenuItem(icon);
//        menuItem.setMnemonic(KeyEvent.VK_D);
//        menuItem.addActionListener(this);
//        submenu.add(menuItem);
//
        //a group of radio button menu items
        submenu.addSeparator();
//        ButtonGroup group = new ButtonGroup();
        
        cut = new JMenuItem("Cut",
                                 KeyEvent.VK_T);
        cut.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_X, ActionEvent.CTRL_MASK));
        cut.getAccessibleContext().setAccessibleDescription(
                "This doesn't really do anything");
        cut.setEnabled(false);
        cut.addActionListener(this);
        submenu.add(cut);
        
        copy = new JMenuItem("Copy",
                                 KeyEvent.VK_T);
        copy.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_C, ActionEvent.CTRL_MASK));
        copy.getAccessibleContext().setAccessibleDescription(
                "This doesn't really do anything");
        copy.setEnabled(false);
        copy.addActionListener(this);
        submenu.add(copy);
        
        paste = new JMenuItem("Paste",
                                 KeyEvent.VK_T);
        paste.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_V, ActionEvent.CTRL_MASK));
        paste.getAccessibleContext().setAccessibleDescription(
                "This doesn't really do anything");
        paste.setEnabled(false);
        paste.addActionListener(this);
        submenu.add(paste);
        
        delete = new JMenuItem("Delete");
//        delete.setAccelerator(KeyStroke.getKeyStroke(
//                KeyEvent.VK_V, ActionEvent.CTRL_MASK));
        delete.getAccessibleContext().setAccessibleDescription(
                "This doesn't really do anything");
        delete.setEnabled(false);
        delete.addActionListener(this);
        submenu.add(delete);
        
         //a group of radio button menu items
        submenu.addSeparator();
//        ButtonGroup group2 = new ButtonGroup();
        
        selectAll = new JMenuItem("Select All",
                                 KeyEvent.VK_T);
        selectAll.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_A, ActionEvent.CTRL_MASK));
        selectAll.getAccessibleContext().setAccessibleDescription(
                "This doesn't really do anything");
        selectAll.setEnabled(false);
        selectAll.addActionListener(this);
        submenu.add(selectAll);
        
        //Build third menu in the menu bar.
        helpmenu = new JMenu("Help");
        helpmenu.setMnemonic(KeyEvent.VK_N);
        helpmenu.getAccessibleContext().setAccessibleDescription(
                "This menu does nothing");
        menuBar.add(helpmenu);

        openPDF = new JMenuItem("Open help as PDF",
                                 KeyEvent.VK_T);
//        undo.setAccelerator(KeyStroke.getKeyStroke(
//                KeyEvent.VK_Z, ActionEvent.CTRL_MASK));
        openPDF.getAccessibleContext().setAccessibleDescription(
                "This doesn't really do anything");
        openPDF.addActionListener(new ActionListener(){
          public void actionPerformed(ActionEvent e)
          {
            Launch.openPDF("docs/cb/manual.pdf");
            System.out.println("Launch docs/cb/manual.pdf");
          }
        }
        );   
        helpmenu.add(openPDF);
        
        openBrowser = new JMenuItem("Open help in Browser",
                                 KeyEvent.VK_T);
//        undo.setAccelerator(KeyStroke.getKeyStroke(
//                KeyEvent.VK_Z, ActionEvent.CTRL_MASK));
        openBrowser.getAccessibleContext().setAccessibleDescription(
                "This doesn't really do anything");
        openBrowser.addActionListener(new ActionListener(){
          public void actionPerformed(ActionEvent e)
          {
            Launch.openURL("docs/cb/index.html");
            System.out.println("Launch docs/cb/index.html");
          }
        }
        );
         helpmenu.add(openBrowser);
        
        indivSel = new JMenuItem("Individual Selection",
                                 KeyEvent.VK_T);
//        undo.setAccelerator(KeyStroke.getKeyStroke(
//                KeyEvent.VK_Z, ActionEvent.CTRL_MASK));
        indivSel.getAccessibleContext().setAccessibleDescription(
                "This doesn't really do anything");
        indivSel.setEnabled(false);
        indivSel.addActionListener(this);
        helpmenu.add(indivSel);
        
        introPanel = new JMenuItem("Intro tab Panel",
                                 KeyEvent.VK_T);
//        undo.setAccelerator(KeyStroke.getKeyStroke(
//                KeyEvent.VK_Z, ActionEvent.CTRL_MASK));
        introPanel.getAccessibleContext().setAccessibleDescription(
                "This doesn't really do anything");
        introPanel.addActionListener(new ActionListener(){
          public void actionPerformed(ActionEvent e)
          {
            Launch.openURL("docs/cb/intr.html");
            System.out.println("Launch docs/cb/intr.html");
          }
        }
        );
        helpmenu.add(introPanel);
        
        compPanel = new JMenuItem("Compare Color tab Panel",
                                 KeyEvent.VK_T);
//        undo.setAccelerator(KeyStroke.getKeyStroke(
//                KeyEvent.VK_Z, ActionEvent.CTRL_MASK));
        compPanel.getAccessibleContext().setAccessibleDescription(
                "This doesn't really do anything");
        compPanel.addActionListener(new ActionListener(){
          public void actionPerformed(ActionEvent e)
          {
            Launch.openURL("docs/cb/cmpr.html");
            System.out.println("Launch docs/cb/cmbr.html");
          }
        }
        );
        helpmenu.add(compPanel);
        
        makePanel = new JMenuItem("Make Colors tab Panel",
                                 KeyEvent.VK_T);
//        undo.setAccelerator(KeyStroke.getKeyStroke(
//                KeyEvent.VK_Z, ActionEvent.CTRL_MASK));
        makePanel.getAccessibleContext().setAccessibleDescription(
                "This doesn't really do anything");
        makePanel.addActionListener(new ActionListener(){
          public void actionPerformed(ActionEvent e)
          {
            Launch.openURL("docs/cb/make.html");
            System.out.println("Launch docs/cb/make.html");
          }
        }
        );
        helpmenu.add(makePanel);
        
        playPanel = new JMenuItem("Play with Color tab Panel",
                                 KeyEvent.VK_T);
//        undo.setAccelerator(KeyStroke.getKeyStroke(
//                KeyEvent.VK_Z, ActionEvent.CTRL_MASK));
        playPanel.getAccessibleContext().setAccessibleDescription(
                "This doesn't really do anything");
        playPanel.addActionListener(new ActionListener(){
          public void actionPerformed(ActionEvent e)
          {
            Launch.openURL("docs/cb/play.html");
            System.out.println("Launch docs/cb/play.html");
          }
        }
        );
        helpmenu.add(playPanel);
        
        testPanel = new JMenuItem("Test Yourself tab Panel",
                                 KeyEvent.VK_T);
//        undo.setAccelerator(KeyStroke.getKeyStroke(
//                KeyEvent.VK_Z, ActionEvent.CTRL_MASK));
        testPanel.getAccessibleContext().setAccessibleDescription(
                "This doesn't really do anything");
        testPanel.addActionListener(new ActionListener(){
          public void actionPerformed(ActionEvent e)
          {
            Launch.openURL("docs/cb/test.html");
            System.out.println("Launch docs/cb/test.html");
          }
        }
        );
        helpmenu.add(testPanel);
        
        colSpacPanel = new JMenuItem("Color Spaces tab Panel",
                                 KeyEvent.VK_T);
//        undo.setAccelerator(KeyStroke.getKeyStroke(
//                KeyEvent.VK_Z, ActionEvent.CTRL_MASK));
        colSpacPanel.getAccessibleContext().setAccessibleDescription(
                "This doesn't really do anything");
        colSpacPanel.addActionListener(new ActionListener(){
          public void actionPerformed(ActionEvent e)
          {
            Launch.openURL("docs/cb/cspc.html");
            System.out.println("Launch docs/cb/cspc.html");
          }
        }
        );
        helpmenu.add(colSpacPanel);
        
        checkPanel = new JMenuItem("Check Display's Color tab Panel",
                                 KeyEvent.VK_T);
//        undo.setAccelerator(KeyStroke.getKeyStroke(
//                KeyEvent.VK_Z, ActionEvent.CTRL_MASK));
        checkPanel.getAccessibleContext().setAccessibleDescription(
                "This doesn't really do anything");
        checkPanel.addActionListener(new ActionListener(){
          public void actionPerformed(ActionEvent e)
          {
            Launch.openURL("docs/cb/chkc.html");
            System.out.println("Launch docs/cb/chkc.html");
          }
        }
        );
        helpmenu.add(checkPanel);
        
        aboutPanel = new JMenuItem("About tab Panel",
                                 KeyEvent.VK_T);
//        undo.setAccelerator(KeyStroke.getKeyStroke(
//                KeyEvent.VK_Z, ActionEvent.CTRL_MASK));
        aboutPanel.getAccessibleContext().setAccessibleDescription(
                "This doesn't really do anything");
        aboutPanel.addActionListener(new ActionListener(){
          public void actionPerformed(ActionEvent e)
          {
            Launch.openURL("docs/cb/abot.html");
            System.out.println("Launch docs/cb/abot.html");
          }
        }
        );
        helpmenu.add(aboutPanel);
        
        helpPanel = new JMenuItem("Help Menu",
                                 KeyEvent.VK_T);
//        undo.setAccelerator(KeyStroke.getKeyStroke(
//                KeyEvent.VK_Z, ActionEvent.CTRL_MASK));
        helpPanel.getAccessibleContext().setAccessibleDescription(
                "This doesn't really do anything");
        helpPanel.addActionListener(new ActionListener(){
          public void actionPerformed(ActionEvent e)
          {
            Launch.openURL("docs/cb/help.html");
            System.out.println("Launch docs/cb/help.html");
          }
        }
        );
        helpmenu.add(helpPanel);     

        return menuBar;
    }

    public Container createContentPane() {
        //Create the content-pane-to-be.
        JPanel contentPane = new JPanel(new BorderLayout());
        contentPane.setOpaque(true);

        //Create a scrolled text area.
        output = new JTextArea(5, 30);
        output.setEditable(false);
        scrollPane = new JScrollPane(output);

        //Add the text area to the content pane.
        contentPane.add(scrollPane, BorderLayout.CENTER);

        return contentPane;
    }

    public void actionPerformed(ActionEvent e) {
        JMenuItem source = (JMenuItem)(e.getSource());
        String s = "Action event detected."
                   + newline
                   + "    Event source: " + source.getText()
                   + " (an instance of " + getClassName(source) + ")";
        output.append(s + newline);
        output.setCaretPosition(output.getDocument().getLength());
    }

    public void itemStateChanged(ItemEvent e) {
        JMenuItem source = (JMenuItem)(e.getSource());
        String s = "Item event detected."
                   + newline
                   + "    Event source: " + source.getText()
                   + " (an instance of " + getClassName(source) + ")"
                   + newline
                   + "    New state: "
                   + ((e.getStateChange() == ItemEvent.SELECTED) ?
                     "selected":"unselected");
        output.append(s + newline);
        output.setCaretPosition(output.getDocument().getLength());
    }

    // Returns just the class name -- no package info.
    protected String getClassName(Object o) {
        String classString = o.getClass().getName();
        int dotIndex = classString.lastIndexOf(".");
        return classString.substring(dotIndex+1);
    }

    /** Returns an ImageIcon, or null if the path was invalid. */
//    protected static ImageIcon createImageIcon(String path) {
//        java.net.URL imgURL = MenuDemo.class.getResource(path);
//        if (imgURL != null) {
//            return new ImageIcon(imgURL);
//        } else {
//            System.err.println("Couldn't find file: " + path);
//            return null;
//        }
//    }

    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event-dispatching thread.
     */
    private static void createAndShowGUI() {
        //Create and set up the window.
        JFrame frame = new JFrame("MenuDemo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Create and set up the content pane.
        Menu menu = new Menu();
        frame.setJMenuBar(menu.createMenuBar());
        frame.setContentPane(menu.createContentPane());

        //Display the window.
        frame.setSize(450, 260);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
//      MenuExp me = new MenuExp();
//      me.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }
}
