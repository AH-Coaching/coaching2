package com.ua.android_helper.calc1;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

public class MainActivity extends Activity implements View.OnClickListener {

    private EditText _editText;
    private boolean _isFirst;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        _editText = (EditText) findViewById(R.id.edtInputField);

        Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/A5mzMlSk.ttf");
        _editText.setTypeface(tf);

        Button button = (Button) findViewById(R.id.btn0);
        button.setOnClickListener(this);
        button = (Button) findViewById(R.id.btn1);
        button.setOnClickListener(this);
        button = (Button) findViewById(R.id.btn2);
        button.setOnClickListener(this);
        button = (Button) findViewById(R.id.btn3);
        button.setOnClickListener(this);
        button = (Button) findViewById(R.id.btn4);
        button.setOnClickListener(this);
        button = (Button) findViewById(R.id.btn5);
        button.setOnClickListener(this);
        button = (Button) findViewById(R.id.btn6);
        button.setOnClickListener(this);
        button = (Button) findViewById(R.id.btn7);
        button.setOnClickListener(this);
        button = (Button) findViewById(R.id.btn8);
        button.setOnClickListener(this);
        button = (Button) findViewById(R.id.btn9);
        button.setOnClickListener(this);
        button = (Button) findViewById(R.id.btnAddition);
        button.setOnClickListener(this);
        ImageButton btnClear = (ImageButton) findViewById(R.id.btnClear);
        btnClear.setOnClickListener(this);

        button = (Button) findViewById(R.id.btnDivision);
        button.setOnClickListener(this);
        button = (Button) findViewById(R.id.btnDot);
        button.setOnClickListener(this);
        button = (Button) findViewById(R.id.btnMultiplication);
        button.setOnClickListener(this);
        button = (Button) findViewById(R.id.btnSubtraction);
        button.setOnClickListener(this);
        button = (Button) findViewById(R.id.btnSummary);
        button.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        String result = _editText.getText().toString();
        if (CalcHelper.getCurrentType() != CalcHelper.OperatorsType.none || _isFirst) {
            result = "";
            _isFirst = false;
        }
        switch (v.getId()) {
            case R.id.btn0:
                result += "0";
                break;
            case R.id.btn1:
                result += "1";
                break;
            case R.id.btn2:
                result += "2";
                break;
            case R.id.btn3:
                result += "3";
                break;
            case R.id.btn4:
                result += "4";
                break;
            case R.id.btn5:
                result += "5";
                break;
            case R.id.btn6:
                result += "6";
                break;
            case R.id.btn7:
                result += "7";
                break;
            case R.id.btn8:
                result += "8";
                break;
            case R.id.btn9:
                result += "9";
                break;
            case R.id.btnClear:
                result = "";
                CalcHelper.clear();
                _isFirst = true;
                break;
            case R.id.btnAddition:
                CalcHelper.addition(_editText.getText().toString());
                _isFirst = true;
                break;
            case R.id.btnDivision:
                CalcHelper.division(_editText.getText().toString());
                _isFirst = true;
                break;
            case R.id.btnDot:
                result += ".";
                break;
            case R.id.btnMultiplication:
                CalcHelper.multiplication(_editText.getText().toString());
                _isFirst = true;
                break;
            case R.id.btnSubtraction:
                CalcHelper.subtraction(_editText.getText().toString());
                _isFirst = true;
                break;
            case R.id.btnSummary:
                result = CalcHelper.summary(_editText.getText().toString());
                _isFirst = true;
                break;
        }

        _editText.setText(result);

    }

}
