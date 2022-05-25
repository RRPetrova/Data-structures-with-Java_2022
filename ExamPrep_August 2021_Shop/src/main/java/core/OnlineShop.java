package core;

import model.Order;
import shared.Shop;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class OnlineShop implements Shop {

    private int INITIAL_CAPACITY = 2;
    private Object[] orders;
    private int size;
    private int capacity;

    public OnlineShop() {
        this.orders = new Object[INITIAL_CAPACITY];
        this.size = 0;
        this.capacity = INITIAL_CAPACITY;
    }

    @Override
    public void add(Order order) {
        if (this.size == this.capacity) {
            resizeList();
        }
        this.orders[this.size++] = order;

    }

    @Override
    public Order get(int index) {
        throwExIfInvalidIndex(index);//TODO: without this line ?
        return (Order) this.orders[index];
    }

    @Override
    public int indexOf(Order order) {
        return indexOfOrderById(order.getId());
    }

    private int indexOfOrderById(int id) {
        for (int i = 0; i < this.size; i++) {
            if (id == this.get(i).getId()) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public Boolean contains(Order order) {
        return this.indexOf(order) >= 0;
    }

    @Override
    public Boolean remove(Order order) {
        int indexOfOrder = this.indexOf(order);
        return removeOrderByIndex(indexOfOrder);
    }

    private Boolean removeOrderByIndex(int indexOfOrder) {
        if (indexOfOrder == -1) {
            return false;
        }
        for (int i = indexOfOrder; i < this.size; i++) {
            this.orders[i] = orders[i + 1];
        }
        this.size--;
        return true;
    }

    @Override
    public Boolean remove(int id) {
     int indexOfOrder = indexOfOrderById(id);
        return removeOrderByIndex(indexOfOrder);
    }

    @Override
    public void set(int index, Order order) {
     throwExIfInvalidIndex(index);
        this.orders[index] = order;
    }

    @Override
    public void set(Order oldOrder, Order newOrder) {
        int indexOfOldOrder = this.indexOf(oldOrder);
       throwExIfInvalidIndex(indexOfOldOrder);
        this.orders[indexOfOldOrder] = newOrder;
    }

    @Override
    public void clear() {
        //  Arrays.asList(this.orders).clear();
        this.orders = new Object[this.capacity]; //capacity
        this.size = 0;
    }

    @Override
    public Order[] toArray() {
        Order[] res = new Order[this.size];
        for (int i = 0; i < this.size; i++) { //to this.size !!! judge
            res[i] = (Order) this.orders[i];
        }
        return res;
    }

    @Override
    public void swap(Order first, Order second) {
        int firstInd =  this.indexOf(first);
        int secInd = this.indexOf(second);

        if (firstInd < 0 || firstInd >= this.size || secInd <  0|| secInd >= this.size) {
            throw new IllegalArgumentException();
        }
        this.orders[firstInd] = second;
        this.orders[secInd] = first;
    }

    @Override
    public List<Order> toList() {
        List<Order> res = new ArrayList<>();
        for (int i = 0; i < this.size; i++) {
            res.add((Order) orders[i]);
        }
        return res;
     // or ->   return Arrays.asList(this.toArray());
    }

    @Override
    public void reverse() {
//        Object[] res = new Object[this.size];
//        int ind = 0;
//        for (int i = this.size-1; i >= 0; i--) {
//            res[ind++] = this.orders[i];
//        }
//        this.orders = res;

        for (int i = 0; i < this.size / 2; i++) {
            Object tmp = this.orders[i];
            this.orders[i] = this.orders[this.size - i - 1];
            this.orders[this.size - i -1] = tmp;
        }
    }

    @Override
    public void insert(int index, Order order) {
        throwExIfInvalidIndex(index); // TODO: ima test za tova !!!!!!!!!!!!!!
        if (this.size  == this.capacity) {
            resizeList();
        }
        for (int i = this.size  ; i > index; i--) {
            this.orders[i] = this.orders[i-1];
        }
        this.size++;
        this.orders[index] = order;
    }

    @Override
    public Boolean isEmpty() {
        return this.size() == 0;
    }

    @Override
    public int size() {
        return this.size;
    }

    private void resizeList() {
        this.capacity *= 2;
        Object[] ordersNew = new Object[this.capacity];

        for (int i = 0; i < this.size; i++) {
            ordersNew[i] = this.orders[i];
        }
        this.orders = ordersNew;
    }

    private void shrinkList() {
        this.capacity /= 2;
        this.orders = Arrays.copyOf(this.orders, this.capacity);
    }

    private void throwExIfInvalidIndex(int index) {
       if (index < 0 || index >= this.size) {
           throw new IndexOutOfBoundsException();
       }
    }
}
