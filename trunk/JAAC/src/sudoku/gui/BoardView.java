package sudoku.gui;

import static java.awt.Color.BLACK;

import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import sudoku.IBoard;
import sudoku.IPencilmarks;
import sudoku.SudokuSolver;

@SuppressWarnings("serial")
public class BoardView extends JPanel {

	private Square activeSquare = null;

	private SudokuSolver sudokuSolver;

	public BoardView(SudokuSolver sudokuSolver) {
		this.sudokuSolver = sudokuSolver;
		setBorder(BorderFactory.createLineBorder(BLACK));
		setLayout(new GridLayout(3, 3));
		for (int n = 0; n < 9; n++)
			add(new Box(this, n));
	}

	public Square getActiveSquare() {
		return activeSquare;
	}

	public IBoard getBoard() {
		return sudokuSolver.getBoard();
	}

	public IPencilmarks getPencilmarks() {
		return sudokuSolver.getPencilmarks();
	}

	public void setActiveSquare(Square activeSquare) {
		this.activeSquare = activeSquare;
	}

}
