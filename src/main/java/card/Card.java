package card;

public class Card {
	
	private int number;
	private String suit;
	private boolean cardNumberCanBeLow = false; // If Card is an Ace, this value will be true, in case of a Straight hand
	private int jackValue = 11;
	private int queenValue = 12;
	private int kingValue = 13;
	private int aceValue = 14;
	private boolean validNumber = false;
	private boolean validSuit = false;
	private boolean cardIsValid;
	private int invalidCardNumber = -1;
	private final String jack = "J";
	private final String queen = "Q";
	private final String king = "K";
	private final String ace = "A";
	private final String clubs = "C";
	private final String hearts = "H";
	private final String diamonds = "D";
	private final String spades = "S";
	private int minDigitValue = 2;
	private int maxDigitValue = 10;

	public Card(String input) {
		if (input != null && input.length() < 2 || input.length() > 3) {
			setCardIsInvalid();
			return;
		}
		
		setNumber(parseOutNumber(input));
		if (validNumber) {
			setSuit(parseOutSuit(input));
		} else {
			setCardIsInvalid();
			return;
		}
		
		if (validNumber && validSuit) {
			setCardIsValid();
		} else {
			setCardIsInvalid();
			return;
		}
		
		if (number == aceValue) {
			cardNumberCanBeLow = true;
		}
	}
	
	/**
	 * eg: 1C or 10D or JS... or 19F
	 * @param input
	 * @return
	 */
	private int parseOutNumber(String input) {
		String initialNumber = "";
		int parsedNumber = invalidCardNumber;
		
		initialNumber = getInitialNumber(input);
		
		// test for 57D for example
		if (validCardDigitValue(initialNumber)) {
			validNumber = true;
		} else {
			validNumber = false;
			return invalidCardNumber;
		}
		
		try {
			parsedNumber = Integer.parseInt(initialNumber); // testing for 2-10
		} catch (NumberFormatException e) {
			parsedNumber = convertFaceCardValueToIntValue(initialNumber); // testing for J, Q, K, A
		}
		
		return parsedNumber;
	}
	
	private String getInitialNumber(String input) {
		String initialNumber = "";
		
		if (input.length() == 2) {
			initialNumber = input.substring(0, 1); 
		} else if (input.length() == 3) {
			initialNumber = input.substring(0, 2);
		}
		
		return initialNumber;
	}
	
	/**
	 * Checks the digit value of this card is between 2 and 10
	 * @param initialNumber
	 * @return
	 */
	private boolean validCardDigitValue(String initialNumber) {
		try {
			int value = Integer.parseInt(initialNumber); // possible values: 0-99
			return (value >= minDigitValue && value <= maxDigitValue) ? true : false;
		} catch (NumberFormatException e) {
			return true; // the card digit value is not a number (could be JQKA); will deal with this in the calling method
		}
	}
	
	/**
	 * Converts a face value like Jack (J) to a number value like 11.
	 * @param intialNumber
	 * @return
	 */
	private int convertFaceCardValueToIntValue(String initialNumber) {
		int number;
		
		switch (initialNumber.toUpperCase()) {
			case jack: number  = jackValue;
					  break;
			case queen: number = queenValue;
					  break;
			case king: number  = kingValue;
					  break;
			case ace: number   = aceValue;
					  break;
			default : number   = invalidCardNumber;
					  validNumber = false;
					  break;
		}
		
		return number;
	}
	
	private String parseOutSuit(String input) {
		String suit = "";
		
		if (input.length() == 2) {
			suit = input.substring(1, 2); 
		} else if (input.length() == 3) {
			suit = input.substring(2, 3); 
		}
		
		if (suit.trim().isEmpty()) {
			validSuit = false;
			return suit;
		} else {
			suit.toUpperCase();
		}
		
		validSuit = validateSuit(suit);
		
		return suit;
	}
	
	private boolean validateSuit(String suit) {
		boolean valid = false;
		
		switch (suit) {
			case clubs:    valid = true;
					       break;
			case diamonds: valid = true;
						   break;
			case hearts:   valid = true;
			  			   break;
			case spades:   valid = true;
						   break;
		}
		
		return valid;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;	
	}

	public String getSuit() {
		return suit;
	}

	public void setSuit(String suit) {
		this.suit = suit;
	}

	public boolean canCardNumberBeLow() {
		return cardNumberCanBeLow;
	}
	
	public boolean isValue() {
		return cardIsValid;
	}
	
	public boolean isValidSuit() {
		return validSuit;
	}
	
	private void setCardIsValid() {
		cardIsValid = true;
	}
	
	private void setCardIsInvalid() {
		cardIsValid = false;
	}
	
}
