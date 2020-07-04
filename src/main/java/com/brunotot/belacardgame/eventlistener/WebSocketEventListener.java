package com.brunotot.belacardgame.eventlistener;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;
import org.springframework.web.socket.messaging.SessionSubscribeEvent;

import com.brunotot.belacardgame.Room;

@Component
public class WebSocketEventListener {

	@Autowired
	private SimpMessageSendingOperations sendingOperations;
	
	@EventListener
	public void handleWebSocketConnectListener(final SessionConnectEvent event) {
		
	}
	
	@Autowired
	Map<String, Room> rooms;
	
	@EventListener
	public void handleWebSocketDisconnectListener(final SessionDisconnectEvent event) {
		StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
		String roomId = (String) headerAccessor.getSessionAttributes().get("roomid");
		Room room = rooms.get(roomId);
		if (room != null) {
			if (!room.isStarted()) {
				String nickname = (String) headerAccessor.getSessionAttributes().get("nickname");
				room.removePlayerByNickname(nickname);
				sendingOperations.convertAndSend("/topic/updateRoom/" + roomId, room);
			}	
		}
	}

	@EventListener
	public void handleWebSocketSubscribeListener(final SessionSubscribeEvent event) {
//		String simpDestination = (String) event.getMessage().getHeaders().get("simpDestination");
//		String[] params = simpDestination.split("/");
//		String roomId = params[params.length - 1];
	}
	
}
