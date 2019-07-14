package com.group10.calculator;

import android.nfc.FormatException;

import java.util.ArrayList;

/**
 * This is class coverts an intermediate expression to suffix expression and relies on it
 * to calculate the result
 */
public class ConvertToSuffix extends FormatException {
    public String Result;
    public MyStack operandStack;
    public MyStack operatorStack;

    public ConvertToSuffix() {
        operandStack = new MyStack();
        operatorStack = new MyStack();
        Result = "";
    }

    /**
     * @param infoNode: data of node
     * @return 1 if infoNode is kind of "+" or "-", 2 if is kind of "*" or "/" or "%", default 0
     */
    public int GetOperator(String infoNode) {
        if ("%".equals(infoNode)) {
            return 3;
        } else if ("*".equals(infoNode) || "/".equals(infoNode)) {
            return 2;
        } else if ("+".equals(infoNode) || "-".equals(infoNode)) {
            return 1;
        } else {
            return 0;
        }
    }

    /**
     * @param infoNode: data of Node
     * @return 0 if infoNode is number, 1 if is kind of "(" or ")", default 2
     */
    public int IsOperator(String infoNode) {
        if (GetOperator(infoNode) == 0) {
            if (!"(".equals(infoNode) && !")".equals(infoNode))
                return 0;
            else
                return 1;
        } else
            return 2;
    }

    /**
     * @param infoNode: data of node
     *                  This is function to add operand or "(" or ")" to the top of stack
     */
    public void PushOperand(String infoNode) {
        operandStack.Push(infoNode);
    }

    /**
     * This is function calculates the result of an expression
     */
    public void ResultOfExpression() {
        MyStack expression = new MyStack();
        ArrayList<String> op = new ArrayList<>();
        IsExistOperator();
        for (Node p = operandStack.pTop; p != null; p = p.pNext) {
            op.add(p.infoNode);
        }
        for (int i = op.size() - 1; i >= 0; i--) {
            if (IsOperator(op.get(i)) == 0) {
                expression.Push(op.get(i));
            } else if (op.get(i).equals("%")) {
                double n = Double.parseDouble(expression.Pop())/100;
                expression.Push(n + "");
            } else {
                String y = expression.Pop();
                String x = expression.Pop();
                if (x == null && y == null) {
                    expression.Push("Error");
                } else if (y != null && x == null) {
                    expression.Push(Double.parseDouble(y) + "");
                } else {
                    double b = Double.parseDouble(y);
                    double a = Double.parseDouble(x);
                    if (op.get(i).equals("+")) {
                        expression.Push((a + b) + "");
                    } else if (op.get(i).equals("-")) {
                        expression.Push((a - b) + "");
                    } else if (op.get(i).equals("*")) {
                        expression.Push((a * b) + "");
                    } else if (op.get(i).equals("/")) {
                        expression.Push((a / b) + "");
                    }
                }
            }
        }
        if (expression.Peak().equals("Error")) {
            Result = expression.Pop();
        } else {
            Result = Double.parseDouble(expression.Pop()) + "";
        }
    }

    /**
     * This is the function to add operator to operandStack if it exists in operatorStack
     */
    public void IsExistOperator() {
        while (!operatorStack.StackIsEmpty()) {
            operandStack.Push(operatorStack.Pop());
        }
    }

    /**
     * @param infoNode: data of node
     *                  This is function to add operator to the top of stack
     */
    public void PushOperator(String infoNode) {
        while (!operatorStack.StackIsEmpty()
                && GetOperator(operatorStack.Peak()) >= GetOperator(infoNode)) {
            operandStack.Push(operatorStack.Pop());
        }
        operatorStack.Push(infoNode);
    }

    /**
     * @return the expression has been converted from suffix to intermediate
     */
    public String ConvertSuffixToIntermediate() {
        MyStack expression = new MyStack();
        MyStack operator = MyStack.CloneStack(operatorStack);
        MyStack operand = MyStack.CloneStack(operandStack);
        Test(operator, operand);
        while (!operator.StackIsEmpty()) {
            operand.Push(operator.Pop());
        }
        ArrayList<String> operatorList = new ArrayList<>();
        for (Node p = operand.pTop; p != null; p = p.pNext) {
            operatorList.add(p.infoNode);
        }
        for (int i = operatorList.size() - 1; i >= 0; i--) {
            if (IsOperator(operatorList.get(i)) == 0) {
                expression.Push(operatorList.get(i));
            } else {
                if (operatorList.get(i).equals("%")) {
                    expression.Push(expression.Pop() + "%");
                } else {
                    String b = expression.Pop();
                    String a = expression.Pop();
                    if (b == null) b = "";
                    if (a == null) {
                        a = b;
                        b = "";
                    }
                    expression.Push(a + operatorList.get(i) + b);
                }
            }
        }
        return expression.Pop();
    }

    private void Test(MyStack operator, MyStack operand){
        int countOperand = 0,countOperator = 0;
        for(Node p = operand.pTop;p!=null;p=p.pNext){
            if(GetOperator(p.infoNode) == 1 || GetOperator(p.infoNode) == 2){
                countOperator++;
            }else if(GetOperator(p.infoNode) != 3){
                countOperand++;
            }
        }
        for(Node q = operator.pTop;q!=null;q=q.pNext){
            if(GetOperator(q.infoNode) == 1 || GetOperator(q.infoNode) == 2){
                countOperator++;
            }else if(GetOperator(q.infoNode) != 3){
                countOperand++;
            }
        }
        if(countOperand == countOperator)
            MyStack.ReverseStack(operator);
    }
}
