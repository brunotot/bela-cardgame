package com.brunotot.belacardgame.controller;

	import java.util.HashMap;
import java.util.Map;

import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

import com.brunotot.belacardgame.Game;
import com.brunotot.belacardgame.Room;

@Controller
public class WebsocketController {

	public static Map<String, Integer> count = new HashMap<>();
	
	@MessageMapping("/hello/{roomId}")
	@SendTo("/topic/greetings/{roomId}")
	public String greeting(
			@DestinationVariable("roomId") String roomId,
			SimpMessageHeaderAccessor headerAccessor,
			@Header("nickname") String username) throws Exception {
		headerAccessor.getSessionAttributes().put("roomid",  roomId);
		headerAccessor.getSessionAttributes().put("nickname", username);
		return username;
	}
	
	@MessageMapping("/hello3/{roomId}")
	@SendTo("/topic/updateRoom/{roomId}")
	public Room updateRoom(@DestinationVariable("roomId") String roomId) throws Exception {
		System.out.println("True");
		Room room = Game.getRoomById(roomId);
		//return "test";
		return room;
	}
	
}
