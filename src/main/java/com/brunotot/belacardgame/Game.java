package com.brunotot.belacardgame;

import java.util.HashMap;
import java.util.Map;

public class Game {

	private static Map<String, Room> rooms = new HashMap<>();
	
	public static Map<String, Room> getAllRooms() {
		return rooms;
	}
	
	public static Room getRoomById(String roomId) {
		return rooms.get(roomId);
	}
	
	public static void addRoom(String roomId, Room room) {
		rooms.put(roomId, room);
	}
	
}
