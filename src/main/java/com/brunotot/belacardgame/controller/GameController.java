package com.brunotot.belacardgame.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.brunotot.belacardgame.Game;
import com.brunotot.belacardgame.Player;
import com.brunotot.belacardgame.Room;

@Controller
@RequestMapping("game")
public class GameController {
	
	@PostMapping("/start")
	@ResponseBody
	public String start(@RequestParam("roomId") String roomId, @RequestParam("nickname") String nickname) {
		Integer currentNumberOfPlayers = (Integer) WebsocketController.count.get(roomId);
		if (currentNumberOfPlayers != null) {
			currentNumberOfPlayers++;
		} else {
			currentNumberOfPlayers = 1;
		}
		
		if (currentNumberOfPlayers < 5) {
			WebsocketController.count.put(roomId, currentNumberOfPlayers);
			Player thisPlayer = new Player(nickname);
			Room room = Game.getRoomById(roomId);
			if (room == null) {
				room = new Room();
			}
			room.addPlayer(thisPlayer);
			Game.addRoom(roomId, room);
			return "true";
		} else {
			return "false";
		}
	}
	
}
