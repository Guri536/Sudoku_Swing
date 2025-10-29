package sudoku;

import java.util.Arrays;
import java.util.Random;

public class Puzzle {
    int[][] numbers = new int[SudokuConstants.GRID_SIZE][SudokuConstants.GRID_SIZE];
    boolean[][] isGiven = new boolean[SudokuConstants.GRID_SIZE][SudokuConstants.GRID_SIZE];

    public Puzzle() {
        super();
    }

    public void newPuzzle() {
        Random random = new Random();
        Long seed = random.nextLong();
        System.out.println(seed);
        PuzzleGenerator pz = new PuzzleGenerator(seed);
        int[][] board = pz.generateBoards();

        for (int row = 0; row < SudokuConstants.GRID_SIZE; ++row) {
            System.arraycopy(board[row], 0, numbers[row], 0, SudokuConstants.GRID_SIZE);
        }
        boolean[][] clues = randomOperate();

        for (int row = 0; row < SudokuConstants.GRID_SIZE; ++row) {
            System.arraycopy(clues[row], 0, isGiven[row], 0, SudokuConstants.GRID_SIZE);
        }
    }

    private boolean[][] randomOperate() {
        boolean[][] clues = new boolean[9][9];

        Random rand = new Random();

        for(int i = 0; i < 9; i++){
            for(int j = 0; j < 9; j++){
                clues[i][j] = (rand.nextInt(1, 11) < 6);
            }
        }

        return clues;
    }
}

