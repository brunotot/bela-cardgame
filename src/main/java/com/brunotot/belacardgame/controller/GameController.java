package com.brunotot.belacardgame.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.brunotot.belacardgame.Card;
import com.brunotot.belacardgame.CardSuit;
import com.brunotot.belacardgame.Player;
import com.brunotot.belacardgame.Room;
import com.brunotot.belacardgame.Zvanje;
import com.brunotot.belacardgame.util.Constants;
import com.brunotot.belacardgame.util.Helper;
import com.google.gson.Gson;

@Controller
@RequestMapping("game")
public class GameController {
	
	@Autowired
	Map<String, Room> rooms;
	
	@Autowired
	List<Card> deck;
	
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
			room = new Room(player, null, null, null, deck);
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
	
	@PostMapping("/isZvanjeValid")
	@ResponseBody
	public String isZvanjeValid(@RequestParam("roomId") String roomId, @RequestParam("nickname") String nickname, @RequestParam("cards") String cardsJson) {
		Gson gson = new Gson();
		Room room = rooms.get(roomId);
		Player player = room.getPlayerByNickname(nickname);
		String[] cardsString = gson.fromJson(cardsJson, String[].class);
		List<Card> cards = new ArrayList<>();
		for (String src : cardsString) {
			int index = 0;
			for (int i = src.length() - 1; i >= 0; i--) {
				if (src.charAt(i) == '/') {
					index = i;
					break;
				}
			}
			String cardNameWithExtension = src.substring(index + 1);
			String cardNameWithoutExtension = cardNameWithExtension.split("\\.")[0];
			String[] params = cardNameWithoutExtension.split("_");
			Card card = Helper.getCardFromPlayerByName(params, player);
			cards.add(card);
		}

		Zvanje zvanje = room.getTeamZvanjeByPlayerNickname(nickname);
		if (zvanje.add(cards)) {
			return "true";
		}
		
		return "false";
	}
	
	@PostMapping("/getCards")
	@ResponseBody
	public String getCards(@RequestParam("roomId") String roomId, @RequestParam("nickname") String nickname) {
		Gson gson = new Gson();
		Room room = rooms.get(roomId);
		if (room != null) {
			Player player = room.getPlayerByNickname(nickname);
			if (player != null) {
				return gson.toJson(player.getCards());
			}
		}
		return null;
	}
	
	@PostMapping("/isStateZvanje")
	@ResponseBody
	public String isStateZvanje(@RequestParam("roomId") String roomId) {
		Room room = rooms.get(roomId);
		if (room.getCurrentState() == Constants.BIRANJE_ZVANJA) {
			return "true";
		} else {
			return "false";
		}
	}

	@Autowired
	private SimpMessageSendingOperations sendingOperations;
	
	@PostMapping("/moveToNextPlayer")
	@ResponseBody
	public String moveToNextPlayer(@RequestParam("roomId") String roomId, @RequestParam("nickname") String nickname, @RequestParam("event") String event) {
		Room room = rooms.get(roomId);
		int currentState = room.getCurrentState();
		Player player = room.getPlayerByNickname(nickname);
		if (currentState == Constants.BIRANJE_ADUTA) {
			if (event.equals(CardSuit.HERC.toString())) {
				room.setAdut(CardSuit.HERC);
				room.setCurrentState(Constants.BIRANJE_ZVANJA);
				room.setPlayerToMove(room.getFirstPlayer());
				room.setCaller(player);
				room.showAllCards();
				sendingOperations.convertAndSend("/topic/loadAllCards/" + roomId, room);
			} else if (event.equals(CardSuit.PIK.toString())) {
				room.setAdut(CardSuit.PIK);
				room.setCurrentState(Constants.BIRANJE_ZVANJA);
				room.setPlayerToMove(room.getFirstPlayer());
				room.setCaller(player);
				room.showAllCards();
				sendingOperations.convertAndSend("/topic/loadAllCards/" + roomId, room);
			} else if (event.equals(CardSuit.KARO.toString())) {
				room.setAdut(CardSuit.KARO);
				room.setCurrentState(Constants.BIRANJE_ZVANJA);
				room.setPlayerToMove(room.getFirstPlayer());
				room.setCaller(player);
				room.showAllCards();
				sendingOperations.convertAndSend("/topic/loadAllCards/" + roomId, room);
			} else if (event.equals(CardSuit.TREF.toString())) {
				room.setAdut(CardSuit.TREF);
				room.setCurrentState(Constants.BIRANJE_ZVANJA);
				room.setPlayerToMove(room.getFirstPlayer());
				room.setCaller(player);
				room.showAllCards();
				sendingOperations.convertAndSend("/topic/loadAllCards/" + roomId, room);
			} else {
				Player dealer = room.getDealer();
				if (nickname.equals(dealer.getNickname())) {
					return "false";
				}
				room.nextPlayer();
			}
		} else if (currentState == Constants.BIRANJE_ZVANJA) {
			if (event != null && !"".equals(event)) {
				// TODO obrada zvanja
			}
			room.nextPlayer();
			if (nickname.equals(room.getDealer().getNickname())) {
				room.setCurrentState(Constants.BIRANJE_KARTE);
			}
		} else {
			Card chosenCard = null;
			for (Card c : player.getCards()) {
				if (c.getCardImageIdentification().equals(event)) {
					chosenCard = c;
					break;
				}
			}
			player.getCards().remove(chosenCard);
		}
		return "true";
	}
	
}
