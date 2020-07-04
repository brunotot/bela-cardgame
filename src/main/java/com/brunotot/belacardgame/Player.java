package com.brunotot.belacardgame;

import java.util.ArrayList;
import java.util.List;

public class Player {
	
	private String nickname;

	private List<Card> cards;
	
	public List<Card> getCards() {
		return cards;
	}

	public void setCards(List<Card> cards) {
		this.cards = cards;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public Player(String nickname) {
		super();
		this.nickname = nickname;
		this.cards = new ArrayList<>();
	}
	
	
}
