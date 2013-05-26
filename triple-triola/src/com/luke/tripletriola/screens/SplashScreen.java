package com.luke.tripletriola.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Scaling;
import com.luke.tripletriola.TripleTriola;

public class SplashScreen extends AbstractScreen {
	private Texture texture;
	TextureRegion textureRegion;
	private Sprite sprite;

	public SplashScreen(TripleTriola game) {
		super(game);
	}

	@Override
	public void show() {
		super.show();
		texture = new Texture(Gdx.files.internal("graphics/splash.png")); //$NON-NLS-1$
		texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		textureRegion = new TextureRegion(texture, 0, 0, texture.getWidth(),
				texture.getHeight());
		sprite = new Sprite(textureRegion);
		sprite.setSize(1.0f, sprite.getHeight() / sprite.getWidth());
		sprite.setOrigin(sprite.getWidth() / 2, sprite.getHeight() / 2);
		sprite.setPosition(-sprite.getWidth() / 2, -sprite.getHeight() / 2);
	}

	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
		stage.clear();
		Image splashImage = new Image(textureRegion);
		splashImage.setSize(width, height);
		splashImage.setScaling(Scaling.stretch);
		Color color = splashImage.getColor();
		color.a = 0f;
		splashImage.setColor(color);
		SequenceAction sequenceAction = Actions.sequence(Actions.fadeIn(0.25f),
				Actions.delay(0.5f), Actions.fadeOut(0.25f), new Action() {
					@Override
					public boolean act(float delta) {
						game.setScreen(game.getMenuScreen());
						return true;
					}
				});
		splashImage.addAction(sequenceAction);
		stage.addActor(splashImage);
	};

	@Override
	public void dispose() {
		super.dispose();
		texture.dispose();
	}
}
