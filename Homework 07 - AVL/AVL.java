import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Your implementation of an AVL Tree.
 *
 * @author Ashley Cain
 * @userid acain36
 * @GTID 9035764777
 * @version 1.0
 */
public class AVL<T extends Comparable<? super T>> {
    // DO NOT ADD OR MODIFY INSTANCE VARIABLES.
    private AVLNode<T> root;
    private int size;

    /**
     * A no-argument constructor that should initialize an empty AVL.
     *
     * Since instance variables are initialized to their default values, there
     * is no need to do anything for this constructor.
     */
    public AVL() {
        // DO NOT IMPLEMENT THIS CONSTRUCTOR!
    }

    /**
     * Initializes the AVL tree with the data in the Collection. The data
     * should be added in the same order it appears in the Collection.
     *
     * @throws IllegalArgumentException if data or any element in data is null
     * @param data the data to add to the tree
     */
    public AVL(Collection<T> data) {
        if (data == null || data.contains(null)) {
            throw new IllegalArgumentException("Data can't be null!");
        }
        for (T value: data) {
            this.add(value);
        }
    }

    /**
     * Adds the data to the AVL. Start by adding it as a leaf like in a regular
     * BST and then rotate the tree as needed.
     *
     * If the data is already in the tree, then nothing should be done (the
     * duplicate shouldn't get added, and size should not be incremented).
     *
     * Remember to recalculate heights and balance factors going up the tree,
     * rebalancing if necessary.
     *
     * @throws java.lang.IllegalArgumentException if the data is null
     * @param data the data to be added
     */
    public void add(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data can't be null!");
        } else if (root == null) {
            root = new AVLNode<>(data);
            size++;
        } else {
            root = addHelper(root, data);
        }
    }

    /**
     * This method helps the add method
     *
     * @param curr is the current node
     * @param data is the data to be added to the tree
     *
     * @return the root of the new tree with the data added and balanced
     */
    private AVLNode<T> addHelper(AVLNode<T> curr, T data) {
        if (curr != null) {
            if (data.compareTo(curr.getData()) < 0) {
                curr.setLeft(addHelper(curr.getLeft(), data));
            } else if (data.compareTo(curr.getData()) > 0) {
                curr.setRight(addHelper(curr.getRight(), data));
            }
        } else {
            size++;
            return new AVLNode<T>(data);
        }
        updateHeightBalanceFactor(curr);
        return balanceNode(curr);
    }

    /**
     * Updates the height and balance factor for the node
     *
     * @param curr is the current node
     */
    private void updateHeightBalanceFactor(AVLNode<T> curr) {
        curr.setHeight(1 + Math.max(easyHeight(curr.getLeft()), easyHeight(curr.getRight())));
        curr.setBalanceFactor(easyHeight(curr.getLeft()) - easyHeight(curr.getRight()));
    }

    /**
     * Helps the method that updates a node's height and BF
     *
     * @param curr is the current node
     *
     * @return an integer value for the node's height
     */
    private int easyHeight(AVLNode<T> curr) {
        if (curr == null) {
            return -1;
        } else {
            return curr.getHeight();
        }
    }

    /**
     * Balances the tree according to the current node's balance factor
     *
     * @param curr is the current node
     *
     * @return the root of the newly balanced tree
     */
    private AVLNode<T> balanceNode(AVLNode<T> curr) {
        if (curr.getBalanceFactor() > 1) {
            if (curr.getLeft().getBalanceFactor() < 0) {
                curr.setLeft(rotateLeft(curr.getLeft()));
            }
            curr = rotateRight(curr);
        } else if (curr.getBalanceFactor() < -1) {
            if (curr.getRight().getBalanceFactor() > 0) {
                curr.setRight(rotateRight(curr.getRight()));
            }
            curr = rotateLeft(curr);
        }
        return curr;
    }

    /**
     * Performs a left rotation on the node
     *
     * @param curr is the current node
     *
     * @return the root of the newly balanced tree
     */
    private AVLNode<T> rotateLeft(AVLNode<T> curr) {
        AVLNode<T> temp = curr.getRight();
        curr.setRight(temp.getLeft());
        temp.setLeft(curr);
        updateHeightBalanceFactor(curr);
        updateHeightBalanceFactor(temp);
        return temp;
    }

    /**
     * Performs a right rotation on the node
     *
     * @param curr is the current node
     *
     * @return the root of the newly balanced tree
     */
    private AVLNode<T> rotateRight(AVLNode<T> curr) {
        AVLNode<T> temp = curr.getLeft();
        curr.setLeft(temp.getRight());
        temp.setRight(curr);
        updateHeightBalanceFactor(curr);
        updateHeightBalanceFactor(temp);
        return temp;
    }

    /**
     * Removes the data from the tree. There are 3 cases to consider:
     *
     * 1: the data is a leaf. In this case, simply remove it.
     * 2: the data has one child. In this case, simply replace it with its
     * child.
     * 3: the data has 2 children. Use the successor to replace the data,
     * not the predecessor. As a reminder, rotations can occur after removing
     * the successor node.
     *
     * Remember to recalculate heights going up the tree, rebalancing if
     * necessary.
     *
     * @throws IllegalArgumentException if the data is null
     * @throws java.util.NoSuchElementException if the data is not found
     * @param data the data to remove from the tree.
     * @return the data removed from the tree. Do not return the same data
     * that was passed in.  Return the data that was stored in the tree.
     */
    public T remove(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data can't be null!");
        }
        if (size == 1 && root.getData().compareTo(data) == 0) {
            T temp = root.getData();
            clear();
            return temp;
        }
        AVLNode<T> dummy = new AVLNode<>(null);
        root = removeHelper(root, data, dummy);
        if (root != null) {
            updateHeightBalanceFactor(root);
        }
        return dummy.getData();
    }

    /**
     * Helps the remove method to remove data from a node and balance the tree
     *
     * @param curr is the current node
     * @param data is the data to be removed
     * @param dummy is node in which to store the data that was removed
     *
     * @return the node holding the removed data
     */
    private AVLNode<T> removeHelper(AVLNode<T> curr, T data, AVLNode<T> dummy) {
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
                AVLNode<T> dummy2Node = new AVLNode<>(null);
                if (curr.getRight() != null) {
                    curr.setRight(removeSuccessorHelper(curr.getRight(), dummy2Node));
                }
                curr.setData(dummy2Node.getData());
            }
        }
        updateHeightBalanceFactor(curr);
        return balanceNode(curr);
    }

    /**
     * Helps the remove helper to ensure replacement by the successor
     *
     * @param curr is the current node
     * @param dummy is the node in which the removed data is stored
     *
     * @return the node containing the removed data
     */
    private AVLNode<T> removeSuccessorHelper(AVLNode<T> curr, AVLNode<T> dummy) {
        if (curr.getLeft() == null) {
            dummy.setData(curr.getData());
            return curr.getRight();
        } else {
            curr.setLeft(removeSuccessorHelper(curr.getLeft(), dummy));
        }
        updateHeightBalanceFactor(curr);
        return balanceNode(curr);
    }

    /**
     * Returns the data in the tree matching the parameter passed in (think
     * carefully: should you use value equality or reference equality?).
     *
     * @throws IllegalArgumentException if the data is null
     * @throws java.util.NoSuchElementException if the data is not found
     * @param data the data to search for in the tree.
     * @return the data in the tree equal to the parameter. Do not return the
     * same data that was passed in.  Return the data that was stored in the
     * tree.
     */
    public T get(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data can't be null!");
        } else if (size == 0) {
            throw new NoSuchElementException("Data is not in the Tree!");
        }
        return getHelper(root, data);
    }

    /**
     * A helper to the get method to find the desired node
     *
     * @param curr is the current node
     * @param data the data to find
     *
     * @return the data that is found in the tree
     */
    private T getHelper(AVLNode<T> curr, T data) {
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
     * Returns whether or not data equivalent to the given parameter is
     * contained within the tree. The same type of equality should be used as
     * in the get method.
     *
     * @throws IllegalArgumentException if the data is null
     * @param data the data to search for in the tree.
     * @return whether or not the parameter is contained within the tree.
     */
    public boolean contains(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data can't be null!");
        }
        return containsHelper(root, data);
    }

    /**
     * Helper method to the contains method to determine whether data exists in a tree
     *
     * @param curr is the current node
     * @param data is the data to find
     *
     * @return a boolean value determined by whether the data is within the tree
     */
    private boolean containsHelper(AVLNode<T> curr, T data) {
        if (curr == null) {
            return false;
        }
        if (data.compareTo(curr.getData()) < 0) {
            return containsHelper(curr.getLeft(), data);
        } else if (data.compareTo(curr.getData()) > 0) {
            return containsHelper(curr.getRight(), data);
        } else {
            return data.equals(curr.getData());
        }
    }

    /**
     * Returns the data on branches of the tree with the maximum depth. If you
     * encounter multiple branches of maximum depth while traversing, then you
     * should list the remaining data from the left branch first, then the
     * remaining data in the right branch. This is essentially a preorder
     * traversal of the tree, but only of the branches of maximum depth.
     *
     * Your list should not duplicate data, and the data of a branch should be
     * listed in order going from the root to the leaf of that branch.
     *
     * Should run in worst case O(n), but you should not explore branches that
     * do not have maximum depth. You should also not need to traverse branches
     * more than once.
     *
     * Hint: How can you take advantage of the balancing information stored in
     * AVL nodes to discern deep branches?
     *
     * Example Tree:
     *                           10
     *                       /        \
     *                      5          15
     *                    /   \      /    \
     *                   2     7    13    20
     *                  / \   / \     \  / \
     *                 1   4 6   8   14 17  25
     *                /           \          \
     *               0             9         30
     *
     * Returns: [10, 5, 2, 1, 0, 7, 8, 9, 15, 20, 25, 30]
     *
     * @return the list of data in branches of maximum depth in preorder
     * traversal order
     */
    public List<T> deepestBranches() {
        List<T> deepest = new ArrayList<>();
        deepestHelper(root, deepest, 0);
        return deepest;
    }

    /**
     * Helper method to determine the deepest nodes and add them to the list
     *
     * @param curr is the current node
     * @param list is the list to add the deepest nodes to
     * @param depth is the depth of the node
     */
    private void deepestHelper(AVLNode<T> curr, List<T> list, int depth) {
        if (curr != null) {
            if (curr.getHeight() + depth == root.getHeight()) {
                list.add(curr.getData());
            }
            deepestHelper(curr.getLeft(), list, depth + 1);
            deepestHelper(curr.getRight(), list, depth + 1);
        }
    }

    /**
     * Returns a sorted list of data that are within the threshold bounds of
     * data1 and data2. That is, the data should be > data1 and < data2.
     *
     * Should run in worst case O(n), but this is heavily dependent on the
     * threshold data. You should not explore branches of the tree that do not
     * satisfy the threshold.
     *
     * Example Tree:
     *                           10
     *                       /        \
     *                      5          15
     *                    /   \      /    \
     *                   2     7    13    20
     *                  / \   / \     \  / \
     *                 1   4 6   8   14 17  25
     *                /           \          \
     *               0             9         30
     *
     * sortedInBetween(7, 14) returns [8, 9, 10, 13]
     * sortedInBetween(3, 8) returns [4, 5, 6, 7]
     * sortedInBetween(8, 8) returns []
     *
     * @throws java.lang.IllegalArgumentException if data1 or data2 are null
     * @param data1 the smaller data in the threshold
     * @param data2 the larger data in the threshold
     * or if data1 > data2
     * @return a sorted list of data that is > data1 and < data2
     */
    public List<T> sortedInBetween(T data1, T data2) {
        if (data1 == null || data2 == null) {
            throw new IllegalArgumentException("Data can't be null!");
        } else if (data1.compareTo(data2) > 0) {
            throw new IllegalArgumentException("First data must be smaller than second!");
        }
        List<T> sortedList = new ArrayList<>();
        sortedHelper(root, sortedList, data1, data2);
        return sortedList;
    }

    /**
     * Helper method for the SortedInBetween method
     *
     * @param curr is the current node
     * @param list is the list to add node's data to if it is between data values
     * @param data1 is the minimum value
     * @param data2 is the maximum value
     */
    private void sortedHelper(AVLNode<T> curr, List<T> list, T data1, T data2) {
        if (curr != null) {
            if (curr.getData().compareTo(data1) > 0) {
                sortedHelper(curr.getLeft(), list, data1, data2);
            }
            if (curr.getData().compareTo(data1) > 0 && curr.getData().compareTo(data2) < 0) {
                list.add(curr.getData());
            }
            if (curr.getData().compareTo(data2) < 0) {
                sortedHelper(curr.getRight(), list, data1, data2);
            }
        }
        return;
    }

    /**
     * Clears the tree.
     */
    public void clear() {
        size = 0;
        root = null;
    }

    /**
     * Returns the height of the root of the tree.
     *
     * Since this is an AVL, this method does not need to traverse the tree
     * and should be O(1)
     *
     * @return the height of the root of the tree, -1 if the tree is empty
     */
    public int height() {
        if (root == null) {
            return -1;
        } else {
            return root.getHeight();
        }
    }

    /**
     * Returns the size of the AVL tree.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return number of items in the AVL tree
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD
        return size;
    }

    /**
     * Returns the root of the AVL tree.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the root of the AVL tree
     */
    public AVLNode<T> getRoot() {
        // DO NOT MODIFY THIS METHOD!
        return root;
    }
}