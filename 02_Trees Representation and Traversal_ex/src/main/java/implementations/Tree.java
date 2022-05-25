package implementations;

import interfaces.AbstractTree;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Tree<E> implements AbstractTree<E> {

    private E value;
    private Tree<E> parent;
    private List<Tree<E>> children;


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
    public void setParent(Tree<E> parent) {
        this.parent = parent;
    }

    @Override
    public void addChild(Tree<E> child) {
        this.children.add(child);
    }

    @Override
    public Tree<E> getParent() {
        return this.parent;
    }

    @Override
    public E getKey() {
        return this.value;
    }

    @Override
    public String getAsString() {
        StringBuilder sb = new StringBuilder();
        traverseTree(sb, 0, this);

        return sb.toString().trim();
    }

    public String traverseTreeBFS() {
        StringBuilder sb = new StringBuilder();
        ArrayDeque<Tree<E>> queue = new ArrayDeque<>();
        queue.offer(this);

        int space = 0;
        while (!queue.isEmpty()) {
            Tree<E> poll = queue.poll();

            if (poll.getParent() != null &&
                    poll.getParent().getKey().equals(this.getKey())) {
                space = 2;
            } else if (poll.children.isEmpty()) {
                space = 4;
            }

            for (int i = 0; i < space; i++) {
                sb.append(" ");
            }

            sb.append(poll.getKey())
                    .append(System.lineSeparator());

            for (Tree<E> child : poll.children) {
                queue.offer(child);
            }
        }
        return sb.toString().trim();
    }

    private void traverseTreeAllNodes(Tree<E> tree, List<Tree<E>> treeList) {
        treeList.add(tree);
        for (Tree<E> child : tree.children) {
            traverseTreeAllNodes(child, treeList);
        }
    }


    private void traverseTree(StringBuilder sb, int numSpace, Tree<E> tree) {
        for (int i = 0; i < numSpace; i++) {
            sb.append(" ");
        }
        sb.append(tree.getKey())
                .append(System.lineSeparator());

        for (Tree<E> child : tree.children) {
            traverseTree(sb, numSpace + 2, child);
        }
    }

    @Override
    public List<E> getLeafKeys() {
        HashSet<E> hashSet = new HashSet<>();

        ArrayDeque<Tree<E>> queue = new ArrayDeque<>();
        queue.offer(this);

        while (!queue.isEmpty()) {
            Tree<E> poll = queue.poll();
            if (poll.children.isEmpty()) {
                hashSet.add(poll.getKey());
            }
            for (Tree<E> child : poll.children) {
                queue.offer(child);
                if (child.children.isEmpty()) {
                    hashSet.add(child.getKey());
                }
            }
        }
        return new ArrayList<>(hashSet);
    }

    @Override
    public List<E> getMiddleKeys() {
        HashSet<E> hashSet = new HashSet<>();

        ArrayDeque<Tree<E>> queue = new ArrayDeque<>();
        queue.offer(this);

        while (!queue.isEmpty()) {
            Tree<E> poll = queue.poll();
            if (!poll.children.isEmpty() && poll.getParent() != null) {
                hashSet.add(poll.getKey());
            }
            for (Tree<E> child : poll.children) {
                queue.offer(child);
                if (!child.children.isEmpty() && child.getParent() != null) {
                    hashSet.add(child.getKey());
                }
            }
        }
        return new ArrayList<>(hashSet);
    }

    private void doDfs(Tree<E> node, List<Tree<E>> result) {
        for (Tree<E> child : node.children) {
            this.doDfs(child, result);
        }
        result.add(node);
    }


    @Override
    public Tree<E> getDeepestLeftmostNode() {
        List<Tree<E>> listOfTrees = new ArrayList<>();

        this.doDfs(this, listOfTrees);

        List<Tree<E>> leaveNodes = listOfTrees.stream()
                .filter(t -> t.children.isEmpty())
                .collect(Collectors.toList());

        int deepest = 0;
        Tree<E> deepestNode = null;
        for (Tree<E> currLeaveNode : leaveNodes) {
            int currLevel = 0;
            Tree<E> originalLeave = currLeaveNode;
            while (currLeaveNode.getParent() != null) { //1 parent 7
                if (++currLevel > deepest) { //1 >0
                    deepest = currLevel;
                    deepestNode = originalLeave;
                }
                currLeaveNode = currLeaveNode.parent;
            }
        }


        return deepestNode;
    }

    @Override
    public List<E> getLongestPath() {
        List<Tree<E>> listOfTrees = new ArrayList<>();

        this.doDfs(this, listOfTrees);

        List<Tree<E>> longestPath = new ArrayList<>();

        for (Tree<E> curNode : listOfTrees) {
            List<Tree<E>> currPath = new ArrayList<>();
            currPath.add(curNode);

            while (curNode.getParent() != null) {
                currPath.add(curNode.getParent());
                curNode = curNode.parent;
            }

            if (currPath.size() > longestPath.size()) {
                longestPath = currPath;
            }
        }

        List<E> keysLongestPath = new ArrayList<>();
        for (int i = longestPath.size() - 1; i >= 0; i--) {
            keysLongestPath.add(longestPath.get(i).getKey());
        }


        return keysLongestPath;
    }

    @Override
    public List<List<E>> pathsWithGivenSum(int sum) {

        List<Tree<E>> listOfTrees = new ArrayList<>();

        this.doDfs(this, listOfTrees);

        List<List<E>> pathWithWantedSum = new ArrayList<>();

        for (Tree<E> curNode : listOfTrees) {
            //от долу - нагоре = bottom -top approach
            List<Tree<E>> currPath = new ArrayList<>();
            currPath.add(curNode);
            int currSum = (int) curNode.getKey();

            while (curNode.getParent() != null) {
                currPath.add(curNode.getParent());
                currSum += (int) curNode.getParent().getKey();

                curNode = curNode.parent;
            }

            if (currSum == sum) {
                List<E> currKeysWithWantedSum = new ArrayList<>();
                for (int i = currPath.size() - 1; i >= 0; i--) {
                    currKeysWithWantedSum.add(currPath.get(i).getKey());
                }
                pathWithWantedSum.add(currKeysWithWantedSum);
            }
        }


        return pathWithWantedSum;
    }

    @Override
    public List<Tree<E>> subTreesWithGivenSum(int sum) {
        List<Tree<E>> res = new ArrayList<>();
        this.doDfsSum(this);
        Deque<Tree> deque = new ArrayDeque<>();
        deque.addAll(this.children);

      while (!deque.isEmpty()){
          Tree<E> next = deque.poll();
            int chSum = doDfsSum(next);

            if (chSum == sum) {
                res.add(next);
            }
            deque.addAll(next.children);
        }


        return res;
    }

    private int doDfsSum(Tree<E> node) {
        int subtreeSum = 0;

        for (Tree<E> child : node.children) {
            subtreeSum += doDfsSum(child);
        }
        return subtreeSum + (int)node.getKey();
    }
}



