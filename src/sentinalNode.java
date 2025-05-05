

public class sentinalNode<T extends Comparable<T>, G extends Comparable<G>, E>
        extends TreeNode<T, G, E> {

    public sentinalNode(T Key) {
        this.Key = Key;
    }

    public <T extends Comparable<T>> Object getsign(){
        return Key;
    }
}
