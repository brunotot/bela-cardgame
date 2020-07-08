package com.brunotot.belacardgame;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.brunotot.belacardgame.util.Constants;

public class Room {
	
	private List<Card> deck;
	
	private Player player1;

	private Player player2;

	private Player player3;

	private Player player4;

	private Player dealer;
	
	private Player caller;
	
	private int totalPointsTeam1;

	private int totalPointsTeam2;

	private int currentPointsTeam1;

	private int currentPointsTeam2;

	private int currentPrimePointsTeam1;

	private int currentPrimePointsTeam2;
	
	private CardSuit adut;
	
	private boolean started;
	
	public Player getCaller() {
		return caller;
	}

	public void setCaller(Player caller) {
		this.caller = caller;
	}

	public CardSuit getAdut() {
		return adut;
	}

	public void setAdut(CardSuit adut) {
		this.adut = adut;
	}

	public Player getDealer() {
		return this.dealer;
	}

	public void setDealer(Player dealer) {
		this.dealer = dealer;
	}

	public List<Card> getDeck() {
		return deck;
	}

	public void setDeck(List<Card> deck) {
		this.deck = deck;
	}

	public boolean isStarted() {
		return started;
	}

	public void setStarted(boolean started) {
		this.started = started;
	}

	private Player playerToMove;

	private int currentState;
	
	public void setCurrentState(int currentState) {
		this.currentState = currentState;
	}
	
	public Player getPlayerToMove() {
		return playerToMove;
	}

	public void setPlayerToMove(Player playerToMove) {
		this.playerToMove = playerToMove;
	}

	public Player getPlayer1() {
		return player1;
	}

	public Player getPlayer2() {
		return player2;
	}

	public Player getPlayer3() {
		return player3;
	}

	public Player getPlayer4() {
		return player4;
	}

	public int getTotalPointsTeam1() {
		return totalPointsTeam1;
	}

	public int getTotalPointsTeam2() {
		return totalPointsTeam2;
	}

	public int getCurrentPointsTeam1() {
		return currentPointsTeam1;
	}

	public int getCurrentPointsTeam2() {
		return currentPointsTeam2;
	}

	public int getCurrentPrimePointsTeam1() {
		return currentPrimePointsTeam1;
	}

	public int getCurrentPrimePointsTeam2() {
		return currentPrimePointsTeam2;
	}

	public void setPlayer1(Player player1) {
		this.player1 = player1;
	}

	public void setPlayer2(Player player2) {
		this.player2 = player2;
	}

	public void setPlayer3(Player player3) {
		this.player3 = player3;
	}

	public void setPlayer4(Player player4) {
		this.player4 = player4;
	}

	public void setTotalPointsTeam1(int totalPointsTeam1) {
		this.totalPointsTeam1 = totalPointsTeam1;
	}

	public void setTotalPointsTeam2(int totalPointsTeam2) {
		this.totalPointsTeam2 = totalPointsTeam2;
	}

	public void setCurrentPointsTeam1(int currentPointsTeam1) {
		this.currentPointsTeam1 = currentPointsTeam1;
	}

	public void setCurrentPointsTeam2(int currentPointsTeam2) {
		this.currentPointsTeam2 = currentPointsTeam2;
	}

	public void setCurrentPrimePointsTeam1(int currentPrimePointsTeam1) {
		this.currentPrimePointsTeam1 = currentPrimePointsTeam1;
	}

	public void setCurrentPrimePointsTeam2(int currentPrimePointsTeam2) {
		this.currentPrimePointsTeam2 = currentPrimePointsTeam2;
	}
	
	public Room(Player player1, Player player2, Player player3, Player player4) {
		this.totalPointsTeam1 = 0;
		this.totalPointsTeam2 = 0;
		this.currentPrimePointsTeam1 = 0;
		this.currentPrimePointsTeam2 = 0;
		this.currentPointsTeam1 = 0;
		this.currentPointsTeam2 = 0;
		this.player1 = player1;
		this.player2 = player2;
		this.player3 = player3;
		this.player4 = player4;
		this.playerToMove = this.player1;
		this.started = false;
		this.currentState = Constants.BIRANJE_ADUTA;
		this.dealer = this.player4;
		this.adut = null;
		this.caller = null;
		//this.deck = new ArrayList<>(getDeck);
	}
	
