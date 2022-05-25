package solutions;

import interfaces.Decrease;
import interfaces.Heap;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MinHeap<E extends Comparable<E> & Decrease<E>> implements Heap<E> {

    private List<E> list;

    public MinHeap() {
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
        //RULE : parent ind <= child ind

        while (index > 0 && this.list.get(getParentIndex(index)).compareTo(this.list.get(index)) > 0) {
            Collections.swap(this.list, getParentIndex(index), index);
            index = getParentIndex(index);
        }
    }


    private int getParentIndex(int index) {
        return (index - 1) / 2;
    }

    @Override
    public E peek() {
        if (this.list.size() == 0) {
            throw new IllegalStateException();
        }
        return this.list.get(0);
    }

    @Override
    public E poll() {
        if (this.size() == 0) {
            throw new IllegalStateException("Empty");
        }
        E lastElement = this.list.get(0);
        Collections.swap(this.list, 0, this.size() - 1);
        this.list.remove(this.size() - 1);

        this.heapifyDown(0);

        return lastElement;
    }

    private void heapifyDown(int index) { // 0
        int currentChildIndex = this.getLeftChildIndex(index);

        while (this.getLeftChildIndex(index) < this.size()) {
            int rightChildIndex = this.getRightChildIndex(index);

            if (rightChildIndex < this.size()) {
                if (this.list.get(currentChildIndex).compareTo(this.getRightChildElement(index)) > 0) {
                    currentChildIndex = rightChildIndex; // going right
                }
            }
            if (this.list.get(index).compareTo(this.list.get(currentChildIndex)) < 0) {
                break;
            }
            Collections.swap(this.list, index, currentChildIndex);
            index =this.getLeftChildIndex(index);
        }
    }




    private int getLeftChildIndex(int index) {
        return index * 2 + 1;
    }

    private E getLeftChildElement(int index) {
        return this.list.get(this.getLeftChildIndex(index));
    }

    private int getRightChildIndex(int index) {
        return index * 2 + 2;
    }

    private E getRightChildElement(int index) {
        return this.list.get(this.getRightChildIndex(index));
    }

    @Override
    public void decrease(E element) {
        int i = this.list.indexOf(element);
        E heap = this.list.get(i);
        heap.decrease();

        this.heapifyUp(i);


//        this.list.remove(element);
//        element.decrease();
//        this.add(element);
    }
}
