package sudoku;

import action.Undoable;

public interface IBoard {

	void copy(IBoard other);

	boolean equals(Object object);

	int get(int y, int x);

	boolean isSolved();

	boolean isValid();

	void loadValues(int[][] values);

	@Undoable(undoMethod = "setValue", args = { "R", "1", "2" })
	int setValue(int value, int y, int x);

}