package enumeration;

import java.util.*;

enum Suit {
	SPADE, DIAMOND, CLUB, HEART
}

enum Rank {
	ACE, TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, TEN, JACK, QUEEN, KING
}

class Card { // A Card
	private Suit suit;
	private Rank rank;

	public Card(Suit suit, Rank rank) {
		this.suit = suit;
		this.rank = rank;
	}

	public Suit getSuit() {
		return suit;
	}

	public Rank getRank() {
		return rank;
	}

	@Override
	public String toString() {
		return "This card is " + rank + " of " + suit;
	}
}

class CardDeck { // A deck of card
	List<Card> deck;

	public CardDeck() {
		deck = new ArrayList<>();
		for (Suit suit : Suit.values()) {
			for (Rank rank : Rank.values()) {
				deck.add(new Card(suit, rank));
			}
		}
	}

	public void print() {
		for (Card card : deck) {
			System.out.println(card);
		}
	}

	public void shuffle() {
		Collections.shuffle(deck);
	}
}

class CardTest{
	public static void main(String[] args) {
		CardDeck deck = new CardDeck();
		deck.print();
		System.out.println("*-----------------------------------*");
		deck.shuffle();
		deck.print();
	}
}
