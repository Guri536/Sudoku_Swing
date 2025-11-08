package sudoku.game;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class SudokuMain extends JFrame {
    GameBoardPanel board = new GameBoardPanel();
    JButton btnNewGame = new JButton("New Game");

    public SudokuMain() {
        Container cp = getContentPane();
        JLabel scoreLabel = new JLabel();

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

        InputMap inputMap = ((JPanel) cp).getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap actionMap = ((JPanel) cp).getActionMap();

        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "takeCapture");
        actionMap.put("takeCapture", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                captureImage();
            }
        });

        board.newGame();

        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Sudoku");
        setVisible(true);
    }

    public void captureImage(){
        try {
            Robot robot = new Robot();

            Rectangle captureArea = this.getBounds();
            BufferedImage screenCapture = robot.createScreenCapture(captureArea);
            File outputFile = new File("screen_capture.png");
            ImageIO.write(screenCapture, "png", outputFile);
            System.out.println("Full screen capture saved to: " +
                        outputFile.getAbsolutePath());

        } catch (AWTException | IOException e) {
            e.printStackTrace();
        }
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
