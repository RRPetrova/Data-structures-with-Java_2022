public class TwoThreeTree<K extends Comparable<K>> {
    private TreeNode<K> root;

    public static class TreeNode<K> {
        private K leftKey;
        private K rightKey;

        private TreeNode<K> leftChild;
        private TreeNode<K> middleChild;
        private TreeNode<K> rightChild;

        private TreeNode(K key) {
            this.leftKey = key;
        }

        public TreeNode(K root, K left, K right) {
            this(root);
            this.leftChild = new TreeNode<>(left);
            this.rightChild = new TreeNode<>(right);
        }

        public TreeNode(K root, TreeNode<K> left, TreeNode<K> right) {
            this.leftKey = root;
            this.leftChild = left;
            this.rightChild = right;
        }

        boolean isThreeNode() {
            return rightKey != null;
        }

        boolean isTwoNode() {
            return rightKey == null;
        }

        boolean isLeaf() {
            return this.leftChild == null && this.middleChild == null && this.rightChild == null;
        }
    }

    public void insert(K key) {
        if (this.root == null) {
            this.root = new TreeNode<>(key);
            return;
        }

        TreeNode<K> inserted = this.insertIntoTree(this.root, key);
        if (inserted != null) {
            this.root = inserted;
        }
    }

    private TreeNode<K> insertIntoTree(TreeNode<K> node, K key) {
        if (node == null) {
            return new TreeNode<>(key);
        }

        if (node.isLeaf()) {
            //case 2-node => add value to correct key- има място в нода и вляво или вдясно слагаме ключът
            if (node.isTwoNode()) {
                if (node.leftKey.compareTo(key) < 0) {
                    node.rightKey = key;
                } else {
                    node.rightKey = node.leftKey;
                    node.leftKey = key;
                }
                return null;
            }
            //3-node => split and return

            //when node.left < key < node.right
            K left = node.leftKey;
            K middle = key;
            K right = node.rightKey;

            // key < node.left < node.right
            if (key.compareTo(node.leftKey) < 0) {
                left = key;
                middle = node.leftKey;
                //node.left < node.right < key
            } else if (key.compareTo(node.rightKey) > 0) {
                middle = node.rightKey;
                right = key;
            }
            //middle is the root // цепим и вдигаме височината
            return new TreeNode<>(middle, left, right);
        }


        //fix tree - if a node down the tree is divided into another node fix what happens with uppper tree
        //fix tree is the node returned when division is made, we have to find a place, and rearrange the tree
        TreeNode<K> insertionResultReturned = null;
        //key < leftkey
        if (node.leftKey.compareTo(key) > 0) {
            insertionResultReturned = insertIntoTree(node.leftChild, key);
        } else if (node.isTwoNode() && node.leftKey.compareTo(key) < 0) {
            insertionResultReturned = insertIntoTree(node.rightChild, key);
        } else if (node.isThreeNode() && node.rightKey.compareTo(key) < 0) {
            insertionResultReturned = insertIntoTree(node.rightChild, key);
        } else {
            insertionResultReturned = insertIntoTree(node.middleChild, key);
        }
        if (insertionResultReturned == null) {
            return null;
        }

        //case 2-node => add value to correct key
        if (node.isTwoNode()) {
            if (insertionResultReturned.leftKey.compareTo(node.leftKey) < 0) {
                node.rightKey = node.leftKey;
                node.leftKey = insertionResultReturned.leftKey;

                node.leftChild = insertionResultReturned.leftChild;
                node.middleChild = insertionResultReturned.rightChild;
            } else {
                node.rightKey = insertionResultReturned.leftKey;

                //пристигаме от дясната страна, наша ст-ст е по-голяма, записваме отдясно, мидл е лефт,
                // по-голям е от лефт кей и по-малък от това, което изплува,
                // а това което е вдясно е по-голямо от всичко
                node.rightChild = insertionResultReturned.rightChild;
                node.middleChild = insertionResultReturned.leftChild;
            }
            return null;
        }


        //3-node => split and return
        K promoteValue = null;
        TreeNode<K> left = null;
        TreeNode<K> right = null;

        //insertResult.leftkey < node.leftKey < node.rightKey => node left key изплува нагоре
        if (insertionResultReturned.leftKey.compareTo(node.leftKey) < 0) {
            promoteValue = node.leftKey;
            left = insertionResultReturned;
            //2:41
            right = new TreeNode<>(node.rightKey, node.middleChild, node.rightChild);
        } else if (insertionResultReturned.leftKey.compareTo(node.rightKey) > 0) {
            //в дясната страна добавяме елемент, няма място, повдигаме нагоре и височината се вдига
            //promote value is the ne el here, it is already the right key
            promoteValue = node.rightKey; //becomes upper noed
            left = new TreeNode<>(node.leftKey, node.leftChild, node.rightChild);
            right = insertionResultReturned;
        } else {//insert in the middle of two node with no mre space left(0,5,3,4 insert 2)
            //divide middle chilf, divide upp until height is 1 more
            //02:48 QA
            promoteValue = insertionResultReturned.leftKey;
            left = new TreeNode<>(node.leftKey, node.leftChild, insertionResultReturned.leftChild);
            right = new TreeNode<>(node.rightKey, insertionResultReturned.rightChild, node.rightChild);

        }


        return new TreeNode<>(promoteValue, left, right);

    }

    public String getAsString() {
        StringBuilder out = new StringBuilder();
        recursivePrint(this.root, out);
        return out.toString().trim();
    }

    private void recursivePrint(TreeNode<K> node, StringBuilder out) {
        if (node == null) {
            return;
        }
        if (node.leftKey != null) {
            out.append(node.leftKey)
                    .append(" ");
        }
        if (node.rightKey != null) {
            out.append(node.rightKey).append(System.lineSeparator());
        } else {
            out.append(System.lineSeparator());
        }
        if (node.isTwoNode()) {
            recursivePrint(node.leftChild, out);
            recursivePrint(node.rightChild, out);
        } else if (node.isThreeNode()) {
            recursivePrint(node.leftChild, out);
            recursivePrint(node.middleChild, out);
            recursivePrint(node.rightChild, out);
        }
    }
}
