package com.luke.tripletriola.domain;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.luke.tripletriola.Resources;
import com.luke.tripletriola.screens.GameScreen;

public class Board {
	BoardPlace boardPlaces[][];
	protected Stage stage;

	public Board(Stage stage) {
		this.boardPlaces = new BoardPlace[3][3];
		this.stage = stage;
		for (int row = 0; row < 3; row++)
			for (int col = 0; col < 3; col++) {
				Card clone = Resources.blank;
				placeCard(clone, PlayerColor.NONE, row, col);
			}
	}

	public void placeCard(Card card, PlayerColor whoose, int row, int col) {
		boardPlaces[row][col] = new BoardPlace(whoose, card);
		Image image = card.getImage();
		if (image.getScaleX() - GameScreen.cardOnBoardScale > 0.01)
			image.setScale(GameScreen.cardOnBoardScale);
		image.setPosition(col * image.getWidth(), row * image.getHeight());
		stage.addActor(image);
	}
}
