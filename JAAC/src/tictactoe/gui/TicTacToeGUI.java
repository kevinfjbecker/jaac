package tictactoe.gui;

import javax.swing.JFrame;

import tictactoe.Driver;

public class TicTacToeGUI {

	private static BoardView boardView;

	private static ComputerPlayer computerPlayer;

	private static Driver driver;

	public static final int H;

	private static char humanPlayer;

	private static MenuInput menuInput;

	private static MouseInput mouseInput;

	public static final int W;

	static {

		H = 256;
		W = 256;

		driver = new Driver();

		computerPlayer = new ComputerPlayer(driver);

		boardView = new BoardView(driver.getTicTacToe(), W, H);

		mouseInput = new MouseInput(boardView, computerPlayer, driver);

		boardView.addMouseListener(mouseInput);

		menuInput = new MenuInput();

	}

	public static void main(String[] args) {

		JFrame frame = new JFrame("  Tic-Tac-Toe");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);

		frame.setJMenuBar(TicTacToeMenu.getMenu(menuInput));

		frame.add(boardView);

		frame.pack();
		frame.setVisible(true);

	}

	public static char humanPlayer() {
		return humanPlayer;
	}

}
