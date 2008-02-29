package tictactoe.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.HashMap;
import java.util.concurrent.Executors;

import javax.swing.JMenuItem;

public class MenuInput implements ActionListener, ItemListener {

	private HashMap<String, Runnable> _menuActions;

	public MenuInput() {
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

	public void itemStateChanged(ItemEvent arg0) {
	}

	private void populateMenuActions() {
		
		_menuActions.put("Exit", new Runnable() {
			public void run() {
				System.exit(0);
			}
		});
		
		_menuActions.put("New Game", new Runnable() {
			public void run() {
				System.exit(0);
			}
		});
		
	}

}
