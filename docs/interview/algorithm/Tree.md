## 二叉树
```java
public class Tree {
    private Node root;

    public Node createTree() {
        Node a = new Node("A");
        Node b = new Node("B", null, a);
        Node c = new Node("C");
        Node d = new Node("D", b, c);
        Node e = new Node("E");
        Node f = new Node("F", e, null);
        Node g = new Node("G", null, f);
        root = new Node("H", d, g);
        return root;
    }

    /**
     * 前序遍历
     */
    public void preOrder(Node node) {
        if (node != null) {
            visitNode(node);
            preOrder(node.getLeft());
            preOrder(node.getRight());
        }
    }

    /**
     * 中序遍历
     */
    public void middleOrder(Node node) {
        if (node != null) {
            middleOrder(node.getLeft());
            visitNode(node);
            middleOrder(node.getRight());
        }
    }

    /**
     * 后序遍历
     */
    public void lastOrder(Node node) {
        if (node != null) {
            lastOrder(node.getLeft());
            lastOrder(node.getRight());
            visitNode(node);
        }
    }

    /**
     * 前序遍历(非递归1)
     */
    public void preOrder2(Node node) {
        if (node != null) {
            Stack<Node> stack = new Stack<>();
            stack.push(node);
            while (!stack.isEmpty()) {
                node = stack.pop();
                visitNode(node);

                if (node.getRight() != null) {
                    stack.push(node.getRight());
                }

                if (node.getLeft() != null) {
                    stack.push(node.getLeft());
                }
            }
        }
    }

    /**
     * 前序遍历(非递归2)
     */
    public void preOrder3(Node node) {
        Stack<Node> stack = new Stack<>();
        while (node != null || !stack.isEmpty()) {
            while (node != null) {
                visitNode(node);
                stack.push(node);
                node = node.getLeft();
            }

            node = stack.pop();
            node = node.getRight();
        }
    }

    /**
     * 中序遍历(非递归)
     */
    public void middleOrder2(Node node) {
        Stack<Node> stack = new Stack<>();
        while (node != null || !stack.isEmpty()) {
            while (node != null) {
                stack.push(node);
                node = node.getLeft();
            }

            node = stack.pop();
            visitNode(node);
            node = node.getRight();
        }
    }

    /**
     * 后序遍历（非递归
     */
    public void lastOrder2(Node node) {
        Stack<Node> stack = new Stack<>();
        Node preNode = null;
        while (node != null || !stack.isEmpty()) {
            while (node != null) {
                stack.push(node);
                node = node.getLeft();
            }

            node = stack.peek();
            if (node.getRight() == null || node.getRight() == preNode) {
                visitNode(node);
                node = stack.pop();
                preNode = node;
                node = null;
            } else {
                node = node.getRight();
            }
        }
    }

    private void visitNode(Node node) {
        LogUtils.i(Tree.class, node.getValue());
    }

    public Node getRoot() {
        return root;
    }
}
```

## 节点
```java
@SuppressWarnings({"unused", "WeakerAccess"})
class Node {
    private Node left;
    private Node right;
    private String value;

    public Node(String value, Node left, Node right) {
        this.value = value;
        this.left = left;
        this.right = right;
    }

    public Node(String value) {
        this(value, null, null);
    }

    public Node getLeft() {
        return left;
    }

    public void setLeft(Node left) {
        this.left = left;
    }

    public Node getRight() {
        return right;
    }

    public void setRight(Node right) {
        this.right = right;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
```

## 参考
- [http://blog.163.com/fly_sky_java/blog/static/14042223420102834937731/](http://blog.163.com/fly_sky_java/blog/static/14042223420102834937731/)
- [http://blog.csdn.net/skylinesky/article/details/6611442](http://blog.csdn.net/skylinesky/article/details/6611442)
