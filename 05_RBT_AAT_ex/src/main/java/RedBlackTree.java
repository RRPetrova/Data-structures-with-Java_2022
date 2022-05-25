import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

public class RedBlackTree<Key extends Comparable<Key>, Value> {

    private static final boolean RED = true;
    private static final boolean BLACK = false;

    private Node root;     // root of the BST

    // BST helper node data type
    private class Node {
        private Key key;           // key
        private Value val;         // associated data
        private Node left, right;  // links to left and right subtrees
        private boolean color;     // color of parent link
        private int size;          // subtree count

        public Node(Key key, Value val, boolean color, int size) {
            this.key = key;
            this.val = val;
            this.color = color;
            this.size = size;
        }

        public Node(Key key, Value val, boolean color) {
            this.key = key;
            this.val = val;
            this.color = color;
        }
    }

    public RedBlackTree() {
    }

    // is node x red; false if x is null ?
    private boolean isRed(Node x) {
        return x != null && x.color == RED;
    }

    // number of node in subtree rooted at x; 0 if x is null
    private int size(Node x) {
        return x == null ? 0 : x.size;
    }


    /**
     * Returns the number of key-value pairs in this symbol table.
     *
     * @return the number of key-value pairs in this symbol table
     */
    public int size() {
        return this.size(root);
    }

    /**
     * Is this symbol table empty?
     *
     * @return {@code true} if this symbol table is empty and {@code false} otherwise
     */
    public boolean isEmpty() {
        return this.root == null;
    }

    public Value get(Key key) {
        Node n = root;

        while (n != null) {
            if (key.compareTo(n.key) > 0) {
                n = n.right;
            } else if (key.compareTo(n.key) < 0) {
                n = n.left;
            } else { //key == n.key   (found it!)
                return n.val;
            }
        }
        return null;
    }

    // value associated with the given key in subtree rooted at x; null if no such key
    private Value get(Node x, Key key) {
        Node n = x;

        while (n != null) {
            if (key.compareTo(n.key) > 0) {
                n = n.right;
            } else if (key.compareTo(n.key) < 0) {
                n = n.left;
            } else { //key == n.key   (found it!)
                return n.val;
            }
        }
        return null;
    }

    public boolean contains(Key key) {

        //return this.get(key) != null;
        Node current = this.root;
        if (this.root == null) {
            return false;
        }

        while (current != null) {
            if (key.compareTo(current.key) > 0) {
                current = current.right;
            } else if (key.compareTo(current.key) < 0) {
                current = current.left;
            } else { //key == n.key   (found it!)
                return true;
            }
        }
        return false;
    }

    public void put(Key key, Value val) {
        root = put(root, key, val);
        root.color = BLACK;    // fix the root color, if other fixes made it red
    }

    // insert the key-value pair in the subtree rooted at h
    private Node put(Node n, Key key, Value val) {
        if (n == null) {
            Node newNode = new Node(key, val, RED, 1);
            //  newNode.size = 1;
            return newNode;
        }

        if (key.compareTo(n.key) < 0) {
            n.left = put(n.left, key, val);
        } else if (key.compareTo(n.key) > 0) {
            n.right = put(n.right, key, val);
        } else {
            n.val = val;
        }

        n = balance(n);

        return n;
    }

    public void deleteMin() {
        if (this.root == null) {
            throw new IllegalArgumentException("Tree is empty!");
        }

        if (this.root.left == null) {
            this.root = null;
            return;
        }
        this.root = deleteMin(this.root);

      /*  Node min = this.root;
        Node parent = null;

        while (min.left != null) {
            parent = min;
            parent.childrenCount--;
            min = min.left;
        }

        if (parent == null) {
            this.root = this.root.right;
        } else {
            parent.left = min.right;
        }

        this.size--;*/
    }

    // delete the key-value pair with the minimum key rooted at h
    private Node deleteMin(Node h) {
        if (h.left == null) {
            return null;
        }

        if (!isRed(h.left) && !isRed(h.left.left)) {
            h = moveRedLeft(h);
        }

        h.left = deleteMin(h.left);

        return balance(h);

    }

    public void deleteMax() {
        if (this.root == null) {
            throw new IllegalArgumentException("Tree is empty!");
        }
        if (this.root.right == null) {
            this.root = null;
            return;
        }
        this.root = deleteMax(this.root);
    }

