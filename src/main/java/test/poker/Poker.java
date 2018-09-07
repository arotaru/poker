package test.poker;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import card.Card;
import hand.Flush;
import hand.FourOfAKind;
import hand.FullHouse;
import hand.Hand;
import hand.OnePair;
import hand.Straight;
import hand.StraightFlush;
import hand.ThreeOfAKind;
import hand.TwoPair;
import hand.UselessHand;

public class Poker
{

  /*
   * Given a set of 5 playing card identifiers such as 2H, 7C, QS, 10D, 2D;
   * determine if this hand is better than some other hand, according to the rules of poker.
   *
   * Hands will be a string with 5 cards comma separated,
   * each card will have 1-2 digits or JQKA and a suit indicator C,D,S,H (i.e. 10C, KH)
   *
   * Possible Hand Types Below:
   *   Straight flush
   *   Four of a kind
   *   Full house
   *   Flush
   *   Straight
   *   Three of a kind
   *   Two pair
   *   One pair
   *
   * The goal of this is to compare between the hand types.
   * Comparing 2 of the same type (i.e. 2 straights) to determine a winner is outside the scope
   * and will not be tested.
   *
   * Implement PokerHand.isGreaterThan(...) method and return whether or not the first hand wins over the second hand.
   */

   static class PokerHand {

      private String handAsString;
      private Hand pokerHand;

      public PokerHand(String hand) {
         handAsString = hand;
         
         // create a list of Card objects based on the handAsString input
         List<Card> cards = getCards();
         
         // Determine type of hand...
         pokerHand = determineTypeOfHand(cards);
      }
      
      private List<Card> getCards() {
    	  List<Card> cards = createCardsFromInput(parseOutCardInput());
    	  return cards;
      }
      
      private String[] parseOutCardInput() {
    	  return handAsString.split(",");
      }
      
      private List<Card> createCardsFromInput(String[] cardsAsInput) {
    	  return Arrays.asList(cardsAsInput).stream()
    			  .map(card -> new Card(card))
    			  .collect(Collectors.toList());
      }
      
      private Hand determineTypeOfHand(List<Card> cards) {
    	  Hand hand = null;
    	  
    	  if (isFlush(cards)) {
    		  hand = new Flush();
    	  } else if (isFourOfAKind(cards)){
    		  hand = new FourOfAKind();
    	  } else if (isFullHouse(cards)) {
    		  hand = new FullHouse();
    	  } else if (isOnePair(cards)) {
    		  hand = new OnePair();
    	  } else if (isStraight(cards)) {
    		  hand = new Straight();
    	  } else if (isStraightFlush(cards)) {
    		  hand = new StraightFlush();
    	  } else if (isThreeOfAKind(cards)) {
    		  hand = new ThreeOfAKind();
    	  } else if (isTwoPair(cards)) {
    		  hand = new TwoPair();
    	  } else {
    		  hand = new UselessHand();
    	  }
    	  
    	  return hand;
      }
      
      private boolean isFlush(List<Card> cards) {
    	  String firstSuit = cards.get(0).getSuit();
    	  
    	  for (Card card : cards) {
    		  if (!firstSuit.equals(card.getSuit())) {
    			  return false;
    		  }
    	  }
    	  
    	  return true;
      }
      
      private boolean isFourOfAKind(List<Card> cards) {
    	  // repeated code with other methods; should move to one method
    	  List<Integer> cardRanks = cards.stream()
    			  .map(card -> card.getNumber())
    			  .collect(Collectors.toList());
    	  
    	  Set<Integer> distinctCardRanks = new HashSet<>(cardRanks);
    	  
    	  if (distinctCardRanks.size() != 2) {
    		  return false;
    	  }
    	  
    	  Integer[] twoRanks = new Integer[distinctCardRanks.size()];
    	  Iterator<Integer> it = distinctCardRanks.iterator();
    	  
    	  for (int i = 0; it.hasNext(); i++) {
    	        twoRanks[i] = it.next();    	        
    	  }
    	  
    	  int firstRank  = twoRanks[0];
    	  int secondRank = twoRanks[1];
    	  
    	  int firstRankFrequency  = Collections.frequency(cardRanks, firstRank);
    	  int secondRankFrequency = Collections.frequency(cardRanks, secondRank);
    	  // -- end repeated code
    	  
    	  if ((firstRankFrequency == 4 && secondRankFrequency == 1) ||
    			  (firstRankFrequency == 1 && secondRankFrequency == 4)) {
    		  return true;
    	  } else {
    		  return false;
    	  }
      }
      
