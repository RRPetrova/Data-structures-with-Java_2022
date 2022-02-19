package implementations;

import interfaces.LinkedList;
import org.w3c.dom.Node;

import java.util.Iterator;

public class SinglyLinkedList<E> implements LinkedList<E> {


    private Node<E> head;
    private int size;


    private static class Node<E> {
        private E value;
        private Node<E> next;

        Node(E element) {
            this.value = element;
        }
    }

    public SinglyLinkedList() {
        this.size = 0;
        this.head = null;
    }


    @Override
    public void addFirst(E element) {
        Node<E> toInsert = new Node<>(element);
        toInsert.next = this.head;
        this.head = toInsert;

        this.size++;
    }

    @Override
    public void addLast(E element) {
        Node<E> toInsert = new Node<>(element);
        if (this.size == 0) {
            this.head = toInsert;
        } else {
            Node<E> current = this.head;
            while (current.next != null) {
                current = current.next;
            }
            current.next = toInsert;
        }
        size++;
    }

    @Override
    public E removeFirst() {
        if (this.size == 0) {
            throw new IllegalStateException("Empty collection!");
        }
        Node<E> toRemove = this.head;
        this.head = this.head.next;
        this.size--;
        return toRemove.value;
    }

    @Override
    public E removeLast() {
        if (this.size == 0) {
            throw new IllegalStateException("Empty collection!");
        }
        if (this.size == 1) {
            E value = this.head.value;
            this.head = null;
            return value;
        }
        Node<E> preLast = this.head;
        Node<E> lastElement = this.head.next;

        while (lastElement.next != null) {
            preLast = lastElement;
            lastElement = lastElement.next;
        }
        preLast.next = null;


        return lastElement.value;
    }

    @Override
    public E getFirst() {
        if (this.size == 0) {
            throw new IllegalStateException("Empty collection!");
        }
        return this.head.value;
    }

    @Override
    public E getLast() {
        if (this.size == 0) {
            throw new IllegalStateException("Empty collection!");
        }

        Node<E> curr = this.head;

        while (curr.next != null) {
            curr = curr.next;
        }


        return curr.value;
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public boolean isEmpty() {
        return this.size == 0;
    }

    @Override
    public Iterator<E> iterator() {
        return null;
    }
}
