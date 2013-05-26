package com.luke.tripletriola.domain;

import java.security.InvalidParameterException;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class Card {
	String name;
	Image image;
	Image redImage;
	Image blueImage;
	String values;

	@Override
	public Card clone() {
		return new Card(name, values);
	}

	public Card(String name, String values) {
		super();
		if (values.length() != 4)
			throw new InvalidParameterException(
					Messages.getString("Card.values")); //$NON-NLS-1$
		this.name = name;
		this.values = values;
		this.image = loadImage(PlayerColor.NONE);
		this.blueImage = loadImage(PlayerColor.BLUE);
		this.redImage = loadImage(PlayerColor.RED);
	}

	public String getName() {
		return name;
	}

	protected Image loadImage(PlayerColor color) {
		String suffix;
		if (color == PlayerColor.BLUE)
			suffix = "-blue"; //$NON-NLS-1$
		else if (color == PlayerColor.RED)
			suffix = "-red"; //$NON-NLS-1$
		else
			suffix = ""; //$NON-NLS-1$
		FileHandle file = Gdx.files.internal(String.format(
				"cards/%s%s.jpg", name, suffix)); //$NON-NLS-1$
		if (!file.exists())
			throw new InvalidParameterException(String.format(
					Messages.getString("Card.file"), name)); //$NON-NLS-1$
		return new Image(new Texture(file));
	}

	public Image getImage(PlayerColor color) {
		if (color == PlayerColor.BLUE)
			return blueImage;
		else if (color == PlayerColor.RED)
			return redImage;
		else if (color == PlayerColor.NONE)
			return image;
		else
			throw new InvalidParameterException(
					Messages.getString("Card.color")); //$NON-NLS-1$
	}

	@SuppressWarnings("nls")
	public int getValue(Direction direction) {
		int beginIndex = direction.ordinal();
		int endIndex = beginIndex + 1;
		String substring = values.substring(beginIndex, endIndex);
		if (substring.equals("A"))
			return 10;
		else
			return Integer.parseInt(substring);
	}
}
