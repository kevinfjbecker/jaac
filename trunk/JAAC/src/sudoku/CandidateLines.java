package sudoku;

import static sudoku.Sudoku.DIMENSION;

public class CandidateLines implements SolverLogic {

	public static void findCandidateLines(IPencilmarks pencilmarks) {

		boolean[][][] box;

		int[] values;

		for (int boxIndex = 0; boxIndex < 9; boxIndex++) {

			box = Aggregator.getBox(boxIndex, pencilmarks);
			values = new int[9];

			countPencilMarksInBox(box, values);

			for (int i = 0; i < DIMENSION; i++) {

				int boxRow;
				if ((boxRow = onlyPairInRow(i, box, values)) != -1) {
					int y = boxRow + (boxIndex / 3) * 3;
					for (int x = 0; x < DIMENSION; x++)
						if (!isInBox(y, x, boxIndex))
							if (pencilmarks.isPossible(y, x, i + 1))
								pencilmarks.setIsPossible(i + 1, y, x, false);
				}

				int boxColumn;
				if ((boxColumn = onlyPairInColumn(i, box, values)) != -1) {
					int x = boxColumn + (boxIndex % 3) * 3;
					for (int y = 0; y < DIMENSION; y++)
						if (!isInBox(y, x, boxIndex))
							if (pencilmarks.isPossible(y, x, i + 1))
								pencilmarks.setIsPossible(i + 1, y, x, false);
				}

			}
		}
	}

	private static int onlyPairInColumn(int i, boolean[][][] box, int[] values) {
		if (values[i] != 2)
			return -1;
		for (int x = 0; x < 3; x++) {
			int n = 0;
			for (int y = 0; y < 3; y++)
				if (box[y][x][i])
					n++;
			if (n == 2)
				return x;
		}
		return -1;
	}

	private static int onlyPairInRow(int i, boolean[][][] box, int[] values) {
		if (values[i] != 2)
			return -1;
		for (int y = 0; y < 3; y++) {
			int n = 0;
			for (int x = 0; x < 3; x++)
				if (box[y][x][i])
					n++;
			if (n == 2)
				return y;
		}
		return -1;
	}

	private static void countPencilMarksInBox(boolean[][][] box, int[] values) {
		for (int y = 0; y < 3; y++)
			for (int x = 0; x < 3; x++)
				for (int m = 0; m < DIMENSION; m++)
					if (box[y][x][m])
						values[m]++;
	}

	private static boolean isInBox(int y, int x, int boxIndex) {
		int xBox = (boxIndex % 3) * 3;
		int yBox = (boxIndex / 3) * 3;
		if (x < xBox || x > xBox + 2)
			return false;
		if (y < yBox || y > yBox + 2)
			return false;
		return true;
	}
}
