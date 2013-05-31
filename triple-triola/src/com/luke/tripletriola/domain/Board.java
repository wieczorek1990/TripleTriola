package com.luke.tripletriola.domain;

import java.util.ArrayList;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.luke.tripletriola.Resources;
import com.luke.tripletriola.screens.GameScreen;

public class Board {
	protected BoardPlace boardPlaces[][];
	protected Stage stage;
	protected GameScreen gameScreen;

	public Board(GameScreen gameScreen, Stage stage) {
		this.gameScreen = gameScreen;
		this.boardPlaces = new BoardPlace[3][3];
		this.stage = stage;
		for (int row = 0; row < 3; row++)
			for (int col = 0; col < 3; col++) {
				Card clone = Resources.blank.clone();
				placeCard(clone, PlayerColor.NONE, row, col, false);
			}
	}

	public boolean placeCard(Card card, PlayerColor whoose, final int row,
			final int col, boolean isMove) {
		if (boardPlaces[row][col] != null
				&& boardPlaces[row][col].whoose != PlayerColor.NONE && isMove)
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
				gameScreen.placeCard(
						gameScreen.getCurrentPlayerPreviewCardNumber(), row,
						col, true);
				super.clicked(event, x, y);
			}
		});
		stage.addActor(image);

		if (isMove)
			move(card, row, col);
		return true;
	}

	private void move(Card card, int row, int col) {
		attack(card, row, col);
	}

	private void attack(Card card, int row, int col) {
		Direction directions[] = { Direction.UP, Direction.RIGHT,
				Direction.DOWN, Direction.LEFT };
		for (Direction direction : directions)
			strike(card, row, col, direction);
	}

	private void strike(Card card, int row, int col, Direction direction) {
		BoardPosition striked = Direction.getNext(row, col, direction);
		int strikedRow = striked.getRow();
		int strikedCol = striked.getCol();
		Direction opositeDirection = direction.getOpositieDirection();
		if (strikedRow < 0 || strikedRow > 2 || strikedCol < 0
				|| strikedCol > 2)
			return;
		BoardPlace attackingBoardPlace = boardPlaces[row][col];
		BoardPlace strikedBoardPlace = boardPlaces[strikedRow][strikedCol];
		if (strikedBoardPlace.whoose != attackingBoardPlace.whoose
				&& card.getValue(direction) > strikedBoardPlace.card
						.getValue(opositeDirection)) {
			changeOwner(strikedRow, strikedCol);
		}
	}

	private void changeOwner(int row, int col) {
		BoardPlace boardPlace = boardPlaces[row][col];
		boardPlace.whoose = boardPlace.whoose.getOpositiePlayerColor();
		placeCard(boardPlace.card, boardPlace.whoose, row, col, false);
	}

	private static int nextInt(int min, int max) {
		Random r = new Random();
		return r.nextInt(max - min + 1) + min;
	}

	public static ArrayList<Integer> prepareCards() {
		int cardCount = Resources.cards.length;
		ArrayList<Integer> cardNumbers = new ArrayList<Integer>();
		for (int card = 0; card < cardCount; card++) {
			cardNumbers.add(card);
		}
		for (int swap = 0; swap < cardCount; swap++) {
			int firstIdx = nextInt(0, cardCount - 1);
			int secondIdx = nextInt(0, cardCount - 1);
			int first = cardNumbers.get(firstIdx);
			int second = cardNumbers.get(secondIdx);
			cardNumbers.set(firstIdx, second);
			cardNumbers.set(secondIdx, first);
		}
		return cardNumbers;
	}
}
