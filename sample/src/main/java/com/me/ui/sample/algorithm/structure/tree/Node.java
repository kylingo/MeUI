package com.me.ui.sample.algorithm.structure.tree;

/**
 * @author tangqi on 17-7-17.
 */
@SuppressWarnings({"unused", "WeakerAccess"})
public class Node {
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
