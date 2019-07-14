package com.group10.calculator;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static android.widget.Toast.*;
import static android.widget.Toast.LENGTH_LONG;

/**
 * This is class extends fragment to display on MainActivity
 */
public class CalculatorFragment extends Fragment implements View.OnClickListener {
    View activity;
    TextView txtHistory;
    TextView txtExpression;

    Button[] btnArray;
    ConvertToSuffix appConvert;
    String numberCurrent = "";

    boolean clickOperand = false;
    boolean clickOperator = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        activity = inflater.inflate(R.layout.fragment_calculator, container, false);
        MappingView();
        return activity;
    }

    /**
     * This is the class to map and capture click event for all views
     */
    private void MappingView() {
        txtHistory = (TextView) activity.findViewById(R.id.textviewHistory);
        txtExpression = (TextView) activity.findViewById(R.id.textviewExpression);
        btnArray = new Button[20];
        appConvert = new ConvertToSuffix();
        ViewID.SetResourcesID();
        for (int i = 0; i < 20; i++) {
            btnArray[i] = (Button) activity.findViewById(ViewID.IDList[i]);
            btnArray[i].setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View v) {
        Button btn = (Button) activity.findViewById(v.getId());
        String data = btn.getText().toString();
        String tag = btn.getTag().toString();
        if (appConvert.Result.equals("Error") ||
                txtExpression.getText().toString().equals("Infinity")) {
            numberCurrent = "";
            appConvert.Result = "";
            appConvert.operatorStack.Destroy();
            appConvert.operandStack.Destroy();
        }
        CheckButton(data, tag);
    }

    /**
     * @param data: data of button
     * @param tag:  tag of button
     *              This is function that checks which button is pressed and calls the function
     *              to handle it
     */
    private void CheckButton(String data, String tag) {
        if ("num".equals(tag)) {
            OperandButtonClicked(data);
        } else if ("operator".equals(tag)) {
            OperatorButtonClicked(data);
        } else if ("dot".equals(tag)) {
            DotButtonClicked(data);
        } else if ("minus".equals(tag)) {
            PlusMinusButtonClicked();
        } else if ("total".equals(tag)) {
            TotalButtonClicked();
        } else if ("del".equals(tag)) {
            DeleteButtonClicked();
        } else if ("clear".equals(tag)) {
            ClearButtonClicked();
        } else if ("percent".equals(tag)) {
            PercentButtonClicked(data);
        }
    }

    /**
     * @param percent: text of button
     *                 This is function handle event Percent Button clicked
     */
    private void PercentButtonClicked(String percent) {
        if (appConvert.operatorStack.StackIsEmpty() &&
                appConvert.operandStack.StackIsEmpty()) return;
        if (clickOperand) {
            appConvert.PushOperator(percent);
            UpdateExpression();
        }
    }

    /**
     * @param operator: text of button
     *                  This is function handle event Operator Button clicked
     */
    private void OperatorButtonClicked(String operator) {
        if (appConvert.operatorStack.StackIsEmpty() &&
                appConvert.operandStack.StackIsEmpty()) return;

        if (!clickOperator) {
            appConvert.PushOperator(operator);
            numberCurrent = "";
            clickOperator = true;
            clickOperand = false;
            UpdateExpression();
        }
    }

    /**
     * @param data: text of button
     *              This is function handle event Dot Button clicked
     */
    private void DotButtonClicked(String data) {
        if (!numberCurrent.contains(".")) {
            numberCurrent += ".";
        }
        UpdateOperand();
        clickOperand = true;
    }

    /**
     * This is function that updates the operand that is in the stack each time it is changed
     * by Dot Button and PlusMinusButton
     */
    private void UpdateOperand() {
        if (clickOperand) {
            if (!appConvert.operandStack.StackIsEmpty()) appConvert.operandStack.Pop();
        }
        if(numberCurrent.equals("00")) numberCurrent = "0";
        appConvert.operandStack.Push( numberCurrent);
        UpdateExpression();
    }

    /**
     * @param data: text of button
     *              This is function handle event Operand Button clicked
     */
    private void OperandButtonClicked(String data) {
        numberCurrent += data;
        UpdateOperand();
        clickOperand = true;
        clickOperator = false;
    }

    /**
     * This is function handle event PlusMinus Button clicked
     */
    private void PlusMinusButtonClicked() {
        if (appConvert.operatorStack.StackIsEmpty() &&
                appConvert.operandStack.StackIsEmpty()) return;

        if (clickOperand) {
            if (numberCurrent.contains("-")) {
                numberCurrent = numberCurrent.replace("-", "");
            } else {
                numberCurrent = "-" + numberCurrent;
            }
            UpdateOperand();
        }
    }


    /**
     * This is function to update the current expression, each time the user completes an operation
     */
    private void UpdateExpression() {
        String stringExpress = appConvert.ConvertSuffixToIntermediate();
        txtExpression.setText(stringExpress);
    }


    /**
     * This is function handle event Total Button clicked
     */
    private void TotalButtonClicked() {
        if (appConvert.operandStack.StackIsEmpty() &&
                appConvert.operatorStack.StackIsEmpty()) return;

        txtHistory.setText(appConvert.ConvertSuffixToIntermediate());
        appConvert.ResultOfExpression();
        numberCurrent = appConvert.Result;
        txtExpression.setText(numberCurrent);
        if (appConvert.Result.equals("Error")) return;

        appConvert.operandStack.Destroy();
        appConvert.operatorStack.Destroy();
        appConvert.operandStack.Push(numberCurrent);
        clickOperator = false;
        clickOperand = true;
        AddValueHistory(txtHistory.getText().toString(),txtExpression.getText().toString());
    }

    /**
     * This is function handle event Clear Button clicked. It resets all of
     * CalculatorFragment's status to the beginning
     */
    private void ClearButtonClicked() {
        appConvert.operandStack.Destroy();
        appConvert.operatorStack.Destroy();
        appConvert.Result = "";
        numberCurrent = "";
        txtExpression.setText("0");
        txtHistory.setText("0");
        clickOperator = false;
        clickOperand = false;
    }

    /**
     * This is function handle event Delete Button clicked. It returns to previous user actions
     */
    private void DeleteButtonClicked() {
        String temp = txtHistory.getText().toString();
        ClearButtonClicked();
        txtHistory.setText(temp);
    }
    /**
     * This is function handle give value when have total and perform into HistoryFragment
     *
     * @Author by: Trần Quang Long
     */
    private void AddValueHistory(String _result, String _operand) {

        Set<String> stringSet = new HashSet<>();
        SharedPreferences sharedPreferences =
                this.getActivity().getSharedPreferences("data", Context.MODE_PRIVATE);

        stringSet = sharedPreferences.getStringSet("data", stringSet);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
        Calendar calendar = Calendar.getInstance();
        Date time = calendar.getTime();
        String value = _result + "|" + _operand + "|" + simpleDateFormat.format(time);
        stringSet.add(value);
        editor.putStringSet("data", stringSet);
        editor.commit();

    }
}
