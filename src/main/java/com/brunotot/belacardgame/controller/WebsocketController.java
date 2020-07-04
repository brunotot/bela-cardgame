package com.brunotot.belacardgame.controller;

	import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

import com.brunotot.belacardgame.Room;

@Controller
public class WebsocketController {
	
	@Autowired
	Map<String, Room> rooms;

	@MessageMapping("/update/{roomId}")
	@SendTo("/topic/updateRoom/{roomId}")
	public Room updateRoom(@DestinationVariable("roomId") String roomId, SimpMessageHeaderAccessor headerAccessor, @Header("nickname") String username) throws Exception {
		headerAccessor.getSessionAttributes().put("roomid",  roomId);
		headerAccessor.getSessionAttributes().put("nickname", username);
		return rooms.get(roomId);
		
	}

	@MessageMapping("/voidSub/{roomId}")
	@SendTo("/topic/voidSubscription/{roomId}")
	public Room updateRoom(@DestinationVariable("roomId") String roomId) throws Exception {
		return rooms.get(roomId);
	}
	
	@MessageMapping("/msgGame/{roomId}")
	@SendTo("/topic/game/{roomId}")
	public Room game(@DestinationVariable("roomId") String roomId) throws Exception {
		return rooms.get(roomId);
	}
	
}
