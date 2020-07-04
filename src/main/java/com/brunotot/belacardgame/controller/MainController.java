package com.brunotot.belacardgame.controller;

import java.util.Map;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.brunotot.belacardgame.Room;

@RestController
public class MainController {

	@RequestMapping(value = "/", method = RequestMethod.GET) 
	public ModelAndView index() {
		ModelAndView model = new ModelAndView();
		model.setViewName("index");
		return model;
	}
	
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public ModelAndView createRoom(@RequestParam("nickname") String nickname) {
		ModelAndView model = new ModelAndView();
		model.setViewName("room");
		String generatedString = RandomStringUtils.randomAlphanumeric(10);
		model.addObject("roomId", generatedString);
		model.addObject("nickname", nickname);
		return model;
	}
	
	@RequestMapping(value = "/setStarted", method = RequestMethod.POST)
	@ResponseBody
	public String setStarted(@RequestParam("roomId") String roomId) {
		Room room = rooms.get(roomId);
		if (room != null) {
			room.setStarted(true);
		}
		return "true";
	}
	
	@Autowired
	Map<String, Room> rooms;
	
	@RequestMapping(value = "/join", method = RequestMethod.POST)
	public ModelAndView joinRoom(@RequestParam("roomid") String roomId, @RequestParam("nickname") String nickname) {
		ModelAndView model = new ModelAndView();
		Room room = rooms.get(roomId);
		if (room == null) {
			model.setViewName("index");
		} else {
			if (room.getTotalPlayersNumber() < 4) {
				model.addObject("roomId", roomId);
				model.addObject("nickname", nickname);
				model.setViewName("room");
			} else {
				model.setViewName("index");
			}
		}
		return model;
	}
	
	@RequestMapping(value = "/game", method = RequestMethod.POST)
	public ModelAndView gameStart(@RequestParam("roomid") String roomId, @RequestParam("nickname") String nickname) {
		ModelAndView model = new ModelAndView();
		model.addObject("roomId", roomId);
		model.addObject("nickname", nickname);
		model.setViewName("game");
		return model;
	}
	
}
