package com.luke.tripletriola.domain;

import java.security.InvalidParameterException;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class Card {
	String name;
	Image image;
	String values;

	public Card clone() {
		return new Card(name, values);
	}

	public Card(String name, String values) {
		super();
		if (values.length() != 4)
			throw new InvalidParameterException(
					Messages.getString("Card.values")); //$NON-NLS-1$
		FileHandle file = Gdx.files.internal(String
				.format("cards/%s.jpg", name)); //$NON-NLS-1$
		if (!file.exists())
			throw new InvalidParameterException(String.format(
					Messages.getString("Card.file"), name)); //$NON-NLS-1$
		Image image = new Image(new Texture(file));
		this.name = name;
		this.values = values;
		this.image = image;
	}

	public String getName() {
		return name;
	}

	public Image getImage() {
		return image;
	}

	@SuppressWarnings("nls")
	public int getValue(Direction direction) {
		int beginIndex = direction.ordinal();
		int endIndex = beginIndex + 1;
		String substring = values.substring(beginIndex, endIndex);
		if (substring == "A")
			return 10;
		else
			return Integer.parseInt(substring);
	}
}
