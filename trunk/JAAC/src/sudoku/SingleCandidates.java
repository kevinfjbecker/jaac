package sudoku;

import static sudoku.Sudoku.DIMENSION;

public class SingleCandidates implements SolverLogic {

	private IPencilmarks _pencilmarks;

	public void findHiddenSingles(IPencilmarks pencilmarks) {
		_pencilmarks = pencilmarks;
		for (int y = 0; y < DIMENSION; y++)
			for (int x = 0; x < DIMENSION; x++) {
				inferenceTraverseRow(y, x);
				inferenceTraverseColumn(y, x);
				inferenceTraverseBox(y, x);
			}
	}

	private void inferenceTraverseBox(int y, int x) {
		for (int k = 1; k <= DIMENSION; k++) {
			if (_pencilmarks.isPossible(y, x, k)) {
				int s = 0;
				for (int i = (y / 3) * 3; i < (y / 3) * 3 + 3; i++)
					for (int j = (x / 3) * 3; j < (x / 3) * 3 + 3; j++)
						if (_pencilmarks.isPossible(i, j, k))
							s++;
				if (s == 1)
					Aggregator.setValue(_pencilmarks,  y, x,k);
			}
		}
	}

	private void inferenceTraverseColumn(int y, int x) {
		for (int k = 1; k <= DIMENSION; k++) {
			if (_pencilmarks.isPossible(y, x, k)) {
				int s = 0;
				for (int i = 0; i < DIMENSION; i++)
					if (_pencilmarks.isPossible(i, x, k))
						s++;
				if (y == 3 && x == 7)
				if (s == 1)
					Aggregator.setValue(_pencilmarks,  y, x,k);
			}
		}
	}

	private void inferenceTraverseRow(int y, int x) {
		for (int k = 1; k <= DIMENSION; k++) {
			if (_pencilmarks.isPossible(y, x, k)) {
				int s = 0;
				for (int i = 0; i < DIMENSION; i++)
					if (_pencilmarks.isPossible(y, i, k))
						s++;
				if (s == 1)
					Aggregator.setValue(_pencilmarks,  y, x, k);
			}
		}
	}

}
