import java.util.NoSuchElementException;

/**
 * Your implementation of a non-circular DoublyLinkedList with a tail pointer.
 *
 * @AshleyCain YOUR NAME HERE
 * @version 1.0
 * @acain36 YOUR USER ID HERE (i.e. gburdell3)
 * @903576477 YOUR GT ID HERE (i.e. 900000000)
 *
 * Collaborators: LIST ALL COLLABORATORS YOU WORKED WITH HERE
 *
 * Resources: LIST ALL NON-COURSE RESOURCES YOU CONSULTED HERE
 */
public class DoublyLinkedList<T> {

    // Do not add new instance variables or modify existing ones.
    private DoublyLinkedListNode<T> head;
    private DoublyLinkedListNode<T> tail;
    private int size;

    // Do not add a constructor.

    /**
     * Adds the element to the specified index. Don't forget to consider whether
     * traversing the list from the head or tail is more efficient!
     *
     * Must be O(1) for indices 0 and size and O(n) for all other cases.
     *
     * @param index the index at which to add the new element
     * @param data  the data to add at the specified index
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index > size
     * @throws java.lang.IllegalArgumentException  if data is null
     */
    public void addAtIndex(int index, T data) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException();
        } else if (data == null) {
            throw new NoSuchElementException();
        }
        if (index == 0) {
            addToFront(data);
        } else if (index == size) {
            addToBack(data);
        } else {
            DoublyLinkedListNode<T> newNode = new DoublyLinkedListNode<>(data);
            DoublyLinkedListNode<T> current;
            if (index < size / 2) {
                current = head;
                for (int i = 0; i < index; i++) {
                    if (i == index - 1) {
                        current.getNext().setPrevious(newNode);
                        newNode.setNext(current.getNext());
                        current.setNext(newNode);
                        newNode.setPrevious(current);
                        size++;
                    }
                    current = current.getNext();
                }
            } else {
                current = tail;
                for (int i = size; i > index; i--) {
                    if (i == index + 1) {
                        current.getPrevious().setNext(newNode);
                        newNode.setPrevious(current.getPrevious());
                        current.setPrevious(newNode);
                        newNode.setNext(current);
                        size++;
                    }
                    current = current.getPrevious();
                }
            }

        }
    }

    /**
     * Adds the element to the front of the list.
     *
     * Must be O(1).
     *
     * @param data the data to add to the front of the list
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void addToFront(T data) {
        DoublyLinkedListNode<T> newNode = new DoublyLinkedListNode<>(data);
        if (data == null) {
            throw new IllegalArgumentException();
        } else if (head == null) {
            head = newNode;
            tail = newNode;
        } else {
            newNode.setNext(head);
            head.setPrevious(newNode);
            head = newNode;
        }
        size++;
    }

    /**
     * Adds the element to the back of the list.
     *
     * Must be O(1).
     *
     * @param data the data to add to the back of the list
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void addToBack(T data) {
        DoublyLinkedListNode<T> newNode = new DoublyLinkedListNode<>(data);
        if (data == null) {
            throw new IllegalArgumentException();
        } else if (head == null) {
            head = newNode;
            tail = newNode;
        } else {
            newNode.setPrevious(tail);
            tail.setNext(newNode);
            tail = newNode;
        }
        size++;
    }

    /**
     * Removes and returns the element at the specified index. Don't forget to
     * consider whether traversing the list from the head or tail is more
     * efficient!
     *
     * Must be O(1) for indices 0 and size - 1 and O(n) for all other cases.
     *
     * @param index the index of the element to remove
     * @return the data formerly located at the specified index
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index >= size
     */
    public T removeAtIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        } else if (size == 0) {
            throw new NoSuchElementException();
        }
        DoublyLinkedListNode<T> current = head;
        if (index == 0) {
            removeFromFront();
        } else if (index == size - 1) {
            removeFromBack();
        } else {
            if (index < size / 2) {
                for (int i = 0; i < index; i++) {
                    current = current.getNext();
                }
                current.getPrevious().setNext(current.getNext());
                current.getNext().setPrevious(current.getPrevious());
            } else {
                current = tail;
                for (int i = size - 1; i > index; i--) {
                    current = current.getPrevious();
                }
                current.getNext().setPrevious(current.getPrevious());
                current.getPrevious().setNext(current.getNext());
            }
        }
        size--;
        return current.getData();
    }

    /**
     * Removes and returns the first element of the list.
     *
     * Must be O(1).
     *
     * @return the data formerly located at the front of the list
     * @throws java.util.NoSuchElementException if the list is empty
     */
    public T removeFromFront() {
        DoublyLinkedListNode<T> newNode = head;
        if (size == 0) {
            throw new NoSuchElementException();
        } else if (size == 1) {
            tail = null;
            head = null;
        } else {
            head.getNext().setPrevious(null);
            head = head.getNext();
        }
        size--;
        return newNode.getData();
    }
    /**
     * Removes and returns the last element of the list.
     *
     * Must be O(1).
     *
     * @return the data formerly located at the back of the list
     * @throws java.util.NoSuchElementException if the list is empty
     */
    public T removeFromBack() {
        DoublyLinkedListNode<T> newNode = tail;
        if (size == 0) {
            throw new NoSuchElementException();
        } else if (size == 1) {
            tail = null;
            head = null;
        } else {
            tail.getPrevious().setNext(null);
            tail = tail.getPrevious();
        }
        size--;
        return newNode.getData();
    }

    /**
     * Returns the element at the specified index. Don't forget to consider
     * whether traversing the list from the head or tail is more efficient!
     *
     * Must be O(1) for indices 0 and size - 1 and O(n) for all other cases.
     *
     * @param index the index of the element to get
     * @return the data stored at the index in the list
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index >= size
     */
    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
        DoublyLinkedListNode<T> current = head;
        if (index == 0) {
            return head.getData();
        } else if (index == size - 1) {
            return tail.getData();
        } else {
            if (index < size / 2) {
                for (int i = 0; i < index; i++) {
                    current = current.getNext();
                }
            } else {
                current = tail;
                for (int i = size - 1; i > index; i--) {
                    current = current.getPrevious();
                }
            }
        }
        return current.getData();
    }

    /**
     * Returns whether or not the list is empty.
     *
     * Must be O(1).
     *
     * @return true if empty, false otherwise
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Clears the list.
     *
     * Clears all data and resets the size.
     *
     * Must be O(1).
     */
    public void clear() {
        head = null;
        tail = null;
        size = 0;
    }

    /**
     * Removes and returns the last copy of the given data from the list.
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the list.
     *
     * Must be O(1) if data is in the tail and O(n) for all other cases.
     *
     * @param data the data to be removed from the list
     * @return the data that was removed
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if data is not found
     */
    public T removeLastOccurrence(T data) {
        DoublyLinkedListNode<T> current = tail;
        if (data == null) {
            throw new IllegalArgumentException();
        } else if (tail.getData() == data) {
            return tail.getData();
        } else {
            for (int i = size - 1; i >= 0; i--) {
                if (current.getData() == data) {
                    removeAtIndex(i);
                    return current.getData();
                } else if (i == 0 && current.getData() != data) {
                    throw new NoSuchElementException();
                }
                current = current.getPrevious();
            }
        }
        size--;
        return current.getData();
    }

    /**
     * Returns an array representation of the linked list. If the list is
     * size 0, return an empty array.
     *
     * Must be O(n) for all cases.
     *
     * @return an array of length size holding all of the objects in the
     * list in the same order
     */
    public Object[] toArray() {
        DoublyLinkedListNode<T> current = head;
        Object[] finalArray = new Object[size];
        for (int i = 0; i < size; i++) {
            finalArray[i] = current.getData();
            current = current.getNext();
        }
        return finalArray;
    }

    /**
     * Returns the head node of the list.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the node at the head of the list
     */
    public DoublyLinkedListNode<T> getHead() {
        // DO NOT MODIFY!
        return head;
    }

    /**
     * Returns the tail node of the list.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the node at the tail of the list
     */
    public DoublyLinkedListNode<T> getTail() {
        // DO NOT MODIFY!
        return tail;
    }

    /**
     * Returns the size of the list.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the size of the list
     */
    public int size() {
        // DO NOT MODIFY!
        return size;
    }
}
