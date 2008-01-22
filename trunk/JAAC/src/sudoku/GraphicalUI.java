package sudoku;

import static java.awt.Color.BLACK;
import static java.awt.Color.DARK_GRAY;
import static java.awt.Color.LIGHT_GRAY;
import static java.awt.Color.WHITE;
import static java.awt.image.BufferedImage.TYPE_INT_RGB;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import static sudoku.Sudoku.DIMENSION;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;
import java.awt.image.BufferedImage;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class GraphicalUI {

	@SuppressWarnings("serial")
	private static class BoardView extends JPanel {

		private BoardView() {
			setBorder(BorderFactory.createLineBorder(BLACK));
			setLayout(new GridLayout(3, 3));
			for (int n = 0; n < 9; n++)
				add(new Box(n));
		}
	}

	@SuppressWarnings("serial")
	private static class Box extends JPanel {
		private Box(int n) {
			setBorder(BorderFactory.createLineBorder(DARK_GRAY));
			setLayout(new GridLayout(3, 3));
			for (int i = 0, y = ((n) / 3) * 3; i < 3; i++, y++)
				for (int k = 0, x = ((n) % 3) * 3; k < 3; k++, x++) {
					add(new Square(y, x));
				}
		}
	}

	@SuppressWarnings("serial")
	private static class Square extends JPanel implements MouseListener {

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

		private boolean haveFocus;

		private int x;

		private int y;

		private Square(int y, int x) {

			this.y = y;
			this.x = x;

			setBackground(Color.WHITE);
			setBorder(BorderFactory.createLineBorder(LIGHT_GRAY));

			addMouseListener(this);

		}

		public Dimension getPreferredSize() {
			return new Dimension(26, 26);
		}

		public void mouseClicked(MouseEvent e) {
		}

		public void mouseEntered(MouseEvent e) {
			haveFocus = true;
			activeSquare = this;
			repaint();
		}

		public void mouseExited(MouseEvent e) {
			haveFocus = false;
			activeSquare = null;
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

				if (board.get(y, x) != 0) {

					g.drawImage(numerals[board.get(y, x) - 1], 1, 1, null);

				} else {

					int dx, dy;

					// pencil-mark code
					for (int y = 0; y < 3; y++)
						for (int x = 0; x < 3; x++)
							if (pencilmarks.isPossible(this.y, this.x, y * 3
									+ x + 1)) {

								dx = x * 8 + 1;
								dy = y * 8 + 1;

								g.drawImage(marks[y * 3 + x], dx, dy, null);
							}
				}
			}
		}
	}

	private static Square activeSquare = null;

	private static Board board = new Board();

	private static JPanel boardView = new BoardView();

	private static KeyListener keyListener = new KeyListener() {

		public void keyPressed(KeyEvent e) {
		}

		public void keyReleased(KeyEvent e) {
		}

		public void keyTyped(KeyEvent e) {
			int n = e.getKeyChar() - '0';
			if (activeSquare != null && 0 <= n && n <= 9) {
				activeSquare.haveFocus = false;
				board.setValue(n, activeSquare.y, activeSquare.x);
				resetPencilmarks();
				boardView.repaint();
			}
		}

	};

	private static Pencilmarks pencilmarks = new Pencilmarks();

	private static void align() {
		for (int y = 0; y < DIMENSION; y++)
			for (int x = 0; x < DIMENSION; x++)
				if (board.get(y, x) != 0)
					Aggregator.setValue(pencilmarks, y, x, board.get(y, x));

	}

	private static void eliminatePencilmarks() {
		
		for (int y = 0; y < DIMENSION; y++)
			for (int x = 0; x < DIMENSION; x++) {

				for (int i = (y / 3) * 3; i < (y / 3) * 3 + 3; i++)
					for (int k = (x / 3) * 3; k < (x / 3) * 3 + 3; k++)
						if (board.get(i, k) != 0 && (i != y || k != x))
							pencilmarks.setIsPossible(board.get(i, k), y, x,
									false);

				for (int i = 0; i < DIMENSION; i++)
					if (board.get(i, x) != 0 && i != y)
						pencilmarks.setIsPossible(board.get(i, x), y, x, false);

				for (int i = 0; i < DIMENSION; i++)
					if (board.get(y, i) != 0 && i != x)
						pencilmarks.setIsPossible(board.get(y, i), y, x, false);

			}
	}

	public static void main(String[] args) {

		JFrame frame = new JFrame("  Sudoku Solver");
		frame.setDefaultCloseOperation(EXIT_ON_CLOSE);

		frame.addKeyListener(keyListener);

		frame.getContentPane().add(boardView);

		frame.pack();
		frame.setVisible(true);

	}

	private static void resetPencilmarks() {
		pencilmarks.initializeWorkspace();
		align();
		eliminatePencilmarks();
	}

}
