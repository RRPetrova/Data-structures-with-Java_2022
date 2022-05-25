package main;

import java.util.*;
import java.util.stream.Collectors;

public class Hierarchy<T> implements IHierarchy<T> {

    private Map<T, HierarchyNode<T>> data;
private HierarchyNode<T> root;

    public Hierarchy(T element) {
        this.data = new HashMap<>();
        HierarchyNode<T> root = new HierarchyNode<>(element);
        this.root = root;
        this.data.put(element, root);
    }


    @Override
    public int getCount() {
        return this.data.size();
    }

    @Override
    public void add(T element, T child) {
        if (!this.data.containsKey(element)) {
            throw new IllegalArgumentException();
        }
        if (this.data.containsKey(child)) {
            throw new IllegalArgumentException();
        }
        HierarchyNode<T> parent = this.data.get(element);
        HierarchyNode<T> newChildren = new HierarchyNode<>(child);

        parent.getChildren().add(newChildren);
        newChildren.setParent(parent);
        this.data.put(child, newChildren);
        this.data.put(parent.getValue(), parent);
    }

    @Override
    public void remove(T element) {
        HierarchyNode<T> elementToRemove = this.data.get(element);
        if (elementToRemove == null) {
            throw new IllegalArgumentException();
        }
        HierarchyNode<T> elementToRemoveParent = elementToRemove.getParent();
        if (elementToRemoveParent == null) {
            throw new IllegalStateException();
        }

        //TODO
        List<HierarchyNode<T>> elementToRemoveChildren = elementToRemove.getChildren();
       // elementToRemoveParent.setChildren(elementToRemoveChildren);
        for (HierarchyNode<T> elementToRemoveChild : elementToRemoveChildren) {
            elementToRemoveChild.setParent(elementToRemoveParent);
        }

        elementToRemoveParent.getChildren().addAll(elementToRemoveChildren);

        elementToRemoveParent.getChildren().remove(elementToRemove);
        this.data.remove(elementToRemove.getValue());

    }

    @Override
    public Iterable<T> getChildren(T element) {
        HierarchyNode<T> elementSearched = this.data.get(element);
        if (elementSearched != null) {
            return elementSearched.getChildren()
                    .stream()
                    .map(HierarchyNode::getValue)
                    .collect(Collectors.toList());
        }
        return null;
    }

    @Override
    public T getParent(T element) {
        HierarchyNode<T> currElement = this.data.get(element);
        if (currElement == null) {
            throw new IllegalArgumentException();
        }
        return currElement.getParent() != null ? currElement.getParent().getValue() : null;


    }

    @Override
    public boolean contains(T element) {
        return this.data.containsKey(element);
    }

    @Override
    public Iterable<T> getCommonElements(IHierarchy<T> other) {
        List<T> common = new ArrayList<>();
        this.data
                .keySet()
                .forEach(e-> {
                    if (other.contains(e)) {
                        common.add(e);
                    }
                });
        return common;
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            Deque<HierarchyNode<T>> deque = new ArrayDeque<>(Collections.singletonList(root));


            @Override
            public boolean hasNext() {
                return deque.size() > 0;
            }

            @Override
            public T next() {
                HierarchyNode<T> nextEl = deque.poll();
                deque.addAll(nextEl.getChildren());
                return nextEl.getValue();
            }
        };
    }
}
