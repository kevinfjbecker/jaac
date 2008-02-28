package sudoku.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.HashMap;
import java.util.TimerTask;
import java.util.concurrent.Executors;

import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;

import sudoku.Board;
import sudoku.SudokuSolver;

public class MenuInput implements ActionListener, ItemListener {

	private BoardView _boardView;

	private HashMap<String, Runnable> _menuActions;

	private SudokuSolver _sudokuSolver;

	public MenuInput(BoardView boardView, SudokuSolver sudokuSolver) {
		_boardView = boardView;
		_sudokuSolver = sudokuSolver;
		_menuActions = new HashMap<String, Runnable>();
		populateMenuActions();
	}

	public void actionPerformed(ActionEvent actionEvent) {
		String text = ((JMenuItem) (actionEvent.getSource())).getText();
		Runnable menuAction = _menuActions.get(text);
		if (menuAction != null) {
			Executors.newSingleThreadExecutor().execute(menuAction);
		}
	}

	public void itemStateChanged(ItemEvent itemEvent) {
	}

	private void matchPencilmarksToBoard() {
		_sudokuSolver.resetPencilmarks();
		_sudokuSolver.align();
		_sudokuSolver.eliminationStep();
		_boardView.repaint();
	}

	private void populateMenuActions() {

		_menuActions.put("Solve", new Runnable() {
			public void run() {
				try {

					// this is an animator.
					new java.util.Timer().scheduleAtFixedRate(new TimerTask() {
						public void run() {
							if (!_sudokuSolver.isSolving()) {
								cancel();
							}
							_boardView.repaint();
						}
					}, 30, 30);

					_sudokuSolver.attemptSolution();
					_boardView.repaint();
				} catch (Exception e) {
				}
			}
		});

		_menuActions.put("Exit", new Runnable() {
			public void run() {
				System.exit(0);
			}
		});

		_menuActions.put("Load", new Runnable() {
			public void run() {
				String input = null;
				String message = "Enter an initial confiuration."
						+ "\n(empty square:  .  |  -  |  0  )";
				input = JOptionPane.showInputDialog(message);

				// guard clause
				if (input == null || input.length() != 9 * 9)
					return;

				// guard clause
				for (int i = 0; i < input.length(); i++) {
					char c = input.charAt(i);
					if (!('0' <= c || c <= '9' || c == '.' || c == '-')) {
						return;
					}
				}

				_sudokuSolver.loadValues(input);
				matchPencilmarksToBoard();
				_boardView.repaint();
			}
		});

		_menuActions.put("Get String", new Runnable() {
			public void run() {
				String s = "";
				int n;
				for (int x = 0; x < 9; x++)
					for (int y = 0; y < 9; y++) {
						n = _sudokuSolver.getBoard().get(x, y);
						if (n == 0)
							s += '-';
						else
							s += n;
					}
				JFrame f = new JFrame("  String Representaion");
				JTextArea t = new JTextArea();
				t.setText(s);
				f.add(t);
				f.pack();
				f.setVisible(true);
			}
		});

		_menuActions.put("New", new Runnable() {
			public void run() {
				_sudokuSolver.resetBoard();
				_sudokuSolver.resetPencilmarks();
				_sudokuSolver.setResetBoard(null);
				_boardView.repaint();
			}
		});

		_menuActions.put("Show Pencil Marks", new Runnable() {
			public void run() {
				_boardView.setShowPencilmarks(!_boardView.getShowPencilmarks());
				_boardView.repaint();
			}
		});

		_menuActions.put("Set as Start", new Runnable() {
			public void run() {
				Board resetBoard = new Board();
				resetBoard.copy(_sudokuSolver.getBoard());
				_sudokuSolver.setResetBoard(resetBoard);
				_boardView.repaint();
			}
		});

		_menuActions.put("Restart", new Runnable() {
			public void run() {

				if (_sudokuSolver.getResetBoard() == null)
					return;

				_sudokuSolver.getBoard().copy(_sudokuSolver.getResetBoard());
				matchPencilmarksToBoard();
				_boardView.repaint();
			}
		});

	}
}
