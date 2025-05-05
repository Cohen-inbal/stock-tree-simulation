

public class Tree_2_3 <T extends Comparable<T>, G>{
    public TreeNode root;



    public Tree_2_3(){
        Tree_2_3_init();
    }
    public void Tree_2_3_init(){
        sentinalNode left = new sentinalNode('-');
        sentinalNode middle = new sentinalNode('+');
        root = new TreeNode(left, middle, null, null, '+' , null, null, 0);
    }

    public TreeNode Tree_2_3_Search(TreeNode root, T key, G secondKey){
        if(root.Left == null){
            if(secondKey == null) {
                if (root.compareKey(key, (Comparable) secondKey) == 0)
                    return root;
                else return null;
            }
            else if(root.compareKey(key, (Comparable) secondKey) == 0 && root.secondKey == secondKey)
                return root;
            else return null;
        }

        if(root.Left.compareKey(key, (Comparable) secondKey) >= 0 )
            return Tree_2_3_Search(root.Left, key, secondKey);
        else if(root.middle.compareKey(key, (Comparable) secondKey) >= 0)
            return Tree_2_3_Search(root.middle, key, secondKey);
        else return Tree_2_3_Search(root.Right, key, secondKey);
    }

    public TreeNode Tree_2_3_Minimum(TreeNode root){
        TreeNode x = root;
        while(x.Left != null){
            x = x.Left;
        }
        x = x.P.middle;
        if(x.getClass() != sentinalNode.class)
            return x;
        else
            throw new IllegalArgumentException();
    }

    public TreeNode Tree_2_3_Successor(TreeNode x){
        TreeNode z = x.P;
        TreeNode y;
        while(x == z.Right || (z.Right == null && x == z.middle)){
            x = z;
            z = z.P;
        }
        if(x == z.Left)
             y = z.middle;
        else   y = z.Right;
        while (y.Left != null){
            y = y.Left;
        }
        if(y.getClass() != sentinalNode.class)
            return y;
        return null;
    }

    public TreeNode Tree_2_3_Predecessor(TreeNode x) {
        TreeNode z = x.P;
        TreeNode y;

        while (x == z.Left || (z.Left == null && x == z.middle)) {
            x = z;
            z = z.P;
        }
        if (x == z.Right) {
            y = z.middle;
        } else {
            y = z.Left;
        }

        while (y.Right != null || y.middle != null || y.Left != null) {
            if (y.Right != null) {
                y = y.Right;
            } else if (y.middle != null) {
                y = y.middle;
            } else {
                y = y.Left;
            }
        }

        if (y.getClass() != sentinalNode.class) {
            return y;
        }
        return null;
    }


    public void UpdateKey(TreeNode x){
        x.Key = x.Left.Key;
        x.secondKey = x.Left.secondKey;
        x.size = x.Left.size;
        if(x.middle != null){
            x.Key = x.middle.Key;
            x.secondKey = x.middle.secondKey;
            x.size += x.middle.size;
        }

        if(x.Right != null) {
            x.Key = x.Right.Key;
            x.secondKey = x.Right.secondKey;
            x.size += x.Right.size;
        }

    }

    public void SetChildren(TreeNode x, TreeNode l, TreeNode m, TreeNode r){
        x.Left = l;
        x.middle = m;
        x.Right = r;
        l.P = x;
        if(m != null)
            m.P = x;
        if (r != null)
            r.P = x;
        UpdateKey(x);
    }

    public TreeNode InsertAndSplit(TreeNode x, TreeNode z){
        TreeNode l = x.Left;
        TreeNode m = x.middle;
        TreeNode r = x.Right;
        if(r == null) {
            if (z.compareTo(l) < 0)
                SetChildren(x, z, l, m);
            else if (z.compareTo(m)<0)
                SetChildren(x, l, z, m);
            else SetChildren(x, l, m, z);
            x = x.P;
            while(x != null){
                x.size += 1;
                x=x.P;
            }
            return null;
        }

        TreeNode y = new TreeNode();
        y.P = x.P;
        if(z.compareTo(l)<0){
            SetChildren(x, z, l, null);
            SetChildren(y, m, r, null);
        }
        else if(z.compareTo(m)<0){
            SetChildren(x, l, z, null);
            SetChildren(y, m, r, null);
        }
        else if(z.compareTo(r)<0){
            SetChildren(x, l, m, null);
            SetChildren(y, z, r, null);
        }
        else {
            SetChildren(x, l, m, null);
            SetChildren(y, r,z,  null);
        }
        y.size = y.Left.size + y.middle.size;
        x.size = x.Left.size + x.middle.size;
        return y;
    }

