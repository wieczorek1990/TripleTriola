package com.luke.tripletriola.screens;

import java.security.InvalidParameterException;
import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.luke.tripletriola.GameType;
import com.luke.tripletriola.PreviewClickListener;
import com.luke.tripletriola.Resources;
import com.luke.tripletriola.TripleTriola;
import com.luke.tripletriola.domain.Board;
import com.luke.tripletriola.domain.Card;
import com.luke.tripletriola.domain.Player;
import com.luke.tripletriola.domain.PlayerColor;

// TODO net, timer, score, whoose turn
public class GameScreen extends AbstractScreen {
	public static final float cardOnBoardScale = 0.35f;
	public static final float cardOnPreviewScale = 0.5f;
	protected Board board;
	protected int currentPreviewCardNumbers[];
	protected int currentTurn;
	protected int height;
	protected Image previewCardImages[];
	protected PreviewClickListener previewClickListeners[];
	protected Player players[];
	protected int width;
	protected int end;
	protected GameType gameType;
	protected ArrayList<Integer> preparedCards;

	public GameScreen(final TripleTriola game, GameType gameType, ArrayList<Integer> cards) {
		super(game);
		this.width = Gdx.graphics.getWidth();
		this.height = Gdx.graphics.getHeight();
		this.board = new Board(this, stage);
		this.currentTurn = 0;
		this.end = 0;
		this.currentPreviewCardNumbers = new int[2];
		this.players = new Player[2];
		this.previewCardImages = new Image[2];
		this.previewClickListeners = new PreviewClickListener[2];
		this.preparedCards = cards;
		stage.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if (end == 1) {
					end = 2;
					return;
				}
				if (end == 2)
					game.setScreen(game.getMenuScreen());
				super.clicked(event, x, y);
			}
		});
		nextTurn();
	}

	public void gameEnd() {
		dispose();
		game.setScreen(game.getMenuScreen());
	}

	public PlayerColor getCurrentPlayer() {
		if (currentTurn % 2 == 1)
			return PlayerColor.BLUE;
		else
			return PlayerColor.RED;
	}

	public Card getCurrentPlayerPreviewCard() {
		PlayerColor player = getCurrentPlayer();
		if (player != PlayerColor.NONE)
			return players[player.ordinal()].cards.get(
					currentPreviewCardNumbers[player.ordinal()]).clone();
		else
			return Resources.reverse.clone();
	}

	public void nextTurn() {
		if (currentTurn == 0) {
			currentPreviewCardNumbers[0] = 0;
			currentPreviewCardNumbers[1] = 0;
			if (gameType == GameType.SIGNLE)
				setCards(Board.prepareCards());
			else
				setCards(preparedCards);
		}
		if (currentTurn != 9) {
			setPreviewCard(players[0].cards.get(0), PlayerColor.BLUE);
			setPreviewCard(players[1].cards.get(0), PlayerColor.RED);
		} else {
			end = 1;
			setPreviewCard(Resources.reverse, PlayerColor.BLUE);
			setPreviewCard(Resources.reverse, PlayerColor.RED);
		}
		currentTurn++;
	}

	public void placeCard(Card card, int row, int col) {
		if (board.placeCard(card, getCurrentPlayer(), row, col, true)) {
			// TODO notify peer
			PlayerColor player = getCurrentPlayer();
			if (player != PlayerColor.NONE) {
				players[player.ordinal()].cards
						.remove(currentPreviewCardNumbers[player.ordinal()]);
				currentPreviewCardNumbers[player.ordinal()] = 0;
			} else {
				throw new InvalidParameterException(
						Messages.getString("GameScreen.player")); //$NON-NLS-1$
			}
			nextTurn();
		}
	}

	public void setCards(ArrayList<Integer> cardNumbers) {
		ArrayList<Card> blueCards = new ArrayList<Card>();
		for (int card = 0; card < 5; card++) {
			blueCards.add(Resources.cards[cardNumbers.get(card)]);
		}
		players[0] = new Player(blueCards, PlayerColor.BLUE);
		ArrayList<Card> redCards = new ArrayList<Card>();
		for (int card = 5; card < 10; card++) {
			redCards.add(Resources.cards[cardNumbers.get(card)]);
		}
		players[1] = new Player(redCards, PlayerColor.RED);
	}

	public void setNextPreviewCard(PlayerColor side) {
		if (side != PlayerColor.NONE) {
			previewCardImages[side.ordinal()].remove();
			previewCardImages[side.ordinal()].removeListener(null);
			ArrayList<Card> cards = players[side.ordinal()].cards;
			int cardCount = cards.size();
			if (cardCount != 0) {
				int nextPreviewCardNumber = (currentPreviewCardNumbers[side
						.ordinal()] + 1) % cardCount;
				currentPreviewCardNumbers[side.ordinal()] = nextPreviewCardNumber;
				setPreviewCard(cards.get(nextPreviewCardNumber), side);
			} else {
				setPreviewCard(Resources.reverse, side);
			}
		} else
			throw new InvalidParameterException(
					Messages.getString("GameScreen.player")); //$NON-NLS-1$
	}

	public void setPreviewCard(Card card, PlayerColor side) {
		Image previewCardImage = card.getImage(side);
		float scale = GameScreen.cardOnPreviewScale;
		previewCardImage.setScale(scale);
		int x;
		if (side == PlayerColor.BLUE) {
			x = 7;
		} else if (side == PlayerColor.RED) {
			x = 1;
		} else
			throw new InvalidParameterException(
					Messages.getString("GameScreen.player")); //$NON-NLS-1$
		previewCardImage.setPosition(
				width * x / 8 - previewCardImage.getWidth() / 2 * scale,
				(height - previewCardImage.getHeight() * scale) / 2);
		PreviewClickListener listener = new PreviewClickListener(side) {
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				setNextPreviewCard(this.side);
				return super.touchDown(event, x, y, pointer, button);
			}
		};
		PreviewClickListener oldListener = previewClickListeners[side.ordinal()];
		previewCardImage.removeListener(oldListener);
		previewCardImage.addListener(listener);
		previewClickListeners[side.ordinal()] = listener;
		previewCardImages[side.ordinal()] = previewCardImage;
		stage.addActor(previewCardImage);
	}
}
