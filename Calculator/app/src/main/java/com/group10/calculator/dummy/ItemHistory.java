package com.group10.calculator.dummy;
/**
 * This is class about views support list view
 */
public class ItemHistory {

    private String result;
    private String operand;
    private String time;

    public ItemHistory(String _result, String _operand, String _time) {
        this.result = _result;
        this.operand = _operand;
        this.time = _time;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getOperand() {
        return operand;
    }

    public void setOperand(String operand) {
        this.operand = operand;
    }
}
