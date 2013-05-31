package com.luke.tripletriola.networking;

public class Move {
	public int row;
	public int col;
	public int cardNubmer;

	@Override
	public String toString() {
		return String.format("%d : (%d, %d)", cardNubmer, row, col); //$NON-NLS-1$
	}
}
