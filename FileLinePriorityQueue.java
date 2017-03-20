import java.util.Comparator;

/**
 * An implementation of the MinPriorityQueueADT interface. This implementation
 * stores FileLine objects. See MinPriorityQueueADT.java for a description of
 * each method.
 *
 */
public class FileLinePriorityQueue implements MinPriorityQueueADT<FileLine> {
	//PriorityQueue to be used and modified for the program
	private FileLine[] queue;
	//Comparator used to compare different objects within queue
	private Comparator<FileLine> cmp;
	//Int used to declare the maxSize of the queue
	private int maxSize;
	//Int used to keep track of the # of items in the queue
	private int numItems;

	/**
	 * Constructor for the FileLinePriorityQueue class, takes in the initial size 
	 * for the queue and the comparator to be used
	 * @param initialSize the initial size for the queue
	 * @param cmp the type of comparator to be used 
	 */
	public FileLinePriorityQueue(int initialSize, Comparator<FileLine> cmp) {
		this.cmp = cmp;
		maxSize = initialSize;
		queue = new FileLine[maxSize];
		numItems = 0;
	
	}

	/**
	 * Removes the minimum element from the Priority Queue, and returns it.
	 * @return the minimum element from the queue
	 */
	public FileLine removeMin() throws PriorityQueueEmptyException {
		// 
		if (numItems <= 0)
			throw new PriorityQueueEmptyException();
		//temporary FileLine to take in the minimum element and be returned
		FileLine temp = queue[0];
		queue[0] = null;
		numItems--;
		return temp;
	}

	/**
	 * Inserts a FileLine into the queue, while keeping its
	 * shape and order properties intact.
	 */
	public void insert(FileLine fl) throws PriorityQueueFullException {
		if (numItems == maxSize)
			throw new PriorityQueueFullException();
		int i = 0;
		while (queue[i] != null)
			i++;
		queue[i] = fl;
		
		//For loop to compare different items within queue to maintain shape and order
		for (int j = i + 1; j < numItems; j++) {
			if (cmp.compare(queue[i], queue[j]) < 0) {
				FileLine temp = queue[i];
				queue[i] = queue[j];
				queue[j] = temp;
				i++;
			} else
				break;
		}
	}

	/**
	 * Checks if the queue is empty
	 * @return true if empty, false if not
	 */
	public boolean isEmpty() {
		return (numItems == 0);
	}
}
