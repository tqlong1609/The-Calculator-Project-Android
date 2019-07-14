package com.group10.calculator;

/**
 * This is the class containing data that can be operand, operator or "(", ")"
 */
public class Node {
    public String infoNode;
    public Node pNext;

    public Node(String infoNode) {
        this.infoNode = infoNode;
        this.pNext = null;
    }
}
