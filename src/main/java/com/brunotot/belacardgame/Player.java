package com.brunotot.belacardgame;

public class Player {
	
	private String nickname;

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public Player(String nickname) {
		super();
		this.nickname = nickname;
	}
	
}
