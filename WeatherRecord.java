
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
	private double[] readings;	//used instead of ArrayList to make the joining code easier

	/**
	 * Constructs a new WeatherRecord by passing the parameter to the parent
	 * constructor and then calling the clear method()
	 */
	public WeatherRecord(int numFiles) {
		super(numFiles);
		readings = new double[numFiles];
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
		station = 0;
		date = 0;
		for (int i = 0; i < readings.length; i++) {
			readings[i] = Double.MIN_VALUE;
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

		// get the station and date
		// merge the readings also handle the empty record merging
		if (station == 0 && date == 0) {
			station = Integer.parseInt(li.getString().split(",")[0]);
			date = Integer.parseInt(li.getString().split(",")[1]);
			readings[li.getFileIterator().getIndex()] = Double.parseDouble(li.getString().split(",")[2]);
		} else {
			readings[li.getFileIterator().getIndex()] = Double.parseDouble(li.getString().split(",")[2]);
		}

	}

	/**
	 * See the assignment description and example runs for the exact output
	 * format.
	 */
	public String toString() {
		
		String returnString = "";
		String returnReadings = "";
		//add the stations and date data to return string
		returnString = returnString + station + "," + date;
		for (int i = 0; i < readings.length; ++i) {
			//if there isn't data, a hyphen should be written
			if (readings[i] == Double.MIN_VALUE) {
				returnReadings = returnReadings + "," + "-";
			}
			//otherwise write the reading's data
			else {
				returnReadings = returnReadings + "," + readings[i];
			}
		}
		//add the readings string to the return string
		returnString = returnString + returnReadings;
		
		return returnString;
	}
}
