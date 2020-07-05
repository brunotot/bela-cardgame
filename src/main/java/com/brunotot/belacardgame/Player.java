package com.brunotot.belacardgame;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Player {
	
	private String nickname;

	private List<Card> cards;
	
	public List<Card> getCards() {
		return cards;
	}

	public void setCards(List<Card> cards) {
		this.cards = cards;
		this.sortCards();
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
	
	public void sortCards() {
		Collections.sort(this.cards, new Comparator<Card>() {
            @Override
            public int compare(Card o1, Card o2) {
            	boolean o1Hidden = o1.isHidden();
            	boolean o2Hidden = o2.isHidden();
            	
            	if (o1Hidden && !o2Hidden) {
            		return 1;
            	} else if (!o1Hidden && o2Hidden) {
            		return -1;
            	} else if (o1Hidden && o2Hidden) {
            		return 0;
            	}
            	
            	int suitComparison = o1.getSuit().compareTo(o2.getSuit());
            	if (suitComparison == 0) {
            		return o1.getRank().compareTo(o2.getRank());
            	}
                return suitComparison;
            }
        });
	}
	
}
