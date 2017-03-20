
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
	// TODO declare data structures required
	FileLine line;
	private String word;
	private ArrayList<String> synonyms;

	/**
	 * Constructs a new ThesaurusRecord by passing the parameter to the parent
	 * constructor and then calling the clear method()
	 */
	public ThesaurusRecord(int numFiles) {
		super(numFiles);
		clear();
	}

	/**
	 * This Comparator should simply behave like the default (lexicographic)
	 * compareTo() method for Strings, applied to the portions of the FileLines'
	 * Strings up to the ":" The getComparator() method of the ThesaurusRecord
	 * class will simply return an instance of this class.
	 */
	private class ThesaurusLineComparator implements Comparator<FileLine> {
		public int compare(FileLine l1, FileLine l2) {
			
			//gets the strings and splits them to get the word
			String s1 = l1.getString();
			String s2 = l2.getString();

			s1 = s1.split(":")[0];
			s2 = s2.split(":")[0];

			return s1.compareTo(s2); // compares the word of each fileline

		}

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
	 * This method should (1) set the word to null and (2) empty the list of
	 * synonyms.
	 */
	public void clear() {
		word = null;
		synonyms = new ArrayList<String>();
	}

	/**
	 * This method should parse the list of synonyms contained in the given
	 * FileLine and insert any which are not already found in this
	 * ThesaurusRecord's list of synonyms.
	 */
	public void join(FileLine w) {

		// splits w's data into useable parts
		String[] line = w.getString().split(":");
		String[] tempsyn = line[1].split(",");

		// if the word is null, then the record was just made and needs the key set
		if (word == null) {
			word = line[0];
			// adds all synonyms to the list since it started empty
			for (int i = 0; i < tempsyn.length; ++i) {
					synonyms.add(tempsyn[i]);
			}
		}
		
		// if the word exists, then we are adding to an existing list
		else {
			for (int i = 0; i < tempsyn.length; ++i) {
				// check if the list already contains the synonym
				if (!synonyms.contains(tempsyn[i])) {
					synonyms.add(tempsyn[i]);
				}
			}
		}
		
		//sort the list of synonyms as per instructions
		Collections.sort(synonyms);
		
		// TODO implement join() functionality
	}

	/**
	 * See the assignment description and example runs for the exact output
	 * format.
	 */
	public String toString() {
		String returnString = "";	//string to be returned
		String returnSynonyms = "";	//synonyms that need to be added to the return string
		// adds the synonyms to the string
		for (int i = 0; i < synonyms.size() - 1; ++i) {
			returnSynonyms = returnSynonyms + synonyms.get(i) + ",";
		}
		//the last one can't have a "," at the end
		returnSynonyms = returnSynonyms + synonyms.get(synonyms.size() - 1);
		//add it all to the return string with newlines
		returnString = word + ":" + returnSynonyms + "\n";

		return returnString;
	}
}
