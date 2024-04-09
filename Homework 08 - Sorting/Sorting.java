import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.LinkedList;
import java.util.PriorityQueue;


/**
 * Your implementation of various sorting algorithms.
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
public class Sorting {

    /**
     * Implement insertion sort.
     *
     * It should be:
     * in-place
     * stable
     * adaptive
     *
     * Have a worst case running time of:
     * O(n^2)
     *
     * And a best case running time of:
     * O(n)
     *
     * @param <T>        data type to sort
     * @param arr        the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     * @throws java.lang.IllegalArgumentException if the array or comparator is
     *                                            null
     */
    public static <T> void insertionSort(T[] arr, Comparator<T> comparator) {
        if (arr == null) {
            throw new IllegalArgumentException("Array Can't be Null!");
        }
        if (comparator == null) {
            throw new IllegalArgumentException("Comparator Can't be Null!");
        }
        for (int i = 1; i < arr.length; i++) {
            int j = i;
            while (j != 0 && comparator.compare(arr[j], arr[j - 1]) < 0) {
                swap(j, j - 1, arr);
                j--;
            }
        }
    }

    /**
     * This Helper method swaps the data at two indices
     * @param a is the first index
     * @param b is the second index
     * @param arr is the array in which the indices lie
     * @param <T> is the data type
     */
    private static <T> void swap(int a, int b, T[] arr) {
        T temp = arr[a];
        arr[a] = arr[b];
        arr[b] = temp;
    }

    /**
     * Implement cocktail sort.
     *
     * It should be:
     * in-place
     * stable
     * adaptive
     *
     * Have a worst case running time of:
     * O(n^2)
     *
     * And a best case running time of:
     * O(n)
     *
     * NOTE: See pdf for last swapped optimization for cocktail sort. You
     * MUST implement cocktail sort with this optimization
     *
     * @param <T>        data type to sort
     * @param arr        the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     * @throws java.lang.IllegalArgumentException if the array or comparator is
     *                                            null
     */
    public static <T> void cocktailSort(T[] arr, Comparator<T> comparator) {
        if (arr == null) {
            throw new IllegalArgumentException("Array Can't be Null!");
        }
        if (comparator == null) {
            throw new IllegalArgumentException("Comparator Can't be Null!");
        }
        int finalSwap = 0;
        int start = 0;
        int end = arr.length - 1;
        boolean swapped = true;
        while (swapped) {
            swapped = false;
            for (int i = finalSwap; i < end; i++) {
                if (comparator.compare(arr[i + 1], arr[i]) < 0) {
                    swapped = true;
                    finalSwap = i;
                    swap(i, i + 1, arr);
                }
            }
            end = finalSwap;
            if (swapped) {
                swapped = false;
                for (int j = finalSwap; j > start; j--) {
                    if (comparator.compare(arr[j - 1], arr[j]) > 0) {
                        swapped = true;
                        finalSwap = j;
                        swap(j - 1, j, arr);
                    }
                }
                start = finalSwap;
            }
        }
    }

    /**
     * Implement merge sort.
     *
     * It should be:
     * out-of-place
     * stable
     * not adaptive
     *
     * Have a worst case running time of:
     * O(n log n)
     *
     * And a best case running time of:
     * O(n log n)
     *
     * You can create more arrays to run merge sort, but at the end, everything
     * should be merged back into the original T[] which was passed in.
     *
     * When splitting the array, if there is an odd number of elements, put the
     * extra data on the right side.
     *
     * Hint: If two data are equal when merging, think about which subarray
     * you should pull from first
     *
     * @param <T>        data type to sort
     * @param arr        the array to be sorted
     * @param comparator the Comparator used to compare the data in arr
     * @throws java.lang.IllegalArgumentException if the array or comparator is
     *                                            null
     */
    public static <T> void mergeSort(T[] arr, Comparator<T> comparator) {
        if (arr == null) {
            throw new IllegalArgumentException("Array Can't be Null!");
        }
        if (comparator == null) {
            throw new IllegalArgumentException("Comparator Can't be Null!");
        } else {
            if (arr.length > 1) {
                int split = arr.length / 2;
                T[] lArray = (T[]) new Object[split];
                T[] rArray = (T[]) new Object[arr.length - split];
                for (int i = 0; i < lArray.length; i++) {
                    lArray[i] = arr[i];
                }
                for (int j = 0; j < rArray.length; j++) {
                    rArray[j] = arr[j + lArray.length];
                }
                mergeSort(lArray, comparator);
                mergeSort(rArray, comparator);
                mergeHelper(lArray, rArray, arr, comparator);
            }
        }
    }

    /**
     * The helper method for merge sort method
     * @param lArray the left subarray
     * @param rArray the right subarray
     * @param arr the array of objects to sort
     * @param comparator the Comparator
     * @param <T> the object type
     */
    private static <T> void mergeHelper(T[] lArray, T[] rArray, T[] arr, Comparator<T> comparator) {
        int i = 0;
        int j = 0;
        while (i < lArray.length && j < rArray.length) {
            if (comparator.compare(lArray[i], rArray[j]) <= 0) {
                arr[i + j] = lArray[i];
                i++;
            } else {
                arr[i + j] = rArray[j];
                j++;
            }
        }
        while (i < lArray.length) {
            arr[i + j] = lArray[i];
            i++;
        }
        while (j < rArray.length) {
            arr[i + j] = rArray[j];
            j++;
        }
    }

    /**
     * Implement quick sort.
     *
     * Use the provided random object to select your pivots. For example if you
     * need a pivot between a (inclusive) and b (exclusive) where b > a, use
     * the following code:
     *
     * int pivotIndex = rand.nextInt(b - a) + a;
     *
     * If your recursion uses an inclusive b instead of an exclusive one,
     * the formula changes by adding 1 to the nextInt() call:
     *
     * int pivotIndex = rand.nextInt(b - a + 1) + a;
     *
     * It should be:
     * in-place
     * unstable
     * not adaptive
     *
     * Have a worst case running time of:
     * O(n^2)
     *
     * And a best case running time of:
     * O(n log n)
     *
     * Make sure you code the algorithm as you have been taught it in class.
     * There are several versions of this algorithm and you may not receive
     * credit if you do not use the one we have taught you!
     *
     * @param <T>        data type to sort
     * @param arr        the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     * @param rand       the Random object used to select pivots
     * @throws java.lang.IllegalArgumentException if the array or comparator or
     *                                            rand is null
     */
    public static <T> void quickSort(T[] arr, Comparator<T> comparator,
                                     Random rand) {
        if (arr == null) {
            throw new IllegalArgumentException("Array Can't be Null!");
        }
        if (comparator == null) {
            throw new IllegalArgumentException("Comparator Can't be Null!");
        }
        if (rand == null) {
            throw new IllegalArgumentException("Rand Can't be Null!");
        }
        qSHelper(arr, comparator, rand, 0, arr.length - 1);
    }

    /**
     * The Quick Sort Method Helper method
     * @param arr the array with objects to be sorted
     * @param comparator the Comparator
     * @param rand the random value to determine the random index
     * @param left the start index
     * @param right the end index
     * @param <T> the object type
     */
    private static <T> void qSHelper(T[] arr, Comparator<T> comparator, Random rand, int left, int right) {
        if (right - left > 1) {
            int pivot = qSAlgorithm(arr, comparator, (rand.nextInt(right - left) + left), left, right);
            qSHelper(arr, comparator, rand, left, pivot - 1);
            qSHelper(arr, comparator, rand, pivot + 1, right);
            if (comparator.compare(arr[0], arr[1]) > 0) {
                swap(0, 1, arr);
            }
        }
    }

    /**
     * The Quick Sort Recursive Algorithm Helper Method
     * @param arr the array to be sorted
     * @param comparator the Comparator
     * @param rand the random index of the pivot
     * @param left the start index
     * @param right the end index
     * @param <T> the data type
     * @return the pivot index
     */
    private static <T> int qSAlgorithm(T[] arr, Comparator<T> comparator, int rand, int left, int right) {
        T pVal = arr[rand];
        swap(left, rand, arr);
        int i = left + 1;
        int j = right;
        while (i < j) {
            while ((i < j) && (comparator.compare(arr[i], pVal) <= 0)) {
                i++;
            }
            while ((i < j) && (comparator.compare(arr[j], pVal) >= 0)) {
                j--;
            }
            if (i < j) {
                swap(i, j, arr);
                i++;
                j--;
            }
        }
        swap(left, j, arr);
        return j;
    }

    /**
     * Implement LSD (least significant digit) radix sort.
     *
     * Make sure you code the algorithm as you have been taught it in class.
     * There are several versions of this algorithm and you may not get full
     * credit if you do not implement the one we have taught you!
     *
     * Remember you CANNOT convert the ints to strings at any point in your
     * code! Doing so may result in a 0 for the implementation.
     *
     * It should be:
     * out-of-place
     * stable
     * not adaptive
     *
     * Have a worst case running time of:
     * O(kn)
     *
     * And a best case running time of:
     * O(kn)
     *
     * You are allowed to make an initial O(n) passthrough of the array to
     * determine the number of iterations you need. The number of iterations
     * can be determined using the number with the largest magnitude.
     *
     * At no point should you find yourself needing a way to exponentiate a
     * number; any such method would be non-O(1). Think about how how you can
     * get each power of BASE naturally and efficiently as the algorithm
     * progresses through each digit.
     *
     * Refer to the PDF for more information on LSD Radix Sort.
     *
     * You may use ArrayList or LinkedList if you wish, but it may only be
     * used inside radix sort and any radix sort helpers. Do NOT use these
     * classes with other sorts. However, be sure the List implementation you
     * choose allows for stability while being as efficient as possible.
     *
     * Do NOT use anything from the Math class except Math.abs().
     *
     * @param arr the array to be sorted
     * @throws java.lang.IllegalArgumentException if the array is null
     */
    public static void lsdRadixSort(int[] arr) {
        if (arr == null) {
            throw new IllegalArgumentException("Array Can't be Null!");
        } else {
            if (arr.length == 2) {
                if (arr[0] > arr[1]) {
                    int tempN = arr[0];
                    arr[0] = arr[1];
                    arr[1] = tempN;
                }
            } else {
                LinkedList<Integer>[] buckets = new LinkedList[19];
                for (int i = 0; i < 19; i++) {
                    buckets[i] = new LinkedList<>();
                }
                int largest = 0;
                for (int j = 0; j < arr.length - 1; j++) {
                    int indValue = Math.abs(arr[j + 1]);
                    if (indValue > largest) {
                        largest = arr[j + 1];
                    }
                }
                int digits = countDigits(largest);
                for (int k = 1; k <= digits; k++) {
                    for (int l = 0; l < arr.length; l++) {
                        int digit = findMod(arr[l], k);
                        if (buckets[digit % 10 + 9] == null) {
                            buckets[digit % 10 + 9] = new LinkedList<>();
                        }
                        buckets[digit % 10 + 9].add(arr[l]);
                    }
                    int index = 0;
                    for (LinkedList<Integer> bucket: buckets) {
                        if (bucket != null) {
                            for (int b: bucket) {
                                arr[index++] = b;
                            }
                            bucket.clear();
                        }
                    }
                }
            }
        }
    }

    /**
     * Helper method for LSD to count the digits in a number
     * @param num the number to count the digits of
     * @return the number of digits
     */
    private static int countDigits(int num) {
        int modNum = num;
        int numDigits = 0;
        while (modNum != 0) {
            modNum = modNum / 10;
            numDigits++;
        }
        return numDigits;
    }

    /**
     * Helper method for LSD sort to find the correct mod
     * @param num the value of the number
     * @param digit the value of the digit
     * @return the correct mod
     */
    private static int findMod(int num, int digit) {
        int numDigits = countDigits(num);
        if (numDigits >= digit) {
            int modNum = num % 10;
            if (numDigits == digit) {
                modNum = num / 10;
            }
            return modNum;
        } else {
            return 0;
        }
    }

    /**
     * Implement heap sort.
     *
     * It should be:
     * out-of-place
     * unstable
     * not adaptive
     *
     * Have a worst case running time of:
     * O(n log n)
     *
     * And a best case running time of:
     * O(n log n)
     *
     * Use java.util.PriorityQueue as the heap. Note that in this
     * PriorityQueue implementation, elements are removed from smallest
     * element to largest element.
     *
     * Initialize the PriorityQueue using its build heap constructor (look at
     * the different constructors of java.util.PriorityQueue).
     *
     * Return an int array with a capacity equal to the size of the list. The
     * returned array should have the elements in the list in sorted order.
     *
     * @param data the data to sort
     * @return the array with length equal to the size of the input list that
     * holds the elements from the list is sorted order
     * @throws java.lang.IllegalArgumentException if the data is null
     */
    public static int[] heapSort(List<Integer> data) {
        if (data == null) {
            throw new IllegalArgumentException("Data Can't be Null Dude!!");
        }
        PriorityQueue<Integer> backingArray = new PriorityQueue<>(data);
        int[] sorted = new int[data.size()];
        int index = 0;
        while (!(backingArray.isEmpty())) {
            sorted[index] = backingArray.poll();
            index++;
        }
        return sorted;
    }
}
