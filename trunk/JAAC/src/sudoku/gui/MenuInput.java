package sudoku.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.HashMap;

import javax.swing.JMenuItem;

import sudoku.Board;
import sudoku.SudokuSolver;

public class MenuInput implements ActionListener, ItemListener {

	private interface MenuAction {
		void execute();
	}

	private BoardView _boardView;

	private HashMap<String, MenuAction> _menuActions;

	private SudokuSolver _sudokuSolver;

	public MenuInput(BoardView boardView, SudokuSolver sudokuSolver) {
		this._boardView = boardView;
		this._sudokuSolver = sudokuSolver;
		_menuActions = new HashMap<String, MenuAction>();
		populateMenuActions();
	}

	public void actionPerformed(ActionEvent actionEvent) {
		String text = ((JMenuItem) (actionEvent.getSource())).getText();
		MenuAction menuAction = _menuActions.get(text);
		if (menuAction != null) {
			menuAction.execute();
		}
		_boardView.repaint();
	}

	public void itemStateChanged(ItemEvent itemEvent) {
	}

	private void populateMenuActions() {

		_menuActions.put("Solve", new MenuAction() {
			public void execute() {
				try {
					_sudokuSolver.attemptSolution();
				} catch (Exception e) {
				}
			}
		});

		_menuActions.put("Exit", new MenuAction() {
			public void execute() {
				System.exit(0);
			}
		});

		_menuActions.put("New", new MenuAction() {
			public void execute() {
				_sudokuSolver.resetBoard();
				_sudokuSolver.resetPencilmarks();
				_sudokuSolver.setResetBoard(null);
			}
		});

		_menuActions.put("Show Pencil Marks", new MenuAction() {
			public void execute() {
				_boardView.setShowPencilmarks(!_boardView.getShowPencilmarks());
			}
		});

		_menuActions.put("Set as Start", new MenuAction() {
			public void execute() {
				Board resetBoard = new Board();
				resetBoard.copy(_sudokuSolver.getBoard());
				_sudokuSolver.setResetBoard(resetBoard);
			}
		});

		_menuActions.put("Restart", new MenuAction() {
			public void execute() {
				
				if (_sudokuSolver.getResetBoard() == null)
					return;
				
				_sudokuSolver.getBoard().copy(_sudokuSolver.getResetBoard());
				_sudokuSolver.resetPencilmarks();
				_sudokuSolver.align();
			}
		});

	}

}
