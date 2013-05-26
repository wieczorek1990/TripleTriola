package com.luke.tripletriola.domain;

public class BoardPosition {
	int row;
	int col;

	public int getRow() {
		return row;
	}

	public int getCol() {
		return col;
	}

	public BoardPosition(int row, int col) {
		super();
		this.row = row;
		this.col = col;
	}
}
