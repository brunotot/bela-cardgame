package com.brunotot.belacardgame;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Zvanje {

	private Map<ZvanjeRank, List<CardSuit>> zvanja;
	
	public Map<ZvanjeRank, List<CardSuit>> getZvanja() {
		return zvanja;
	}

	public void setZvanja(Map<ZvanjeRank, List<CardSuit>> zvanja) {
		this.zvanja = zvanja;
	}

	public Zvanje() {
		this.zvanja = new HashMap<>();
	}
	
	public boolean add(List<Card> cards) {
		Map<ZvanjeRank, List<CardSuit>> zvanja = new HashMap<>();
		List<Card> temp = new ArrayList<>(cards);
		Map<CardRank, Integer> countMap = new HashMap<>();
		Map<CardSuit, List<Card>> map = new HashMap<>();
		for (Card c : cards) {
			Integer count = countMap.get(c.getRank());
			if (count == null) {
				count = 1;
				countMap.put(c.getRank(), count);
			} else {
				countMap.put(c.getRank(), count + 1);
			}
			
			List<Card> list = map.get(c.getSuit());
			if (list == null) {
				list = new ArrayList<>();
				list.add(c);
				map.put(c.getSuit(), list);
			} else {
				list.add(c);
				map.put(c.getSuit(), list);
			}
		}
		
		for (Map.Entry<CardRank, Integer> entry : countMap.entrySet()) {
			CardRank rank = entry.getKey();
			Integer quantity = entry.getValue();
			if (quantity == 4 && !rank.equals(CardRank.VII) && !rank.equals(CardRank.VIII)) {
				String val = rank.toString() + "_" + "X4";
				ZvanjeRank zvanjeRank = ZvanjeRank.valueOf(val);
				List<CardSuit> suits = zvanja.get(zvanjeRank);
				if (suits == null) {
					suits = new ArrayList<>();
					suits.add(null);
					zvanja.put(zvanjeRank, suits);
				}
				
				for (int i = 0; i < temp.size(); i++) {
					Card card = temp.get(i);
					if (card.getRank().equals(rank)) {
						temp.remove(i);
						i--;
					}
				}
			}
		}
		
		List<Card> tempSuccess = new ArrayList<>();
		for (Map.Entry<CardSuit, List<Card>> entry : map.entrySet()) {
			CardSuit suit = entry.getKey();
			List<Card> list = entry.getValue();
		
			List<Integer> indices = new ArrayList<>();
			list.forEach(card -> indices.add(card.getRank().ordinal()));
			
			int counter = 1;
			for (int j = 1; j < indices.size(); j++) {
				if (indices.get(j - 1) == indices.get(j) - 1) {
					counter++;
				} else {
					if (counter >= 3) {
						int zvanjeValue;
						if (counter == 3) {
							zvanjeValue = 20;
						} else if (counter == 4) {
							zvanjeValue = 50;
						} else {
							zvanjeValue = 100;
						}
						CardRank r = CardRank.values()[indices.get(j)];
						String val = r.toString() + "_" + zvanjeValue;
						ZvanjeRank rank = ZvanjeRank.valueOf(val);
						List<CardSuit> suits = zvanja.get(rank);
						if (suits == null) {
							suits = new ArrayList<>();
							suits.add(suit);
							zvanja.put(rank, suits);
						} else {
							suits.add(suit);
							zvanja.put(rank, suits);
						}
						
						for (int k = j - counter + 1; k <= j; k++) {
							for (Card cr : list) {
								if (cr.getRank().ordinal() == indices.get(k)) {
									tempSuccess.add(cr);
									break;
								}
							}
						}
					}
					counter = 1;
				}
				if (j == indices.size() - 1) {
					if (counter >= 3) {
						int zvanjeValue;
						if (counter == 3) {
							zvanjeValue = 20;
						} else if (counter == 4) {
							zvanjeValue = 50;
						} else {
							zvanjeValue = 100;
						}
						CardRank r = CardRank.values()[indices.get(j)];
						String val = r.toString() + "_" + zvanjeValue;
						ZvanjeRank rank = ZvanjeRank.valueOf(val);
						List<CardSuit> suits = zvanja.get(rank);
						if (suits == null) {
							suits = new ArrayList<>();
							suits.add(suit);
							zvanja.put(rank, suits);
						} else {
							suits.add(suit);
							zvanja.put(rank, suits);
						}
						
						for (int k = j - counter + 1; k <= j; k++) {
							for (Card cr : list) {
								if (cr.getRank().ordinal() == indices.get(k)) {
									tempSuccess.add(cr);
									break;
								}
							}
						}
					}
				}
			}
		}
		
		for (Card cardTempSuccess : tempSuccess) {
			temp.remove(cardTempSuccess);
		}
		
		if (!temp.isEmpty()) {
			return false;
		}
		
		// apply changes
		this.zvanja = Stream.concat(this.zvanja.entrySet().stream(), zvanja.entrySet().stream())
		.collect(Collectors.toMap
				(
				    Map.Entry::getKey, 
				    Map.Entry::getValue,
				    (value1, value2) -> {
				    	List<CardSuit> newList = Stream.concat(value1.stream(), value2.stream()).collect(Collectors.toList());
				    	return newList;
				    })
				);
		
		return true;
	}
	
}
