import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        SplayTree tree = new SplayTree();
        int commands = Integer.parseInt(scanner.nextLine());
        for (int i = 0; i < commands; i++) {
            String[] words = scanner.nextLine().split(" ");
            switch (words[0]) {
                case "add"   -> tree.add(Integer.parseInt(words[1]));
                case "del"   -> tree.delete(Integer.parseInt(words[1]));
                case "find"  -> System.out.println(tree.find(tree.root, Integer.parseInt(words[1])));
                case "sum"   -> System.out.println(tree.sum(tree.root, Integer.parseInt(words[1]), Integer.parseInt(words[2])));
            }
        }
        scanner.close();
    }
}

// Node with pointers to left and right children and parent
class Node {
    int data;
    Node left = null, right = null, parent = null;
    Node(int data) {
        this.data = data;
    }
}

// Splay tree with add and del and find and sum methods
class SplayTree {
    public Node root = null;
    private Node insert(Node current, int x) {
        if (current == null) {
            current = new Node(x);
            return current;
        }
        else if (x < current.data) {
            current.left = insert(current.left, x);
            current.left.parent = current;
        }
        else if (x > current.data) {
            current.right = insert(current.right, x);
            current.right.parent = current;
        }
        return current;
    }

    // insert x number into tree and splay it
    public void add(int x) {
        root = insert(root, x);
        root = splay(root, x);
    }

    // if x is in tree delete it and splay parent node
    private Node del(Node current, int x) {
        if (current == null)
            return null;
        if (current.data > x) {
            current.left = del(current.left, x);
            return current;
        } else if (current.data < x) {
            current.right = del(current.right, x);
            return current;
        }
        if (current.right == null && current.left == null) {
            if (current.parent != null)
                root = splay(root, current.parent.data);
            return null;
        } else if (current.left == null) {
            current.right.parent = current.parent;
            if (current.parent != null) {
                if (current.parent.right != null && current.parent.right.data == current.data)
                    current.parent.right = current.right;
                else
                    current.parent.left = current.right;
                root = splay(root, current.parent.data);
            }
            return current.right;
        } else if (current.right == null) {
            current.left.parent = current.parent;
            if (current.parent != null) {
                if (current.parent.left != null && current.parent.left.data == current.data)
                    current.parent.left = current.left;
                else
                    current.parent.right = current.left;
                root = splay(root, current.parent.data);
            }
            return current.left;
        } else {
            Node tempParent = current;
            Node temp = current.left;
            while (temp.right != null) {
                tempParent = temp;
                temp = temp.right;
            }
            if (tempParent != current)
                tempParent.right = temp.left;
            else
                tempParent.left = temp.left;
            if (temp.left != null)
                temp.left.parent = tempParent;
            current.data = temp.data;
            if (current.parent != null)
                root = splay(root, current.parent.data);
            return current;
        }
    }

    // main function delete the cal recursive function del
    public void delete(int x) {
        root = del(root, x);
    }

    // return true and splay the tree if x is found
    public boolean find(Node current, int x) {
        if (current == null)
            return false;
        if (current.data == x) {
            root = splay(root, current.data);
            return true;
        }
        if (current.data < x)
            return find(current.right, x);
        else
            return find(current.left, x);
    }

    // return sum of node that are between l and r in long
    public long sum(Node current, int l, int r) {
        if (current == null)
            return 0;
        if (current.data > r)
            return sum(current.left, l, r);
        if (current.data < l)
            return sum(current.right, l, r);
        return current.data + sum(current.right, l, r) + sum(current.left, l, r);
    }

    // rotate a node with it left child
    private Node rightRotate(Node node) {
        Node temp = node.left;
        node.left = temp.right;
        if (temp.right != null)
            temp.right.parent = node;
        temp.right = node;
        temp.parent = node.parent;
        node.parent = temp;
        return temp;
    }

    // rotate a node with it right child
    private Node leftRotate(Node node) {
        Node temp = node.right;
        node.right = temp.left;
        if (temp.left != null)
            temp.left.parent = node;
        temp.left = node;
        temp.parent = node.parent;
        node.parent = temp;
        return temp;
    }

    // recursive function that splays tree
    private Node splay(Node current, int splay) {
        if (current == null || current.data == splay)
            return current;

        if (current.data > splay) {
            if (current.left == null)
                return current;

            if (current.left.data > splay) {
                current.left.left = splay(current.left.left, splay);
                current = rightRotate(current);
            }
            else if (current.left.data < splay) {
                current.left.right = splay(current.left.right, splay);
                if (current.left.right != null)
                    current.left = leftRotate(current.left);
            }
            return (current.left == null) ? current : rightRotate(current);
        } else {
            if (current.right == null)
                return current;

            if (current.right.data > splay) {
                current.right.left = splay(current.right.left, splay);
                if (current.right.left != null)
                    current.right = rightRotate(current.right);
            }
            else if (current.right.data < splay) {
                current.right.right = splay(current.right.right, splay);
                current = leftRotate(current);
            }
            return (current.right == null) ? current : leftRotate(current);
        }
    }
}
