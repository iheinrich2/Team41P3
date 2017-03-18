import java.util.Comparator;

/**
 * An implementation of the MinPriorityQueueADT interface. This implementation stores FileLine objects.
 * See MinPriorityQueueADT.java for a description of each method. 
 *
 */
public class FileLinePriorityQueue implements MinPriorityQueueADT<FileLine> {
    // TODO
	private FileLine[] queue;
    private Comparator<FileLine> cmp;
    private int maxSize;
    private int numItems;

    public FileLinePriorityQueue(int initialSize, Comparator<FileLine> cmp) {
		this.cmp = cmp;
		maxSize = initialSize;
		queue = new FileLine[maxSize];
		numItems = 0;
		// TODO
    }

    public FileLine removeMin() throws PriorityQueueEmptyException {
		// TODO
    	if (numItems <= 0) throw new PriorityQueueEmptyException();
    	FileLine temp = queue[0];
    	queue[0] = null;
    	numItems--;
		return temp;
    }

    public void insert(FileLine fl) throws PriorityQueueFullException {
		// TODO
    	if (numItems == maxSize) throw new PriorityQueueFullException();
		int i = 0;
		while (queue[i] != null) i++;
		queue[i] = fl;
		for (int j = i+1; j < numItems; j++) {
			if (cmp.compare(queue[i], queue[j]) < 0) {
				FileLine temp = queue[i];
				queue[i] = queue[j];
				queue[j] = temp;
				i++;
				// j < maxSize with break at queue[j] == null
			}
			else break;
			}
    }

    public boolean isEmpty() {
		// TODO
		return (numItems == 0);
    }
}