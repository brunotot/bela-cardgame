package com.brunotot.belacardgame.controller;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

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
		model.setViewName("game");
		String generatedString = RandomStringUtils.randomAlphanumeric(10);
		model.addObject("roomId", generatedString);
		model.addObject("nickname", nickname);
		return model;
	}
	
	@RequestMapping(value = "/join", method = RequestMethod.POST)
	public ModelAndView joinRoom(@RequestParam("roomid") String roomId, @RequestParam("nickname") String nickname) {
		ModelAndView model = new ModelAndView();
		if (WebsocketController.count.get(roomId) < 4) { // simulated, if exists in database
			model.addObject("roomId", roomId);
			model.addObject("nickname", nickname);
			model.setViewName("game");
		} else {
			model.setViewName("index");
		}
		return model;
	}
	
}
