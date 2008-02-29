package tictactoe.gui;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import tictactoe.gui.MenuInput;

public class TicTacToeMenu {

	public static JMenuBar getMenu(MenuInput menuInput) {

		JMenuBar menuBar = new JMenuBar();

		JMenu fileMenu = new JMenu("File");
		
		JMenuItem exitMenuItem = new JMenuItem("Exit");
		exitMenuItem.addActionListener(menuInput);
		fileMenu.add(exitMenuItem);

		JMenu gameMenu = new JMenu("Game");
		
		JMenuItem newGameMenuItem = new JMenuItem("New Game");
		newGameMenuItem.addActionListener(menuInput);
		gameMenu.add(newGameMenuItem);
		
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
