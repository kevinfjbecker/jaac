package sudoku.gui;

import static java.awt.Color.DARK_GRAY;

import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

@SuppressWarnings("serial")
class Box extends JPanel {

	Box(BoardView boardView, int n) {
		setBorder(BorderFactory.createLineBorder(DARK_GRAY));
		setLayout(new GridLayout(3, 3));
		for (int i = 0, y = ((n) / 3) * 3; i < 3; i++, y++)
			for (int k = 0, x = ((n) % 3) * 3; k < 3; k++, x++) {
				add(new Square(boardView, y, x));
			}
	}

}
