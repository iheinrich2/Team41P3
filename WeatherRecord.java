
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
	// TODO declare data structures required
	FileLine line;
	private int station;
	int date;
	private ArrayList<Double> readings;
	private String returnString = "";

	/**
	 * Constructs a new WeatherRecord by passing the parameter to the parent
	 * constructor and then calling the clear method()
	 */
	public WeatherRecord(int numFiles) {
		super(numFiles);
		clear();
	}

	/**
	 * This comparator should first compare the stations associated with the
	 * given FileLines. If they are the same, then the dates should be compared.
	 */
	private class WeatherLineComparator implements Comparator<FileLine> {
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
	 * This method should fill each entry in the data structure containing the
	 * readings with Double.MIN_VALUE
	 */
	public void clear() {
		// TODO initialize/reset data members
		try {
			for (int i = 0; i < readings.size(); i++)
				readings.set(i, Double.MIN_VALUE);
		} catch (NullPointerException e) {
			// nullPointerException is caught
		}

	}

	/**
	 * This method should parse the String associated with the given FileLine to
	 * get the station, date, and reading contained therein. Then, in the data
	 * structure holding each reading, the entry with index equal to the
	 * parameter FileLine's index should be set to the value of the reading.
	 * Also, so that this method will handle merging when this WeatherRecord is
	 * empty, the station and date associated with this WeatherRecord should be
	 * set to the station and date values which were similarly parsed.
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

				// if I took the data from the top of the file containing
				// FileLine li, iterate li's iterator
				li.getFileIterator().next();
			}
		} else {
			if (Integer.parseInt(li.getString().split(",")[0]) == this.station
					&& Integer.parseInt(li.getString().split(",")[1]) == this.date) {
				for (int i = 2; i < line.length; i++) {
					readings.add(Double.parseDouble(line[i]));

					// if I took the data from the top of the file containing
					// FileLine li, iterate li's iterator
					li.getFileIterator().next();
				}
			}
			// if the station and date does not contain a reading for this file
			// (aka reading type (i.e. DEWP, TEMP, etc.)), add a
			// null element to be turned into a '-' later.
			else
				readings.add(null);
		}

	}

	/**
	 * See the assignment description and example runs for the exact output
	 * format.
	 */
	public String toString() {
		// TODO
		for (int i = 0; i < readings.size(); i++) {
			returnString = returnString + station + "," + date + "," + readings.get(i) + "\n";
		}
		return returnString;
	}
}
