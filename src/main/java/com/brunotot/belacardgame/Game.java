package com.brunotot.belacardgame;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Game {

	@Autowired
	Map<String, Room> rooms;
	
}
