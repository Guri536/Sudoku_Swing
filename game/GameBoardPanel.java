package sudoku.game;

import java.awt.*;
import java.awt.event.*;
import java.io.Serial;
import java.util.Arrays;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class GameBoardPanel extends JPanel {
    @Serial
    private static final long serialVersionUID = 1L;

    public static final int CELL_SIZE = 60;
    public static final int BOARD_WIDTH = CELL_SIZE * SudokuConstants.GRID_SIZE;
    public static final int BOARD_HEIGHT = CELL_SIZE * SudokuConstants.GRID_SIZE;

    private final Cell[][] cells = new Cell[SudokuConstants.GRID_SIZE][SudokuConstants.GRID_SIZE];
    private final Puzzle puzzle = new Puzzle();

    private JLabel scoreLabel;
    private int score;
    public boolean isACtive = false;

    public GameBoardPanel() {
        super.setLayout(new GridLayout(SudokuConstants.GRID_SIZE, SudokuConstants.GRID_SIZE));

        for (int row = 0; row < SudokuConstants.GRID_SIZE; ++row) {
            for (int col = 0; col < SudokuConstants.GRID_SIZE; ++col) {
                cells[row][col] = new Cell(row, col);
                super.add(cells[row][col]);

                int left = (col % 3 == 0) ? 3 : 1;
                int bottom = ((row + 1) % 3 == 0) ? 3 : 1;

                cells[row][col].setBorder(
                        BorderFactory.createMatteBorder(
                                0, left, bottom, 0, Color.BLACK
                        )
                );
            }
        }

        for (int row = 0; row < SudokuConstants.GRID_SIZE; ++row) {
            for (int col = 0; col < SudokuConstants.GRID_SIZE; ++col) {
                if (cells[row][col].isEditable()) {
                    cells[row][col].addFocusListener(new cellFocusManager(cells[row][col]));
                    cells[row][col].getDocument().addDocumentListener(new CellDocumentListener(cells[row][col]));
                }
            }
        }

        super.setPreferredSize(new Dimension(BOARD_WIDTH, BOARD_HEIGHT));
    }

    public void newGame() {
        isACtive = false;
        puzzle.newPuzzle();
        System.out.println(Arrays.deepToString(puzzle.numbers));
        System.out.println(Arrays.deepToString(puzzle.isGiven));

        score = 0;
        updateScoreLabel(null);

        for (int row = 0; row < SudokuConstants.GRID_SIZE; ++row) {
            for (int col = 0; col < SudokuConstants.GRID_SIZE; ++col) {
                cells[row][col].newGame(puzzle.numbers[row][col], puzzle.isGiven[row][col]);
            }
        }
        isACtive = true;
    }

    public void setScore(JLabel scoreLabel){
        this.scoreLabel = scoreLabel;
    }

    private void updateScoreLabel(String state)
    {
        if(state == null) state = "";
        if (scoreLabel != null) {
            scoreLabel.setText("Score: " + score + " " + state);
        }
    }

    public boolean isSolved() {
        for (int row = 0; row < SudokuConstants.GRID_SIZE; ++row) {
            for (int col = 0; col < SudokuConstants.GRID_SIZE; ++col) {
                if (cells[row][col].status == CellStatus.TO_GUESS ||
                        cells[row][col].status == CellStatus.WRONG_GUESS) {
                    return false;
                }
            }
        }
        return true;
    }

    private class CellDocumentListener implements DocumentListener {
        private final Cell sourceCell;

        public CellDocumentListener(Cell cell) {
            this.sourceCell = cell;
        }

        @Override
        public void insertUpdate(DocumentEvent e) {
            handleCellChange();
        }

        @Override
        public void removeUpdate(DocumentEvent e) {
            if (!sourceCell.isEditable()) {
                return;
            }
            sourceCell.status = CellStatus.TO_GUESS;
            sourceCell.paint();
        }

        @Override
        public void changedUpdate(DocumentEvent e) {
        }

        private void handleCellChange() {
            if (!sourceCell.isEditable() || !isACtive) {
                return;
            }

            if (!sourceCell.getText().isEmpty() && sourceCell.status != CellStatus.GIVEN) {
                int numberIn = Integer.parseInt(sourceCell.getText());
//                System.out.println("You entered " + numberIn);

                if (numberIn == sourceCell.number) {
                    sourceCell.status = CellStatus.CORRECT_GUESS;
                    sourceCell.setEnabled(false);
                    incrementScore();
                } else {
                    sourceCell.status = CellStatus.WRONG_GUESS;
                    decrementScore();
                }
                scoreLabel.updateUI();

                sourceCell.paint();

                if (isSolved()) {
                    JOptionPane.showMessageDialog(null, "Congratulations! You solved the puzzle!");
                }
            }
        }
    }

    private void incrementScore(){
        score += 100;
        scoreLabel.setBackground(Cell.BG_CORRECT_GUESS.brighter());
        updateScoreLabel("(+100)");
    }

    private void decrementScore(){
        score -= 25;
        score = Math.max(0, score);
        scoreLabel.setBackground(Cell.BG_WRONG_GUESS.brighter());
        updateScoreLabel("(-25)");
    }

    private record cellFocusManager(Cell cell) implements FocusListener {
        @Override
        public void focusGained(FocusEvent e) {
            cell.setBackground(Cell.ACTIVE_CELL);
        }

        @Override
        public void focusLost(FocusEvent e) {
            if (cell.isEnabled()) {
                cell.setBackground(Cell.BG_TO_GUESS);
            }
        }
    }

}
