package com.luke.tripletriola.networking;

public class Move {
	int card;
	int column;
	int row;

	public Move(int card, int column, int row) {
		super();
		this.card = card;
		this.column = column;
		this.row = row;
	}

	@Override
	public String toString() {
		return card + ": (" + row + ", " + column + ")";   //$NON-NLS-1$//$NON-NLS-2$//$NON-NLS-3$
	}
}
