import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.function.Consumer;

import java.util.List;

public class BinarySearchTree<E extends Comparable<E>> {
    private Node<E> root;

    public BinarySearchTree(E element) {
        this.root = new Node<>(element);
    }

    public BinarySearchTree() {

    }

    //    public BinarySearchTree(Node<E> secondTree) {
//        this.copyTree(secondTree);
//    }
//    private void copyTree(Node<E> node) {
//        if (node != null) {
//            this.insert(node.value);
//            this.copyTree(node.leftChild);
//            this.copyTree(node.rightChild);
//        }
//    }
    public BinarySearchTree(Node<E> secondTree) {
        this.root = new Node<>(secondTree);

    }

    public static class Node<E> {
        private E value;
        private Node<E> leftChild;
        private Node<E> rightChild;
        private int count;

        public Node(E value) {
            this.value = value;
            this.count = 1;
        }

        //second tree constructor
        public Node(Node<E> second) {
            this.value = second.value;
            this.count = second.count;
            if (second.getLeft() != null) {
                this.leftChild = new Node<>(second.getLeft());
            }
            if (second.getRight() != null) {
                this.rightChild = new Node<>(second.getRight());
            }
        }

        public Node<E> getLeft() {
            return this.leftChild;
        }

        public Node<E> getRight() {
            return this.rightChild;
        }

        public E getValue() {
            return this.value;
        }
    }

    public void eachInOrder(Consumer<E> consumer) {
        inOrder(consumer, this.root);
    }

    private void inOrder(Consumer<E> consumer, Node<E> node) {
        if (node == null) {
            return;
        }
        inOrder(consumer, node.getLeft());
        consumer.accept(node.getValue());
        inOrder(consumer, node.getRight());

    }


    public Node<E> getRoot() {
        return this.root;
    }

    public void insert(E element) {
        if (this.root == null) {
            this.root = new Node<>(element);
        } else {
            insertRecursiveElementInBST(this.root, element);
        }

    }


    private void insertRecursiveElementInBST(Node<E> node, E element) {
        if (elementIsGreater(element, node)) {
            if (node.getRight() == null) {
                node.rightChild = new Node<>(element);
            } else {

                insertRecursiveElementInBST(node.rightChild, element);
            }
        } else if (elementIsLess(element, node)) {
            if (node.getLeft() == null) {
                node.leftChild = new Node<>(element);
            } else {
                insertRecursiveElementInBST(node.leftChild, element);
            }
        }
        node.count++;
    }

    private boolean elementIsLess(E element, Node<E> node) {
        return element.compareTo(node.value) < 0;
    }

    private boolean elementIsGreater(E element, Node<E> node) {
        return element.compareTo(node.value) > 0;
    }

    public boolean contains(E element) {
//        Node<E> currentNode = this.root;
//        while (currentNode != null) {
//            if (element.compareTo(currentNode.value) < 0) {
//                currentNode = currentNode.leftChild;
//            } else if (element.compareTo(currentNode.value) > 0) {
//                currentNode = currentNode.rightChild;
//            } else if (element.compareTo(currentNode.value) == 0) {
//                return true;
//            }
//        }
//        return false;
        return containsRecursive(this.root, element);
    }

    private boolean containsRecursive(Node<E> node, E element) {
        if (node == null) {
            return false;
        }
        if (element.compareTo(node.value) == 0) {
            return true;
        } else if (elementIsGreater(element, node)) {
            return containsRecursive(node.rightChild, element);
        }
        return containsRecursive(node.getLeft(), element);
    }


