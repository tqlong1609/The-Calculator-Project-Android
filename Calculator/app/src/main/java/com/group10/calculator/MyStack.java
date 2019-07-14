package com.group10.calculator;

public class MyStack {
    public Node pTop;
    private Node pTail;

    public MyStack() {
        pTop = null;
        pTail = null;
    }

    /**
     * @return true if stack is empty else return false
     */
    public boolean StackIsEmpty() {
        return pTop == null;
    }

    /**
     * @param infoNode: data of node
     *                  This is function add a node to the top of stack
     */
    public void Push(String infoNode) {
        Node p = new Node(infoNode);
        if (StackIsEmpty()) {
            pTop = p;
        } else {
            p.pNext = pTop;
            pTop = p;
        }
    }

    /**
     * @return data of node at the top of stack and delete itself
     */
    public String Pop() {
        if (StackIsEmpty()) {
            return null;
        } else {
            Node p = pTop;
            pTop = pTop.pNext;
            return p.infoNode;
        }
    }

    /**
     * This is function to remove the nodes in the stack
     */
    public void Destroy() {
        while (!StackIsEmpty()) {
            Node p = pTop;
            pTop = pTop.pNext;
            p = null;
        }
    }

    /**
     * @return a node at the top of stack and don't delete itself
     */
    public String Peak() {
        if (StackIsEmpty())
            return null;
        return pTop.infoNode;
    }

    /**
     * @param infoNode: data of node
     * @return info of node if it exist in stack
     */
    public String SearchNode(String infoNode) {
        for (Node p = pTop; p != null; p = p.pNext) {
            if (infoNode.equals(p.infoNode)) return p.infoNode;
        }
        return null;
    }

    /**
     * @param p: data of node
     *           This is function to add nodes at the end of the stack
     */
    private void AddLast(Node p) {
        if (StackIsEmpty()) {
            pTop = pTail = p;
        } else {
            pTail.pNext = p;
            pTail = p;
        }
    }

    /**
     * @param stack: stack need to copy
     * @return a copy of A
     */
    public static MyStack CloneStack(MyStack stack) {
        MyStack temp = new MyStack();
        for (Node p = stack.pTop; p != null; p = p.pNext) {
            temp.AddLast(p);
        }
        return temp;
    }

    /**
     * @param stack: stack need reverse
     *             This is function that has a stack reverse function
     */
    public static void ReverseStack(MyStack stack){
        MyStack temp = new MyStack();
        for(Node p = stack.pTop; p!=null;p=p.pNext){
            temp.Push(p.infoNode);
        }
        stack.pTop = temp.pTop;
    }
}
