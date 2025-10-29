package sudoku;

import java.util.*;

/**
 * Randomly generates 9 * 9 sudoku boards and puzzles with difficulty.
 * <p>
 * A valid sudoku board:
 * <p>
 * * Each of the digits 1-9 must occur exactly once in each row.
 * * Each of the digits 1-9 must occur exactly once in each column.
 * * Each of the digits 1-9 must occur exactly once in each of the 9 3*3
 * blocks of the board.
 * <p>
 * A valid sudoku puzzle:
 * <p>
 * * Has a random number of empty cells.
 * * Has one and only one solution.
 *
 * @author Esther Lin
 */
public class PuzzleGenerator {

    // arrays of each level's minimal and maximal difficulty
    static final int[] LV_MIN_DIFF = new int[]{0, 26, 40, 50, 100, 200, 300};
    static final int[] LV_MAX_DIFF = new int[]{0, 27, 41, 100, 200, 300, 2000};

    private final Random rand;

    public PuzzleGenerator(long seed) {
        rand = new Random(seed);
    }

    /**
     * Returns the given number of randomly generated 9*9 sudoku boards.
     * <p>
     * the number of sudoku boards to generate
     *
     * @return an array containing the given number of randomly generated sudoku boards
     */
    public int[][] generateBoards() {
        int[][] board = new int[9][9];
        boolean[] flag = new boolean[10];

        // randomly fills the top-most 3 blocks by
        for (int k = 0; k < 9; k += 3) {                // block
            for (int i = 0; i < 3; i++) {                // row
                while (true) {
                    validate(board, i, k, flag);
                    for (int j = k; j < k + 3; j++) {    // cell
                        int r = randomCandidate(flag);
                        board[i][j] = r;
                        flag[r] = false;
                    }
                    // for the second row of the middle block, after randomly
                    // filling numbers, check if the next row has 3 candidates.
                    // If not, revert and try again.
                    if (k != 3 || i != 1) break;
                    validate(board, i + 1, k, flag);
                    if (numOfcandidates(flag) == 3) break;
                    for (int j = k; j < k + 3; j++) {
                        board[i][j] = 0;
                    }
                }
            }
        }

        // randomly fills the left-most 2 blocks except the one at the top
        for (int k = 3; k < 9; k += 3) {                // block
            for (int j = 0; j < 3; j++) {                // column
                while (true) {
                    validate(board, k, j, flag);
                    for (int i = k; i < k + 3; i++) {    // cell
                        int r = randomCandidate(flag);
                        board[i][j] = r;
                        flag[r] = false;
                    }
                    // for the second column of the middle block, after randomly
                    // filling numbers, checks if the next column has 3 candidates.
                    // If not, revert and try again.
                    if (k != 3 || j != 1) break;
                    validate(board, k, j + 1, flag);
                    if (numOfcandidates(flag) == 3) break;
                    for (int i = k; i < k + 3; i++) {
                        board[i][j] = 0;
                    }
                }
            }
        }

        solve(board, 30);
        printBoard(board);// solves the other 4 blocks
        return board;
    }

    private int randomCandidate(boolean[] flag) {
        while (true) {
            int r = rand.nextInt(9) + 1;
            if (flag[r]) return r;
        }
    }

    public static void main(String[] args){
        Random random = new Random();
        Long rand = random.nextLong();
        System.out.println(rand);
        PuzzleGenerator pz = new PuzzleGenerator(rand);
        pz.generateBoards();
    }

    /**
     * Returns the number of valid candidates in the given flag.
     *
     * @param flag the array of whether each of the digits 1-9 is valid
     * @return the number of valid candidates in flag
     */
    private int numOfcandidates(boolean[] flag) {
        int res = 0;
        for (int k = 1; k <= 9; k++) {
            if (flag[k]) res++;
        }
        return res;
    }

    /**
     * Backtracks to determine if the given board is solvable, and fills the board
     * with the solution.
     *
     * @param board the 9*9 sudoku board
     * @param p     the cell number to start with
     * @return true if the sudoku is solvable, otherwise returns false
     */
    private boolean solve(int[][] board, int p) {
        if (p == 81) return true;    // solution found
        int i = p / 9;
        int j = p % 9;
        if (board[i][j] > 0) return solve(board, p + 1);    // skips filled cell

        boolean[] flag = new boolean[10];
        validate(board, i, j, flag);
        for (int k = 1; k <= 9; k++) {    // tries with each valid candidate
            if (flag[k]) {
                board[i][j] = k;
                if (solve(board, p + 1)) return true;    // solves next cell
            }
        }
        board[i][j] = 0;    // board not solvable, reverts filled value
        return false;
    }

    /**
     * Updates flag for the given cell by checking its row, column and block.
     *
     * @param board the 9*9 sudoku board
     * @param i     the index of row
     * @param j     the index of column
     * @param flag  the array of whether each of the digits 1-9 is valid
     */
    private void validate(int[][] board, int i, int j, boolean[] flag) {
        Arrays.fill(flag, true);
        for (int k = 0; k < 9; k++) {
            if (board[i][k] > 0) flag[board[i][k]] = false;    // validate row
            if (board[k][j] > 0) flag[board[k][j]] = false;    // validate column
            int r = i / 3 * 3 + k / 3;
            int c = j / 3 * 3 + k % 3;
            if (board[r][c] > 0) flag[board[r][c]] = false;    // validate block
        }
    }

    public void printBoard(int[][] board) {
        for (int i = 0; i < 9; i++) {
            System.out.print("[");
            for (int j = 0; j < 8; j++) {
                System.out.print(board[i][j] + ",");
            }
            System.out.println(board[i][8] + "]");
        }
    }

}
