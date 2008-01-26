package sudoku.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import sudoku.SudokuSolver;

public class MenuInput implements ActionListener, ItemListener {

	private BoardView boardView;

	private SudokuSolver sudokuSolver;

	public MenuInput(BoardView boardView, SudokuSolver sudokuSolver) {
		this.boardView = boardView;
		this.sudokuSolver = sudokuSolver;
	}

	public void actionPerformed(ActionEvent actionEvent) {
		try {
			sudokuSolver.attemptSolution();
		} catch (Exception e) {
		}
		boardView.repaint();
	}

	public void itemStateChanged(ItemEvent itemEvent) {
	}

}
