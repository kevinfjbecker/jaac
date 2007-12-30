package sudoku;

import action.Undoable;

public interface IPencilmarks {

	boolean equals(Object object);
	
	boolean[] getMarks(int y, int x);

	int getNumberOfPossibilities(int y, int x);

	int getValue(int y, int x);

	boolean isCertain(int y, int x);

	boolean isPossible(int y, int x, int value);

	@Undoable(undoMethod = "setIsPossible", args = { "0", "1", "2", "R" })
	boolean setIsPossible(int value, int y, int x, boolean isPossible);

}