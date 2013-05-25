package com.luke.tripletriola.domain;

import java.security.InvalidParameterException;
import java.util.ArrayList;

public class Player {
	public ArrayList<Card> cards;
	PlayerColor color;

	public Player(ArrayList<Card> cards, PlayerColor color) {
		super();
		if (color == PlayerColor.NONE)
			throw new InvalidParameterException(
					Messages.getString("Player.color")); //$NON-NLS-1$
		this.cards = cards;
		this.color = color;
	}
}
