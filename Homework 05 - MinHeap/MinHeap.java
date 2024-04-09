import java.util.ArrayList;
import java.util.NoSuchElementException;

/**
 * Your implementation of a MinHeap.
 *
 * @author Ashley Cain
 * @version 1.0
 * @userid acain36
 * @GTID 903576477
 *
 * Collaborators: LIST ALL COLLABORATORS YOU WORKED WITH HERE
 *
 * Resources: LIST ALL NON-COURSE RESOURCES YOU CONSULTED HERE
 */
public class MinHeap<T extends Comparable<? super T>> {

    /**
     * The initial capacity of the MinHeap when created with the default
     * constructor.
     *
     * DO NOT MODIFY THIS VARIABLE!
     */
    public static final int INITIAL_CAPACITY = 13;

    // Do not add new instance variables or modify existing ones.
    private T[] backingArray;
    private int size;

    /**
     * Constructs a new MinHeap.
     *
     * The backing array should have an initial capacity of INITIAL_CAPACITY.
     */
    public MinHeap() {
        this.backingArray = (T[]) new Comparable[this.INITIAL_CAPACITY];
        this.size = 0;
    }

    /**
     * Creates a properly ordered heap from a set of initial values.
     *
     * You must use the BuildHeap algorithm that was taught in lecture! Simply
     * adding the data one by one using the add method will not get any credit.
     * As a reminder, this is the algorithm that involves building the heap
     * from the bottom up by repeated use of downHeap operations.
     *
     * Before doing the algorithm, first copy over the data from the
     * ArrayList to the backingArray (leaving index 0 of the backingArray
     * empty). The data in the backingArray should be in the same order as it
     * appears in the passed in ArrayList before you start the BuildHeap
     * algorithm.
     *
     * The backingArray should have capacity 2n + 1 where n is the
     * number of data in the passed in ArrayList (not INITIAL_CAPACITY).
     * Index 0 should remain empty, indices 1 to n should contain the data in
     * proper order, and the rest of the indices should be empty.
     *
     * @param data a list of data to initialize the heap with
     * @throws java.lang.IllegalArgumentException if data or any element in data
     *                                            is null
     */
    public MinHeap(ArrayList<T> data) {
        if (data == null) {
            throw new IllegalArgumentException("Data can't be null!");
        }
        this.backingArray = (T[]) new Comparable[2 * data.size() + 1];
        this.size = data.size();
        for (int i = 0; i < this.size; i++) {
            if (data.get(i) == null) {
                throw new IllegalArgumentException("No data in passed in ArrayList can be null!");
            }
            this.backingArray[i + 1] = data.get(i);
        }
        for (int i = this.size / 2; i > 0; i--) {
            minHeapify(i);
        }
    }

    /**
     * Helps the minHeap method to order the data in the backingArray according to minHeap order rules
     * Used for minHeap and Remove Methods
     * @param index is the index of the node passed in
     */
    private void minHeapify(int index) {
        while (2 * index <= this.size) {
            int smallestIndex = 2 * index;
            if (this.backingArray[2 * index + 1] != null) {
                if ((backingArray[2 * index].compareTo(this.backingArray[2 * index + 1])) > 0) {
                    smallestIndex++;
                }
            }
            if ((backingArray[smallestIndex].compareTo(this.backingArray[index])) < 0) {
                swapData(smallestIndex, index);
                index = smallestIndex;
            } else {
                break;
            }
        }
    }

    /**
     * Swaps the data between two indexes
     * @param i is the first index
     * @param j is the second index
     */
    private void swapData(int i, int j) {
        T temp = this.backingArray[i];
        this.backingArray[i] = this.backingArray[j];
        this.backingArray[j] = temp;
    }


    /**
     * Adds an item to the heap. If the backing array is full (except for
     * index 0) and you're trying to add a new item, then double its capacity.
     * The order property of the heap must be maintained after adding.
     * 
     * @param data the data to add
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void add(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data can't be null!");
        }
        if (this.size + 1 == this.backingArray.length) {
            T[] tempArray = this.backingArray;
            this.backingArray = (T[]) new Comparable[2 * this.backingArray.length];
            for (int i = 1; i <= this.size; i++) {
                backingArray[i] = tempArray[i];
            }
        }
        this.backingArray[this.size + 1] = data;
        this.size++;
        int index = this.size;
        int parent = this.size / 2;
        while (parent >= 1) {
            if (this.backingArray[index].compareTo(this.backingArray[parent]) < 0) {
                swapData(index, parent);
                index = parent;
                parent = index / 2;
            } else {
                break;
            }
        }
    }

    /**
     * Removes and returns the min item of the heap. As usual for array-backed
     * structures, be sure to null out spots as you remove. Do not decrease the
     * capacity of the backing array.
     * The order property of the heap must be maintained after adding.
     *
     * @return the data that was removed
     * @throws java.util.NoSuchElementException if the heap is empty
     */
    public T remove() {
        if (isEmpty()) {
            throw new NoSuchElementException("There are no elements in the Heap!");
        }
        T temp = this.backingArray[1];
        this.backingArray[1] = this.backingArray[this.size];
        this.backingArray[this.size] = null;
        this.size--;
        minHeapify(1);
        return temp;
    }

    /**
     * Returns the minimum element in the heap.
     *
     * @return the minimum element
     * @throws java.util.NoSuchElementException if the heap is empty
     */
    public T getMin() {
        if (isEmpty()) {
            throw new NoSuchElementException("There are no elements in the Heap!");
        }
        return this.backingArray[1];
    }

    /**
     * Returns whether or not the heap is empty.
     *
     * @return true if empty, false otherwise
     */
    public boolean isEmpty() {
        return this.size == 0;
    }

    /**
     * Clears the heap.
     *
     * Resets the backing array to a new array of the initial capacity and
     * resets the size.
     */
    public void clear() {
        this.backingArray = (T[]) new Comparable[this.INITIAL_CAPACITY];
        this.size = 0;
    }

    /**
     * Returns the backing array of the heap.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the backing array of the list
     */
    public T[] getBackingArray() {
        // DO NOT MODIFY THIS METHOD!
        return backingArray;
    }

    /**
     * Returns the size of the heap.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the size of the list
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD!
        return size;
    }
}
