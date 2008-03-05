package tictactoe.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.HashMap;
import java.util.concurrent.Executors;

import javax.swing.JMenuItem;

import tictactoe.Driver;

public class MenuInput implements ActionListener, ItemListener {

	private BoardView _boardView;

	private ComputerPlayer _computerPlayer;
	
	private Driver _driver;

	private HashMap<String, Runnable> _menuActions;

	public MenuInput(BoardView boardView, ComputerPlayer computerPlayer, Driver driver) {
		_boardView = boardView;
		_computerPlayer = computerPlayer;
		_driver = driver;
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
		System.out.println("*");
	}

	private void populateMenuActions() {

		_menuActions.put("Exit", new Runnable() {
			public void run() {
				System.exit(0);
			}
		});

		_menuActions.put("New Game", new Runnable() {
			public void run() {
				_driver.clearBoard(_driver.getTicTacToe());
				_driver.setCurrentPlayer('x');
				if(TicTacToeGUI.getHumanPlayerMark()=='o')
					_computerPlayer.run();
				_boardView.repaint();
			}
		});

		_menuActions.put("Play as 'X'", new Runnable() {
			public void run() {
				if (TicTacToeGUI.getHumanPlayerMark() == 'x')
					return;
				TicTacToeGUI.setHumanPlayerMark('x');
				_driver.clearBoard(_driver.getTicTacToe());
				_driver.setCurrentPlayer('x');
				_boardView.repaint();
			}
		});

		_menuActions.put("Play as 'O'", new Runnable() {
			public void run() {
				if (TicTacToeGUI.getHumanPlayerMark() == 'o')
					return;
				TicTacToeGUI.setHumanPlayerMark('o');
				restartGame();
				_computerPlayer.run();
				_boardView.repaint();
			}
		});
	}

	private void restartGame() {
		_driver.clearBoard(_driver.getTicTacToe());
		_driver.setCurrentPlayer('x');
	}

}