	public Room(Player player1, Player player2, Player player3, Player player4, List<Card> deck) {
		this.totalPointsTeam1 = 0;
		this.totalPointsTeam2 = 0;
		this.currentPrimePointsTeam1 = 0;
		this.currentPrimePointsTeam2 = 0;
		this.currentPointsTeam1 = 0;
		this.currentPointsTeam2 = 0;
		this.player1 = player1;
		this.player2 = player2;
		this.player3 = player3;
		this.player4 = player4;
		this.playerToMove = this.player1;
		this.started = false;
		this.deck = new ArrayList<>(deck);
		this.deck.forEach(card -> card.setHidden(false));
		this.currentState = Constants.BIRANJE_ADUTA;
		this.dealer = this.player4;
		this.adut = null;
		this.caller = null;
	}
	
	public boolean addPlayer(Player player) {
		if (this.player1 == null) {
			this.player1 = player;
		} else if (this.player2 == null) {
			this.player2 = player;
		} else if (this.player3 == null) {
			this.player3 = player;
		} else if (this.player4 == null) {
			this.player4 = player;
		} else {
			return false;
		}
		return true;
	}
	
	public boolean removePlayerByNickname(String player) {
		if (this.player1 != null && this.player1.getNickname().equals(player)) {
			this.player1 = null;
		} else if (this.player2 != null && this.player2.getNickname().equals(player)) {
			this.player2 = null;
		} else if (this.player3 != null && this.player3.getNickname().equals(player)) {
			this.player3 = null;
		} else if (this.player4 != null && this.player4.getNickname().equals(player)) {
			this.player4 = null;
		} else {
			return false;
		}
		return true;
	}
	
	public Integer getTotalPlayersNumber() {
		int total = 0;
		if (this.player1 != null) {
			total++;
		}
		if (this.player2 != null) {
			total++;
		}
		if (this.player3 != null) {
			total++;
		}
		if (this.player4 != null) {
			total++;
		}
		return total;
	}

	public void nextPlayer() {
		if (this.playerToMove == this.player1) {
			this.playerToMove = this.player2;
		} else if (this.playerToMove == this.player2) {
			this.playerToMove = this.player3;
		} else if (this.playerToMove == this.player3) {
			this.playerToMove = this.player4;
		} else {
			this.playerToMove = this.player1;
		}
	}
	
	public Player getFirstPlayer() {
		if (this.dealer == this.player1) {
			return this.player2;
		} else if (this.dealer == this.player2) {
			return this.player3;
		} else if (this.dealer == this.player3) {
			return this.player4;
		} else {
			return this.player1;
		}
	}

	public Player getPlayerByNickname(String nickname) {
		if (this.player1 != null && this.player1.getNickname().equals(nickname)) {
			return this.player1;
		} else if (this.player2 != null && this.player2.getNickname().equals(nickname)) {
			return this.player2;
		} else if (this.player3 != null && this.player3.getNickname().equals(nickname)) {
			return this.player3;
		} else if (this.player4 != null && this.player4.getNickname().equals(nickname)) {
			return this.player4;
		}
		return null;
	}
	
	public void shuffleDeck() {
		Collections.shuffle(this.deck);
	}
	
	public void dealCards() {
		List<Card> p1Cards = this.deck.subList(0, 8);
		p1Cards.get(0).setHidden(true);
		p1Cards.get(1).setHidden(true);
		
		List<Card> p2Cards = this.deck.subList(8, 16);
		p2Cards.get(0).setHidden(true);
		p2Cards.get(1).setHidden(true);
		
		List<Card> p3Cards = this.deck.subList(16, 24);
		p3Cards.get(0).setHidden(true);
		p3Cards.get(1).setHidden(true);
		
		List<Card> p4Cards = this.deck.subList(24, 32);
		p4Cards.get(0).setHidden(true);
		p4Cards.get(1).setHidden(true);
		
		this.player1.setCards(p1Cards);
		this.player2.setCards(p2Cards);
		this.player3.setCards(p3Cards);
		this.player4.setCards(p4Cards);
	}

	public int getCurrentState() {
		return this.currentState;
	}

	public void showAllCards() {
		List<Card> cardsPlayer1 = this.player1.getCards();
		cardsPlayer1.forEach(card -> card.setHidden(false));
		this.player1.setCards(cardsPlayer1);
		
		List<Card> cardsPlayer2 = this.player2.getCards();
		cardsPlayer2.forEach(card -> card.setHidden(false));
		this.player2.setCards(cardsPlayer2);
		
		List<Card> cardsPlayer3 = this.player3.getCards();
		cardsPlayer3.forEach(card -> card.setHidden(false));
		this.player3.setCards(cardsPlayer3);
		
		List<Card> cardsPlayer4 = this.player4.getCards();
		cardsPlayer4.forEach(card -> card.setHidden(false));
		this.player4.setCards(cardsPlayer4);
	}
	
}
