package sudoku.game;

import java.awt.*;
import java.io.Serial;
import javax.swing.*;
import javax.swing.text.*;

public class Cell extends JTextField {
    @Serial
    private static final long serialVersionUID = 1L;

    public static final Color BG_GIVEN = new Color(240, 240, 240);
    public static final Color FG_GIVEN = Color.BLACK;
    public static final Color FG_NOT_GIVEN = Color.GRAY;
    public static final Color BG_TO_GUESS = Color.GRAY.brighter();
    public static final Color BG_CORRECT_GUESS = new Color(161, 255, 161);
    public static final Color BG_WRONG_GUESS = new Color(255, 163, 163);
    public static final Font FONT_NUMBERS = new Font("SansSerif", Font.PLAIN, 28);
    public static final Color ACTIVE_CELL = new Color(229, 247, 255);

    int row, col;
    int number;
    CellStatus status;

    public Cell(int row, int col) {
        super();
        this.row = row;
        this.col = col;
        super.setHorizontalAlignment(JTextField.CENTER);
        super.setFont(FONT_NUMBERS);
        super.setDisabledTextColor(Color.BLACK);
        super.setCaretColor(new Color(255, 255, 255, 0));
        super.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        ((AbstractDocument) this.getDocument()).setDocumentFilter(new SudokuInputFilter());
    }

    public void newGame(int number, boolean isGiven) {
        this.number = number;
        status = isGiven ? CellStatus.GIVEN : CellStatus.TO_GUESS;
        this.setEnabled(!isGiven);
        paint();
    }

    public void paint() {
        if (status == CellStatus.GIVEN) {
            super.setText(number + "");
            super.setEditable(false);
            super.setBackground(BG_GIVEN);
            super.setForeground(FG_GIVEN);
        } else if (status == CellStatus.TO_GUESS) {
            super.setText("");
            super.setEditable(true);
            super.setBackground(BG_TO_GUESS);
            super.setForeground(FG_NOT_GIVEN);
        } else if (status == CellStatus.CORRECT_GUESS) {
            super.setBackground(BG_CORRECT_GUESS);
        } else if (status == CellStatus.WRONG_GUESS) {
            super.setBackground(BG_WRONG_GUESS);
        }
    }

    // This class filters input to allow only a single digit from 1-9
    private static class SudokuInputFilter extends DocumentFilter {
        @Override
        public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
            if (string == null) {
                return;
            }
            replace(fb, 0, fb.getDocument().getLength(), string, attr);
        }

        @Override
        public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
            int newLength = fb.getDocument().getLength() - length + text.length();

            if (newLength <= 2 && text.matches("[1-9]?")) {
                if(newLength == 2){
                    super.replace(fb, 0, 1, text, attrs);
                } else super.replace(fb, offset, length, text, attrs);
            }
        }
    }
}


