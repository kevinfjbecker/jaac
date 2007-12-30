package tictactoe;

public class LineEvaluation {

	private char c;

	private int mine, other, empty;

	public LineEvaluation(char c) {
		this.c = c;
	}

	public void addToTally(char c) {
		if (c == this.c)
			mine++;
		else if (c == ' ')
			empty++;
		else
			other++;
	}

	public int getTally() {
		return mine - other;
	}

	public void clear() {
		mine = other = empty = 0;
	}
	
}
