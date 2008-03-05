package tictactoe.gui;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.SwingUtilities;

import tictactoe.Driver;

public class MouseInput implements MouseListener {

	private BoardView boardView;

	private ComputerPlayer computerPlayer;

	private Driver driver;

	public MouseInput(BoardView boardView, ComputerPlayer computerPlayer,
			Driver driver) {
		this.boardView = boardView;
		this.computerPlayer = computerPlayer;
		this.driver = driver;
	}

	public void mouseClicked(MouseEvent mouseEvent) {
		if (driver.isGameOver()) {
			return;
		}
		if (driver.getCurrentPlayer() == TicTacToeGUI.getHumanPlayerMark()) {
			int x = mouseEvent.getX();
			int y = mouseEvent.getY();
			x = x / (TicTacToeGUI.W / 3);
			y = y / (TicTacToeGUI.H / 3);
			if (driver.getTicTacToe().isOpen(y, x)) {
				driver.getTicTacToe().set(y, x, driver.getCurrentPlayer());
				driver.changePlayer();
				boardView.repaint();
			} else {
				return;
			}
		}
		if (driver.isGameOver()) {
			return;
		}
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				try {
					computerPlayer.run();
					boardView.repaint();
				} catch (Exception exception) {
				}
			}
		});
	}

	public void mouseEntered(MouseEvent e) {
	}

	public void mouseExited(MouseEvent e) {
	}

	public void mousePressed(MouseEvent e) {
	}

	public void mouseReleased(MouseEvent e) {
	}

}
