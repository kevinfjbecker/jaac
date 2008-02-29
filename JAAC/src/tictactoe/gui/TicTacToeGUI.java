package tictactoe.gui;

import javax.swing.JFrame;

import tictactoe.Driver;

public class TicTacToeGUI {

	public static BoardView boardView;

	public static ComputerPlayer computerPlayer;

	public static final int H;

	public static boolean isPlayersTurn;

	public static boolean isXsTurn;

	private static MenuInput menuInput;

	public static Driver driver;

	public static final int W;

	static {

		H = 256;
		W = 256;

		isPlayersTurn = true;
		isXsTurn = true;

		driver = new Driver();
		
		computerPlayer = new ComputerPlayer();

		boardView = new BoardView(driver.getTicTacToe(), W, H);
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

}
