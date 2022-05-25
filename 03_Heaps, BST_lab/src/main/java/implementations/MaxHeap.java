package implementations;

import interfaces.Heap;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MaxHeap<E extends Comparable<E>> implements Heap<E> {

    private List<E> list;

    public MaxHeap() {
        this.list = new ArrayList<>();
    }


    @Override
    public int size() {
        return this.list.size();
    }

    @Override
    public void add(E element) {
        this.list.add(element);
        this.heapifyUp(this.size() - 1);
    }

    private void heapifyUp(int index) {

        //parent index <= child index
        while (index > 0 && this.list.get(index).compareTo(this.list.get(getParentIndex(index))) > 0) {
            Collections.swap(this.list, getParentIndex(index), index);
            index = getParentIndex(index);
        }

    }

    private int getParentIndex(int index) {
        return (index -1 ) / 2;
    }

    @Override
    public E peek() {
        if (this.list.size() == 0) {
            throw new IllegalStateException("Empty collection");
        }
        return this.list.get(0);
    }
}
