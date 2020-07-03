package com.brunotot.belacardgame.eventlistener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;
import org.springframework.web.socket.messaging.SessionSubscribeEvent;

import com.brunotot.belacardgame.Game;
import com.brunotot.belacardgame.Room;
import com.brunotot.belacardgame.controller.WebsocketController;

@Component
public class WebSocketEventListener {

	@Autowired
	private SimpMessageSendingOperations sendingOperations;
	
	@EventListener
	public void handleWebSocketConnectListener(final SessionConnectEvent event) {
		
	}
	
	@EventListener
	public void handleWebSocketDisconnectListener(final SessionDisconnectEvent event) {
		final StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
		final String roomId = (String) headerAccessor.getSessionAttributes().get("roomid");
		final String nickname = (String) headerAccessor.getSessionAttributes().get("nickname");
		final String returnMessage = nickname + " disconnected from room: " + roomId;
		final Integer count = (Integer) WebsocketController.count.get(roomId) - 1;
		WebsocketController.count.put(roomId, count);
		Room room = Game.getRoomById(roomId);
		room.removePlayerByNickname(nickname);
		sendingOperations.convertAndSend("/topic/greetings/" + roomId, returnMessage);
		sendingOperations.convertAndSend("/topic/updateRoom/" + roomId, room);
	}

	@EventListener
	public void handleWebSocketSubscribeListener(final SessionSubscribeEvent event) {
		String simpDestination = (String) event.getMessage().getHeaders().get("simpDestination");
		String[] params = simpDestination.split("/");
		String roomId = params[params.length - 1];
	}
	
}
