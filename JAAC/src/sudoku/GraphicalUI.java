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

		BoardView() {
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
				for (int k = 0, x = ((n) % 3) * 3; k < 3; k++, x++){
					add(new Square(y, x));}
		}
	}

	@SuppressWarnings("serial")
	private static class Square extends JPanel implements KeyListener,
			MouseListener {

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

		private int x;

		private int y;

		private boolean haveFocus;

		Square(int y, int x) {

			this.y = y;
			this.x = x;

			setBackground(Color.WHITE);
			setBorder(BorderFactory.createLineBorder(LIGHT_GRAY));

			addMouseListener(this);
			addKeyListener(this);
		}

		public Dimension getPreferredSize() {
			return new Dimension(26, 26);
		}

		public void keyPressed(KeyEvent e) {
			System.out.println("*");
		}

		public void keyReleased(KeyEvent e) {
			System.out.println("*");
		}

		public void keyTyped(KeyEvent e) {
			System.out.println("*");
		}

		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		public void mouseEntered(MouseEvent e) {
			haveFocus = true;
			repaint();
		}

		public void mouseExited(MouseEvent e) {
			haveFocus = false;
			repaint();
		}

		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub

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

	static Board board = new Board();

	static JPanel boardView = new BoardView();

	static Pencilmarks pencilmarks = new Pencilmarks();

	Square[] squares = new Square[9*9];
	
	public static void main(String[] args) {

		populateLogicalBoard();

		JFrame frame = new JFrame("  Sudoku Solver");
		frame.setDefaultCloseOperation(EXIT_ON_CLOSE);

		frame.getContentPane().add(boardView);
		
		frame.pack();
		frame.setVisible(true);

	}

	private static void populateLogicalBoard() {

		String s = "7---6---1-14---23--35---48----1-3---2---9---5---6-7----63---95--82---74-4---3---2";

		int[][] a = new int[DIMENSION][DIMENSION];
		for (int i = 0; i < s.length(); i++)
			if (s.charAt(i) == '-')
				a[i / DIMENSION][i % DIMENSION] = 0;
			else
				a[i / DIMENSION][i % DIMENSION] = s.charAt(i) - '0';
		board.loadValues(a);

		for (int y = 0; y < DIMENSION; y++)
			for (int x = 0; x < DIMENSION; x++)
				if (board.get(y, x) != 0)
					Aggregator.setValue(pencilmarks, y, x, board.get(y, x));

		eliminatePencilmarks();
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

}