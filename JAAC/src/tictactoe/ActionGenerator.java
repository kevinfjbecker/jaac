package tictactoe;

import static tictactoe.ITicTacToe.BOARD_LENGTH;
import static tictactoe.ITicTacToe.BOARD_WIDTH;

import java.lang.reflect.Method;
import java.util.ArrayList;

import action.Action;

public class ActionGenerator {

	private ITicTacToe _ticTacToe;

	public ActionGenerator(ITicTacToe ticTacToe) {
		_ticTacToe = ticTacToe;
	}

	public ArrayList<Action> generateMoves(char c) throws SecurityException,
			NoSuchMethodException {
		ArrayList<Action> legalMoves = new ArrayList<Action>();
		for (int y = 0; y < BOARD_LENGTH; y++)
			for (int x = 0; x < BOARD_WIDTH; x++)
				if (_ticTacToe.isOpen(y, x))
					legalMoves.add(getSetAction(_ticTacToe, y, x, c));
		return legalMoves;
	}

	private Action getSetAction(ITicTacToe ticTacToe, int y, int x, char c)
			throws NoSuchMethodException {

		Class[] parameterTypes;
		parameterTypes = new Class[] { int.class, int.class, char.class };

		Method method;
		method = ticTacToe.getClass().getMethod("set", parameterTypes);

		Object[] arguments;
		arguments = new Object[] { new Integer(y), new Integer(x),
				new Character(c) };

		return new Action(ticTacToe, method, arguments);
	}
}
