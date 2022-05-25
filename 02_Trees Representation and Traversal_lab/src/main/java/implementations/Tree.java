package implementations;

import interfaces.AbstractTree;

import java.util.*;

public class Tree<E> implements AbstractTree<E> {

    private E value;
    private Tree<E> parent;
    private List<Tree<E>> children;

//    public Tree(E value) {
//        this.value = value;
//        this.parent = null;
//        this.children = new ArrayList<>();
//    }

    public Tree(E value, Tree<E>... subtrees) {
        this.value = value;
        this.parent = null;
        this.children = new ArrayList<>();

        for (Tree<E> subtree : subtrees) {
            this.children.add(subtree);
            subtree.parent = this;
        }
        // instead of foreach
        // this.children.addAll(Arrays.asList(subtrees));
    }

    @Override
    public List<E> orderBfs() {
        List<E> result = new ArrayList<>();
        if (this.value == null) {
            return result;
        }

        ArrayDeque<Tree<E>> queue = new ArrayDeque<>();
        queue.add(this);

        while (!queue.isEmpty()) {
            Tree<E> curr = queue.poll();
            result.add(curr.value);

            for (Tree<E> child : curr.children) {
                queue.offer(child);
            }
        }
        return result;
    }

    @Override
    public List<E> orderDfs() {
        List<E> result = new ArrayList<>();

        this.doDfs(this, result);


        return result;
    }


    private void doDfs(Tree<E> node, List<E> result) {
        for (Tree<E> child : node.children) {
            this.doDfs(child, result);
        }
        result.add(node.value);
    }

    @Override
    public void addChild(E parentKey, Tree<E> child) {

        Tree<E> searchedNode = searchForNode(parentKey);
        if (searchedNode == null) {
            throw new IllegalArgumentException("not found");
        }
        searchedNode.children.add(child);
        child.parent = searchedNode;

    }

    private Tree<E> searchForNode(E parentKey) {
        ArrayDeque<Tree<E>> queue = new ArrayDeque<>();
        queue.add(this);

        while (!queue.isEmpty()) {
            Tree<E> curr = queue.poll();
            System.out.println(curr.value);
            if (curr.value.equals(parentKey)) {
                return curr;
            }
            for (Tree<E> eTree : curr.children) {
                System.out.println("ins: " + eTree.value);
                queue.offer(eTree);
            }
        }

        return null;
    }

    @Override
    public void removeNode(E nodeKey) {
        Tree<E> searchedNode = searchForNode(nodeKey);
        if (searchedNode == null) {
            throw new IllegalArgumentException("not found");
        }

        for (Tree<E> child : searchedNode.children) {
            child.parent = null;
        }

        searchedNode.children.clear();
        Tree<E> parent = searchedNode.parent;
        if (parent != null) {
            parent.children.remove(searchedNode);
        }
        searchedNode.value = null;
    }

    @Override
    public void swap(E firstKey, E secondKey) {
        Tree<E> node1 = searchForNode(firstKey);
        Tree<E> node2 = searchForNode(secondKey);

        if (node1 == null || node2 == null) {
            throw new IllegalArgumentException("ddfs");
        }

        Tree<E> parent1 = node1.parent;
        Tree<E> parent2 = node2.parent;

        if (parent1 == null) {
            this.value = node2.value;
            this.parent = null;
            //TODO: parent2.parent = null;
            this.children = node2.children;
            return;
        } else if (parent2 == null) {
            this.value = node1.value;
            this.parent = null;
            //TODO: parent1.parent = null;
            this.children = node1.children;
            return;
        }

        node1.parent = parent2;
        node2.parent = parent1;

        int index1 = parent1.children.indexOf(node1);
        int index2 = parent2.children.indexOf(node2);

        parent1.children.set(index1, node2);
        parent2.children.set(index2, node1);


    }
}



