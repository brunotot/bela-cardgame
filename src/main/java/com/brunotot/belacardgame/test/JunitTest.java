package com.brunotot.belacardgame.test;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.brunotot.belacardgame.Card;
import com.brunotot.belacardgame.CardRank;
import com.brunotot.belacardgame.CardSuit;
import com.brunotot.belacardgame.Player;
import com.brunotot.belacardgame.Room;

public class JunitTest {
	
	static List<Card> deck;
	
	/**
	 * Run before all tests.
	 * 
	 * @throws Exception on error
	 */
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		deck = new ArrayList<>();
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
	}
	
	/**
	 * Run after all tests.
	 * 
	 * @throws Exception on error
	 */
	@AfterAll
	static void tearDownAfterClass() throws Exception {
		System.out.println("after all");
	}
	
	/**
	 * Run before each test.
	 * 
	 * @throws Exception on error
	 */
	@BeforeEach
	void setUp() throws Exception {
		System.out.println("before each");
	}
	
	/**
	 * Run after each test.
	 * 
	 * @throws Exception on error
	 */
	@AfterEach
	void tearDown() throws Exception {
		System.out.println("after each");
	}
	
	@Test
	void myTest() throws Exception {
		Player p1 = new Player("test1");
		Player p2 = new Player("test2");
		Player p3 = new Player("test3");
		Player p4 = new Player("test4");
		Room room = new Room(p1, p2, p3, p4, deck);
		room.shuffleDeck();
		room.dealCards();
		
		Player p1New = room.getPlayer1();
		System.out.println("Before sort...");
		p1New.getCards().forEach(c -> System.out.print(c.getRank()+"_"+c.getSuit()+(c.isHidden()?"[hidden]":"")+" "));
		System.out.println();
		System.out.println("After sort...");
		p1New.sortCards();
		p1New.getCards().forEach(c -> System.out.print(c.getRank()+"_"+c.getSuit()+(c.isHidden()?"[hidden]":"")+" "));
	}
		
	
}
