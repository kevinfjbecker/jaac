package sudoku.gui;

import static javax.swing.JFrame.EXIT_ON_CLOSE;

import javax.swing.JFrame;

import sudoku.SudokuSolver;

public class SudokuGUI {

	private static SudokuSolver sudokuSolver;

	private static BoardView boardView;

	private static KeyInput keyInput;

	private static MenuInput menuInput;

	static {
		sudokuSolver = new SudokuSolver();
		boardView = new BoardView(sudokuSolver);
		keyInput = new KeyInput(boardView, sudokuSolver);
		menuInput = new MenuInput(boardView, sudokuSolver);
	}

	public static void main(String[] args) {

		JFrame frame = new JFrame("  Sudoku Solver");
		frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
		frame.setResizable(false);

		frame.addKeyListener(keyInput);

		frame.setJMenuBar(SudokuMenu.getMenu(menuInput));

		frame.getContentPane().add(boardView);

		frame.pack();
		frame.setVisible(true);

	}

}
