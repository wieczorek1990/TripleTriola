package com.luke.tripletriola.domain;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.luke.tripletriola.Resources;
import com.luke.tripletriola.TripleTriola;
import com.luke.tripletriola.screens.GameScreen;

public class Board {
	BoardPlace boardPlaces[][];
	protected Stage stage;
	protected GameScreen gameScreen;

	public Board(GameScreen gameScreen, Stage stage) {
		this.gameScreen = gameScreen;
		this.boardPlaces = new BoardPlace[3][3];
		this.stage = stage;
		for (int row = 0; row < 3; row++)
			for (int col = 0; col < 3; col++) {
				Card clone = Resources.blank.clone();
				placeCard(clone, PlayerColor.NONE, row, col);
			}
	}

	public boolean placeCard(Card card, PlayerColor whoose, final int row,
			final int col) {
		if (boardPlaces[row][col] != null
				&& boardPlaces[row][col].whoose != PlayerColor.NONE)
			return false;
		boardPlaces[row][col] = new BoardPlace(whoose, card);
		Image image = card.getImage(whoose);
		float scale = GameScreen.cardOnBoardScale;
		if (image.getScaleX() - scale > 0.01)
			image.setScale(scale);
		int w = Gdx.graphics.getWidth();
		int h = Gdx.graphics.getHeight();
		int x = (int) (w - image.getWidth() * 3 * scale) / 2;
		int y = (int) (h - image.getHeight() * 3 * scale) / 2;
		image.setPosition(x + col * image.getWidth() * scale,
				y + row * image.getHeight() * scale);
		image.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if (TripleTriola.DEBUG)
					System.out.println(String.format(
							Messages.getString("Board.clicked"), row, //$NON-NLS-1$
							col));
				gameScreen.placeCard(gameScreen.getCurrentPlayerPreviewCard(),
						row, col);
				super.clicked(event, x, y);
			}
		});
		stage.addActor(image);
		return true;
	}
}
