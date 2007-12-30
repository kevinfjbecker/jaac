package tictactoe;

import action.Undoable;

public interface ITicTacToe {

	int BOARD_WIDTH = 3;

	int BOARD_LENGTH = 3;

	@Undoable(undoMethod = "set", args = { "0", "1", "R" })
	char clear(int y, int x);

	char get(int y, int x);

	boolean isOpen(int y, int x);

	@Undoable(undoMethod = "clear", args = { "0", "1" })
	void set(int y, int x, char c);

}
