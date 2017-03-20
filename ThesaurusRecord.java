/////////////////////////////////////////////////////////////////////////////
// Semester:         CS367 Spring 2017
// PROJECT:          team41_p3
// FILE:             WeatherRecord
//
// TEAM:    Team 41, IDGAF
// Authors: 
// Author1: (Jarrett Benson, jbenson6@wisc.edu, jbenson6, Lec 002)
// Author2: (Cameron Carlson, ccarlson24@wisc.edu, ccarlson, Lec 002) 
// Author3: (Isaac Heinrich, iheinrich@wisc.edu, iheinrich, Lec 002)  
///////////////////////////////////////////////////////////////////////////////

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * The ThesaurusRecord class is the child class of Record to be used when
 * merging thesaurus data.
 */

public class ThesaurusRecord extends Record {
	
	//String to use for the word later
	private String word;
	//ArrayList used to add and remove synonyms 
	private ArrayList<String> synonyms;

	/**
	 * Constructor for the ThesaurusRecord class that passes its parameter to the parent
	 * constructor and calls clear.
	 */
	public ThesaurusRecord(int numFiles) {
		super(numFiles);
		clear();
	}

	/**
	 * Comparator class that behaves much like the default, but is applied to the FileLines'
	 * Strings up until the colon. 
	 */
	private class ThesaurusLineComparator implements Comparator<FileLine> {
		/**
		 * Method to compare the two FileLines passed in 
		 * @return the result of the comparison of the two FileLines' strings
		 */
		public int compare(FileLine l1, FileLine l2) {
			
			//String to store the first passed in FileLine's string
			String s1 = l1.getString();
			//String to store the second passed in FileLine's string
			String s2 = l2.getString();

			s1 = s1.split(":")[0];
			s2 = s2.split(":")[0];

			return s1.compareTo(s2); // compares the word of each fileline

		}
		
		/**
		 * Method to check if something is equal to the object passed in
		 * @return true if it does equal the passed in object, false if not
		 */
		public boolean equals(Object o) {
			return this.equals(o);
		}
	}

	/**
	 * This method should simply create and return a new instance of the
	 * ThesaurusLineComparator class.
	 */
	public Comparator<FileLine> getComparator() {
		return new ThesaurusLineComparator();
	}

	/**
	 * Method to set the word to null, and clears the arrayList of synonyms
	 */
	public void clear() {
		word = null;
		synonyms = new ArrayList<String>();
	}

	/**
	 * Method to parse the list of synonyms in the FileLine that is passed in
	 * and insert any synonyms not already in the ThesaurusRecord's list.
	 */
	public void join(FileLine w) {

		// splits w's data into useable parts
		String[] line = w.getString().split(":");
		String[] tempsyn = line[1].split(",");

		// merges w if the word's match
		if (word == null) {
			word = line[0];
			// adds each synonym to the list if it doesn't already exist
			for (int i = 0; i < tempsyn.length; ++i) {
					synonyms.add(tempsyn[i]);
			}
		}
		
		else {
			for (int i = 0; i < tempsyn.length; ++i) {
				if (!synonyms.contains(tempsyn[i])) {
					synonyms.add(tempsyn[i]);
				}
			}
		}
		
		Collections.sort(synonyms);
	}

	/**
	 * toString method for the ThesaurusRecord class, generates the string representation
	 * of the class
	 * @return returnString, returns the properly formatted synonyms with word
	 */
	public String toString() {
		//String to be returned
		String returnString = "";
		//String used to keep track of synonyms
		String returnSynonyms = "";
		// adds the synonyms to the return string and formats everything
		for (int i = 0; i < synonyms.size() - 1; ++i) {
			returnSynonyms = returnSynonyms + synonyms.get(i) + ",";
		}
		returnSynonyms = returnSynonyms + synonyms.get(synonyms.size() - 1);
		returnString = word + ":" + returnSynonyms + "\n";

		return returnString;
	}
}
