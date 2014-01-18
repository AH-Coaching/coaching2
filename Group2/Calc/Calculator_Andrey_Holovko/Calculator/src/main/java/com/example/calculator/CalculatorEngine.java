package com.example.calculator;

/**
 * Created by Androidsmith on 14.01.14.
 */
public class CalculatorEngine {
    private double operand;
    private double last_operand;
    private double operand_wait;
    private String operator_wait;

    public CalculatorEngine() {
        //Obnuleniye
        this.operand = 0;
        this.operand_wait = 0;
        this.operator_wait = "";
    }

    public void setOperand(double op) {
        operand = op;
    }

    public void setOperandHowLast() {
        operand = last_operand;
    }

    public void setOperator(String operato) {
        operator_wait = operato;
    }

    public double getResult() {

        return operand;
    }

    public void operationDo() {
        last_operand=operand;
        if (operator_wait.equals("+")) {
            operand = operand_wait + operand;
        } else if (operator_wait.equals("-")) {
            operand = operand_wait - operand;
        } else if (operator_wait.equals("x")) {
            operand = operand_wait * operand;
        } else if (operator_wait.equals("÷")) {
            if (operand != 0) {
                operand = operand_wait / operand;
            }
        } else if (operator_wait.equals("=")) {
        }

        operand_wait = operand;
    }

    public void setParam(Double operan1, String operat) {
        operand_wait = operan1;
        operator_wait = operat;

    }

    ;
}