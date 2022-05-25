import java.util.*;

public class HashTable<K, V> implements Iterable<KeyValue<K, V>> {
    private static final int INITIAL_CAPACITY = 16;
    private static final double LOAD_FACTOR = 0.80d;

    private LinkedList<KeyValue<K, V>>[] slots;
    private int count;
    private int capacity;


    public HashTable() {
        this(INITIAL_CAPACITY);
    }

    public HashTable(int capacity) {
        this.slots = new LinkedList[capacity];
        this.count = 0;
        this.capacity = capacity;
    }

    public void add(K key, V value) {
        this.growIfNeeded();
        int slotNumber = this.findSlotNumber(key);

        if (this.slots[slotNumber] == null) {
            this.slots[slotNumber] = new LinkedList<>();
        }

        for (KeyValue<K, V> element : slots[slotNumber]) {
            if (element.getKey().equals(key)) {
                throw new IllegalArgumentException();
            }
        }

        KeyValue<K, V> kvp = new KeyValue<>(key, value);
        this.slots[slotNumber].addLast(kvp);
        this.count++;

    }

    private int findSlotNumber(K key) {
        return Math.abs(key.hashCode()) % this.slots.length;
    }

    private void growIfNeeded() {
        if ((double) (this.size() + 1) / this.capacity() > LOAD_FACTOR) {
            this.grow();
        }
    }

    private void grow() {

        HashTable<K, V> newHashTable = new HashTable<>(this.capacity * 2);

        for (LinkedList<KeyValue<K, V>> slot : this.slots) {
            if (slot != null) {
                for (KeyValue<K, V> kvKeyValue : slot) {
                    newHashTable.add(kvKeyValue.getKey(), kvKeyValue.getValue());
                }

            }
        }
        this.slots = newHashTable.slots;
        this.capacity *= 2;
    }

    public int size() {
        return this.count;
    }

    public int capacity() {
        return this.capacity;

    }

    public boolean addOrReplace(K key, V value) {
        this.growIfNeeded();

        int index = this.findSlotNumber(key);

        LinkedList<KeyValue<K, V>> slot = this.slots[index];

        if (slot == null) {
            slot = new LinkedList<>();
        }

        boolean updated = false;
        for (KeyValue<K, V> kvp : slot) {
            if (kvp.getKey().equals(key)) {
                kvp.setValue(value);
                updated = true;
            }
        }

        if (!updated) {
            KeyValue<K, V> newKeyValue = new KeyValue<>(key, value);
            slot.addLast(newKeyValue);

            this.count++;
        }
        this.slots[index] = slot;
        return updated;
    }

    public V get(K key) {
        KeyValue<K, V> elemtn = this.find(key);
        if (elemtn == null) {
            throw new IllegalArgumentException();
        }
        return elemtn.getValue();
    }

    public KeyValue<K, V> find(K key) {
        int slotNumber = this.findSlotNumber(key);
        LinkedList<KeyValue<K, V>> elements = this.slots[slotNumber];
        if (elements != null) {
            for (KeyValue<K, V> element : elements) {
                if (element.getKey().equals(key)) {
                    return element;
                }
            }
        }
        return null;
    }

    public boolean containsKey(K key) {
        return this.find(key) != null;
    }

    public boolean remove(K key) {
        int slotNumber = this.findSlotNumber(key);

        LinkedList<KeyValue<K, V>> slot = this.slots[slotNumber];
        if (slot == null) {
            return false;
        }

        KeyValue<K, V> toRemove = null;

        for (KeyValue<K, V> kvp : slot) {
            if (kvp.getKey().equals(key)) {
                toRemove = kvp;
                break;
            }
        }

        boolean res = toRemove != null && slot.remove(toRemove);
        if (res) {
            this.count--;
        }
        return res;
    }

    public void clear() {
        this.capacity = INITIAL_CAPACITY;
        this.slots = new LinkedList[this.capacity];
        this.count = 0;
    }

    public Iterable<K> keys() {
        List<K> result = new ArrayList<>();
        for (KeyValue<K, V> kvKeyValue : this) {
            result.add(kvKeyValue.getKey());
        }
        return result;
    }

    public Iterable<V> values() {
        List<V> result = new ArrayList<>();
        for (KeyValue<K, V> kvKeyValue : this) {
            result.add(kvKeyValue.getValue());
        }
        return result;
    }

    @Override
    public Iterator<KeyValue<K, V>> iterator() {
        return new HashIterator();
    }

    private class HashIterator implements Iterator<KeyValue<K, V>> {

        Deque<KeyValue<K, V>> elements;

        HashIterator() {
            this.elements = new ArrayDeque<>();
            for (
                    LinkedList<KeyValue<K, V>> slot : slots) {
                if (slot != null) {
                    elements.addAll(slot);
                }
            }
        }

        @Override
        public boolean hasNext() {
            return !elements.isEmpty();
        }


        @Override
        public KeyValue<K, V> next() {
            if (!hasNext()) {
                throw new IllegalStateException();
            }
            return elements.poll();
        }
    }


}
