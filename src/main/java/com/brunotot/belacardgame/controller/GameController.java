package com.brunotot.belacardgame.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.brunotot.belacardgame.Player;
import com.brunotot.belacardgame.Room;

@Controller
@RequestMapping("game")
public class GameController {
	
	@Autowired
	Map<String, Room> rooms;
	
	@PostMapping("/start")
	@ResponseBody
	public String start(@RequestParam("roomId") String roomId, @RequestParam("nickname") String nickname) {
		Room room = rooms.get(roomId);
		Player player = new Player(nickname);
		if (room != null) {
			Integer total = room.getTotalPlayersNumber();
			if (total < 4) {
				room.addPlayer(player);
				rooms.put(roomId, room);
				return "true";
			} else {
				return "false";
			}
		} else {
			room = new Room(player, null, null, null);
			rooms.put(roomId, room);
			return "true";
		}
	}

	@PostMapping("/remove")
	@ResponseBody
	public String remove(@RequestParam("roomId") String roomId) {
		rooms.remove(roomId);
		return "true";
	}
	
	@PostMapping("/moveToNextPlayer")
	@ResponseBody
	public String moveToNextPlayer(@RequestParam("roomId") String roomId, @RequestParam("nickname") String nickname) {
		rooms.get(roomId).nextPlayer();
		return "true";
	}
	
}
