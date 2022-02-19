package implementations;

import interfaces.AbstractStack;
import org.w3c.dom.Node;

import java.util.Iterator;

public class Stack<E> implements AbstractStack<E> {

    private Node<E> top;
    private int size;

    private static class Node<E> {
        private E value;
        private Node<E> next;

        Node(E element) {
            this.value = element;
        }
    }

    public Stack() {
        this.top = null;
        this.size = 0;
    }

    @Override
    public void push(E element) {
        this.size++;
        Node<E> newElement = new Node<>(element);
        newElement.next = this.top;
        this.top = newElement;
    }

    @Override
    public E pop() {

        if (size == 0) {
            throw new IllegalStateException("Empty stack");
        }
        this.size--;
        Node<E> oldTop = this.top;
        this.top.next = this.top;
        return (E) oldTop.value;
    }

    @Override
    public E peek() {
        if (size == 0) {
            throw new IllegalStateException("Empty stack");
        }
        return (E) this.top.value;
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

            private Stack.Node<E> curr = top;

            @Override
            public boolean hasNext() {
                return this.curr.next != null;
            }

            @Override
            public E next() {
                E value = this.curr.value;
                this.curr = this.curr.next;
                return value;
            }
        };
    }
}
