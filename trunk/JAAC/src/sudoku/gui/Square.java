package sudoku.gui;

import static java.awt.Color.BLACK;
import static java.awt.Color.LIGHT_GRAY;
import static java.awt.Color.WHITE;
import static java.awt.image.BufferedImage.TYPE_INT_RGB;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;
import java.awt.image.BufferedImage;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class Square extends JPanel implements MouseListener {

	private static BufferedImage marks[];

	private static BufferedImage[] numerals;

	static {

		numerals = new BufferedImage[9];
		for (int i = 0; i < 9; i++) {

			numerals[i] = new BufferedImage(24, 24, TYPE_INT_RGB);
			Graphics2D g2d = (Graphics2D) numerals[i].getGraphics();

			g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
					RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

			Color resetColor = g2d.getColor();
			g2d.setColor(WHITE);
			g2d.fillRect(0, 0, 24, 24);

			g2d.setColor(BLACK);
			FontRenderContext frc = g2d.getFontRenderContext();
			Font f = new Font(Font.SERIF, Font.PLAIN, 24);
			String s = new String("" + (i + 1));
			TextLayout textLayout = new TextLayout(s, f, frc);
			textLayout.draw(g2d, 6, 20);

			g2d.setColor(resetColor);
			g2d.dispose();
		}

	}

	static {

		marks = new BufferedImage[9];
		for (int i = 0; i < 9; i++) {

			marks[i] = new BufferedImage(8, 8, TYPE_INT_RGB);
			Graphics2D g2d = (Graphics2D) marks[i].getGraphics();

			g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
					RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

			Color resetColor = g2d.getColor();
			g2d.setColor(WHITE);
			g2d.fillRect(0, 0, 24, 24);

			g2d.setColor(BLACK);
			FontRenderContext frc = g2d.getFontRenderContext();
			Font f = new Font(Font.SERIF, Font.PLAIN, 6);
			String s = new String("" + (i + 1));
			TextLayout textLayout = new TextLayout(s, f, frc);
			textLayout.draw(g2d, 2, 6);

			g2d.setColor(resetColor);
			g2d.dispose();
		}

	}

	private BoardView boardView;

	private boolean haveFocus;

	private int x;

	private int y;

	public Square(BoardView boardView, int y, int x) {

		this.boardView = boardView;
		this.y = y;
		this.x = x;

		setBackground(Color.WHITE);
		setBorder(BorderFactory.createLineBorder(LIGHT_GRAY));

		addMouseListener(this);

	}

	public int getBoardPositionX() {
		return x;
	}

	public int getBoardPositionY() {
		return y;
	}

	public Dimension getPreferredSize() {
		return new Dimension(26, 26);
	}

	public void mouseClicked(MouseEvent e) {
	}

	public void mouseEntered(MouseEvent e) {
		haveFocus = true;
		boardView.setActiveSquare(this);
		repaint();
	}

	public void mouseExited(MouseEvent e) {
		haveFocus = false;
		boardView.setActiveSquare(null);
		repaint();
	}

	public void mousePressed(MouseEvent e) {
	}

	public void mouseReleased(MouseEvent e) {
	}

	public void paint(Graphics g) {

		super.paint(g);

		if (haveFocus) {

			g.drawLine(4, 22, 22, 22);

		} else {

			if (boardView.getBoard().get(y, x) != 0) {

				g.drawImage(numerals[boardView.getBoard().get(y, x) - 1], 1, 1,
						null);

			} else if (boardView.getShowPencilmarks()) {

				int dx, dy;

				// pencil-mark code
				for (int y = 0; y < 3; y++)
					for (int x = 0; x < 3; x++)
						if (boardView.getPencilmarks().isPossible(this.y,
								this.x, y * 3 + x + 1)) {

							dx = x * 8 + 1;
							dy = y * 8 + 1;

							g.drawImage(marks[y * 3 + x], dx, dy, null);
						}
			}
		}
	}

	public void setHasFocus(boolean b) {
		haveFocus = b;
	}

}
