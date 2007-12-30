package sudoku;

import static sudoku.Sudoku.DIMENSION;

public class Aggregator {

	public static int[][] getBox(int n, IBoard board) {
		int[][] box = new int[3][3];
		for (int i = 0, y = ((n) / 3) * 3; i < 3; i++, y++)
			for (int k = 0, x = ((n) % 3) * 3; k < 3; k++, x++)
				box[i][k] = board.get(y, x);
		return box;
	}

	public static boolean[][][] getBox(int n, IPencilmarks pencilmarks) {
		boolean[][][] box = new boolean[3][3][9];
		for (int i = 0, y = ((n) / 3) * 3; i < 3; i++, y++)
			for (int k = 0, x = ((n) % 3) * 3; k < 3; k++, x++)
				for (int m = 0; m < DIMENSION; m++)
					box[i][k] = pencilmarks.getMarks(y, x);
		return box;
	}

	public static boolean[][] geColumn(int n, IPencilmarks pencilmarks) {
		boolean[][] column = new boolean[9][9];
		for (int y = 0; y < DIMENSION; y++)
			for (int m = 0; m < DIMENSION; m++)
				column[y] = pencilmarks.getMarks(y, n);
		return column;
	}

	public static boolean[][] getRow(int n, IPencilmarks pencilmarks) {
		boolean[][] row = new boolean[9][9];
		for (int x = 0; x < DIMENSION; x++)
			for (int m = 0; m < DIMENSION; m++)
				row[x] = pencilmarks.getMarks(n, x);
		return row;
	}

	public static void setValue(IPencilmarks marks, int y, int x, int value) {
		for (int i = 1; i <= DIMENSION; i++)
			marks.setIsPossible(i, y, x, i == value ? true : false);
	}

}