    public BinarySearchTree<E> search(E element) {


        Node<E> currentNode = this.root;
        if (currentNode.value.compareTo(element) == 0) {
            return new BinarySearchTree<>(currentNode);
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

        return currentNode != null ? new BinarySearchTree<>(currentNode) : null;
    }

    public List<E> range(E first, E second) {
        List<E> result = new ArrayList<>();
        //BFS
        if (this.root == null) {
            return result;
        }

        Deque<Node<E>> queue = new ArrayDeque();
        queue.offer(this.root);

        while (!queue.isEmpty()) {
            Node<E> current = queue.poll();

            if (current.getRight() != null) {
                queue.offer(current.getRight());
            }
            if (current.getLeft() != null) {
                queue.offer(current.getLeft());
            }
            // 1 3 5 7  -> range 2-5
            //current 3 => 3-2 = 1 ; 3-5 = -2
            if (current.value.compareTo(first) > 0 && current.value.compareTo(second) < 0) {
                result.add(current.value);
            } else if (current.value.compareTo(first) == 0 || current.value.compareTo(second) == 0) {
                result.add(current.value);
            }
        }


        return result;
    }

    public void deleteMin() {
        if (this.root == null) {
            throw new IllegalStateException();
        }
        if (this.root.leftChild == null) {
            this.root = this.root.getRight();
            return;
        }
        Node<E> current = this.root;
        while (current.leftChild.leftChild != null) {
            current.count--;
            current = current.getLeft();
        }
        current.count--;
        current.leftChild = current.getLeft().getRight();
    }

    public void deleteMax() {
        if (this.root == null) {
            throw new IllegalStateException();
        }
        if (this.root.rightChild == null) {
            this.root = this.root.getLeft();
            return;
        }
        Node<E> current = this.root;
        while (current.rightChild.rightChild != null) {
            current.count--;
            current = current.getRight();
        }
        current.count--;
        current.rightChild = current.getRight().getLeft();


    }

    public int count() {
        return this.root == null ? 0 : this.root.count;
    }

    public int rank(E element) {
        return countElementsSmallerThanGivenElRecursive(this.root, element);
    }

    private int countElementsSmallerThanGivenElRecursive(Node<E> node, E element) {
        if (this.root == null) {
            return 0;
        }
//element is greater
        if (element.compareTo(node.value) == 0) {
            return node.getLeft() == null ? 0 : node.getLeft().count;
        } else if (elementIsLess(element, node)) {
            countElementsSmallerThanGivenElRecursive(node.rightChild, element);
        }

        if (node.getLeft() == null) {
            return 0;
        }
        return node.getLeft().count + 1 + countElementsSmallerThanGivenElRecursive(node.getRight(), element);

    }

    public E ceil(E element) {
        if (this.root == null) {
            return null;
        }
        Node<E> current = this.root;
        Node<E> nearestBigger = null;
        while (current != null) {
            if (elementIsLess(element, current)) {
                nearestBigger = current;
                current = current.getLeft();
            } else if (elementIsGreater(element, current)) {
                nearestBigger = current;
                current = current.getRight();
            } else {
                Node<E> rightNode = current.getRight();
                if (rightNode != null && nearestBigger != null) {
                    if (elementIsLess(rightNode.getValue(), nearestBigger)) {
                        //nearest bigger приема по-малкото от двете
                        nearestBigger = rightNode;
                    }
                } else if (nearestBigger == null) {
                    nearestBigger = rightNode;
                }
                break;
            }
        }
        return nearestBigger != null ? nearestBigger.getValue() : null;
    }


    public E floor(E element) {
        if (this.root == null) {
            return null;
        }
        Node<E> current = this.root;
        Node<E> nearestSmaller = null;

        while (current != null) {
            if (elementIsGreater(element, current)) {
                nearestSmaller = current;
                current = current.getRight();
            } else if (elementIsLess(element, current)) {
                nearestSmaller = current;
                current = current.getLeft();
            } else {
                Node<E> leftNode = current.getLeft();
                if (leftNode != null && nearestSmaller != null) {
                    nearestSmaller = elementIsGreater(leftNode.value, nearestSmaller) ? leftNode : nearestSmaller;
                } else if (nearestSmaller == null) {
                    nearestSmaller = leftNode;
                }
                break;
            }
        }
        return nearestSmaller != null ? nearestSmaller.value : null;
    }
}