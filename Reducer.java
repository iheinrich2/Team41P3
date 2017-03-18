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
    private String type,dirName,outFile;

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
	 * Constructs a new instance of Reducer with the given type (a string indicating which type of data is being merged),
	 * the directory which contains the files to be merged, and the name of the output file.
	 */
    public Reducer(String type, String dirName, String outFile) {
		this.type = type;
		this.dirName = dirName;
		this.outFile = outFile;
    }

	/**
	 * Carries out the file merging algorithm described in the assignment description. 
	 */
    public void run() {
		File dir = new File(dirName);
		File[] files = dir.listFiles();
		Arrays.sort(files);

		Record r = null;

		// new instance of the fileList
		fileList = new ArrayList<FileIterator>();

		for(int i = 0; i < files.length; i++) {
			//Variable used to check if the file in the files array at each 
			//index is a text file
			File f = files[i];
			if(f.isFile() && f.getName().endsWith(".txt")) {
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
		FileLinePriorityQueue queue = new FileLinePriorityQueue
				(fileList.size(), r.getComparator());
		try {
			for (int i = 0; i < fileList.size(); i++) {

				//Take from input files and put into queue
				queue.insert(fileList.get(i).next());
			}
		} catch (PriorityQueueFullException e) {}
		FileIterator itr = null;

		try {
			//While the queue has entries, remove the lowest entry and compare
			//with r, if they match, join the two
			while (queue.isEmpty() == false) {
				FileLine minEntry = queue.removeMin();
				itr = minEntry.getFileIterator();
				r.join(minEntry);
			}

	

			queue.insert(itr.next());
		} catch (PriorityQueueFullException e){
			e.printStackTrace();
		} 
		catch (PriorityQueueEmptyException e) {
			e.printStackTrace();
		}
    }
}
