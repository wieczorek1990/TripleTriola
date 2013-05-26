package com.luke.tripletriola.domain;

public enum PlayerColor {
	NONE, RED, BLUE;

	public PlayerColor getOpositiePlayerColor() {
		switch (this) {
		case RED:
			return BLUE;
		case BLUE:
			return RED;
		default:
			return NONE;
		}
	}
}
