package implementations;

import interfaces.List;

import java.util.Arrays;
import java.util.Iterator;

public class ArrayList<E> implements List<E> {

    private static final int INITIAL_SIZE = 4;
    private Object[] arrayList;
    private int size;
    private int capacity;

    public ArrayList() {
        this.arrayList = new Object[INITIAL_SIZE];
        this.size = 0;
        this.capacity = INITIAL_SIZE;
    }


    @Override
    public boolean add(E element) {
        if (this.size == this.capacity) {
            resizeList();
        }
        this.arrayList[this.size++] = element;
        return true;
    }

    @Override
    public boolean add(int index, E element) {
        if (invalidIndex(index)) {
            return false;
        }

        if (this.arrayList.length == this.capacity || this.arrayList.length + 1 == this.capacity) {
            resizeList();
        }

        //  Object currElement = this.arrayList[index];
        for (int i = this.size; i > index; i--) {
            this.arrayList[i] = this.arrayList[i - 1];

        }
        this.arrayList[index] = element;
        this.size++;
        return true;
    }

    @Override
    public E get(int index) {
        if (invalidIndex(index)) {
            throw new IndexOutOfBoundsException("Index out of bounds!");
        }

        return (E) this.arrayList[index];
    }

    @Override
    public E set(int index, E element) {
        if (invalidIndex(index)) {
            throw new IndexOutOfBoundsException("Index" + index + "out of bounds!. Size is " + this.size);
        }
        Object oldElement = this.arrayList[index];
        this.arrayList[index] = element;
        return (E) oldElement;
    }

    @Override
    public E remove(int index) {
        if (invalidIndex(index)) {
            throw new IndexOutOfBoundsException("Index" + index + "out of bounds!. Size is " + this.size);
        }
        // 1, 2, 3, 4
        // 1,    3, 4
        Object elementAboutToBeRemoved = this.arrayList[index];
        for (int i = index; i < arrayList.length - 1; i++) {
            this.arrayList[i] = arrayList[i + 1];
        }
        this.size--;
        if (this.size < this.arrayList.length / 3) {
            shrinkList();
        }
        return (E) elementAboutToBeRemoved;
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public int indexOf(E element) {
        if (!Arrays.asList(this.arrayList).contains(element)) {
            return -1;
        }

        for (int i = 0; i < arrayList.length; i++) {
            if (element.equals(arrayList[i])) {
                return i;
            }
        }

        return -1;
    }

    @Override
    public boolean contains(E element) {
        return Arrays.asList(this.arrayList).contains(element);
    }

    @Override
    public boolean isEmpty() {
        return this.size == 0;
    }

    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {

            private int index = 0;

            @Override
            public boolean hasNext() {
                //1, 2, 3
                return this.index < size;
            }

            @Override
            public E next() {
                return get(this.index++);
            }
        };
    }



    private void resizeList() {
        this.capacity *= 2;
        Object[] arrayListNew = new Object[this.capacity];

        for (int i = 0; i < this.arrayList.length; i++) {
            arrayListNew[i] = this.arrayList[i];
        }

        this.arrayList = arrayListNew;
    }

    private void shrinkList() {
        this.capacity /=2;
      this.arrayList = Arrays.copyOf(this.arrayList, this.capacity);
    }

    private boolean invalidIndex(int index) {
        return index < 0 || index >= this.size;
    }
}
