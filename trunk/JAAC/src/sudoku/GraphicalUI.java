package sudoku;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class GraphicalUI {

	@SuppressWarnings("serial")
	private static class Board extends JPanel {

		Board() {
			setBorder(BorderFactory.createLineBorder(Color.BLACK));
			setLayout(new GridLayout(3, 3));
			for (int i = 0; i < 9; i++)
				add(new Box());
		}
	}

	@SuppressWarnings("serial")
	private static class Box extends JPanel {
		Box() {
			setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
			setLayout(new GridLayout(3, 3));
			for (int k = 0; k < 9; k++)
				add(new Square());
		}
	}

	@SuppressWarnings("serial")
	private static class Square extends JPanel {

		static BufferedImage marks;

		static BufferedImage numerals;

		static {
			try {
				marks = ImageIO.read(new File("data/pencil-marks.jpg").toURI()
						.toURL());
			} catch (Exception e) {
			}
		}

		static {
			try {
				numerals = ImageIO.read(new File("data/numerals.jpg").toURI()
						.toURL());
			} catch (Exception e) {
			}
		}

		Square() {
			setBackground(Color.WHITE);
			setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
		}

		public Dimension getPreferredSize() {
			return new Dimension(26, 26);
		}

		public void paint(Graphics g) {

			int x,y,dx1,dy1,dx2,dy2,sx1,sy1,sx2,sy2;
			
			super.paint(g);

			if (Math.random() < .5) {

				// filled in number code
				x = (int) Math.floor(Math.random() * 3);
				y = (int) Math.floor(Math.random() * 3);

				System.out.println(x + "," + y);
				
				dx1 = 1;
				dy1 = 1;
				dx2 = 25;
				dy2 = 25;
				sx1 = x * 24;
				sy1 = y * 24;
				sx2 = (x + 1) * 24;
				sy2 = (y + 1) * 24;

				System.out.println(numerals.getWidth() +"."+numerals.getHeight());
				
				g.drawImage(numerals, dx1, dy1, dx2, dy2, sx1, sy1, sx2, sy2,
						null);
			} else {
				// pencil-mark code
				for (x = 0; x < 3; x++)
					for (y = 0; y < 3; y++)
						if (Math.random() < .3) {

							dx1 = x * 8 + 1;
							dy1 = y * 8 + 1;
							dx2 = (x + 1) * 8;
							dy2 = (y + 1) * 8;
							sx1 = x * 8;
							sy1 = y * 8;
							sx2 = (x + 1) * 8;
							sy2 = (y + 1) * 8;

							g.drawImage(marks, dx1, dy1, dx2, dy2, sx1, sy1,
									sx2, sy2, null);
						}
			}
		}
	}

	public static void main(String[] args) {

		JFrame frame = new JFrame("Sudoku Solver");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JPanel board = new Board();

		frame.getContentPane().add(board);

		frame.pack();
		frame.setVisible(true);
	}

}
