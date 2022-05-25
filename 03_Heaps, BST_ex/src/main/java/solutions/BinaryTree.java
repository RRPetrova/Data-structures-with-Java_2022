package solutions;

import java.util.ArrayList;
import java.util.List;

public class BinaryTree {

    private int key;
    private BinaryTree first;
    private BinaryTree second;


    public BinaryTree(int key, BinaryTree first, BinaryTree second) {
        this.key = key;
        this.first = first;
        this.second = second;
    }

    public Integer findLowestCommonAncestor(int first, int second) {
      List<Integer> firstPath = findPath(first);
      List<Integer> secPath = findPath(second);

      int smallerPath = Math.min(firstPath.size(), secPath.size());


      int commonIndex = -1;
        for (int i = 0; i < smallerPath; i++) {
            commonIndex = i;
            if (!firstPath.get(i).equals(secPath.get(i))) {
                break;
            }
        }
        return firstPath.get(commonIndex);
    }


    private List<Integer> findPath(int element) {
        List<Integer>  res = new ArrayList<>();
traverse(this, res, element);

        return res;
    }


    private boolean traverse(BinaryTree tree, List<Integer> result, int element) {

       if (tree == null ){
           return  false;
       }
        if(tree.key == element) {
            return true;
        }
       result.add(tree.key);
        boolean traverse = traverse(tree.first, result, element);
        if (traverse) {
            return true;
        }
        boolean traverse1 = traverse(tree.second, result, element);
        if (traverse1) {
            return true;
        }

        result.remove(Integer.valueOf(tree.key));
        return false;
    }

    public List<Integer> topView() {
        return null;
    }
}
