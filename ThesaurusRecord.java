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
import java.util.Comparator;
import java.io.PrintWriter;
import java.io.File;
import java.io.FileNotFoundException;

/**
 * The ThesaurusRecord class is the child class of Record to be used when
 * merging thesaurus data.
 */

public class ThesaurusRecord extends Record {
	// TODO declare data structures required
	FileLine line;
	private String word;
	private ArrayList<String> synonyms;
	private String returnString = "";
	private String returnSynonyms = "";
	private File output;

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

			String s1 = l1.getString();
			String s2 = l2.getString();

			s1 = s1.split(":")[0];
			s2 = s2.split(":")[0];

			return s1.compareTo(s2);	//compares the word of each fileline
			
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
		synonyms.clear();
	}

	/**
	 * This method should parse the list of synonyms contained in the given
	 * FileLine and insert any which are not already found in this
	 * ThesaurusRecord's list of synonyms.
	 */
	public void join(FileLine w) {
		
		//creates the file and printwriter
		output = new File(Reducer.outFile);
		PrintWriter printWriter = null;
		try {
			printWriter = new PrintWriter(Reducer.outFile);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		//splits w's data into useable parts
		String[] line = w.getString().split(":");
		String[] tempsyn = line[1].split(",");
		
		//merges w if the word's match
		if (line[0] == word) {
			//adds each synonym to the list if it doesn't already exist
			for (int i = 0; i < tempsyn.length; ++i) {
				if (!synonyms.contains(tempsyn[i])) {
					synonyms.add(tempsyn[i]);
				}
			}
		}
		
		else {
			//if the words don't match, write the word and synonyms to output
			printWriter.println(this.toString());
		}
		
		// TODO implement join() functionality
	}

	/**
	 * See the assignment description and example runs for the exact output
	 * format.
	 */
	public String toString() {
		//adds the synonyms to the return string and formats everything
		for (int i = 0; i < synonyms.size()-1; ++i) {
			returnSynonyms = returnSynonyms + synonyms.get(i) + ",";
		}
		returnSynonyms = returnSynonyms + synonyms.get(synonyms.size()-1);
		returnString = word + ";" + returnSynonyms;
		
		return returnString;
	}
}
