package core;

import model.Message;
import shared.DataTransferSystem;

import java.util.ArrayList;
import java.util.List;

public class MessagingSystem implements DataTransferSystem {
    private Node root;
    private int size;

    private static class Node {
        Message message;
        Node left;
        Node right;

        Node(Message message) {
            this.message = message;
        }
    }

    public MessagingSystem() {
    }

    @Override
    public void add(Message message) {
        if (this.root == null) {
            this.root = new Node(message);
        } else {
            Node current = this.root;
            Node prev = current;

            while (current != null) {
                prev = current;
                if (message.getWeight() < (current.message.getWeight())) {
                    current = current.left;
                } else if (message.getWeight() > current.message.getWeight()) {
                    current = current.right;
                } else if (message.getWeight() == current.message.getWeight()) {
                    throw new IllegalArgumentException();
                }
            }
            // currentNode = new Node<>(element);
            if (prev.message.getWeight() > message.getWeight()) {
                prev.left = new Node(message);
            } else if (prev.message.getWeight() < message.getWeight()) {
                prev.right = new Node(message);
            }
        }
        this.size++;
    }


    @Override
    public Message getByWeight(int weight) {
        Node currentNode = this.root;

        while (currentNode != null) {
            if (currentNode.message.getWeight() < weight) {
                currentNode = currentNode.right;
            } else if (currentNode.message.getWeight() > weight) {
                currentNode = currentNode.left;
            } else {
                return currentNode.message;
            }
        }
        throw new IllegalArgumentException();
    }

    @Override
    public Message getLightest() {
        Node current = this.root;
        if (current == null) {
            throw new IllegalStateException();
        }
        Node prev = current;
        while (current != null) {
            prev = current;
            current = current.left;
        }
        return prev.message;
    }

    @Override
    public Message getHeaviest() {
        Node current = this.root;
        if (current == null) {
            throw new IllegalStateException();
        }
        while (current.right != null) {
            current = current.right;
        }
        return current.message;
    }

    @Override
    public Message deleteLightest() {
        Message result = null;
        Node current = this.root;

        if (current == null) {
            throw new IllegalStateException();
        }

        if (current.left == null) {
            result = current.message;
            this.root = current.right;
        } else {
            Node prev = current;
            while (prev.left.left != null) {
                prev = prev.left;
            }
            result = prev.left.message;
            prev.left = prev.left.right;
        }
        size--;
        return result;
    }

    @Override
    public Message deleteHeaviest() {
        Message result = null;
        Node current = this.root;

        if (current == null) {
            throw new IllegalStateException();
        }

        if (current.right == null) {
            result = current.message;
            this.root = current.left;
        } else {
            Node prev = current;
            while (prev.right.right != null) {
                prev = prev.right;
            }
            result = prev.right.message;
            prev.right = prev.right.left;
        }
        size--;
        return result;
    }

    @Override
    public Boolean contains(Message message) {
        Node currentNode = this.root;

        while (currentNode != null) {
            if (message.getWeight() < currentNode.message.getWeight()) {
                currentNode = currentNode.left;
            } else if (message.getWeight() > currentNode.message.getWeight()) {
                currentNode = currentNode.right;
            } else if (message.getWeight() == currentNode.message.getWeight()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public List<Message> getOrderedByWeight() {
        return getInOrder();
    }

    @Override
    public List<Message> getPostOrder() {
        List<Message> res = new ArrayList<>();
        Node current = this.root;

        getPostOrderRecursive(current, res);
        return res;
    }

    private void getPostOrderRecursive(Node node, List<Message> res) {
        if (node == null) {
            return ;
        }
        getPostOrderRecursive(node.left, res);
        getPostOrderRecursive(node.right, res);
        res.add(node.message);
    }

    @Override
    public List<Message> getPreOrder() {
        List<Message> res = new ArrayList<>();
        Node current = this.root;

        getPreOrderRecursive(current, res);
        return res;

    }

    private void getPreOrderRecursive(Node node, List<Message> res) {
        if (node == null) {
            return ;
        }
        res.add(node.message);
        getPreOrderRecursive(node.left, res);
        getPreOrderRecursive(node.right, res);
    }

    @Override
    public List<Message> getInOrder() {
        List<Message> res = new ArrayList<>();
        Node current = this.root;

        getInOrderRecursive(current, res);
        return res;
    }

    private void getInOrderRecursive(Node node, List<Message> res) {
        if (node == null) {
            return ;
        }
        getPreOrderRecursive(node.left, res);
        res.add(node.message);
        getPreOrderRecursive(node.right, res);
    }

    @Override
    public int size() {
        return this.size;
    }
}