    // delete the key-value pair with the maximum key rooted at h
    private Node deleteMax(Node h) {
        if (isRed(h.left)) {
            h = rotateRight(h);
        }

        if (h.right == null) {
            return null;
        }

        if (!isRed(h.right) && !isRed(h.right.right)) {
            h = moveRedRight(h);
        }

        h.right = deleteMax(h.right);

        return balance(h);
    }

    public void delete(Key key) {
        if (key == null || isEmpty()) {
            throw new IllegalArgumentException();
        }

        if (!contains(key)) {
            return;
        }
        this.root = delete(this.root, key);
        if (!isEmpty()) {
            this.root.color = BLACK;
        }
    }

    // delete the key-value pair with the given key rooted at h
    private Node delete(Node h, Key key) {
        return this.root = deleteRecursive(this.root, key);
    }

    private Node deleteRecursive(Node node, Key key) {
        if (node == null) {
            return node;
        }

        if (key.compareTo(node.key) < 0) {
            node.left = deleteRecursive(node.left, key);
        } else if (key.compareTo(node.key) > 0) {
            node.right = deleteRecursive(node.right, key);
        } else {
            if (node.left == null) {
                return node.right;
            } else if (node.right == null) {
                return node.left;
            }

            node.key = min(node.right).key;
            node.right = deleteRecursive(node.right, node.key);
        }
        balance(node);
        return node;
    }

   /* private Key minValue(Node node) {
        Key minv = node.key;
        while (node.left != null) {
            minv = node.left.key;
            node = node.left;
        }

        return minv;
    }*/

    private Node rotateRight(Node h) {
        Node temp = h.left;
        h.left = temp.right;
        temp.right = h;

        temp.color = h.color;
        h.color = RED;
        temp.size = h.size;
        h.size = 1 + size(h.left) + size(h.right);
        return temp;
    }

    // make a right-leaning link lean to the left
    private Node rotateLeft(Node h) {
        Node temp = h.right;
        h.right = temp.left;
        temp.left = h;

        temp.color = h.color;
        h.color = RED;
        temp.size = h.size;
        h.size = 1 + size(h.left) + size(h.right);
        return temp;
    }

    // flip the colors of a node and its two children
    private void flipColors(Node h) { //current node = RED, BLACK children
        h.color = RED;
        if (h.left != null) {
            h.left.color = BLACK;
        }
        if (h.right != null) {
            h.right.color = BLACK;
        }
    }

    // Assuming that h is red and both h.left and h.left.left
    // are black, make h.left or one of its children red.
    private Node moveRedLeft(Node h) {
        flipColors(h);
        if (isRed(h.right.left)) {
            h.right = rotateRight(h.right);
            h = rotateLeft(h);
            flipColors(h);
        }
        return h;
    }

    // Assuming that h is red and both h.right and h.right.left
    // are black, make h.right or one of its children red.
    private Node moveRedRight(Node h) {
        flipColors(h);
        if (isRed(h.left.left)) {
            h = rotateRight(h);
            flipColors(h);
        }
        return h;
    }

    // restore red-black tree invariant
    private Node balance(Node h) {
        if (isRed(h.right) && !isRed(h.left)) {    // fixes an initial right-leaning red edge
            h = rotateLeft(h);
        }
        if (isRed(h.left) && isRed(h.left.left)) {  // balances a 4-node, as needed
            h = rotateRight(h);
        }
        if (isRed(h.left) && isRed(h.right)) {   // splits the 4-node, passing its middle
            flipColors(h);                          // key to its parent
        }

        h.size = 1 + size(h.left) + size(h.right);
        return h;
    }

    public int height() {
        return this.height(root);
    }

    private int height(Node x) {
        if (x == null) {
            return -1;
        }
        return Math.max(height(x.left), height(x.right)) + 1;
    }

    public Key min() {
        return min(this.root).key;
    }

    // the smallest key in subtree rooted at x; null if no such key
    private Node min(Node x) {
        if (x == null) {
            return null;
        }
        Node min = x;
        while (x.left != null) {
            min = x.left;
            x = x.left;
        }
        return min;
    }

    public Key max() {
        return max(this.root).key;
    }

    // the largest key in the subtree rooted at x; null if no such key
    private Node max(Node x) {
        if (x == null) {
            return null;
        }
        Node max = x;
        while (x.right != null) {
            max = x.right;
            x = x.right;
        }

        return max;
    }

