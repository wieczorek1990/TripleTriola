package com.luke.tripletriola.screens;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.luke.tripletriola.PreviewClickListener;
import com.luke.tripletriola.Resources;
import com.luke.tripletriola.TripleTriola;
import com.luke.tripletriola.domain.Board;
import com.luke.tripletriola.domain.Card;
import com.luke.tripletriola.domain.Player;
import com.luke.tripletriola.domain.PlayerColor;

// TODO net, timer, score
public class GameScreen extends AbstractScreen {
	public static final float cardOnBoardScale = 0.35f;
	public static final float cardOnPreviewScale = 0.5f;
	protected Player blue;
	protected Board board;
	protected int currentPreviewCardNumberBlue;
	protected int currentPreviewCardNumberRed;
	protected int currentTurn = 0;
	int height;
	protected Image previewCardImageBlue;
	protected Image previewCardImageRed;
	protected Player red;
	int width;

	public GameScreen(TripleTriola game) {
		super(game);
		width = Gdx.graphics.getWidth();
		height = Gdx.graphics.getHeight();
		this.board = new Board(this, stage);
		nextTurn();
	}

	public PlayerColor getCurrentPlayer() {
		if (currentTurn == 10)
			return PlayerColor.NONE;
		if (currentTurn % 2 == 1)
			return PlayerColor.BLUE;
		else
			return PlayerColor.RED;
	}

	public Card getCurrentPlayerPreviewCard() {
		PlayerColor player = getCurrentPlayer();
		if (player == PlayerColor.BLUE)
			return blue.cards.get(currentPreviewCardNumberBlue).clone();
		else if (player == PlayerColor.RED)
			return red.cards.get(currentPreviewCardNumberRed).clone();
		else
			return Resources.reverse.clone();
	}

	private int nextInt(int min, int max) {
		Random r = new Random();
		return r.nextInt(max - min + 1) + min;
	}

	public void nextTurn() {
		if (currentTurn == 0) {
			currentPreviewCardNumberBlue = 0;
			prepareCards();
		}
		currentTurn++;
		if (getCurrentPlayer() != PlayerColor.NONE) {
			setPreviewCard(blue.cards.get(0), PlayerColor.BLUE);
			setPreviewCard(red.cards.get(0), PlayerColor.RED);
		} else {
			setPreviewCard(Resources.reverse, PlayerColor.BLUE);
			setPreviewCard(Resources.reverse, PlayerColor.RED);
		}
	}

	public void placeCard(Card card, int row, int col) {
		if (currentTurn == 10) {
			gameEnd();
			return;
		}
		if (board.placeCard(card, getCurrentPlayer(), row, col)) {
			PlayerColor player = getCurrentPlayer();
			if (player == PlayerColor.BLUE) {
				blue.cards.remove(currentPreviewCardNumberBlue);
				currentPreviewCardNumberBlue = 0;
			} else if (player == PlayerColor.RED) {
				red.cards.remove(currentPreviewCardNumberRed);
				currentPreviewCardNumberRed = 0;
			}
			nextTurn();
		}
	}

	// TODO
	public void gameEnd() {
		System.out.println("Game end!");
	}

	private void prepareCards() {
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
		ArrayList<Card> blueCards = new ArrayList<Card>();
		for (int card = 0; card < 5; card++) {
			blueCards.add(Resources.cards[cardNumbers.get(card)]);
		}
		blue = new Player(blueCards, PlayerColor.BLUE);
		ArrayList<Card> redCards = new ArrayList<Card>();
		for (int card = 5; card < 10; card++) {
			redCards.add(Resources.cards[cardNumbers.get(card)]);
		}
		red = new Player(redCards, PlayerColor.RED);
	}

	@Override
	public void render(float delta) {
		super.render(delta);
		shapeRenderer.begin(ShapeType.Line);
		shapeRenderer.setColor(0, 0, 0, 1);
		int x = width / 4;
		shapeRenderer.line(x, -height / 2, x, height / 2);
		shapeRenderer.line(-x, -height / 2, -x, height / 2);
		shapeRenderer.end();
	}

	public void setNextPreviewCard(PlayerColor side) {
		if (currentTurn == 10) {
			gameEnd();
			return;
		}
		ArrayList<Card> cards;
		int nextPreviewCardNumber;
		Image currentPreviewCardImage;
		if (side == PlayerColor.BLUE) {
			currentPreviewCardImage = previewCardImageBlue;
			cards = blue.cards;
			currentPreviewCardNumberBlue = (currentPreviewCardNumberBlue + 1)
					% cards.size();
			nextPreviewCardNumber = currentPreviewCardNumberBlue;
		} else if (side == PlayerColor.RED) {
			currentPreviewCardImage = previewCardImageRed;
			cards = red.cards;
			currentPreviewCardNumberRed = (currentPreviewCardNumberRed + 1)
					% cards.size();
			nextPreviewCardNumber = currentPreviewCardNumberRed;
		} else
			throw new InvalidParameterException(
					Messages.getString("GameScreen.side")); //$NON-NLS-1$
		currentPreviewCardImage.remove();
		setPreviewCard(cards.get(nextPreviewCardNumber), side);
	}

	public void setPreviewCard(Card card, PlayerColor side) {
		Image previewCardImage = card.getImage(side);
		if (previewCardImage.getListeners().size == 0) {
			float scale = GameScreen.cardOnPreviewScale;
			previewCardImage.setScale(scale);
			int x;
			if (side == PlayerColor.BLUE) {
				previewCardImageBlue = previewCardImage;
				x = 7;
			} else if (side == PlayerColor.RED) {
				previewCardImageRed = previewCardImage;
				x = 1;
			} else
				throw new InvalidParameterException(
						Messages.getString("GameScreen.side")); //$NON-NLS-1$
			previewCardImage.setPosition(
					width * x / 8 - previewCardImage.getWidth() / 2 * scale,
					(height - previewCardImage.getHeight() * scale) / 2);
			previewCardImage.addListener(new PreviewClickListener(side) {
				@Override
				public boolean touchDown(InputEvent event, float x, float y,
						int pointer, int button) {
					setNextPreviewCard(this.side);
					return super.touchDown(event, x, y, pointer, button);
				}
			});
		}
		stage.addActor(previewCardImage);
	}
}
