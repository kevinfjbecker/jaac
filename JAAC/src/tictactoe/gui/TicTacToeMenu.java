package tictactoe.gui;

import javax.swing.JMenu;
import javax.swing.JMenuBar;

public class TicTacToeMenu {

	public static JMenuBar getMenu() {

		JMenuBar menuBar = new JMenuBar();

		JMenu fileMenu = new JMenu("File");
		fileMenu.add("Exit");

		JMenu gameMenu = new JMenu("Game");
		gameMenu.add("New Game");
		gameMenu.add("Play as 'X'");
		gameMenu.add("Play as 'O'");
		
		JMenu viewMenu = new JMenu("View");
		viewMenu.add("Watch MinMax");
		
		JMenu helpMenu = new JMenu("Help");
		helpMenu.add("About");

		menuBar.add(fileMenu);
		menuBar.add(gameMenu);
		menuBar.add(viewMenu);
		menuBar.add(helpMenu);

		return menuBar;
	}
	
}
