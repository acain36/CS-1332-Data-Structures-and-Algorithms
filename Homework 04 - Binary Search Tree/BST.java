import java.util.*;

/**
 * Your implementation of a BST.
 *
 * @author Ashley Cain
 * @version 1.0
 * @userid acain36
 * @GTID 903576477
 *
 * Collaborators: LIST ALL COLLABORATORS YOU WORKED WITH HERE
 *
 * Resources: https://www.geeksforgeeks.org/queue-poll-method-in-java/ used for the levelorder method as reference
 */
public class BST<T extends Comparable<? super T>> {

    /*
     * Do not add new instance variables or modify existing ones.
     */
    private BSTNode<T> root;
    private int size;

    /**
     * Constructs a new BST.
     *
     * This constructor should initialize an empty BST.
     *
     * Since instance variables are initialized to their default values, there
     * is no need to do anything for this constructor.
     */
    public BST() {
        // DO NOT IMPLEMENT THIS CONSTRUCTOR!
    }

    /**
     * Constructs a new BST.
     *
     * This constructor should initialize the BST with the data in the
     * Collection. The data should be added in the same order it is in the
     * Collection.
     *
     * Hint: Not all Collections are indexable like Lists, so a regular for loop
     * will not work here. However, all Collections are Iterable, so what type
     * of loop would work?
     *
     * @param data the data to add
     * @throws java.lang.IllegalArgumentException if data or any element in data
     *                                            is null
     */
    public BST(Collection<T> data) {
        if (data == null || data.contains(null)) {
            throw new IllegalArgumentException("Data can't be null!");
        }
        for (T value: data) {
            this.add(value);
        }
    }

    /**
     * Adds the data to the tree.
     *
     * This must be done recursively.
     *
     * The data becomes a leaf in the tree.
     *
     * Traverse the tree to find the appropriate location. If the data is
     * already in the tree, then nothing should be done (the duplicate
     * shouldn't get added, and size should not be incremented).
     *
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to add
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void add(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data can't be null!");
        }
        BSTNode<T> newNode = new BSTNode<T>(data);
        if (this.root == null) {
            this.root = newNode;
            this.size++;
        } else {
            addHelper(data, root);
        }
    }

    /**
     * Helps the add method to traverse through the BST to find location to add to
     *
     * @param data is the data meant to be added to the BST
     * @param currentNode is the current node
     */
    private void addHelper(T data, BSTNode<T> currentNode) {
        BSTNode<T> newNode = new BSTNode<T>(data);
        if (data.compareTo(currentNode.getData()) < 0) {
            if (currentNode.getLeft() == null) {
                currentNode.setLeft(newNode);
                this.size++;
            } else {
                addHelper(data, currentNode.getLeft());
            }
        } else if (data.compareTo(currentNode.getData()) > 0) {
            if (currentNode.getRight() == null) {
                currentNode.setRight(newNode);
                this.size++;
            } else {
                addHelper(data, currentNode.getRight());
            }
        }
    }