      private boolean isFullHouse(List<Card> cards) {
    	// repeated code with other methods; should move to one method
    	  List<Integer> cardRanks = cards.stream()
    			  .map(card -> card.getNumber())
    			  .collect(Collectors.toList());
    	  
    	  Set<Integer> distinctCardRanks = new HashSet<>(cardRanks);
    	  
    	  if (distinctCardRanks.size() != 2) {
    		  return false;
    	  }
    	  
    	  Integer[] twoRanks = new Integer[distinctCardRanks.size()];
    	  Iterator<Integer> it = distinctCardRanks.iterator();
    	  
    	  for (int i = 0; it.hasNext(); i++) {
    	        twoRanks[i] = it.next();    	        
    	  }
    	  
    	  int firstRank  = twoRanks[0];
    	  int secondRank = twoRanks[1];
    	  
    	  int firstRankFrequency  = Collections.frequency(cardRanks, firstRank);
    	  int secondRankFrequency = Collections.frequency(cardRanks, secondRank);
    	  // -- end repeated code
    	  
    	  if ((firstRankFrequency == 3 && secondRankFrequency == 2) ||
    			  (firstRankFrequency == 2 && secondRankFrequency == 3)) {
    		  return true;
    	  } else {
    		  return false;
    	  }
      }
      
      private boolean isOnePair(List<Card> cards) {
    	  List<Integer> cardRanks = cards.stream()
    			  .map(card -> card.getNumber())
    			  .collect(Collectors.toList());
    	  
    	  Set<Integer> distinctCardRanks = new HashSet<>(cardRanks);
    	  
    	  if (distinctCardRanks.size() != 4) {
    		  return false;
    	  }
    	  
    	  Integer[] fourRanks = new Integer[distinctCardRanks.size()];
    	  Iterator<Integer> it = distinctCardRanks.iterator();
    	  
    	  for (int i = 0; it.hasNext(); i++) {
    		  fourRanks[i] = it.next();    	        
    	  }
    	  
    	  int firstRank  = fourRanks[0];
    	  int secondRank = fourRanks[1];
    	  int thirdRank  = fourRanks[2];
    	  int fourtRank  = fourRanks[3];
    	  
    	  int firstRankFrequency  = Collections.frequency(cardRanks, firstRank);
    	  int secondRankFrequency = Collections.frequency(cardRanks, secondRank);
    	  int thirdRankFrequency  = Collections.frequency(cardRanks, thirdRank);
    	  int fourtRankFrequency  = Collections.frequency(cardRanks, fourtRank);
    	  
    	  if (firstRankFrequency  == 2 ||
    	      secondRankFrequency == 2 ||
    		  thirdRankFrequency  == 2 ||
    		  fourtRankFrequency  == 2) {
    		  return true;
    	  } else {
    		  return false;
    	  }
      }
      
      private boolean isStraight(List<Card> cards) {
    	  List<Integer> cardRanks = cards.stream()
    			  .map(card -> card.getNumber())
    			  .collect(Collectors.toList());
    	  
    	  Collections.sort(cardRanks);
    	  
    	  boolean failedWithAcesHigh = false;
    	  
    	  for (int i = 0; i < cardRanks.size() -1; i++) {
    		  if (cardRanks.get(i) + 1 != cardRanks.get(i +1)) {
    			  failedWithAcesHigh = true;
    			  break;
    		  }
    	  }
    	  
    	  if (failedWithAcesHigh) {
    		  for (Card card : cards) {
    			  if (card.canCardNumberBeLow()) {
    				  card.setNumber(1);
    			  }
    		  }
    		  
    		  cardRanks = cards.stream()
        			  .map(card -> card.getNumber())
        			  .collect(Collectors.toList());
    		  
    		  Collections.sort(cardRanks);
    		  
    		  for (int i = 0; i < cardRanks.size() -1; i++) {
        		  if (cardRanks.get(i) + 1 != cardRanks.get(i +1)) {
        			  return false;
        		  }
        	  }
    	  }
    	  
    	  return true;
      }
      
      private boolean isStraightFlush(List<Card> cards) {
    	  // check for same suit...
    	  List<String> cardSuits = cards.stream()
    			  .map(card -> card.getSuit())
    			  .collect(Collectors.toList());
    	  
    	  Set<String> distinctCardSuits = new HashSet<>(cardSuits);
    	  
    	  if (distinctCardSuits.size() != 1) {
    		  return false;
    	  }
    	  
    	  // check for straight...
    	  return isStraight(cards);
      }
      
      private boolean isThreeOfAKind(List<Card> cards) {
    	// repeated code with other methods; should move to one method
    	  List<Integer> cardRanks = cards.stream()
    			  .map(card -> card.getNumber())
    			  .collect(Collectors.toList());
    	  
    	  Set<Integer> distinctCardRanks = new HashSet<>(cardRanks);
    	  
    	  if (distinctCardRanks.size() != 3) {
    		  return false;
    	  }
    	  
    	  Integer[] twoRanks = new Integer[distinctCardRanks.size()];
    	  Iterator<Integer> it = distinctCardRanks.iterator();
    	  
    	  for (int i = 0; it.hasNext(); i++) {
    	        twoRanks[i] = it.next();    	        
    	  }
    	  
    	  int firstRank  = twoRanks[0];
    	  int secondRank = twoRanks[1];
    	  int thirdRank  = twoRanks[2];
    	  
    	  int firstRankFrequency  = Collections.frequency(cardRanks, firstRank);
    	  int secondRankFrequency = Collections.frequency(cardRanks, secondRank);
    	  int thirdRankFrequency  = Collections.frequency(cardRanks, thirdRank);
    	  // -- end repeated code
    	  
    	  if ((firstRankFrequency == 3 && secondRankFrequency == 1 && thirdRankFrequency == 1) ||
    		  (firstRankFrequency == 1 && secondRankFrequency == 3 && thirdRankFrequency == 1) ||
    		  (firstRankFrequency == 1 && secondRankFrequency == 1 && thirdRankFrequency == 3)) {
    		  return true;
    	  } else {
    		  return false;
    	  }
      }
      
