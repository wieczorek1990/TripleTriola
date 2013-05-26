package com.luke.tripletriola;

import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.luke.tripletriola.domain.Side;

public class PreviewClickListener extends ClickListener {
	protected Side side;

	public PreviewClickListener(Side side) {
		this.side = side;
	}
}
