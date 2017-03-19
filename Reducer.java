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

import java.io.*;
import java.util.*;
import java.lang.*;

/**
 * Reducer solves the following problem: given a set of sorted input files (each
 * containing the same type of data), merge them into one sorted file.
 *
 */
public class Reducer {
	// list of files for stocking the PQ
	private List<FileIterator> fileList;
	private String type, dirName, outFile;

	public static void main(String[] args) {
		if (args.length != 3) {
			System.out.println("Usage: java Reducer <weather|thesaurus> <dir_name> <output_file>");
			System.exit(1);
		}

		String type = args[0];
		String dirName = args[1];
		String outFile = args[2];

		Reducer r = new Reducer(type, dirName, outFile);
		r.run();

	}

	/**
	 * Constructs a new instance of Reducer with the given type (a string
	 * indicating which type of data is being merged), the directory which
	 * contains the files to be merged, and the name of the output file.
	 */
	public Reducer(String type, String dirName, String outFile) {
		this.type = type;
		this.dirName = dirName;
		this.outFile = outFile;
	}

	/**
	 * Carries out the file merging algorithm described in the assignment
	 * description.
	 */
	public void run() {
		File dir = new File(dirName);
		File[] files = dir.listFiles();
		Arrays.sort(files);

		Record r = null;

		// new instance of the fileList
		fileList = new ArrayList<FileIterator>();

		for (int i = 0; i < files.length; i++) {
			// Variable used to check if the file in the files array at each
			// index is a text file
			File f = files[i];
			if (f.isFile() && f.getName().endsWith(".txt")) {
				fileList.add(new FileIterator(f.getAbsolutePath(), i));
			}
		}

		switch (type) {
		case "weather":
			r = new WeatherRecord(fileList.size());
			break;
		case "thesaurus":
			r = new ThesaurusRecord(fileList.size());
			break;
		default:
			System.out.println("Invalid type of data! " + type);
			System.exit(1);
		}

		// TODO
		FileLinePriorityQueue queue = new FileLinePriorityQueue(fileList.size(), r.getComparator());
		Comparator<FileLine> cmp = r.getComparator();

		for (int i = 0; i < fileList.size(); i++) {
			try {
				queue.insert(fileList.get(i).next());
			} catch (PriorityQueueFullException e) {
				e.printStackTrace();
			}
		}
		
		try {
			// Create the output writer
			PrintWriter out = new PrintWriter(outFile);
			// Initialize the last entry in the record.
			FileLine last = queue.removeMin();
			r.join(last);
			if (fileList.get(last.getFileIterator().getIndex()).hasNext())
				queue.insert(fileList.get(last.getFileIterator().getIndex()).next());
			while (!queue.isEmpty()) {
				FileLine joinfl = queue.removeMin();
				if (cmp.compare(joinfl, last) == 0) {
					r.join(joinfl);
					last = joinfl;
					if (fileList.get(last.getFileIterator().getIndex()).hasNext())
						queue.insert(fileList.get(last.getFileIterator().getIndex()).next());
				} else {
					// write record to the output file
					out.println(r.toString());
					r.clear();
					r.join(joinfl);
					last = joinfl;
					if (fileList.get(last.getFileIterator().getIndex()).hasNext())
						queue.insert(fileList.get(last.getFileIterator().getIndex()).next());
				}

			}
			out.println(r.toString());
			out.flush();
			out.close();

		} catch (PriorityQueueEmptyException e) {
			e.printStackTrace();
		} catch (PriorityQueueFullException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		/*
		 * try {
		 * 
		 * for (FileIterator fileIterator : fileList) { while
		 * (fileIterator.hasNext()) { FileLine fileLine = fileIterator.next();
		 * queue.insert(fileLine); } }
		 * 
		 * File file = new File(outFile); PrintWriter printWriter = new
		 * PrintWriter(outFile); // While the queue has entries, remove the
		 * lowest entry and compare // with r, if they match, join the two while
		 * (queue.isEmpty() == false) { FileLine minEntry = queue.removeMin();
		 * System.out.println(minEntry.getString()); r.join(minEntry);
		 * printWriter.print(r.toString()); } printWriter.print(r.toString());
		 * r.clear();
		 * 
		 * } catch (PriorityQueueFullException e) { e.printStackTrace(); } catch
		 * (PriorityQueueEmptyException e) { e.printStackTrace(); } catch
		 * (FileNotFoundException e) { e.printStackTrace(); }
		 */
	}
}
