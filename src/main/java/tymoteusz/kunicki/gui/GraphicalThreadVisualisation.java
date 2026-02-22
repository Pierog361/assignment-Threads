package tymoteusz.kunicki.gui;

import tymoteusz.kunicki.board.Board;

import javax.swing.*;
import java.awt.*;

public class GraphicalThreadVisualisation extends JFrame implements Runnable {
    private JTextPane boardTextPanel;
    private JPanel panel1;

    private final Board board;

    public GraphicalThreadVisualisation(Board board) {
        setSize(400, 400);
        setTitle("BSS Thread Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(panel1);
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);

        setUpTextPanel();

        this.board = board;
    }

    @Override
    public void run() {
        boolean running = true;

        while (running) {
            updateTheBoard();
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                running = false;
            }
        }
    }

    public synchronized void updateTheBoard() {
        boardTextPanel.setText(board.toString());
    }

    private void setUpTextPanel() {
        //Seting up the boardtextPanel
        boardTextPanel.setPreferredSize(new Dimension(330, 230));
        boardTextPanel.setMinimumSize(new Dimension(330, 230));
        boardTextPanel.setMinimumSize(new Dimension(330, 230));
        boardTextPanel.setFont(new Font("Monospaced", Font.PLAIN, 19));

        boardTextPanel.setAlignmentX(JTextPane.CENTER_ALIGNMENT);
        boardTextPanel.setAlignmentY(JTextPane.CENTER_ALIGNMENT);
    }
}
