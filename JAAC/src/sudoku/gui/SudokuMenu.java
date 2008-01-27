package sudoku.gui;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class SudokuMenu {

	public static JMenuBar getMenu(MenuInput menuInput) {

		JMenuBar menuBar = new JMenuBar();

		JMenu fileMenu = new JMenu("File");

		JMenuItem newMenuItem = new JMenuItem("New");
		newMenuItem.addActionListener(menuInput);
		fileMenu.add(newMenuItem);
		
		JMenuItem loadMenuItem = new JMenuItem("Load");
		loadMenuItem.addActionListener(menuInput);
		fileMenu.add(loadMenuItem);

		JMenuItem exitMenuItem = new JMenuItem("Exit");
		exitMenuItem.addActionListener(menuInput);
		fileMenu.add(exitMenuItem);

		JMenu editMenu = new JMenu("Edit");
		editMenu.add("Undo");
		editMenu.add("Redo");

		JMenu puzzleMenu = new JMenu("Puzzle");
		
		JMenuItem setAsStartMenuItem = new JMenuItem("Set as Start");
		setAsStartMenuItem.addActionListener(menuInput);
		puzzleMenu.add(setAsStartMenuItem);
		
		JMenuItem restartMenuItem = new JMenuItem("Restart");
		restartMenuItem.addActionListener(menuInput);
		puzzleMenu.add(restartMenuItem);

		JMenuItem solveMenuItem = new JMenuItem("Solve");
		solveMenuItem.addActionListener(menuInput);
		puzzleMenu.add(solveMenuItem);

		JMenu viewMenu = new JMenu("View");

		JCheckBoxMenuItem showMarksMenuItem;
		showMarksMenuItem = new JCheckBoxMenuItem("Show Pencil Marks");
		showMarksMenuItem.addActionListener(menuInput);
		viewMenu.add(showMarksMenuItem);

		viewMenu.add("Watch Solver");

		JMenu helpMenu = new JMenu("Help");
		helpMenu.add("About");

		menuBar.add(fileMenu);
		menuBar.add(editMenu);
		menuBar.add(viewMenu);
		menuBar.add(puzzleMenu);
		menuBar.add(helpMenu);

		return menuBar;

	}
}
