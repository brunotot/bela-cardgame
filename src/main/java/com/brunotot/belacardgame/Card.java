package com.brunotot.belacardgame;

public class Card {

	private CardSuit suit;
	
	private CardRank rank;
	
	private float normalValue;
	
	private float primeValue;

	private boolean hidden;
	
	public boolean isHidden() {
		return hidden;
	}

	public void setHidden(boolean hidden) {
		this.hidden = hidden;
	}

	public CardSuit getSuit() {
		return suit;
	}

	public CardRank getRank() {
		return rank;
	}

	public float getNormalValue() {
		return normalValue;
	}

	public float getPrimeValue() {
		return primeValue;
	}

	public void setSuit(CardSuit suit) {
		this.suit = suit;
	}

	public void setRank(CardRank rank) {
		this.rank = rank;
	}

	public void setNormalValue(float normalValue) {
		this.normalValue = normalValue;
	}

	public void setPrimeValue(float primeValue) {
		this.primeValue = primeValue;
	}
	
	public String getCardImagePath() {
		return "img/cards/" + this.rank + "_" + this.suit + ".png";
	}

	public Card(CardSuit suit, CardRank rank, float normalValue, float primeValue) {
		super();
		this.suit = suit;
		this.rank = rank;
		this.normalValue = normalValue;
		this.primeValue = primeValue;
		this.hidden = false;
	}
	
}
