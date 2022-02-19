package implementations;

import interfaces.AbstractQueue;
import org.w3c.dom.Node;

import java.util.Iterator;

public class Queue<E> implements AbstractQueue<E> {

    private Node<E> head;
    private int size;

    private static class Node<E> {
        private E value;
        private Node<E> next;

        Node(E element) {
            this.value = element;
        }
    }

    public Queue() {
        this.head = null;
        this.size = 0;
    }

    @Override
    public void offer(E element) {
        //fifo 1,2,3 -> 4 -> 1,2.3.4
        if (this.head == null) {
            this.head = new Node<>(element);
        } else {
            Node<E> current = this.head;

            while (current.next != null) {
                current = current.next;
            }
            current.next = new Node<>(element);
        }
        size++;
    }

    @Override
    public E poll() {
        if (this.isEmpty()) {
            throw new IllegalStateException("Empty queue");
        }
        Node<E> oldHead = this.head;
this.head = oldHead.next;
        this.size--;
        return  oldHead.value;

    }

    @Override
    public E peek() {
        if (this.isEmpty()) {
            throw new IllegalStateException("Empty queue");
        }
        return this.head.value;
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
        return new Iterator<E>() {

        private Queue.Node<E> curr = head;

            @Override
            public boolean hasNext() {
                return curr != null;
            }

            @Override
            public E next() {
                E tmp = curr.value;
                curr = curr.next;
                return tmp;
            }
        };
    }
}
