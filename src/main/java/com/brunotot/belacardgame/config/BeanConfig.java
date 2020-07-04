package com.brunotot.belacardgame.config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.brunotot.belacardgame.Card;
import com.brunotot.belacardgame.CardRank;
import com.brunotot.belacardgame.CardSuit;
import com.brunotot.belacardgame.Room;

@Configuration
public class BeanConfig {
	
	@Bean
	public Map<String, Room> rooms() {
		return new HashMap<>();
	}
	
	@Bean
	public List<Card> deck() {
		List<Card> deck = new ArrayList<>();
		deck.add(new Card(CardSuit.HERC, CardRank.AS, 11, 11));
		deck.add(new Card(CardSuit.HERC, CardRank.KRALJ, 4, 4));
		deck.add(new Card(CardSuit.HERC, CardRank.BABA, 3, 3));
		deck.add(new Card(CardSuit.HERC, CardRank.DECKO, 2, 20));
		deck.add(new Card(CardSuit.HERC, CardRank.X, 10, 10));
		deck.add(new Card(CardSuit.HERC, CardRank.IX, 0.99f, 14));
		deck.add(new Card(CardSuit.HERC, CardRank.VIII, 0.50f, 0.50f));
		deck.add(new Card(CardSuit.HERC, CardRank.VII, 0.01f, 0.01f));
	
		deck.add(new Card(CardSuit.PIK, CardRank.AS, 11, 11));
		deck.add(new Card(CardSuit.PIK, CardRank.KRALJ, 4, 4));
		deck.add(new Card(CardSuit.PIK, CardRank.BABA, 3, 3));
		deck.add(new Card(CardSuit.PIK, CardRank.DECKO, 2, 20));
		deck.add(new Card(CardSuit.PIK, CardRank.X, 10, 10));
		deck.add(new Card(CardSuit.PIK, CardRank.IX, 0.99f, 14));
		deck.add(new Card(CardSuit.PIK, CardRank.VIII, 0.50f, 0.50f));
		deck.add(new Card(CardSuit.PIK, CardRank.VII, 0.01f, 0.01f));
	
		deck.add(new Card(CardSuit.KARO, CardRank.AS, 11, 11));
		deck.add(new Card(CardSuit.KARO, CardRank.KRALJ, 4, 4));
		deck.add(new Card(CardSuit.KARO, CardRank.BABA, 3, 3));
		deck.add(new Card(CardSuit.KARO, CardRank.DECKO, 2, 20));
		deck.add(new Card(CardSuit.KARO, CardRank.X, 10, 10));
		deck.add(new Card(CardSuit.KARO, CardRank.IX, 0.99f, 14));
		deck.add(new Card(CardSuit.KARO, CardRank.VIII, 0.50f, 0.50f));
		deck.add(new Card(CardSuit.KARO, CardRank.VII, 0.01f, 0.01f));
	
		deck.add(new Card(CardSuit.TREF, CardRank.AS, 11, 11));
		deck.add(new Card(CardSuit.TREF, CardRank.KRALJ, 4, 4));
		deck.add(new Card(CardSuit.TREF, CardRank.BABA, 3, 3));
		deck.add(new Card(CardSuit.TREF, CardRank.DECKO, 2, 20));
		deck.add(new Card(CardSuit.TREF, CardRank.X, 10, 10));
		deck.add(new Card(CardSuit.TREF, CardRank.IX, 0.99f, 14));
		deck.add(new Card(CardSuit.TREF, CardRank.VIII, 0.50f, 0.50f));
		deck.add(new Card(CardSuit.TREF, CardRank.VII, 0.01f, 0.01f));
	
		return deck;
	}
}
