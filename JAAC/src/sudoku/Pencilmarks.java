package sudoku;

import static sudoku.Sudoku.DIMENSION;

import java.util.Arrays;

public class Pencilmarks implements IPencilmarks {

	private boolean[][][] _workspace;

	public Pencilmarks() {
		_workspace = new boolean[DIMENSION][DIMENSION][DIMENSION];
		initializeWorkspace();
	}

	public void clear() {
		initializeWorkspace();
	}

	public boolean equals(Object object) {
		if (object instanceof Pencilmarks)
			return Arrays.deepEquals(((Pencilmarks) object)._workspace,
					_workspace);
		return false;
	}

	public boolean[] getMarks(int y, int x) {
		return _workspace[y][x];
	}

	public int getNumberOfPossibilities(int y, int x) {
		int numberOfPossibleValues = 0;
		for (boolean b : _workspace[y][x])
			if (b)
				numberOfPossibleValues++;
		return numberOfPossibleValues;
	}

	public int getValue(int y, int x) {
		for (int i = 0; i < DIMENSION; i++)
			if (_workspace[y][x][i])
				return i + 1;
		return -1;
	}

	public void initializeWorkspace() {
		for (boolean[][] row : _workspace)
			for (boolean[] col : row)
				for (int i = 0; i < DIMENSION; i++)
					col[i] = true;
	}

	public boolean isCertain(int y, int x) {
		return getNumberOfPossibilities(y, x) == 1;
	}

	public boolean isPossible(int y, int x, int value) {
		return _workspace[y][x][value - 1];
	}

	public boolean setIsPossible(int value, int y, int x, boolean isPossible) {
		boolean wasPossible = _workspace[y][x][value - 1];
		_workspace[y][x][value - 1] = isPossible;
		return wasPossible;
	}

}
