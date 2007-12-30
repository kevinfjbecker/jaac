package tictactoe;

import java.util.Arrays;

public class TicTacToe implements ITicTacToe {

	private char[][] _board;

	public TicTacToe() {
		_board = new char[3][3];
		for (char[] row : _board)
			for (int i = 0; i < row.length; i++)
				row[i] = ' ';
	}

	public char clear(int y, int x) {
		char c = _board[y][x];
		_board[y][x] = ' ';
		return c;
	}

	public char get(int y, int x) {
		return _board[y][x];
	}

	public boolean isOpen(int y, int x) {
		return _board[y][x] == ' ';
	}

	public void set(int y, int x, char c) {
		_board[y][x] = c;
	}

	public String toString() {
		String s = "";
		for (char[] row : _board)
			s += Arrays.toString(row) + '\n';
		return s;
	}

}
