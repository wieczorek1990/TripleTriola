package com.luke.tripletriola.domain;

import java.security.InvalidParameterException;

public enum Direction {
	UP, LEFT, RIGHT, DOWN;

	public static BoardPosition getNext(int row, int col, Direction direction) {
		switch (direction) {
		case UP:
			return new BoardPosition(row + 1, col);
		case RIGHT:
			return new BoardPosition(row, col + 1);
		case LEFT:
			return new BoardPosition(row, col - 1);
		case DOWN:
			return new BoardPosition(row - 1, col);
		default:
			throw new InvalidParameterException(
					Messages.getString("Direction.direction")); //$NON-NLS-1$
		}
	}

	public Direction getOpositieDirection() {
		switch (this) {
		case UP:
			return DOWN;
		case DOWN:
			return UP;
		case LEFT:
			return RIGHT;
		case RIGHT:
			return LEFT;
		default:
			throw new InvalidParameterException(
					Messages.getString("Direction.direction")); //$NON-NLS-1$
		}
	}
}
