// File: TTTPanel.java
// Author: Dr. Watts
// Contents: This file contains an application program to play
// Tic Tac Toe using a simple GUI interface.

package com.dynet.kjanssen;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class TTTPanel extends JPanel
{
    private TicTacToe ttt;
    private int player;
    private int winner;
    private boolean done;
    private Font font;
    private int wins [];
    private JLabel title, stats;
    private DisplayPanel display;
    private Image imageX, imageO;

    TTTPanel ()
    {
        ttt = new TicTacToe ();
        player = 1;
        winner = 0;
        done = false;
        wins = new int [4];
        setBackground (Color.WHITE);
        font = new Font ("Arial", Font.PLAIN, 30);
        title = new JLabel ("Tic Tac Toe");
        title.setFont (new Font ("Arial", Font.PLAIN, 40));
        add(title);
        display = new DisplayPanel ();
        add (display);
        stats = new JLabel ("X: " + wins[1] + " O: " + wins[2] + " Cat: " + wins[3]);
        stats.setFont (font);
        add (stats);
        imageX = new ImageIcon(getClass().getResource("X.jpg")).getImage();
        imageO = new ImageIcon(getClass().getResource("O.jpg")).getImage();
    }

    void UpdatePanel()
    {
        String who = new String ();
        done = true;
        switch (winner)
        {
            case 1: who = "X"; break;
            case 2: who = "O"; break;
            case 3: who = "Cat"; break;
        }
        stats.setText (who + " wins!!");
        stats.repaint ();
        int delay = 2000; //milliseconds
        ActionListener taskPerformer = new ActionListener()
        {
            public void actionPerformed(ActionEvent evt)
            {
                wins[winner]++;
                ttt = new TicTacToe ();
                display = new DisplayPanel();
                add (display);
                stats.setText ("X: " + wins[1]
                        + " O: " + wins[2]
                        + " Cat: "
                        + wins[3]);
                stats.repaint();
                done = false;

                if (player == 2) {
                    ttt.MakeBestPlay (player);
                    player = player == 1 ? 2 : 1;
                }
            }
        };
        Timer timer = new Timer(delay, taskPerformer);
        timer.setRepeats(false);
        timer.start();
    }

    class DisplayPanel extends JPanel
    {
        TTTSquare [] squares;
        DisplayPanel ()
        {
            setLayout(new GridLayout (3, 3, 2, 2));
            setBackground (Color.BLACK);
            squares = new TTTSquare [9];
            int s = 0;
            for (int r = 1; r <=3; r++)
                for (int c = 1; c <= 3; c++)
                {
                    squares[s] = new TTTSquare (r, c);
                    add (squares[s]);
                    s++;
                }
        }

        public void paintSquares () {
            for (int s = 0; s < 9; s++)
                squares[s].repaint();
        }

        public void paintComponent (Graphics g)
        { // paint the Tic Tac Toe game display
            super.paintComponent (g);
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint (RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);
            for (int s = 0; s < 9; s++)
                squares[s].repaint();
        }

        class TTTSquare extends JPanel implements MouseListener
        {
            int row, col;
            TTTSquare (int R, int C)
            {
                row = R;
                col = C;
                setPreferredSize (new Dimension(100, 100));
                setBackground (Color.WHITE);
                addMouseListener (this);
            }
            public void paintComponent (Graphics g)
            { // paint this Tic Tac Toe game square
                super.paintComponent (g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint (RenderingHints.KEY_ANTIALIASING,
                        RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setFont (font);
                char who = ttt.GetPosition (row, col);
                if (who == '1')
                    g2.drawImage(imageX, 0, 0, 100, 100, null);
                else if (who == '2')
                    g2.drawImage(imageO, 0, 0, 100, 100, null);
            }
            public void mouseClicked (MouseEvent e)
            { // Accept user's move; update if legal
                boolean goodMove = ttt.Play (row, col, player);
                if (goodMove && !done)
                {
                    player = player == 1 ? 2 : 1;
                    repaint();
                    winner = ttt.Test();
                    if (winner > 0)
                        UpdatePanel ();
                    else {
                        goodMove = ttt.MakeBestPlay (player);
                        if (goodMove && !done)
                        {
                            player = player == 1 ? 2 : 1;

                            int delay = 500; //milliseconds
                            ActionListener taskPerformer = new ActionListener()
                            {
                                public void actionPerformed(ActionEvent evt)
                                {
                                    paintSquares();
                                    winner = ttt.Test();
                                    if (winner > 0)
                                        UpdatePanel ();
                                }
                            };
                            Timer timer = new Timer(delay, taskPerformer);
                            timer.setRepeats(false);
                            timer.start();
                        }
                    }
                }
            }
            public void mousePressed (MouseEvent event) {}
            public void mouseReleased (MouseEvent event) {}
            public void mouseEntered (MouseEvent event) {}
            public void mouseExited (MouseEvent event) {}
        }
    }

    public static void main (String [] args)
    {
        JFrame frame = new JFrame ("Tic Tac Toe");
        TTTPanel tttPanel = new TTTPanel ();
        frame.getContentPane().add (tttPanel);
        frame.setSize (312, 430);
        frame.setLocation (600, 400);
        frame.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
        frame.setResizable (false);
        frame.setVisible (true);
    }
}
