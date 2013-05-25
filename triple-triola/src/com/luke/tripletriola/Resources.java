package com.luke.tripletriola;

import com.luke.tripletriola.domain.Card;

public class Resources {
	@SuppressWarnings("nls")
	public static final Card cards[] = { new Card("measure", "A872"),
			new Card("repeat", "6A76"), new Card("note", "A3A3"),
			new Card("note2", "59A3"), new Card("note4", "2A69"),
			new Card("note8", "926A"), new Card("note16", "4AA2"),
			new Card("note32", "649A"), new Card("note64", "A486"),
			new Card("note128", "A946"), };
	@SuppressWarnings("nls")
	public static final Card reverse = new Card("reverse", "AAAA");
	@SuppressWarnings("nls")
	public static final Card blank = new Card("blank", "AAAA");
}