    public Key floor(Key key) {
        Node floor = floor(this.root, key);
        if (floor == null) {
            throw new IllegalArgumentException();
        }
        return floor.key;
    }

    // the largest key in the subtree rooted at x less than or equal to the given key
    private Node floor(Node x, Key key) {
        if (x == null) {
            return null;
        }

        Node floor = null;
        if (key.compareTo(x.key) < 0) {
            return floor(x.left, key);
        } else if (key.compareTo(x.key) > 0) {
            floor= floor(x.right, key);
        }
        if (floor != null) {
            return floor;
        }

        return x;
    }

    public Key ceiling(Key key) {
        Node ceil = floor(this.root, key);
        if (ceil == null) {
            throw new IllegalArgumentException();
        }
        return ceil.key;
    }

    // the smallest key in the subtree rooted at x greater than or equal to the given key
    private Node ceiling(Node x, Key key) {
        if (x == null) {
            return null;
        }

        Node ceil = null;
        if (key.compareTo(x.key) < 0) {
            ceil= ceiling(x.left, key);
        } else if (key.compareTo(x.key) > 0) {
            return ceiling(x.right, key);
        }
        if (ceil != null) {
            return ceil;
        }

        return x;
    }

    public Key select(int rank) {
        Key key = this.select(this.root, rank);
        if (key == null) {
            throw new IllegalArgumentException("ERROR");
        }

        return key;
    }

    // Return key in BST rooted at x of given rank.
    // Precondition: rank is in legal range.
    private Key select(Node x, int rank) {
        if (x == null) {
            return null;
        }

        int leftSize = this.size(x.left);
        if (leftSize == rank) {
            return x.key;
        }

        if (leftSize > rank) {
            return this.select( x.left,rank);
        } else {
            return this.select(x.right,rank - (leftSize + 1) );
        }
    }

    public int rank(Key key) {
        return this.rank(key, this.root);
    }

    // number of keys less than key in the subtree rooted at x
    private int rank(Key key, Node x) {
        if (x == null) {
            return 0;
        }

        int compare = key.compareTo(x.key);

        if (compare < 0) {
            return this.rank(key, x.left);
        }

        if (compare > 0) {
            return 1 + this.size(x.left) + this.rank(key, x.right);
        }

        return this.size(x.left);
    }

    public Iterable<Key> keys() {
        List<Key> res = new ArrayList<>();
        Node current = this.root;
        if (this.root == null) {
            return res;
        }
        Deque<Node> queue = new ArrayDeque<>();
        queue.offer(this.root);

        while (queue.isEmpty()) {
            Node poll = queue.poll();
            res.add(poll.key);
            if (poll.key.compareTo(current.key) > 0) {
                current = current.right;
            } else if (poll.key.compareTo(current.key) < 0) {
                current = current.left;
            }
            // queue.offer(current);
        }
        return res;
    }

    public Iterable<Key> keys(Key lo, Key hi) {
        return null;
    }

    // add the keys between lo and hi in the subtree rooted at x
    // to the queue
    private void keys(Node x, Deque<Key> queue, Key lo, Key hi) {
    }

    public int size(Key lo, Key hi) {
        return 0;
    }

    private boolean check() {
        return false;
    }

    // does this binary tree satisfy symmetric order?
    // Note: this test also ensures that data structure is a binary tree since order is strict
    private boolean isBST() {
        return false;
    }

    // is the tree rooted at x a BST with all keys strictly between min and max
    // (if min or max is null, treat as empty constraint)
    private boolean isBST(Node x, Key min, Key max) {
        return false;
    }

    // are the size fields correct?
    private boolean isSizeConsistent() {
        return false;
    }

    private boolean isSizeConsistent(Node x) {
        return false;
    }

    // check that ranks are consistent
    private boolean isRankConsistent() {
        return false;
    }

    // Does the tree have no red right links, and at most one (left)
    // red links in a row on any path?
    private boolean isTwoThree() {
        return false;
    }

    private boolean isTwoThree(Node x) {
        return false;
    }

    // do all paths from root to leaf have same number of black edges?
    private boolean isBalanced() {
        return false;
    }

    // does every path from the root to a leaf have the given number of black links?
    private boolean isBalanced(Node x, int black) {
        return false;
    }
}