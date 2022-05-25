package core;

import model.Task;
import shared.Scheduler;

import java.util.*;

public class ProcessScheduler implements Scheduler {

    private static final int INITIAL_SIZE = 4;
    private Task[] tasks;
    private int size;
    private int capacity;

    public ProcessScheduler() {
        this.tasks = new Task[INITIAL_SIZE];
        this.size = 0;
        this.capacity = INITIAL_SIZE;
    }

    @Override
    public void add(Task task) {
        if (this.size == this.capacity) {
            resizeList();
        }
        this.tasks[this.size++] = task;
    }

    private void resizeList() {
        this.capacity *= 2;
        Task[] arrayListNew = new Task[this.capacity];

        for (int i = 0; i < this.tasks.length; i++) {
            arrayListNew[i] = this.tasks[i];
        }
        this.tasks = arrayListNew;
    }

    @Override
    public Task process() {
        if (this.tasks.length == 0) {
            return null;
        }
        Task elementAboutToBeRemoved = this.tasks[0];
        removeTaskReduceSize(0);
        return elementAboutToBeRemoved;
    }

    @Override
    public Task peek() {
        if (size() == 0) {
            return null;
        }
        return this.tasks[0];
    }

    @Override
    public Boolean contains(Task task) {
        for (Task task1 : this.tasks) {
            if (task.equals(task1)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public Boolean remove(Task task) {
        if (size() == 0) {
            throw new IllegalArgumentException("empty");
        }
        int indexToRemove = getIndexByTaskObj(task);
        if (indexToRemove == -1) {
            throw new IllegalArgumentException();
        }
        removeTaskReduceSize(indexToRemove);
        return true;
    }

    private int getIndexByTaskObj(Task task) {
        int indexToRemove = -1;
        for (int i = 0; i < tasks.length; i++) {
            if (task.equals(tasks[i])) {
                indexToRemove = i;
            }
        }
        return indexToRemove;
    }

    @Override
    public Boolean remove(int id) {
        if (size() == 0) {
            throw new IllegalArgumentException("empty");
        }
        int indexToRemove = findIndexById(id);
        if (indexToRemove == -1) {
            throw new IllegalArgumentException();
        }
        removeTaskReduceSize(indexToRemove);
        return true;
    }

    private void removeTaskReduceSize(int indexToRemove) {
        for (int i = indexToRemove; i < tasks.length - 1; i++) {
            this.tasks[i] = tasks[i + 1];
        }
        this.size--;
    }

    @Override
    public void insertBefore(int id, Task task) {
        int insertBeforeIndex = findIndexById(id);
        if (insertBeforeIndex == -1) {
            throw new IllegalArgumentException();
        }
        if (this.tasks.length == this.capacity || this.tasks.length + 1 == this.capacity) {
            resizeList();
        }
        // 5 8 10 12 15 18  -ins before 2
        // 5 8 -- 10 12 15 18
        for (int i = this.size; i > insertBeforeIndex; i--) {
            this.tasks[i] = this.tasks[i - 1];

        }
        this.tasks[insertBeforeIndex] = task;
        this.size++;

    }

    private int findIndexById(int id) {
        int insertBeforeIndex = -1;
        for (int i = 0; i < tasks.length; i++) {
            if (id == tasks[i].getId()) {
                insertBeforeIndex = i;
                break;
            }
        }
        return insertBeforeIndex;
    }

    @Override
    public void insertAfter(int id, Task task) {
        int insertAterIndex = findIndexById(id);
        if (insertAterIndex == -1) {
            throw new IllegalArgumentException();
        }
        if (this.tasks.length == this.capacity || this.tasks.length + 1 == this.capacity) {
            resizeList();
        }
        // 5 8 10 12 15 18  -ins after 2
        // 5 8 10 ... 12 15 18
        for (int i = this.size; i > insertAterIndex + 1; i--) {
            this.tasks[i] = this.tasks[i - 1];
        }
        this.tasks[insertAterIndex + 1] = task;
        this.size++;
    }

    @Override
    public void clear() {
        this.tasks = new Task[this.capacity];
        this.size = 0;
    }

    @Override
    public Task[] toArray() {
        Task[] res = new Task[this.size];
        for (int i = 0; i < this.size; i++) { //to this.size !!! judge
            res[i] =  this.tasks[i];
        }
        return res;
    }

    @Override
    public void reschedule(Task first, Task second) {
        int firstInd = getIndexByTaskObj(first);
        int secInd = getIndexByTaskObj(second);

        if (firstInd < 0 || firstInd >= this.size || secInd < 0 || secInd >= this.size) {
            throw new IllegalArgumentException();
        }
        this.tasks[firstInd] = second;
        this.tasks[secInd] = first;
    }

    @Override
    public List<Task> toList() {
        return Arrays.asList(this.toArray());
    }

    @Override
    public void reverse() {
        for (int i = 0; i < this.size / 2; i++) {
            Task tmp = this.tasks[i];
            this.tasks[i] = this.tasks[this.size - i - 1];
            this.tasks[this.size - i - 1] = tmp;
        }
    }

    @Override
    public Task find(int id) {
        return this.tasks[findIndexById(id)];
    }

    @Override
    public Task find(Task task) {
        return this.tasks[getIndexByTaskObj(task)];
    }

    private boolean invalidIndex(int index) {
        return index < 0 || index >= this.size;
    }
}
