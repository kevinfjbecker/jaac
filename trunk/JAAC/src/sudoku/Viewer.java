package sudoku;

import static sudoku.Sudoku.DIMENSION;

public class Viewer {

	private static String lineOf(char c, int n) {
		String s = "";
		for (int i = 0; i < n; i++)
			s += c;
		return s;
	}

	public static String showBoard(IBoard board) {
		String s = "";

		for (int y = 0; y < DIMENSION; y++) {

			if (y > 0) {
				s += '\n';
				if (y % 3 == 0)
					s += lineOf('-', 17) + "\n";
			}

			for (int x = 0; x < DIMENSION; x++) {
				if (x > 0)
					if (x % 3 == 0)
						s += '|';
					else
						s += '.';

				if (board.get(y, x) == 0)
					s += ' ';
				else
					s += board.get(y, x);
			}
		}

		s += '\n';

		return s;
	}

	public static String showPencilmarks(IPencilmarks pencilmarks) {
		String s = "";
		for (int y = 0; y < DIMENSION; y++) {
			if (y > 0) {
				if (y % 3 == 0)
					s += lineOf('-', 73) + "\n" + lineOf('-', 73) + "\n";
				else
					s += lineOf('-', 73) + "\n";
			}
			for (int k = 0; k < DIMENSION; k += 3) {
				for (int x = 0; x < DIMENSION; x++) {
					for (int i = k; i < k + 3; i++) {
						if (i % 3 == 0 && x > 0) {
							if (x % 3 == 0)
								s += " | | ";
							else
								s += " | ";
						} else if (x != 0 || i != k)
							s += '.';
						s += pencilmarks.isPossible(y, x, i + 1) ? i + 1 : " ";
					}
				}
				s += '\n';
			}
		}
		return s;
	}

	public static String showPositions(boolean[]... positions) {
		String s = "";
		for (boolean[] p : positions) {
			s += '[';
			for (boolean b : p)
				s += b ? '1' : ' ';
			s += "]\n";
		}
		return s;
	}

	public static String showWorkspaceCertainty(IPencilmarks pencilmarks) {
		String s = "";

		for (int y = 0; y < DIMENSION; y++) {

			if (y > 0) {
				s += '\n';
				if (y % 3 == 0)
					s += lineOf('-', 17) + "n";
			}

			for (int x = 0; x < DIMENSION; x++) {
				if (x > 0)
					if (x % 3 == 0)
						s += '|';
					else
						s += '.';

				s += pencilmarks.getNumberOfPossibilities(y, x);
			}
		}

		s += '\n';

		return s;
	}

}
