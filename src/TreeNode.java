

public class TreeNode<T extends Comparable<T>, G extends Comparable<G>, E> implements Comparable<TreeNode<T, G, E>>  {
    public T Key;
    public G secondKey;
    public Tree_2_3 object;
    public TreeNode Left;
    public TreeNode Right;
    public TreeNode middle;
    public TreeNode P;
    public TreeNode next;
    public int size;


    public TreeNode(){}

    public TreeNode(TreeNode left, TreeNode middle, TreeNode right, TreeNode P, T Key, G secondKey ,Tree_2_3 object, int size) {
        this.Left = left;
        this.Right = right;
        this.middle = middle;
        this.P = P;
        this.Key = Key;
        this.secondKey = secondKey;
        this.object = object;
        this.size = size;
    }

    @Override
    public int compareTo(TreeNode other) {
            if (this.Key.equals('+'))
                return 1;
            if(this.Key.equals('-'))
                return -1;
            if(other.Key.equals('+'))
                return -1;
            if(other.Key.equals('-'))
                return 1;

            if (this.Key.compareTo((T) other.Key) < 0) {
                return -1;
            }
            if (this.Key.compareTo((T) other.Key) > 0) {
                return 1;
            }
            return (this.secondKey.compareTo((G) other.secondKey));

    }

    public int compareKey(T key, G secondKey){
        if (key.equals('+'))
            return -1;
        if (key.equals('-'))
            return 1;
        if (this.Key.equals('+'))
            return 1;
        if (this.Key.equals('-'))
            return -1;
        if (secondKey == null) {
            return this.Key.compareTo(key);
        }
        else {
            if (this.Key.compareTo(key) < 0) {
                return -1;
            }
            if (this.Key.compareTo(key) > 0) {
                return 1;
            }
            return (this.secondKey.compareTo((G) secondKey));
        }

    }
    public void setNext(TreeNode next) {
        this.next = next;
    }
    public TreeNode getNext() {
        return this.next;
    }
}
