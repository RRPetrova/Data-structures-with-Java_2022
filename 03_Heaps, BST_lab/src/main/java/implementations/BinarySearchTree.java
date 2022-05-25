package implementations;

import interfaces.AbstractBinarySearchTree;

public class BinarySearchTree<E extends Comparable<E>> implements AbstractBinarySearchTree<E> {

    private Node<E> root;
    private Node<E> leftChild;
    private Node<E> rightChild;

    public BinarySearchTree() {
        this.root = null;
        this.leftChild = null;
        this.rightChild = null;
    }

    public BinarySearchTree(Node<E> secondTree) {
        this.copyTree(secondTree);

    }

    private void copyTree(Node<E> node) {
        if (node != null) {
            this.insert(node.value);
            this.copyTree(node.leftChild);
            this.copyTree(node.rightChild);
        }
    }


    @Override
    public void insert(E element) {
        if (this.root == null) {
            this.root = new Node<>(element);
        } else {
            Node<E> currentNode = this.root;
            Node<E> prev = currentNode;

            while (currentNode != null) {
                prev = currentNode;
                if (element.compareTo(currentNode.value) < 0) {
                    currentNode = currentNode.leftChild;
                } else if (element.compareTo(currentNode.value) > 0) {
                    currentNode = currentNode.rightChild;
                } else if (element.compareTo(currentNode.value) == 0) {
                    return;
                }
            }
            // currentNode = new Node<>(element);
            if (prev.value.compareTo(element) > 0) {
                prev.leftChild = new Node<>(element);
            } else if (prev.value.compareTo(element) < 0) {
                prev.rightChild = new Node<>(element);
            }
        }

    }

    @Override
    public boolean contains(E element) {
        Node<E> currentNode = this.root;

        while (currentNode != null) {
            if (element.compareTo(currentNode.value) < 0) {
                currentNode = currentNode.leftChild;
            } else if (element.compareTo(currentNode.value) > 0) {
                currentNode = currentNode.rightChild;
            } else if (element.compareTo(currentNode.value) == 0) {
                return true;
            }
        }
        return false;
    }

    @Override
    public AbstractBinarySearchTree<E> search(E element) {
        AbstractBinarySearchTree<E> result = new BinarySearchTree<>();

        Node<E> currentNode = this.root;
        if (currentNode.value.compareTo(element) == 0) {
            return this;
        }

        while (currentNode != null) {
            if (element.compareTo(currentNode.value) < 0) {
                currentNode = currentNode.leftChild;
            } else if (element.compareTo(currentNode.value) > 0) {
                currentNode = currentNode.rightChild;
            } else if (element.compareTo(currentNode.value) == 0) {
                return new BinarySearchTree<>(currentNode);
            }
        }

        return result;
    }


    @Override
    public Node<E> getRoot() {
        return this.root;
    }

    @Override
    public Node<E> getLeft() {
        return this.leftChild;
    }

    @Override
    public Node<E> getRight() {
        return this.rightChild;
    }

    @Override
    public E getValue() {
        return this.root.value;
    }
}
