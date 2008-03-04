package tictactoe.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.RenderingHints;

import javax.swing.JPanel;

import tictactoe.ITicTacToe;

@SuppressWarnings("serial")
public class BoardView extends JPanel {

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

	private int _h;

	private ITicTacToe _ticTacToe;

	private int _w;

	public BoardView(ITicTacToe ticTacToe, int w, int h) {
		_h = h;
		_w = w;
		_ticTacToe = ticTacToe;
		setBackground(saturatedBlack);
	}

	private void draw(Graphics g, int x, int y, char c) {
		if (c == 'x') {
			drawX(g, posMap[x], posMap[y]);
		} else {
			drawO(g, posMap[x], posMap[y]);
		}
	}

	private void drawBoard(Graphics g) {
		g.setColor(saturatedWhite);
		g.fillRect(_w / 3 - 3, _h / 24, 6, 11 * _h / 12);
		g.fillRect(2 * _w / 3 - 3, _h / 24, 6, 11 * _h / 12);
		g.fillRect(_w / 24, _h / 3 - 3, 11 * _w / 12, 6);
		g.fillRect(_w / 24, 2 * _h / 3 - 3, 11 * _w / 12, 6);
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

	public Dimension getPreferredSize() {
		return new Dimension(256, 256);
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
				if (!_ticTacToe.isOpen(y, x))
					draw(g, x, y, _ticTacToe.get(y, x));
			}

	}

}
