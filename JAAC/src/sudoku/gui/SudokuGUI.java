package sudoku.gui;

import static javax.swing.JFrame.EXIT_ON_CLOSE;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;

import sudoku.SudokuSolver;

public class SudokuGUI {

	private static SudokuSolver sudokuSolver;

	private static BoardView boardView;

	private static KeyInputListener keyListener;

	static {
		sudokuSolver = new SudokuSolver();
		boardView = new BoardView(sudokuSolver);
		keyListener = new KeyInputListener(boardView, sudokuSolver);
	}

	private static JMenuBar getMenu() {

		JMenuBar menuBar = new JMenuBar();

		JMenu fileMenu = new JMenu("File");
		fileMenu.add("New");
		fileMenu.add("Exit");

		JMenu editMenu = new JMenu("Edit");
		editMenu.add("Undo");
		editMenu.add("Redo");

		JMenu puzzleMenu = new JMenu("Puzzle");
		puzzleMenu.add("Set as Start");
		puzzleMenu.add("Restart");
		puzzleMenu.add("Solve");

		JMenu viewMenu = new JMenu("View");
		viewMenu.add("Show Pencil Marks");
		viewMenu.add("Watch Solver");

		JMenu helpMenu = new JMenu("Help");
		helpMenu.add("About");

		menuBar.add(fileMenu);
		menuBar.add(editMenu);
		menuBar.add(viewMenu);
		menuBar.add(puzzleMenu);
		menuBar.add(helpMenu);

		return menuBar;
	}

	public static void main(String[] args) {

		JFrame frame = new JFrame("  Sudoku Solver");
		frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
		frame.setResizable(false);

		frame.addKeyListener(keyListener);

		frame.setJMenuBar(getMenu());

		frame.getContentPane().add(boardView);

		frame.pack();
		frame.setVisible(true);

	}

}