      private boolean isTwoPair(List<Card> cards) {
    	// repeated code with other methods; should move to one method
    	  List<Integer> cardRanks = cards.stream()
    			  .map(card -> card.getNumber())
    			  .collect(Collectors.toList());
    	  
    	  Set<Integer> distinctCardRanks = new HashSet<>(cardRanks);
    	  
    	  if (distinctCardRanks.size() != 3) {
    		  return false;
    	  }
    	  
    	  Integer[] twoRanks = new Integer[distinctCardRanks.size()];
    	  Iterator<Integer> it = distinctCardRanks.iterator();
    	  
    	  for (int i = 0; it.hasNext(); i++) {
    	        twoRanks[i] = it.next();    	        
    	  }
    	  
    	  int firstRank  = twoRanks[0];
    	  int secondRank = twoRanks[1];
    	  int thirdRank  = twoRanks[2];
    	  
    	  int firstRankFrequency  = Collections.frequency(cardRanks, firstRank);
    	  int secondRankFrequency = Collections.frequency(cardRanks, secondRank);
    	  int thirdRankFrequency  = Collections.frequency(cardRanks, thirdRank);
    	  // -- end repeated code
    	  
    	  if ((firstRankFrequency == 2 && secondRankFrequency == 2 && thirdRankFrequency == 1) ||
    		  (firstRankFrequency == 2 && secondRankFrequency == 1 && thirdRankFrequency == 2) ||
    		  (firstRankFrequency == 1 && secondRankFrequency == 2 && thirdRankFrequency == 2)) {
    		  return true;
    	  } else {
    		  return false;
    	  }
      }

      public Boolean isGreaterThan(PokerHand hand2) {
         // This is where you'll implement the poker hand comparison logic
    	 
    	 /*
    	  *  1. Create Card objects based on input string
    	  *  	- separate out the input string by , and call a method that returns a list of 5 Card objects
    	  *  	- the Card constructor will take a String and parse out the number and suit. (do I want to check for invalid cards?)
    	  *  	- if the Card is an Ace, then have a boolean 'canBeLow' = true, since an Ace can be a 1 in a Straight hand
    	  *  2. Create a Hand object by passing in a list of Card objects
    	  *  	- the Hand abstract class, which has a hand point value and getter/setter for that value
    	  *  	- each hand type will be a sub-class (might want to take this approach over having a boolean value for each
    	  *  		type inside the Hand class, just to show I can do inheritance)
    	  *  	- each Hand sub-class can have an assigned point value, which can be compared against another sub-class
    	  *  		to determine which is higher
    	  */
    	  
    	  return this.pokerHand.getRank() < hand2.pokerHand.getRank() ? true : false;
      }

      @Override
      public String toString() {
         return handAsString;
      }
   }

   public static void testHand1IsGreaterThanHand2(String hand1AsString,
                                                  String hand2AsString,
                                                  Boolean expectedResult) {
      PokerHand hand1 = new PokerHand(hand1AsString);
      PokerHand hand2 = new PokerHand(hand2AsString);
      System.out.println("Hand1[" + hand1 + "] > Hand2[" + hand2 + "] \t-- " +
                         "expected: " + expectedResult + ", actual: " + hand1.isGreaterThan(hand2));
   }

   public static void main(String[] args) {
	   // test that Cards are created correctly
//	  testHand1IsGreaterThanHand2(
//		         "0C,8C,10D,99C,7X",
//		         "S,897H,8D,9H,10D",
//		         true);
	   
	   
      testHand1IsGreaterThanHand2(
         "8C,9C,10C,JC,QC", // straight flush
         "6S,7H,8D,9H,10D",
         true);

      testHand1IsGreaterThanHand2(
         "4H,4D,4C,4S,JS", //four of a kind
         "6C,6S,KH,AS,AD",
         true);

      testHand1IsGreaterThanHand2(
         "6C,6D,6H,9C,KD",
         "5C,3C,10C,KC,7C", // flush
         false);

      testHand1IsGreaterThanHand2(
         "4H,4D,4C,KC,KD", // full house
         "9D,6S,KH,AS,AD",
         true);

      testHand1IsGreaterThanHand2(
         "6C,6D,6H,9C,KD",
         "2C,3C,4S,5S,6S", // straight
         false);

      testHand1IsGreaterThanHand2(
         "7C,7D,7S,3H,4D", // three of a kind
         "9S,6S,10D,AS,AD",
         true);

      testHand1IsGreaterThanHand2(
         "2S,2D,JH,7S,AC",
         "8C,8H,10S,KH,KS", // two pair
         false);

      testHand1IsGreaterThanHand2(
         "AC,AH,3C,QH,10C", // one pair
         "3S,2D,KH,JS,AD",
         true);
   }
}



