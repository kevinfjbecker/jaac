package tictactoe;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.RenderingHints;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class GraphicalUI {

	@SuppressWarnings("serial")
	static class Board extends JPanel {

		static Color saturatedBlack = new Color(0, 0, 55);

		static Color saturatedWhite = new Color(227, 227, 255);

		static Polygon xMark = new Polygon();
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

		int _h = 256;

		int _w = 256;

		Board() {
			setBackground(saturatedBlack);
		}

		public Dimension getPreferredSize() {
			return new Dimension(256, 256);
		}

		void drawX(Graphics g, int x, int y) {
			xMark.translate(x, y);
			g.fillPolygon(xMark);
			xMark.translate(-x, -y);
		}

		void drawO(Graphics g, int x, int y) {
			g.setColor(saturatedWhite);
			g.fillOval(0 + x, 0 + y, 64, 64);
			g.setColor(saturatedBlack);
			g.fillOval(9 + x, 9 + y, 46, 46);
		}

		@Override
		public void paint(Graphics g) {
			super.paint(g);

			Graphics2D g2d = (Graphics2D) g;

			g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
					RenderingHints.VALUE_ANTIALIAS_ON);

			drawBoard(g);

//			 drawX(g, 15, 15);

			 drawO(g, 13, 13);
			 drawO(g,96,96);
			 drawO(g,178,178);

		}

		private void drawBoard(Graphics g) {
			g.setColor(saturatedWhite);
			g.fillRect(_w / 3 - 3, _h / 24, 6, 11 * _h / 12);
			g.fillRect(2 * _w / 3 - 3, _h / 24, 6, 11 * _h / 12);
			g.fillRect(_w / 24, _h / 3 - 3, 11 * _w / 12, 6);
			g.fillRect(_w / 24, 2 * _h / 3 - 3, 11 * _w / 12, 6);
		}

	}

	public static void main(String[] args) {

		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		frame.add(new Board());

		frame.pack();
		frame.setVisible(true);

	}

}
