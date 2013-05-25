package com.luke.tripletriola.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.luke.tripletriola.Resources;
import com.luke.tripletriola.TripleTriola;
import com.luke.tripletriola.domain.Board;
import com.luke.tripletriola.domain.Card;
import com.luke.tripletriola.domain.PlayerColor;

public class GameScreen extends AbstractScreen implements InputProcessor {
	public static final float cardOnBoardScale = 0.35f;
	protected Board board;
	protected PlayerColor currentPlayer = PlayerColor.NONE;
	protected int currentTurn = 0;

	public GameScreen(TripleTriola game) {
		super(game);
		Gdx.input.setInputProcessor(this);
		this.board = new Board(stage);
		nextTurn();
		Image image = Resources.blank.getImage();
		image.scale(-(1.0f - GameScreen.cardOnBoardScale));
		image.setPosition(0, 0);
		stage.addActor(image);
	}

	public void nextTurn() {
		currentTurn++;
		if (currentTurn % 2 == 1)
			currentPlayer = PlayerColor.BLUE;
		else
			currentPlayer = PlayerColor.RED;
	}

	public void placeCard(Card card, int row, int col) {
		board.placeCard(card, currentPlayer, row, col);
	}

	@Override
	public void render(float delta) {
		super.render(delta);
		shapeRenderer.begin(ShapeType.Line);
		shapeRenderer.setColor(0, 0, 0, 1);
		int width = Gdx.graphics.getWidth();
		int height = Gdx.graphics.getHeight();
		// shapeRenderer.line(-width / 2, -height / 2, width / 2, height / 2);
		int x = width / 6;
		shapeRenderer.line(x, -height / 2, x, height / 2);
		shapeRenderer.end();
	}

	@Override
	public boolean keyDown(int keycode) {
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		return false;
	}
}
