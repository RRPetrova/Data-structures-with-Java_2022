package implementations;

import interfaces.AbstractQueue;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PriorityQueue<E extends Comparable<E>> implements AbstractQueue<E> {

    private List<E> elements;

    public PriorityQueue() {
        this.elements = new ArrayList<>();
    }


    @Override
    public int size() {
        return this.elements.size();
    }

    @Override
    public void add(E element) {
        this.elements.add(element);
        this.heapifyUp(this.size() - 1);
    }

    private void heapifyUp(int index) {

        //parent index < child index
        while (index > 0 && this.elements.get(index).compareTo(this.elements.get(getParentIndex(index))) > 0) {
            Collections.swap(this.elements, getParentIndex(index), index);
            index = getParentIndex(index);
        }

    }
    private int getParentIndex(int index) {
        return (index -1 ) / 2;
    }
    @Override
    public E peek() {
        if (this.size() == 0) {
            throw new IllegalStateException("Empty queue");
        }
        return this.elements.get(0);
    }

    @Override
    public E poll() {
        if (this.size() == 0) {
            throw new IllegalStateException("Empty queue");
        }
        E lastElement = this.elements.get(0);
        Collections.swap(this.elements, 0, this.size() - 1);
        this.elements.remove(this.size() - 1);

        this.heapifyDown(0);

        return lastElement;
    }

    private void heapifyDown(int index) { // 0
        while (this.getLeftChildIndex(index) < this.size() &&
                this.elements.get(index).compareTo(this.getLeftChildElement(index)) < 0) {
            int currentChildIndex = this.getLeftChildIndex(index); // 1 && 16<25 true (16-25 < 0)
            int rightChildIndex = this.getRightChildIndex(index); //2
            if (rightChildIndex < this.size() && //true && (25 - 6 > 0 => false
                    // => not going right, staying on the left side)
                    this.elements.get(currentChildIndex).compareTo(this.getRightChildElement(index)) < 0) {
                currentChildIndex = rightChildIndex; // going right
            }

            Collections.swap(this.elements, index, currentChildIndex);
            index = currentChildIndex;
        }

    }

    private int getLeftChildIndex(int index) {
        return index * 2 + 1;
    }

    private E getLeftChildElement(int index) {
        return this.elements.get(this.getLeftChildIndex(index));
    }

    private int getRightChildIndex(int index) {
        return index * 2 + 2;
    }

    private E getRightChildElement(int index) {
        return this.elements.get(this.getRightChildIndex(index));
    }
}
