package lab09;

/**
 * NumberName objects store the name of a number. For example, 42 would be
 * stored as "forty two". This class has not been completely tested. It is
 * possible that some number names are incorrect or misspelled.
 * 
 * @author Peter Jensen
 * @version Spring 2014
 */
public class NumberName4 {
	
	// Do not add any instance variables!

	// Name arrays

	final static private int[] groupWeights = { 1000000000, 1000000, 1000, 100 };
	final static private String[] groupNames = { "billion", "million",
			"thousand", "hundred" };
	final static private String[] tensNames = { "twenty", "thirty", "forty",
			"fifty", "sixty", "seventy", "eighty", "ninety" };
	final static private String[] onesNames = { "one", "two", "three", "four",
			"five", "six", "seven", "eight", "nine", "ten", "eleven", "twelve",
			"thirteen", "fourteen", "fifteen", "sixteen", "seventeen",
			"eighteen", "nineteen" };

	// The name of the number is stored as an array of characters.

	private char[] numberName;

	/**
	 * Given an integer, this initializes the object with the name of that
	 * integer.
	 * 
	 * @param any
	 *            integer
	 */
	public NumberName4(int number) {
		numberName = buildName(number, true).trim().toCharArray();
	}

	/**
	 * Returns the name for the given number. The boolean flag indicates if
	 * "zero" can be returned. If true "zero" is returned for 0. If false, an
	 * empty string is returned for 0. The returned string may have a trailing
	 * space.
	 * 
	 * @param any
	 *            integer
	 * @param true if "zero" is allowed return the name for the integer, as a
	 *        String
	 */
	private String buildName(int number, boolean zeroAllowed) {
		if (number < 0)
			return "negative " + buildName(-number, false);

		if (number == 0)
			return zeroAllowed ? "zero" : "";

		for (int i = 0; i < groupWeights.length; i++)
			if (number >= groupWeights[i])
				return buildName(number / groupWeights[i], false) + " "
						+ groupNames[i] + " "
						+ buildName(number % groupWeights[i], false);

		if (number >= 20)
			return tensNames[number / 10 - 2] + " "
					+ buildName(number % 10, false);

		return onesNames[number - 1];
	}

	/**
	 * Returns the name of the number.
	 * 
	 * @return the name of the number
	 */
	public String toString() {
		return new String(numberName);
	}

	/**
	 * Compares two numberNames to see if they represent the same number.
	 * 
	 * @param other
	 *            another numberName object
	 * @return true if the objects represent the same number
	 */
	public boolean equals(Object other) {
		if (!(other instanceof NumberName4))
			return false;

		NumberName4 otherNumberName = (NumberName4) other;

		if (this.numberName.length != otherNumberName.numberName.length)
			return false;

		for (int i = 0; i < this.numberName.length; i++)
			if (this.numberName[i] != otherNumberName.numberName[i])
				return false;

		return true;
	}

	/**
	 * Returns the hashCode for this object. Note that if two NumberName objects
	 * are equal, they must return the same hash code.
	 * 
	 * Note - you need to do a computation here. Do not rely on any hash code
	 * function that is built in to java.
	 * 
	 * @return the hash code for this object
	 */
	public int hashCode() {
		int hash = 0;

		for (char c : numberName)
			hash = hash * 1000 + c;

		return hash;
	}
}
