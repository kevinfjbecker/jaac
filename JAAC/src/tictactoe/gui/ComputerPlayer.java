package tictactoe.gui;

public class ComputerPlayer implements Runnable {

	private int turn = 1;

	public void reset() {
		turn = 1;
	}

	public void run() {
		try {

			switch (turn) {
			case 1:
				TicTacToeGUI.driver.makeBestMove(TicTacToeGUI.isXsTurn ? 'x'
						: 'o');
				break;
			default:
				TicTacToeGUI.driver.makeMinMaxMove(TicTacToeGUI.isXsTurn ? 'x'
						: 'o');
				break;
			}

			TicTacToeGUI.isPlayersTurn = !TicTacToeGUI.isPlayersTurn;
			TicTacToeGUI.isXsTurn = !TicTacToeGUI.isXsTurn;
			turn++;

		} catch (Exception exception) {
		}

	}

}
