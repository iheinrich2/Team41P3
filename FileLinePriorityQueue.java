
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
 * An implementation of the MinPriorityQueueADT interface. This implementation
 * stores FileLine objects. See MinPriorityQueueADT.java for a description of
 * each method.
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
		queue = new FileLine[maxSize + 1];
		numItems = 0;
		// TODO
	}

	public FileLine removeMin() throws PriorityQueueEmptyException {
		// TODO
		if (numItems <= 0)
			throw new PriorityQueueEmptyException();

		FileLine returnLine = queue[1];
		swap(1, numItems);
		queue[numItems--] = null;

		int curr = 1;
		while ((curr = reheapify(curr)) > 0);

		return returnLine;
	}

	public void insert(FileLine fl) throws PriorityQueueFullException {
		// TODO
		if (numItems == maxSize)
			throw new PriorityQueueFullException();

		queue[++numItems] = fl;

		// heapify till the top
		int curr = numItems;
		while (reheapify(curr /= 2) > 0);
	}

	public boolean isEmpty() {
		// TODO
		return (numItems == 0);
	}

	/**
	 * Compare the parent at index with its children to adjust the three nodes
	 * so that parent <= left <= right
	 * 
	 * @param parent
	 *            the index of the parent
	 * @return next index to be altered, 0 if done
	 */
	private int reheapify(int parent) {
		// cannot reheapify 0
		if (parent == 0)
			return 0;

		int left = parent * 2;
		int right = parent * 2 + 1;
		int next = 0;

		// if right exists and left > right, swap
		if (right <= numItems && cmp.compare(queue[left], queue[right]) > 0) {
			swap(left, right);
			next = right;
		}

		// if left exists and parent > left, swap
		if (left <= numItems && cmp.compare(queue[parent], queue[left]) > 0) {
			swap(parent, left);
			next = left;
		}

		return next;
	}
	
	/**
     * Swaps two elements of the line array
     * @param i the first index to be swapped
     * @param j the second index to be swapped
     */
    private void swap(int i, int j) {
    	FileLine tmp = queue[i];
    	queue[i] = queue[j];
    	queue[j] = tmp;
    }
}
