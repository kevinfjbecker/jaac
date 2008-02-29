package tictactoe.gui;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.SwingUtilities;

public class MouseInput implements MouseListener {

	// TODO: bug -> the play is able to click on occupied loactions
	// resulting in the computer making multiple moves
	public void mouseClicked(MouseEvent mouseEvent) {
		if (TicTacToeGUI.driver.isGameOver()) {
			return;
		}
		if (TicTacToeGUI.isPlayersTurn) {
			int x = mouseEvent.getX();
			int y = mouseEvent.getY();
			x = x / (TicTacToeGUI.W / 3);
			y = y / (TicTacToeGUI.H / 3);
			if (TicTacToeGUI.driver.getTicTacToe().isOpen(y, x)) {
				TicTacToeGUI.driver.getTicTacToe().set(y, x,
						TicTacToeGUI.isXsTurn ? 'x' : 'o');
			}
			TicTacToeGUI.isPlayersTurn = !TicTacToeGUI.isPlayersTurn;
			TicTacToeGUI.isXsTurn = !TicTacToeGUI.isXsTurn;
			TicTacToeGUI.boardView.repaint();
		}
		if (TicTacToeGUI.driver.isGameOver()) {
			return;
		}
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				try {
					TicTacToeGUI.computerPlayer.run();
					TicTacToeGUI.boardView.repaint();
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
