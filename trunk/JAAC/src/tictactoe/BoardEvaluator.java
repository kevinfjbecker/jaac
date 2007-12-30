package tictactoe;

import static tictactoe.ITicTacToe.BOARD_LENGTH;
import static tictactoe.ITicTacToe.BOARD_WIDTH;

public class BoardEvaluator {

	private ITicTacToe _ticTacToe;

	private char _winner;

	public BoardEvaluator(ITicTacToe ticTacToe) {
		_ticTacToe = ticTacToe;
	}

	public void clearWinner() {
		_winner = ' ';
	}

	public int evaluate(char c) {
		if (isGameOver()) {
			if (getWinner() == c)
				return 50;
			if (getWinner() == (c == 'x' ? 'o' : 'x'))
				return -50;
		}
		int tally = 0;
		LineEvaluation lineEvaluation = new LineEvaluation(c);
		for (int y = 0; y < BOARD_LENGTH; y++) {
			lineEvaluation.clear();
			for (int x = 0; x < BOARD_WIDTH; x++)
				lineEvaluation.addToTally(_ticTacToe.get(y, x));
			tally += lineEvaluation.getTally();
		}
		for (int x = 0; x < BOARD_WIDTH; x++) {
			lineEvaluation.clear();
			for (int y = 0; y < BOARD_LENGTH; y++)
				lineEvaluation.addToTally(_ticTacToe.get(y, x));
			tally += lineEvaluation.getTally();
		}
		for (int t = 0, b = 2; t <= 2 && b >= 0; t++, b--) {
			if (t != b) {
				lineEvaluation.clear();
				lineEvaluation.addToTally(_ticTacToe.get(0, t));
				lineEvaluation.addToTally(_ticTacToe.get(1, 1));
				lineEvaluation.addToTally(_ticTacToe.get(2, b));
				tally += lineEvaluation.getTally();
			}
		}
		return tally;
	}

	public char getWinner() {
		return _winner;
	}

	public boolean isGameOver() {
		for (int y = 0; y < BOARD_LENGTH; y++)
			if (_ticTacToe.get(y, 0) != ' '
					&& _ticTacToe.get(y, 0) == _ticTacToe.get(y, 1)
					&& _ticTacToe.get(y, 1) == _ticTacToe.get(y, 2)) {
				_winner = _ticTacToe.get(y, 0);
				return true;
			}
		for (int x = 0; x < BOARD_WIDTH; x++)
			if (_ticTacToe.get(0, x) != ' '
					&& _ticTacToe.get(0, x) == _ticTacToe.get(1, x)
					&& _ticTacToe.get(1, x) == _ticTacToe.get(2, x)) {
				_winner = _ticTacToe.get(0, x);
				return true;
			}
		if (_ticTacToe.get(1, 1) != ' '
				&& _ticTacToe.get(0, 0) == _ticTacToe.get(1, 1)
				&& _ticTacToe.get(1, 1) == _ticTacToe.get(2, 2)) {
			_winner = _ticTacToe.get(1, 1);
			return true;
		}
		if (_ticTacToe.get(1, 1) != ' '
				&& _ticTacToe.get(0, 2) == _ticTacToe.get(1, 1)
				&& _ticTacToe.get(1, 1) == _ticTacToe.get(2, 0)) {
			_winner = _ticTacToe.get(1, 1);
			return true;
		}

		for (int y = 0; y < BOARD_LENGTH; y++)
			for (int x = 0; x < BOARD_WIDTH; x++)
				if (_ticTacToe.get(y, x) == ' ')
					return false;

		_winner = ' ';
		return true;
	}

}
