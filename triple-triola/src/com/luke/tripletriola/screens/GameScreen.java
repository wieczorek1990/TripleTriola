package com.luke.tripletriola.screens;

import java.util.ArrayList;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.luke.tripletriola.Resources;
import com.luke.tripletriola.TripleTriola;
import com.luke.tripletriola.domain.Board;
import com.luke.tripletriola.domain.Card;
import com.luke.tripletriola.domain.Player;
import com.luke.tripletriola.domain.PlayerColor;

// TODO net, timer, score, blue/red cards 
public class GameScreen extends AbstractScreen {
	public static final float cardOnBoardScale = 0.35f;
	public static final float cardOnPreviewScale = 0.7f;
	int width;
	int height;
	protected Board board;
	protected PlayerColor currentPlayer = PlayerColor.NONE;
	protected int currentTurn = 0;
	protected int currentPreviewCardNumber;
	protected Player red;
	protected Player blue;
	protected Image previewCardImage;

	public GameScreen(TripleTriola game) {
		super(game);
		width = Gdx.graphics.getWidth();
		height = Gdx.graphics.getHeight();
		this.board = new Board(stage);
		nextTurn();
	}

	private void setNextPreviewCard() {
		previewCardImage.remove();
		ArrayList<Card> cards;
		if (currentPlayer == PlayerColor.BLUE)
			cards = blue.cards;
		else
			cards = red.cards;
		currentPreviewCardNumber = (currentPreviewCardNumber + 1)
				% cards.size();
		if (TripleTriola.DEBUG)
			System.out.println("currentPreviewCardNumber="
					+ currentPreviewCardNumber);
		setPreviewCard(cards.get(currentPreviewCardNumber));
	}

	public void setPreviewCard(Card card) {
		previewCardImage = card.getImage();
		if (previewCardImage.getListeners().size == 0) {
			previewCardImage.setScale(GameScreen.cardOnPreviewScale);
			previewCardImage.setPosition(width * 33 / 48, height * 11 / 64);
			previewCardImage.addListener(new ClickListener() {
				@Override
				public boolean touchDown(InputEvent event, float x, float y,
						int pointer, int button) {
					setNextPreviewCard();
					return super.touchDown(event, x, y, pointer, button);
				}
			});
		}
		stage.addActor(previewCardImage);
	}

	public void nextTurn() {
		if (currentTurn == 0) {
			currentPreviewCardNumber = 0;
			prepareCards();
		}
		currentTurn++;
		if (currentTurn % 2 == 1)
			currentPlayer = PlayerColor.BLUE;
		else
			currentPlayer = PlayerColor.RED;
		if (currentTurn == 10)
			currentPlayer = PlayerColor.NONE;
		if (currentPlayer == PlayerColor.BLUE)
			setPreviewCard(blue.cards.get(0));
		else if (currentPlayer == PlayerColor.RED)
			setPreviewCard(red.cards.get(0));
		else
			setPreviewCard(Resources.reverse);
	}

	private int nextInt(int min, int max) {
		Random r = new Random();
		return r.nextInt(max - min + 1) + min;
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

	public void placeCard(Card card, int row, int col) {
		board.placeCard(card, currentPlayer, row, col);
	}

	@Override
	public void render(float delta) {
		super.render(delta);
		shapeRenderer.begin(ShapeType.Line);
		shapeRenderer.setColor(0, 0, 0, 1);
		// shapeRenderer.line(-width / 2, -height / 2, width / 2, height / 2);
		int x = width / 6;
		shapeRenderer.line(x, -height / 2, x, height / 2);
		shapeRenderer.end();
	}
}