    public void Tree_2_3_Insert(TreeNode root, TreeNode z){
        TreeNode zCopy = z;
        if(root.size < 1){
            if(root.Left == null){
                root.Left = z;
                root.Key = z.Key;
                z.P = root;
            }
            else if(root.middle == null){
                if(root.Left.compareTo(z) <0){
                    SetChildren(root, root.Left, z, null);
                }
                else SetChildren(root, z, root.Left, null);
            }
            else{
                if (root.Left.getClass() == sentinalNode.class){
                    SetChildren(root, root.Left, z, root.middle);
                }
                else {
                    if (root.Left.compareTo(z) > 0)
                        SetChildren(root, z, root.Left, root.middle);
                    else if (root.middle.compareTo(z) > 0)
                        SetChildren(root, root.Left, z, root.middle);
                    else SetChildren(root, root.Left, root.middle, z);
                }
            }
            UpdateKey(root);
            z.P = root;
        }
        else {
            TreeNode y = root;
            while (y.Left != null) {
                if (z.compareTo(y.Left) < 0)
                    y = y.Left;
                else if (z.compareTo(y.middle) < 0)
                    y = y.middle;
                else y = y.Right;
            }
            TreeNode x = y.P;
            z = InsertAndSplit(x, z);
            while (x.P != null) {
                x = x.P;
                if (z != null)
                    z = InsertAndSplit(x, z);
                else UpdateKey(x);
            }
            if (z != null) {
                TreeNode w = new TreeNode();
                SetChildren(w, x, z, null);
                w.size = w.Left.size + w.middle.size;
                this.root = w;
            }
        }
        TreeNode next  = Tree_2_3_Successor(zCopy);
        TreeNode before = Tree_2_3_Predecessor(zCopy);
        zCopy.setNext(next);
        if (before != null) {
            before.setNext(zCopy);
        }
    }

    public TreeNode BorrowOrMerge(TreeNode y){
        TreeNode z = y.P;
        TreeNode x;
        if(y == z.Left){
            x = z.middle;
            if(x.Right != null) {
                SetChildren(y, y.Left, x.Left, null);
                y.size = y.Left.size + y.middle.size;
                SetChildren(x, x.middle, x.Right, null);
                x.size = x.Left.size + x.middle.size;
            }
            else {
                SetChildren(x, y.Left, x.Left, x.middle);
                x.size = x.Left.size + x.middle.size + x.Right.size;
                y.Left = null;
                y.P = null;
                if (z.Right != null) {
                    SetChildren(z, x, z.Right, null);
                    z.size = z.Left.size + z.middle.size;
                }
                else {
                    SetChildren( z,x,null, null);
                    z.size = z.Left.size;
                }
            }
            return z;
        }
        if(y == z.middle){
            x = z.Left;
            if(x.Right != null) {
                SetChildren(y, x.Right, y.Left, null);
                y.size = y.Left.size + y.middle.size;
                SetChildren(x, x.Left, x.middle, null);
                x.size = x.Left.size + x.middle.size;
            }
            else {
                SetChildren(x, x.Left, x.middle, y.Left);
                x.size = x.Left.size + x.middle.size + x.Right.size;
                y.Left = null;
                y.P = null;
                if (z.Right != null) {
                    SetChildren(z, x, z.Right, null);
                    z.size = z.Left.size + z.middle.size;
                }
                else {
                    SetChildren( z,x,null, null);
                    z.size = z.Left.size;
                }
            }
            return z;
        }
        x = z.middle;
        if(x.Right != null){
            SetChildren(y, x.Right, y.Left, null);
            y.size = y.Left.size + y.middle.size;
            SetChildren(x, x.Left, x.middle, null);
            x.size = x.Left.size + x.middle.size;
        }
        else {
            SetChildren(x, x.Left, x.middle, y.Left);
            x.size = x.Left.size + x.middle.size + x.Right.size;
            y.Left = null;
            y.P = null;
            SetChildren(z, z.Left, x, null);
            z.size = z.Left.size + z.middle.size;
        }
        return z;
    }

    public void Tree_2_3_Delete(TreeNode root, TreeNode x){
        if (x == null)
            return;
        TreeNode next = Tree_2_3_Successor(x);
        TreeNode before = Tree_2_3_Predecessor(x);
        if(before != null){
            before.setNext(next);
        }


        TreeNode y = x.P;
        if(y!=null) {
            if (x == y.Left)
                SetChildren(y, y.middle, y.Right, null);
            else if (x == y.middle)
                SetChildren(y, y.Left, y.Right, null);
            else SetChildren(y, y.Left, y.middle, null);
        }

        x = null;


        if(y.middle != null) {
            y = y.P;
            while (y != null) {
                y.size -= 1;
                y = y.P;
            }
        }
        else{
        while(y != null){
            if(y.middle == null){
                if(y != root)
                    y = BorrowOrMerge(y);
                else{
                    root = y.Left;
                    if (root != null){
                        y.Left.P = null;
                        y.Left.size = y.size -1;

                    }

                    y = null;
                    break;
                }
            }
            else {
                UpdateKey(y);
                y = y.P;
            }
        }
        }

    }



    public int Rank(TreeNode x){
        int rank = 1;
        if (x == null)
            return 0;
        TreeNode y = x.P;
        while(y != null){
            if(x == y.middle){
                rank = rank + y.Left.size;
            } else if (x == y.Right) {
                rank = rank + y.Left.size + y.middle.size;
            }
            x = y;
            y = y.P;
        }
        return rank;
    }

    public String[] inOrderlist(TreeNode x, TreeNode root, TreeNode start, TreeNode finish, String[] list, int [] a, int k){
        if(x == null || a[0] == k){
            return list;
        }
        if(x.Left == null){
            if(start.compareTo(x) <= 0 && x.compareTo(finish) <= 0) {
                list[a[0]] = (String) x.secondKey;
                a[0] ++;
            }
        }
        else {
            if(start.compareKey(x.Left.Key, null)<=0)
                inOrderlist(x.Left, root, start, finish, list, a, k);
            if(x.middle != null && start.compareKey(x.middle.Key, null)<=0)
                inOrderlist(x.middle, root, start, finish, list, a, k);
            if(x.Right != null  && start.compareKey(x.Right.Key, null)<=0)
                inOrderlist(x.Right, root, start, finish, list, a, k);
        }
        return list;
    }
}
