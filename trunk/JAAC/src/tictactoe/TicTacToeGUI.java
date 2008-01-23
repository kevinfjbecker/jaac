package tictactoe;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class TicTacToeGUI {

	@SuppressWarnings("serial")
	private static class BoardView extends JPanel {

		private MouseListener mouseListener = new MouseListener() {

			public void mouseClicked(MouseEvent mouseEvent) {

				if (Driver.isGameOver()) {
					return;
				}

				if (isPlayersTurn) {

					int x = mouseEvent.getX();
					int y = mouseEvent.getY();
					x = x / (W / 3);
					y = y / (H / 3);
					if (ticTacToe.isOpen(y, x)) {
						ticTacToe.set(y, x, isXsTurn ? 'x' : 'o');
					}

					isPlayersTurn = !isPlayersTurn;
					isXsTurn = !isXsTurn;
					repaint();

				}

				if (Driver.isGameOver()) {
					return;
				}

				SwingUtilities.invokeLater(new Runnable() {

					public void run() {

						try {

							computerPlayer.run();
							repaint();

						} catch (Exception exception) {
						}

					}
				});

			}

			public void mouseEntered(MouseEvent e) {
			}

			public void mouseExited(MouseEvent e) {
			}

			public void mousePressed(MouseEvent e) {
			}

			public void mouseReleased(MouseEvent e) {
			}

		};

		private static int[] posMap = { 11, 96, 179 };

		private static Color saturatedBlack = new Color(0, 0, 55);

		private static Color saturatedWhite = new Color(227, 227, 255);

		private static Polygon xMark = new Polygon();
		static {
			xMark.addPoint(7, 0);
			xMark.addPoint(31, 24);
			xMark.addPoint(56, 0);
			xMark.addPoint(63, 7);
			xMark.addPoint(38, 31);
			xMark.addPoint(63, 56);
			xMark.addPoint(56, 63);
			xMark.addPoint(31, 38);
			xMark.addPoint(7, 63);
			xMark.addPoint(0, 56);
			xMark.addPoint(24, 31);
			xMark.addPoint(0, 7);
		}

		private BoardView() {
			setBackground(saturatedBlack);
			addMouseListener(mouseListener);
		}

		public Dimension getPreferredSize() {
			return new Dimension(256, 256);
		}

		private void drawBoard(Graphics g) {
			g.setColor(saturatedWhite);
			g.fillRect(W / 3 - 3, H / 24, 6, 11 * H / 12);
			g.fillRect(2 * W / 3 - 3, H / 24, 6, 11 * H / 12);
			g.fillRect(W / 24, H / 3 - 3, 11 * W / 12, 6);
			g.fillRect(W / 24, 2 * H / 3 - 3, 11 * W / 12, 6);
		}

		private void drawO(Graphics g, int x, int y) {
			g.setColor(saturatedWhite);
			g.fillOval(0 + x, 0 + y, 65, 65);
			g.setColor(saturatedBlack);
			g.fillOval(9 + x, 9 + y, 47, 47);
		}

		private void drawX(Graphics g, int x, int y) {
			g.setColor(saturatedWhite);
			xMark.translate(x, y);
			g.fillPolygon(xMark);
			xMark.translate(-x, -y);
		}

		@Override
		public void paint(Graphics g) {
			super.paint(g);

			Graphics2D g2d = (Graphics2D) g;

			g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
					RenderingHints.VALUE_ANTIALIAS_ON);

			drawBoard(g);

			for (int x = 0; x < 3; x++)
				for (int y = 0; y < 3; y++) {
					if (!ticTacToe.isOpen(y, x))
						draw(g, x, y, ticTacToe.get(y, x));
				}

		}

		private void draw(Graphics g, int x, int y, char c) {
			if (c == 'x') {
				drawX(g, posMap[x], posMap[y]);
			} else {
				drawO(g, posMap[x], posMap[y]);
			}
		}

	}

	private static class ComputerPlayer implements Runnable {

		private int turn = 1;

		public void reset() {
			turn = 1;
		}

		public void run() {
			try {

				switch (turn) {
				case 1:
					Driver.makeBestMove(isXsTurn ? 'x' : 'o');
					break;
				default:
					Driver.makeMinMaxMove(isXsTurn ? 'x' : 'o');
					break;
				}

				isPlayersTurn = !isPlayersTurn;
				isXsTurn = !isXsTurn;
				turn++;

			} catch (Exception exception) {
			}

		}

	}

	private static ComputerPlayer computerPlayer = new ComputerPlayer();

	private static boolean isPlayersTurn = true;

	private static boolean isXsTurn = true;

	private static int H = 256;

	private static ITicTacToe ticTacToe = Driver.getTicTacToe();

	private static int W = 256;

	public static void main(String[] args) {

		JFrame frame = new JFrame("  Tic-Tac-Toe");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);

		frame.add(new BoardView());

		frame.pack();
		frame.setVisible(true);

	}

}
