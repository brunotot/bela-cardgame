package com.brunotot.belacardgame;

public class Room {

	private Player player1;

	private Player player2;

	private Player player3;

	private Player player4;

	private int totalPointsTeam1;

	private int totalPointsTeam2;

	private int currentPointsTeam1;

	private int currentPointsTeam2;

	private int currentPrimePointsTeam1;

	private int currentPrimePointsTeam2;
	
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

	public Room() {
		this.totalPointsTeam1 = 0;
		this.totalPointsTeam2 = 0;
		this.currentPrimePointsTeam1 = 0;
		this.currentPrimePointsTeam2 = 0;
		this.currentPointsTeam1 = 0;
		this.currentPointsTeam2 = 0;
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
	
	public int getTotalPlayersNumber() {
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
	
}