    /**
     * Removes and returns the data from the tree matching the given parameter.
     *
     * This must be done recursively.
     *
     * There are 3 cases to consider:
     * 1: The node containing the data is a leaf (no children). In this case,
     * simply remove it.
     * 2: The node containing the data has one child. In this case, simply
     * replace it with its child.
     * 3: The node containing the data has 2 children. Use the successor to
     * replace the data. You MUST use recursion to find and remove the
     * successor (you will likely need an additional helper method to
     * handle this case efficiently).
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the tree.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to remove
     * @return the data that was removed
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if the data is not in the tree
     */
    public T remove(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data can't be null!");
        }
        BSTNode<T> dummy = new BSTNode<>(null);
        this.root = removeHelper(root, data, dummy);
        return dummy.getData();
    }

    /**
     * Helper method for the Remove Method
     * Helps to find the desired node with the data and remove it depending on number of children
     *
     * implements a second helper for those nodes that have 2 children
     *
     * @param curr is the current node
     * @param data is the desired data to be found
     * @param dummy is the node designed to hold the desired node when found
     *
     * @return the desired node when found
     */
    private BSTNode<T> removeHelper(BSTNode<T> curr, T data, BSTNode<T> dummy) {
        if (curr == null) {
            throw new NoSuchElementException("Data is not in the Tree!");
        } else if (data.compareTo(curr.getData()) < 0) {
            curr.setLeft(removeHelper(curr.getLeft(), data, dummy));
        } else if (data.compareTo(curr.getData()) > 0) {
            curr.setRight(removeHelper(curr.getRight(), data, dummy));
        } else if (data.compareTo(curr.getData()) == 0) {
            dummy.setData(curr.getData());
            this.size--;
            if (curr.getLeft() == null && curr.getRight() == null) {
                return null;
            } else if (curr.getLeft() != null && curr.getRight() == null) {
                return curr.getLeft();
            } else if (curr.getRight() != null && curr.getLeft() == null) {
                return curr.getRight();
            } else {
                BSTNode<T> dummy2Node = new BSTNode<>(null);
                curr.setRight(removeSuccessorHelper(curr.getRight(), dummy2Node));
                curr.setData(dummy2Node.getData());
            }
        }
        return curr;
    }

    /**
     * Successor Helper method for the remove helper
     * Helps to find and return the successor node in situations where desired node has 2 children
     *
     * @param curr is the current node
     * @param dummy is the node designed to hold the desired node when found
     *
     * @return the successor node to the desired node back to the original helper method
     */
    private BSTNode<T> removeSuccessorHelper(BSTNode<T> curr, BSTNode<T> dummy) {
        if (curr.getLeft() == null) {
            dummy.setData(curr.getData());
            return curr.getRight();
        } else {
            curr.setLeft(removeSuccessorHelper(curr.getLeft(), dummy));
        }
        return curr;
    }

    /**
     * Returns the data from the tree matching the given parameter.
     *
     * This must be done recursively.
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the tree.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to search for
     * @return the data in the tree equal to the parameter
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if the data is not in the tree
     */
    public T get(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data can't be null!");
        }
        return getHelper(this.root, data);
    }

    /**
     * Helper method for get method that iterates recursively to find data and returns
     *
     * @param curr is the current node
     * @param data is the desired data
     *
     * @return data from desired node once found
     */
    private T getHelper(BSTNode<T> curr, T data) {
        if (curr.getData() == null) {
            throw new NoSuchElementException("Data is not in the Tree!");
        }
        if (data.compareTo(curr.getData()) < 0) {
            return getHelper(curr.getLeft(), data);
        } else if (data.compareTo(curr.getData()) > 0) {
            return getHelper(curr.getRight(), data);
        } else {
            return curr.getData();
        }
    }

    /**
     * Returns whether or not data matching the given parameter is contained
     * within the tree.
     *
     * This must be done recursively.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to search for
     * @return true if the parameter is contained within the tree, false
     * otherwise
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public boolean contains(T data) {
        if (data == null) {
            throw new NoSuchElementException("Data can't be null!");
        }
        return containsHelper(this.root, data);
    }

    /**
     * Helper method for contains to determine whether there is a node with desired data within tree
     *
     * @param curr is the current Node
     * @param data is the desired data
     *
     * @return true if the data is contained and false otherwise
     */
    private boolean containsHelper(BSTNode<T> curr, T data) {
        if (curr.getData() == null) {
            return false;
        }
        if (data.compareTo(curr.getData()) < 0) {
            return containsHelper(curr.getLeft(), data);
        } else if (data.compareTo(curr.getData()) > 0) {
            return containsHelper(curr.getRight(), data);
        } else if (data.compareTo(curr.getData()) == 0) {
            return true;
        }
        return false;
    }

    /**
     * Generate a pre-order traversal of the tree.
     *
     * This must be done recursively.
     *
     * Must be O(n).
     *
     * @return the preorder traversal of the tree
     */
    public List<T> preorder() {
        List<T> preorderList = new ArrayList<>();
        preorderHelper(preorderList, this.root);
        return preorderList;
    }

    /**
     * This is a helper method for preorder method that adds data to list in CLR order
     *
     * @param preorderList is the list to add the data to
     * @param curr is the current node
     */
    private void preorderHelper(List<T> preorderList, BSTNode<T> curr) {
        if (curr != null) {
            preorderList.add(curr.getData());
            preorderHelper(preorderList, curr.getLeft());
            preorderHelper(preorderList, curr.getRight());
        }
    }
    /**
     * Generate an in-order traversal of the tree.
     *
     * This must be done recursively.
     *
     * Must be O(n).
     *
     * @return the inorder traversal of the tree
     */
    public List<T> inorder() {
        List<T> inorderList = new ArrayList<>();
        inorderHelper(inorderList, this.root);
        return inorderList;
    }

    /**
     * This is a helper method for preorder method that adds data to list in LCR order
     *
     * @param inorderList is the list to add the data to
     * @param curr is the current node
     */
    private void inorderHelper(List<T> inorderList, BSTNode<T> curr) {
        if (curr != null) {
            inorderHelper(inorderList, curr.getLeft());
            inorderList.add(curr.getData());
            inorderHelper(inorderList, curr.getRight());
        }
    }

    /**
     * Generate a post-order traversal of the tree.
     *
     * This must be done recursively.
     *
     * Must be O(n).
     *
     * @return the postorder traversal of the tree
     */
    public List<T> postorder() {
        List<T> postorderList = new ArrayList<>();
        postorderHelper(postorderList, this.root);
        return postorderList;
    }

    /**
     * This is a helper method for preorder method that adds data to list in LRC order
     *
     * @param postorderList is the list to add the data to
     * @param curr is the current node
     */
    private void postorderHelper(List<T> postorderList, BSTNode<T> curr) {
        if (curr != null) {
            postorderHelper(postorderList, curr.getLeft());
            postorderHelper(postorderList, curr.getRight());
            postorderList.add(curr.getData());
        }
    }

    /**
     * Generate a level-order traversal of the tree.
     *
     * This does not need to be done recursively.
     *
     * Hint: You will need to use a queue of nodes. Think about what initial
     * node you should add to the queue and what loop / loop conditions you
     * should use.
     *
     * Must be O(n).
     *
     * @return the level order traversal of the tree
     */
    public List<T> levelorder() {
        Queue<BSTNode<T>> myQueue = new LinkedList<>();
        List<T> myList = new ArrayList<>();
        myQueue.add(this.root);
        while (!(myQueue.isEmpty())) {
            BSTNode<T> temp = myQueue.poll();
            myList.add(temp.getData());
            if (temp.getLeft() != null) {
                myQueue.add(temp.getLeft());
            }
            if (temp.getRight() != null) {
                myQueue.add(temp.getRight());
            }
        }
        return myList;
    }

    /**
     * Returns the height of the root of the tree.
     *
     * This must be done recursively.
     *
     * A node's height is defined as max(left.height, right.height) + 1. A
     * leaf node has a height of 0 and a null child has a height of -1.
     *
     * Must be O(n).
     *
     * @return the height of the root of the tree, -1 if the tree is empty
     */
    public int height() {
        return heightHelper(this.root);
    }

    /**
     * Helps to find the height of the root by comparing height of left node and right to find max height
     *
     * @param curr is the current node
     *
     * @return an integer representing the height
     */
    private int heightHelper(BSTNode<T> curr) {
        if (curr == null) {
            return -1;
        } else {
            return Math.max(heightHelper(curr.getLeft()), heightHelper(curr.getRight())) + 1;
        }
    }

    /**
     * Clears the tree.
     *
     * Clears all data and resets the size.
     *
     * Must be O(1).
     */
    public void clear() {
        this.root = null;
        this.size = 0;
    }

    /**
     * Finds and retrieves the k-largest elements from the BST in sorted order,
     * least to greatest.
     *
     * This must be done recursively.
     *
     * In most cases, this method will not need to traverse the entire tree to
     * function properly, so you should only traverse the branches of the tree
     * necessary to get the data and only do so once. Failure to do so will
     * result in an efficiency penalty.
     *
     * EXAMPLE: Given the BST below composed of Integers:
     *
     *                50
     *              /    \
     *            25      75
     *           /  \
     *          12   37
     *         /  \    \
     *        10  15    40
     *           /
     *          13
     *
     * kLargest(5) should return the list [25, 37, 40, 50, 75].
     * kLargest(3) should return the list [40, 50, 75].
     *
     * Should have a running time of O(log(n) + k) for a balanced tree and a
     * worst case of O(n + k).
     *
     * @param k the number of largest elements to return
     * @return sorted list consisting of the k largest elements
     * @throws java.lang.IllegalArgumentException if k > n, the number of data
     *                                            in the BST
     */
    public List<T> kLargest(int k) {
        if (k > this.size) {
            throw new IllegalArgumentException("There are not enough elements in tree!");
        } else {
            List<T> kList = new ArrayList<>();
            kLargestHelper(this.root, kList, k);
            return reverseHelper(kList);
        }
    }

    /**
     * This method helps the kLargest method to order data from largest ot smallest k# times
     *
     * @param curr is the current node
     * @param kList is the list of the largest data
     * @param k is the number of data to return
     */
    private void kLargestHelper(BSTNode<T> curr, List<T> kList, int k) {
        if (curr != null && kList.size() < k) {
            kLargestHelper(curr.getRight(), kList, k);
            if (kList.size() < k) {
                kList.add(curr.getData());
            }
            if (kList.size() < k) {
                kLargestHelper(curr.getLeft(), kList, k);
            }
        }
    }

    /**
     * Helps reverse the list to be from smallest to largest
     *
     * @param kList is the list to modify
     *
     * @return the list of the k largest data from smallest to largest
     */
    private List<T> reverseHelper(List<T> kList) {
        if (kList.size() <= 1) {
            return kList;
        }
        List<T> myList = new ArrayList<>();
        myList.add(kList.get(kList.size() - 1));
        myList.addAll(reverseHelper(kList.subList(0, kList.size() - 1)));
        return myList;
    }


    /**
     * Returns the root of the tree.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the root of the tree
     */
    public BSTNode<T> getRoot() {
        // DO NOT MODIFY THIS METHOD!
        return root;
    }

    /**
     * Returns the size of the tree.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the size of the tree
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD!
        return size;
    }
}
