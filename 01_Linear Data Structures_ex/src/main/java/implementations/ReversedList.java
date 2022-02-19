package implementations;

import java.util.Iterator;

public class ReversedList<E> {

    private final int INITIAL_CAPACITY = 2;
    private int size;
    private int capacity;
    private int tail;
    private int head;
    private Object[] elements;

    public ReversedList() {
        this.elements = new Object[INITIAL_CAPACITY];
        this.capacity = INITIAL_CAPACITY;
    }

    public void add(E element) {
        if (this.size == capacity) {
            this.elements = grow();
        }
        this.elements[this.size++] = element;
    }

    private Object[] grow() {
        this.capacity *= 2;
        Object[] arrayListNew = new Object[this.capacity];

        for (int i = 0; i < this.elements.length; i++) {
            arrayListNew[i] = this.elements[i];
        }
        return arrayListNew;
    }


    public int size() {
        return this.size;
    }


    public int capacity() {
        return this.elements.length;
    }


    public E get(int index) { // 1 2 3
        if (index < 0 || index >= this.size) {
            throw new IndexOutOfBoundsException("Invalid index: " + index);
        }

        return (E) this.elements[this.size-1-index];
    }


    public void removeAt(int index) {
        if (index < 0 || index >= this.elements.length) {
            throw new IndexOutOfBoundsException("Invalid index: " + index);
        }

        Object elementAboutToBeRemoved = this.elements[this.size-1-index];
        System.out.println(elementAboutToBeRemoved);
        for (int i = this.size-1-index; i < this.elements.length-1; i++) {
            // ot 2 do
            this.elements[i] = this.elements[i + 1];
        }
        this.size--;

    }


    public Iterator<E> iterator() {
        return new Iterator<E>() {


            @Override
            public boolean hasNext() {
                return false;
            }

            @Override
            public E next() {
                return null;
            }
        };
    }
}
