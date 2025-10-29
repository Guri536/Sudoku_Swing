package sudoku.game;

import java.awt.*;
import java.awt.event.*;
import java.io.Serial;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

/**
 * The main Sudoku program
 */
public class SudokuMain extends JFrame {
    @Serial
    private static final long serialVersionUID = 1L;

    JLabel scoreLabel = new JLabel();
    GameBoardPanel board = new GameBoardPanel();
    JButton btnNewGame = new JButton("New Game");

    public SudokuMain() {
        Container cp = getContentPane();
        cp.setLayout(new BorderLayout());
        cp.add(board, BorderLayout.CENTER);

        Font barFont = new Font("SansSerif", Font.BOLD, 18);
        scoreLabel.setHorizontalAlignment(JLabel.CENTER);
        scoreLabel.setFont(barFont);
        scoreLabel.setBorder(new EmptyBorder(10, 10, 10, 10));
        btnNewGame.setFont(barFont);
        board.setScore(scoreLabel);

        JPanel buttonPanel = new JPanel(new GridLayout(1, 2));
        buttonPanel.add(scoreLabel);
        buttonPanel.add(btnNewGame);
        cp.add(buttonPanel, BorderLayout.SOUTH);

        btnNewGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                board.newGame();
            }
        });

        board.newGame();

        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Sudoku");
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new SudokuMain();
            }
        });
    }
}
