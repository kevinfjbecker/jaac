package sudoku;

import static java.awt.Color.BLACK;
import static java.awt.Color.DARK_GRAY;
import static java.awt.Color.LIGHT_GRAY;
import static java.awt.Color.WHITE;
import static java.awt.image.BufferedImage.TYPE_INT_RGB;
import static javax.swing.JFrame.EXIT_ON_CLOSE;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.RenderingHints;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;
import java.awt.image.BufferedImage;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class GraphicalUI {

	@SuppressWarnings("serial")
	private static class Board extends JPanel {

		Board() {
			setBorder(BorderFactory.createLineBorder(BLACK));
			setLayout(new GridLayout(3, 3));
			for (int n = 0; n < 9; n++)
				add(new Box(n));
		}
	}

	@SuppressWarnings("serial")
	private static class Box extends JPanel {
		Box(int n) {
			setBorder(BorderFactory.createLineBorder(DARK_GRAY));
			setLayout(new GridLayout(3, 3));
			for (int i = 0, y = ((n) / 3) * 3; i < 3; i++, y++)
				for (int k = 0, x = ((n) % 3) * 3; k < 3; k++, x++)
				add(new Square(y, x));
		}
	}

	@SuppressWarnings("serial")
	private static class Square extends JPanel {

		static BufferedImage marks[];

		static BufferedImage[] numerals;

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

		int x;

		int y;

		Square(int y, int x) {
			
			this.y = y;
			this.x = x;
			
			setBackground(Color.WHITE);
			setBorder(BorderFactory.createLineBorder(LIGHT_GRAY));
		}

		public Dimension getPreferredSize() {
			return new Dimension(26, 26);
		}

		public void paint(Graphics g) {

			super.paint(g);

			if (Math.random() < .3) {

				// filled in number code
				int i = (int) Math.floor(Math.random() * 9);

				g.drawImage(numerals[i], 1, 1, null);

			} else {

				int dx, dy;

				// pencil-mark code
				for (int x = 0; x < 3; x++)
					for (int y = 0; y < 3; y++)
						if (Math.random() < .3) {

							dx = x * 8 + 1;
							dy = y * 8 + 1;

							g.drawImage(marks[y * 3 + x], dx, dy, null);
						}
			}
		}
	}

	public static void main(String[] args) {

		JFrame frame = new JFrame("  Sudoku Solver");
		frame.setDefaultCloseOperation(EXIT_ON_CLOSE);

		JPanel board = new Board();

		frame.getContentPane().add(board);

		frame.pack();
		frame.setVisible(true);
	}

}
