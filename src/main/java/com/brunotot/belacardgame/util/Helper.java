package com.brunotot.belacardgame.util;

import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.context.support.WebApplicationContextUtils;

import com.brunotot.belacardgame.Room;

public class Helper {

	public static Object getBean(HttpServletRequest request, String beanName) {
		return WebApplicationContextUtils
				.getWebApplicationContext(request.getServletContext())
				.getBean(beanName);
	}
	
	public static String isRoomOwner(HttpServletRequest request, String roomId, String nickname) {
		Map<String, Room> rooms = (Map<String, Room>) Helper.getBean(request, "rooms");
		Room room = rooms.get(roomId);
		if (room == null || room.getPlayer1().getNickname().equals(nickname)) {
			return "true";
		} else {
			return "false";
		}
	}
	
	public static String[] getPlayerNicknames(HttpServletRequest request, String roomId, String nickname) {
		Map<String, Room> rooms = (Map<String, Room>) Helper.getBean(request, "rooms");
		Room room = rooms.get(roomId);
		if (room.getPlayer1().getNickname().equals(nickname)) {
			return new String[]{room.getPlayer3().getNickname(), room.getPlayer4().getNickname(), room.getPlayer2().getNickname(), room.getPlayer1().getNickname()};
		} else if (room.getPlayer2().getNickname().equals(nickname)) {
			return new String[]{room.getPlayer4().getNickname(), room.getPlayer1().getNickname(), room.getPlayer3().getNickname(), room.getPlayer2().getNickname()};
		} else if (room.getPlayer3().getNickname().equals(nickname)) {
			return new String[]{room.getPlayer1().getNickname(), room.getPlayer2().getNickname(), room.getPlayer4().getNickname(), room.getPlayer3().getNickname()};
		} else {
			return new String[]{room.getPlayer2().getNickname(), room.getPlayer3().getNickname(), room.getPlayer1().getNickname(), room.getPlayer4().getNickname()};
		}
	}
	
}
