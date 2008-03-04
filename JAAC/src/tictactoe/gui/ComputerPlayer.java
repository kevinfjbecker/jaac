package tictactoe.gui;

import tictactoe.Driver;

public class ComputerPlayer implements Runnable {

	private Driver driver;

	public ComputerPlayer(Driver driver) {
		this.driver = driver;
	}

	public void run() {
		try {

			switch (getTurnNumber()) {
			case 0:
				driver.makeRandomMove(driver.getCurrentPlayer());
				break;
			case 1:
				driver.makeBestMove(driver.getCurrentPlayer());
				break;
			default:
				driver.makeMinMaxMove(driver.getCurrentPlayer());
				break;
			}

			driver.changePlayer();

		} catch (Exception exception) {
		}

	}

	private int getTurnNumber() {
		int n = 0;
		for (int x = 0; x < 3; x++)
			for (int y = 0; y < 3; y++)
				if (!driver.getTicTacToe().isOpen(y, x))
					n++;
		System.out.print(n);
		return n;
	}
}
