import java.util.function.Consumer;

class AATree<T extends Comparable<T>> {

    public static class Node<T> {
        private Node<T> left, right;
        private int level;
        private T value;

        public Node(T value) {
            this.value = value;
            this.level = 1;
        }

    }

    private Node<T> root;

    public AATree() {

    }

    public boolean isEmpty() {
        return this.root == null;
    }

    public void clear() {
        this.root = null;
    }

    public void insert(T element) {
        this.root = insertRecursive(this.root, element);
    }

    private Node<T> insertRecursive(Node<T> node, T element) {
        if (node == null) {
            return new Node<>(element);
        }

        if (node.value.compareTo(element) > 0) {
            node.left = insertRecursive(node.left, element);
        } else {
            node.right = insertRecursive(node.right, element);
        }

        node = skew(node); //rotate right
        node = split(node); //rotate left
        return node;
    }

    private Node<T> split(Node<T> node) {
        if (node.right != null && node.right.level == node.level
                && node.right.right != null && node.right.right.level == node.level) {
            node = rotateLeft(node);
            node.level++;
        }

        return node;
    }

    private Node<T> skew(Node<T> node) {
        if (node.left != null && node.left.level == node.level) {
            node = rotateRight(node);
        }

        return node;
    }

    private Node<T> rotateRight(Node<T> node) {
        Node<T> res = node.left;
        node.left = res.right;
        res.right = node;
        return res;

    }

    private Node<T> rotateLeft(Node<T> node) {

        Node<T> res = node.right;
        node.right = res.left;
        res.left = node;
        return res;
    }

    public int countNodes() {
        return countChildren(this.root);
    }

    private int countChildren(Node<T> node) {
        return node == null ? 0 : countChildren(node.left) + countChildren(node.right) + 1;
    }

    public boolean search(T element) {
        Node<T> curr = this.root;
        if (curr == null) {
            return false;
        }

        while (curr != null) {
            if (curr.value.compareTo(element) < 0) {
                curr = curr.right;
            } else if (curr.value.compareTo(element) > 0) {
                curr = curr.left;
            } else {
                return true;
            }
        }
        return false;
    }

    public void inOrder(Consumer<T> consumer) {
        consumeInOrder(this.root, consumer);
    }

    private void consumeInOrder(Node<T> node, Consumer<T> consumer) {
        if (node == null) {
            return;
        }
        consumeInOrder(node.left, consumer);
        consumer.accept(node.value);
        consumeInOrder(node.right, consumer);
    }


    public void preOrder(Consumer<T> consumer) {
        consumePreOrder(this.root, consumer);
    }

    private void consumePreOrder(Node<T> node, Consumer<T> consumer) {
        if (node == null) {
            return;
        }
        consumer.accept(node.value);
        consumePreOrder(node.left, consumer);
        consumePreOrder(node.right, consumer);
    }

    public void postOrder(Consumer<T> consumer) {
        consumePostOrder(this.root, consumer);
    }

    private void consumePostOrder(Node<T> node, Consumer<T> consumer) {
        if (node == null) {
            return;
        }
        consumePostOrder(node.left, consumer);
        consumePostOrder(node.right, consumer);
        consumer.accept(node.value);
    }
}