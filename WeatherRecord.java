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

/**
 * The WeatherRecord class is the child class of Record to be used when merging
 * weather data. Station and Date store the station and date associated with
 * each weather reading that this object stores. l stores the weather readings,
 * in the same order as the files from which they came are indexed.
 */
public class WeatherRecord extends Record {
	//FileLine used later to store a passed in FileLine in join
	FileLine line;
	//Station associated with weather reading
	private int station;
	//Date associated with weather reading
	int date;
	//ArrayList used to add and remove readings 
	private ArrayList<Double> readings;
	//String used later in toString to be returned
	private String returnString = "";

	/**
	 * Constructor for the ThesaurusRecord class that passes its parameter to the parent
	 * constructor and calls clear.
	 */
	public WeatherRecord(int numFiles) {
		super(numFiles);
		clear();
	}

	/**
	 * Comparator used to compare stations associated with the passed in FileLines,
	 * the stations are the same then their dates are compared.
	 */
	private class WeatherLineComparator implements Comparator<FileLine> {
		
		/**
		 * Method used to compare two FileLines passed in 
		 * @return either the result of comparing the two dates
		 * or the result of the two stations being compared
		 */
		public int compare(FileLine l1, FileLine l2) {
			// split the first line in order to access station and date
			// information
			String[] lineFile1 = l1.getString().split(",");
			String[] lineFile2 = l2.getString().split(",");

			// assign station information in order to compare
			Integer station1 = Integer.parseInt(lineFile1[0]);
			Integer station2 = Integer.parseInt(lineFile2[0]);

			// check to see if the stations are the same. If so, return the
			// compared dates.
			if (station1.equals(station2)) {
				Integer date1 = Integer.parseInt(lineFile1[1]);
				Integer date2 = Integer.parseInt(lineFile2[1]);

				return date1.compareTo(date2);
			} else {
				return station1.compareTo(station2);
			}
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
	 * WeatherLineComparator class.
	 */
	public Comparator<FileLine> getComparator() {
		return new WeatherLineComparator();
	}

	/**
	 * Method to set each entry containing the readings to Double.MIN_VALUE
	 */
	public void clear() {
		try {
			for (int i = 0; i < readings.size(); i++)
				readings.set(i, Double.MIN_VALUE);
		} catch (NullPointerException e) {
			// nullPointerException is caught
		}

	}

	/**
	 * Method to parse the string given with the passed in FileLine to get the date, 
	 * station and reading. 
	 */
	public void join(FileLine li) {

		// create an array of the strings in FileLine line
		String[] line = li.getString().split(",");

		if (station == 0 && date == 0 && readings.size() == 0) {
			this.line = li;
			this.station = Integer.parseInt(line[0]);
			this.date = Integer.parseInt(line[1]);
			for (int i = 2; i < line.length; i++) {
				readings.add(Double.parseDouble(line[i]));
				li.getFileIterator().next();
			}
		} else {
			if (Integer.parseInt(li.getString().split(",")[0]) == this.station
					&& Integer.parseInt(li.getString().split(",")[1]) == this.date) {
				for (int i = 2; i < line.length; i++) {
					readings.add(Double.parseDouble(line[i]));				
					li.getFileIterator().next();
				}
			}
			// if the station and date do not contain a reading, add a
			// null element to the readings
			else
				readings.add(null);
		}

	}

	/**
	 * toString method for the WeatherRecord class, generates the string representation
	 * of the class
	 * @return returnString, returns the properly formatted 
	 */
	public String toString() {
		for (int i = 0; i < readings.size(); i++) {
			returnString = returnString + station + "," + date + "," + readings.get(i) + "\n";
		}
		return returnString;
	}
}
