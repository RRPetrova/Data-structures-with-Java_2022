package implementations;

import java.util.LinkedHashMap;
import java.util.Map;

public class TreeFactory {
    private Map<Integer, Tree<Integer>> nodesByKeys;

    public TreeFactory() {
        this.nodesByKeys = new LinkedHashMap<>();
    }

    public Tree<Integer> createTreeFromStrings(String[] input) {
        for (String s : input) {
            //5 4
            String[] pair = s.split("\\s+");
            int par = Integer.parseInt(pair[0]);
            int ch = Integer.parseInt(pair[1]);
            addEdge(par, ch);
        }

        return getRoot();
    }

    private Tree<Integer> getRoot() {
        for (Tree<Integer> value : this.nodesByKeys.values()) {
            if (value.getParent() == null) {
                return value;
            }
        }
        return null;
    }

    public Tree<Integer> createNodeByKey(int key) {
        this.nodesByKeys.putIfAbsent(key, new Tree<>(key));
        return this.nodesByKeys.get(key);
    }

    public void addEdge(int parent, int child) {
        Tree<Integer> parentTree = this.createNodeByKey(parent);
        Tree<Integer> childTree = this.createNodeByKey(child);

        parentTree.addChild(childTree);
        childTree.setParent(parentTree);
    }
}



