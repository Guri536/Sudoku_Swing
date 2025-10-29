# **Java Sudoku Game**

A simple Sudoku game built using Java Swing. This application provides a 9x9 Sudoku grid with a graphical user interface for users to play.

## **Features**

* **New Puzzles:** Generates a new random Sudoku puzzle with a "New Game" button.  
* **Interactive UI:** Players can click on cells and type in numbers from 1 to 9\.  
* **Input Validation:** Only valid numbers (1-9) can be entered into a cell.  
* **Real-time Feedback:** Cells change color to indicate a correct (Green) or incorrect (Red) guess.  
* **Scoring System:** Players gain points for correct guesses and lose a small number of points for incorrect ones.  

## **Getting Started**

Follow these instructions to get a copy of the project up and running on your local machine.

### **Prerequisites**

You will need a Java Development Kit (JDK) installed on your system to compile and run the application.

### **Installation & Running**

1. Clone the repository:  
   Open your terminal or command prompt and use the following command to clone the repo (replace \[REPOSITORY\_URL\] with the actual URL):  
```bash []
git clone https://github.com/Guri536/Sudoku_Swing.git
```

2. Navigate to the project directory:  
   Change into the directory created by cloning.  
```bash
cd sudoku_swing 
```
3. Compile the code:  
   Navigate to the sudoku/game directory where the .java files are located.  
```bash
cd sudoku/game  
javac \*.java
```

4. Run the application:  
   Go back up one directory (to be in the main project folder) and run the SudokuMain class:  
```bash
cd ..  
java sudoku.game.SudokuMain
```
   This will launch the Sudoku game window.

## **Project Files**

* **SudokuMain.java**: The main entry point for the application. It creates the main window (JFrame) and assembles the UI components.  
* **GameBoardPanel.java**: The core UI component that displays the 9x9 Sudoku grid. It manages the game state, user input, and scoring.  
* **Cell.java**: Represents a single cell in the Sudoku grid. It is a custom JTextField that handles input and visual status changes.  
* **Puzzle.java**: Manages the game's logic, holding the solution and the user-visible puzzle. It uses PuzzleGenerator to create new games.  
* **PuzzleGenerator.java**: Responsible for generating new, solvable Sudoku boards.  
* **CellStatus.java**: An enum that defines the different states a cell can be in (e.g., GIVEN, TO\_GUESS, CORRECT\_GUESS).  
* **SudokuConstants.java**: A utility class to store constant values, such as the GRID\_SIZE.