package implementations;

import interfaces.Deque;

import java.util.Iterator;

public class ArrayDeque<E> implements Deque<E> {

    private final int INITIAL_CAPACITY = 7;
    private int size;
    private int head;
    private int tail;
    private Object[] elements;

    public ArrayDeque() {
        this.elements = new Object[INITIAL_CAPACITY];
        int middle = INITIAL_CAPACITY / 2;
        head = tail = middle;
    }

    @Override
    public void add(E element) {
        addLast(element);
    }

    private Object[] grow() {
        int newCapacity = this.elements.length * 2 + 1;

        Object[] newElements = new Object[newCapacity];
        int mid = newCapacity / 2;
        int begin = mid - this.size / 2;

        int index = this.head;

        for (int i = begin; index <= this.tail; i++) {
            newElements[i] = this.elements[index++];
        }
        this.head = begin;
        this.tail = this.head + this.size-1;
        return newElements;
    }

    @Override
    public void offer(E element) {
        addLast(element);
    }

    @Override
    public void addFirst(E element) {
        if (this.size == 0) {
            this.addLast(element);
        } else {
            if (this.head == 0) {
                this.elements = grow();
            }
            this.elements[--this.head] = element;
            this.size++;
        }
    }

    @Override
    public void addLast(E element) {
        if (this.size == 0) {
            this.elements[this.tail] = element;
        } else {
            if (this.tail == this.elements.length - 1) {
                this.elements = grow();
            }
            this.elements[++this.tail] = element;
        }
        this.size++;
    }

    @Override
    public void push(E element) {
        addFirst(element);
    }

    @Override
    public void insert(int index, E element) {
        int realIndex = this.head + index;
        if (realIndex < this.head || realIndex > this.tail) {
            throw new IndexOutOfBoundsException("Invalid index: " + realIndex);
        }
        if (realIndex - this.head < this.tail - realIndex) {
            insertShiftLeft(realIndex - 1, element);
        } else {
            insertShiftRight(realIndex, element);
        }

    }

    private void insertShiftRight(int index, E element) {
        E element1 = (E) this.elements[this.tail];
        for (int i = this.tail; i > index; i--) {
            this.elements[i] = this.elements[i - 1];
        }
        this.elements[index] = element;
        this.addLast(element1);

    }

    private void insertShiftLeft(int index, E element) {
        E element1 = (E) this.elements[this.head];
        for (int i = this.head; i <= index; i++) {
            this.elements[i] = this.elements[i + 1];
        }
        this.elements[index] = element;
        this.addFirst(element1);
    }

    @Override
    public void set(int index, E element) {
        int realIndex = this.head + index;
        if (realIndex < this.head || realIndex > this.tail) {
            throw new IndexOutOfBoundsException("Invalid index: " + index);
        }

        this.elements[realIndex] = element;
    }

    @Override
    public E peek() {
        if (this.size != 0) {
            return (E) this.elements[this.head];
        }

        return null;
    }

    @Override
    public E poll() {
        return removeFirst();
    }

    @Override
    public E pop() {
        return removeFirst();
    }

    @Override
    public E get(int index) {
        int realIndex = this.head + index;
        if (realIndex < this.head || realIndex > this.tail) {
            throw new IndexOutOfBoundsException("Invalid index: " + index);
        }
        return (E) this.elements[realIndex];
    }

    @Override
    public E get(Object object) {
        if (this.size == 0) {
            return null;
        }
        for (int i = this.head; i <= this.tail; i++) {
            if (this.elements[i].equals(object)) {
                return (E) this.elements[i];
            }
        }

        return null;
    }

    @Override
    public E remove(int index) { // __543____ // 5
        // ind 1 => 4(value) => 2(head ind)+1 = 3(ind) = 4(value)
        int realIndex = this.head + index;
        if (realIndex < this.head || realIndex > this.tail) {
            throw new IndexOutOfBoundsException("Invalid index: " + index);
        }
        return (E) this.elements[realIndex];
    }

    @Override
    public E remove(Object object) {
        if (this.size == 0) {
            return null;
        }

        for (int i = this.head; i <= this.tail; i++) {
            if (this.elements[i].equals(object)) {

                E element = (E) this.elements[i];
                this.elements[i] = null;

                for (int j = i; j < this.tail; j++) {
                    this.elements[j] = this.elements[j + 1];
                }
                this.removeLast();
                return element;
            }
        }
        return null;
    }

    @Override
    public E removeFirst() {
        if (this.size != 0) {
            E element = (E)this.elements[this.head]; //first el
            this.elements[this.head] = null;
            this.head++;
            this.size--;

            return element;
        }
        return null;
    }

    @Override
    public E removeLast() {
        if (this.size != 0) {
            E element = (E)this.elements[this.tail];
            this.elements[this.tail] = null;
            this.size--;
            this.tail--;
            return element;
        }
        return null;
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public int capacity() {
        return this.elements.length;
    }

    @Override
    public void trimToSize() {
        Object[] newEl = new Object[this.size];
        int ind = 0;
        for (int i = this.head; i <= this.tail; i++) {
            newEl[ind] = this.elements[i];
            ind++;
        }
        this.elements = newEl;
    }

    @Override
    public boolean isEmpty() {
        return this.size == 0;
    }

    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {

            private int index = head;


            @Override
            public boolean hasNext() {
                return index <= tail;
            }

            @Override
            public E next() {
                return (E) elements[index++];
            }
        };
    }
}
