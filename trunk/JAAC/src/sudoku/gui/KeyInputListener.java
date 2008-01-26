package sudoku.gui;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import sudoku.SudokuSolver;

public class KeyInputListener implements KeyListener {

	private BoardView boardView;

	private SudokuSolver sudokuSolver;

	public KeyInputListener(BoardView boardView, SudokuSolver sudokuSolver) {
		this.boardView = boardView;
		this.sudokuSolver = sudokuSolver;
	}

	public void keyPressed(KeyEvent e) {
	}

	public void keyReleased(KeyEvent e) {
	}

	public void keyTyped(KeyEvent e) {
		int n = e.getKeyChar() - '0';
		if (boardView.getActiveSquare() != null && 0 <= n && n <= 9) {
			boardView.getActiveSquare().setHasFocus(false);
			boardView.getBoard().setValue(n,
					boardView.getActiveSquare().getBoardPositionY(),
					boardView.getActiveSquare().getBoardPositionX());
			resetPencilmarks();
			boardView.repaint();
		}
	}

	private void resetPencilmarks() {
		sudokuSolver.resetPencilmarks();
		sudokuSolver.align();
		sudokuSolver.eliminationStep();
	}

}
