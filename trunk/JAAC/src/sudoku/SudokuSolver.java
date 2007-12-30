package sudoku;

import static sudoku.Sudoku.DIMENSION;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Proxy;

import action.Action;
import action.ActionHistory;
import action.ActionProxy;
import action.Executor;

public class SudokuSolver {

	public static void main(String... args) throws IllegalArgumentException,
			SecurityException, ClassNotFoundException, IllegalAccessException,
			InvocationTargetException, NoSuchMethodException {

		SudokuSolver sudokuSolver = new SudokuSolver();

		sudokuSolver.loadValues(Sudoku.fiendishExample);
		System.out.println(sudokuSolver.showBoard());

		long t = System.currentTimeMillis();

		sudokuSolver.attemptSolution();

		t = System.currentTimeMillis() - t;

		System.out.println(sudokuSolver.showBoard());

		System.out.println("execution time: " + t + "ms.");

	}

	private ActionHistory _actionHistory;

	private IBoard _board;

	private ActionProxy _boardProxy;

	private Executor _defaultExecutor;

	private IPencilmarks _pencilmarks;

	private ActionProxy _pencilmarksProxy;

	private SingleCandidates _singleCandidates;

	public SudokuSolver() {

		_defaultExecutor = new Executor() {
			public Object execute(Action action)
					throws IllegalArgumentException, IllegalAccessException,
					InvocationTargetException, SecurityException,
					NoSuchMethodException {
				return action.execute();
			}
		};

		_actionHistory = new ActionHistory();

		_boardProxy = new ActionProxy(new Board());

		_boardProxy.setActionHandler(_defaultExecutor);

		_board = (IBoard) Proxy.newProxyInstance(Board.class.getClassLoader(),
				new Class[] { IBoard.class }, _boardProxy);

		_pencilmarksProxy = new ActionProxy(new Pencilmarks());

		_pencilmarksProxy.setActionHandler(_defaultExecutor);

		_pencilmarks = (IPencilmarks) Proxy.newProxyInstance(Pencilmarks.class
				.getClassLoader(), new Class[] { IPencilmarks.class },
				_pencilmarksProxy);

		_singleCandidates = new SingleCandidates();
	}

	public void align() {
		for (int y = 0; y < DIMENSION; y++)
			for (int x = 0; x < DIMENSION; x++)
				if (_board.get(y, x) != 0)
					Aggregator.setValue(_pencilmarks, y, x, _board.get(y, x));

	}

	private void applyAriadnesThread() throws IllegalArgumentException,
			SecurityException, ClassNotFoundException, IllegalAccessException,
			InvocationTargetException, NoSuchMethodException {

		for (int y = 0; y < DIMENSION; y++)
			for (int x = 0; x < DIMENSION; x++)
				if (_board.get(y, x) == 0)
					for (int value = 1; value <= DIMENSION; value++)
						if (_pencilmarks.isPossible(y, x, value)) {

							_actionHistory.openTransaction();
							Aggregator.setValue(_pencilmarks, y, x, value);
							applyLogic();
							_actionHistory.closeTransaction();

							if (!_board.isValid() || hasBlanksInPencilmarks()) {
								_actionHistory.undo();
							} else {
								applyAriadnesThread();
							}

							if (_board.isSolved())
								return;
							_actionHistory.undo();
						}
	}

	private void applyLogic() {
		IBoard previous = new Board();
		do {
			do {
				previous.copy(_board);
				eliminationStep();
				fillInSingleCandidates();

			} while (!_board.equals(previous));
			previous.copy(_board);
			_singleCandidates.findHiddenSingles(_pencilmarks);
			fillInSingleCandidates();

		} while (!_board.equals(previous));
	}

	public void attemptSolution() throws IllegalArgumentException,
			SecurityException, ClassNotFoundException, IllegalAccessException,
			InvocationTargetException, NoSuchMethodException {

		align();

		applyLogic();

		_boardProxy.setActionHandler(_actionHistory);
		_pencilmarksProxy.setActionHandler(_actionHistory);

		applyAriadnesThread();

	}

	private void eliminationStep() {
		for (int y = 0; y < DIMENSION; y++)
			for (int x = 0; x < DIMENSION; x++) {
				eliminationTraverseRow(y, x);
				eliminationTraverseColumn(y, x);
				eliminationTraverseBox(y, x);
			}
	}

	private void eliminationTraverseBox(int y, int x) {
		for (int i = (y / 3) * 3; i < (y / 3) * 3 + 3; i++)
			for (int k = (x / 3) * 3; k < (x / 3) * 3 + 3; k++)
				if (_board.get(i, k) != 0 && (i != y || k != x))
					_pencilmarks.setIsPossible(_board.get(i, k), y, x, false);
	}

	private void eliminationTraverseColumn(int y, int x) {
		for (int i = 0; i < DIMENSION; i++)
			if (_board.get(i, x) != 0 && i != y)
				_pencilmarks.setIsPossible(_board.get(i, x), y, x, false);
	}

	private void eliminationTraverseRow(int y, int x) {
		for (int i = 0; i < DIMENSION; i++)
			if (_board.get(y, i) != 0 && i != x)
				_pencilmarks.setIsPossible(_board.get(y, i), y, x, false);
	}

	private void fillInSingleCandidates() {
		for (int y = 0; y < DIMENSION; y++)
			for (int x = 0; x < DIMENSION; x++)
				if (_pencilmarks.isCertain(y, x))
					_board.setValue(_pencilmarks.getValue(y, x), y, x);
	}

	private boolean hasBlanksInPencilmarks() {
		for (int y = 0; y < DIMENSION; y++)
			for (int x = 0; x < DIMENSION; x++)
				if (_pencilmarks.getNumberOfPossibilities(y, x) == 0)
					return true;
		return false;
	}

	public void loadValues(int[][] boardValues) {
		_board.loadValues(boardValues);
	}

	public void loadValues(String s) {
		int[][] a = new int[DIMENSION][DIMENSION];
		for (int i = 0; i < s.length(); i++)
			if (s.charAt(i) == '-')
				a[i / DIMENSION][i % DIMENSION] = 0;
			else
				a[i / DIMENSION][i % DIMENSION] = s.charAt(i) - '0';
		_board.loadValues(a);
	}

	public String showBoard() {
		return Viewer.showBoard(_board);
	}

	public String showPencilmarks() {
		return Viewer.showPencilmarks(_pencilmarks);
	}

}
