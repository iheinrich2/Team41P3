import java.util.ArrayList;
import java.util.Comparator;

/**
 * The ThesaurusRecord class is the child class of Record to be used when
 * merging thesaurus data.
 */

public class ThesaurusRecord extends Record {
	// TODO declare data structures required
	private String word;
	private ArrayList<String> synonyms = new ArrayList<String>();
	String output = "";
	String[] tempSynArray;

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

				//gets the string of each file line
			String s1 = l1.getString();
			String s2 = l2.getString();

				//splits the string up to the colon and only takes the first string
			s1 = s1.split(":")[0];
			s2 = s2.split(":")[0];

				//use normal compare at this point to compare the strings
			return s1.compareTo(s2);
			
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
			//set the word
		String word = w.getString();
		word = word.split(":")[0];
		
			//takes the file line string after : and splits it by , to store all synonyms
		tempSynArray = word.split(":")[1].split(",");
		
			//parses the arraylist for the given synonym, and if it doesn't exist, adds it to the list
		for (int i = 0; i < 0; ++i) {
			if (!synonyms.contains(tempSynArray[i])) {
				synonyms.add(tempSynArray[i]);
			}
		}
	}

	/**
	 * See the assignment description and example runs for the exact output
	 * format.
	 */
	public String toString() {
		output = word + ":";
		for (int i = 0; i < tempSynArray.length-1; ++i) {
			output = output + tempSynArray[i] + ",";
		}
		output = output + tempSynArray[tempSynArray.length-1];
		return output;
	}
}
