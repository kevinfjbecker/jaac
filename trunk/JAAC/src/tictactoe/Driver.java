package tictactoe;

import static tictactoe.ITicTacToe.BOARD_LENGTH;
import static tictactoe.ITicTacToe.BOARD_WIDTH;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.Scanner;

import action.Action;
import action.ActionHistory;
import action.ActionProxy;
import action.IAction;

public class Driver {

	static ActionProxy actionProxy = new ActionProxy(new TicTacToe());

	static ActionHistory actionHistory = new ActionHistory();

	static {
		actionProxy.setActionHandler(actionHistory);
	}

	static ITicTacToe ticTacToe = (ITicTacToe) Proxy.newProxyInstance(
			TicTacToe.class.getClassLoader(), new Class[] { ITicTacToe.class },
			actionProxy);

	static ActionGenerator actionGenerator = new ActionGenerator(ticTacToe);

	static BoardEvaluator boardEvaluator = new BoardEvaluator(ticTacToe);

	private static int MAX_DEPTH = 10;

	public static void main(String[] args) throws SecurityException,
			NoSuchMethodException, IllegalArgumentException,
			IllegalAccessException, InvocationTargetException,
			ClassNotFoundException {

		char c = 'x';
		for (int i = 1; !boardEvaluator.isGameOver() && i <= 1; i++) {
			makeRandomMove(c);
			System.out.println(ticTacToe);
			c = c == 'x' ? 'o' : 'x';
			if (boardEvaluator.isGameOver())
				break;
			makeHumanMove(c);
			System.out.println(ticTacToe);
			c = c == 'x' ? 'o' : 'x';
		}
		while (!boardEvaluator.isGameOver()) {
			makeMinMaxMove(c);
			System.out.println(ticTacToe);
			c = c == 'x' ? 'o' : 'x';
			if (boardEvaluator.isGameOver())
				break;
			makeHumanMove(c);
			System.out.println(ticTacToe);
			c = c == 'x' ? 'o' : 'x';
		}
	}

	public static void clearBoard(ITicTacToe ticTacToe) {
		for (int y = 0; y < BOARD_LENGTH; y++)
			for (int x = 0; x < BOARD_WIDTH; x++)
				ticTacToe.clear(y, x);
	}

	public static void makeMinMaxMove(char c) throws SecurityException,
			NoSuchMethodException, IllegalArgumentException,
			IllegalAccessException, InvocationTargetException,
			ClassNotFoundException {

		ArrayList<Action> actions;
		actions = actionGenerator.generateMoves(c);
		int[] evaluations = new int[actions.size()];
		for (int i = 0; i < evaluations.length; i++) {
			actions.get(i).execute();
			evaluations[i] = p_min_at(c == 'x' ? 'o' : 'x', 1);
			actionHistory.undo();
		}
		int best = 0;
		for (int i = 1; i < evaluations.length; i++) {
			if (evaluations[i] > evaluations[best])
				best = i;
		}
		actions.get(best).execute();
	}

	private static int p_max_at(char c, int d) throws SecurityException,
			NoSuchMethodException, IllegalArgumentException,
			ClassNotFoundException, IllegalAccessException,
			InvocationTargetException {

		if (d == MAX_DEPTH || boardEvaluator.isGameOver())
			return boardEvaluator.evaluate(c);
		int max = Integer.MIN_VALUE;
		for (IAction a : actionGenerator.generateMoves(c)) {
			a.execute();
			int m = p_min_at(c == 'x' ? 'o' : 'x', d + 1);
			if (max < m)
				max = m;
			actionHistory.undo();
		}
		return max;
	}

	private static int p_min_at(char c, int d) throws SecurityException,
			IllegalArgumentException, NoSuchMethodException,
			ClassNotFoundException, IllegalAccessException,
			InvocationTargetException {

		if (d == MAX_DEPTH || boardEvaluator.isGameOver())
			return boardEvaluator.evaluate(c == 'x' ? 'o' : 'x');
		int min = Integer.MAX_VALUE;
		for (IAction a : actionGenerator.generateMoves(c)) {
			a.execute();
			int m = p_max_at(c == 'x' ? 'o' : 'x', d + 1);
			if (min > m)
				min = m;
			actionHistory.undo();
		}
		return min;
	}

	public static void makeBestMove(char c) throws SecurityException,
			NoSuchMethodException, IllegalArgumentException,
			IllegalAccessException, InvocationTargetException,
			ClassNotFoundException {
		ArrayList<Action> actions;
		actions = actionGenerator.generateMoves(c);
		int[] evaluations = new int[actions.size()];
		for (int i = 0; i < evaluations.length; i++) {
			actions.get(i).execute();
			evaluations[i] = boardEvaluator.evaluate(c);
			actionHistory.undo();
		}
		int best = 0;
		for (int i = 1; i < evaluations.length; i++) {
			if (evaluations[i] > evaluations[best])
				best = i;
		}
		actions.get(best).execute();
	}

	private static void makeHumanMove(char c) {
		System.out.print("Make your move (<int-x: 0-2> <int-y: 0-2>): ");
		Scanner scanner = new Scanner(System.in);
		int x = scanner.nextInt();
		int y = scanner.nextInt();
		ticTacToe.set(y, x, c);
	}

	public static void makeRandomMove(char c) throws NoSuchMethodException,
			IllegalAccessException, InvocationTargetException {
		ArrayList<Action> actions;
		actions = actionGenerator.generateMoves(c);
		actions.get((int) (Math.random() * actions.size())).execute();
	}

}
