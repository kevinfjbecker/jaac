package sudoku;

import static sudoku.Sudoku.DIMENSION;

import java.util.Arrays;

public class Board implements IBoard {

	private int[][] _positions;

	public Board() {
		_positions = new int[DIMENSION][DIMENSION];
		initializeBoard();
	}

	public void copy(IBoard other) {
		for (int y = 0; y < DIMENSION; y++)
			for (int x = 0; x < DIMENSION; x++)
				setValue(other.get(y, x), y, x);
	}

	public boolean equals(Object object) {
		if (object instanceof Board)
			return Arrays.deepEquals(((Board) object)._positions, _positions);
		return false;
	}

	public int get(int y, int x) {
		return _positions[y][x];
	}

	private void initializeBoard() {
		for (int[] row : _positions)
			for (int i = 0; i < DIMENSION; i++)
				row[i] = 0;
	}

	public boolean isSolved() {
		for (int y = 0; y < DIMENSION; y++)
			for (int x = 0; x < DIMENSION; x++)
				if (_positions[y][x] == 0)
					return false;
		return true;
	}

	public boolean isValid() {

		int[] values = new int[10];

		// check rows
		for (int y = 0; y < DIMENSION; y++) {
			Arrays.fill(values, 0);
			for (int x = 0; x < DIMENSION; x++) {
				values[_positions[y][x]]++;
			}
			for (int i = 1; i < values.length; i++)
				if (values[i] > 1)
					return false;
		}

		// check columns
		for (int x = 0; x < DIMENSION; x++) {
			Arrays.fill(values, 0);
			for (int y = 0; y < DIMENSION; y++) {
				values[_positions[y][x]]++;
			}
			for (int i = 1; i < values.length; i++)
				if (values[i] > 1)
					return false;
		}

		// check boxes
		int[][] box;
		for (int n = 0; n < DIMENSION; n++) {
			Arrays.fill(values, 0);
			box = Aggregator.getBox(n, this);
			for (int y = 0; y < 3; y++)
				for (int x = 0; x < 3; x++) {
					values[box[y][x]]++;
				}
			for (int i = 1; i < values.length; i++)
				if (values[i] > 1)
					return false;
		}
		return true;
	}

	public void loadValues(int[][] values) {
		for (int y = 0; y < DIMENSION; y++)
			for (int x = 0; x < DIMENSION; x++)
				_positions[y][x] = values[y][x];
	}

	public int setValue(int value, int y, int x) {
		int previousValue = _positions[y][x];
		_positions[y][x] = value;
		return previousValue;
	}

}
