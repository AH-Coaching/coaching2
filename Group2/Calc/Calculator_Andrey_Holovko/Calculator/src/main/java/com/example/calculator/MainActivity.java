package com.example.calculator;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.DecimalFormat;

public class MainActivity extends Activity implements View.OnClickListener {
    private static String numbers = "01234567890.";
    DecimalFormat df = new DecimalFormat("@###########");
    private EditText editText;
    private CalculatorEngine mCalculatorEngine;
    private Boolean exprFull = false;
    private Boolean operationWas = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mCalculatorEngine = new CalculatorEngine();
        editText = (EditText) findViewById(R.id.edit_text);
        Button btn = (Button) findViewById(R.id.btnOne);
        btn.setOnClickListener(this);
        btn = (Button) findViewById(R.id.btnTwo);
        btn.setOnClickListener(this);
        btn = (Button) findViewById(R.id.btnThree);
        btn.setOnClickListener(this);
        btn = (Button) findViewById(R.id.btnFour);
        btn.setOnClickListener(this);
        btn = (Button) findViewById(R.id.btnFive);
        btn.setOnClickListener(this);
        btn = (Button) findViewById(R.id.btnSix);
        btn.setOnClickListener(this);
        btn = (Button) findViewById(R.id.btnSeven);
        btn.setOnClickListener(this);
        btn = (Button) findViewById(R.id.btnEight);
        btn.setOnClickListener(this);
        btn = (Button) findViewById(R.id.btnNine);
        btn.setOnClickListener(this);
        btn = (Button) findViewById(R.id.btnNull);
        btn.setOnClickListener(this);
        btn = (Button) findViewById(R.id.btnDot);
        btn.setOnClickListener(this);
        btn = (Button) findViewById(R.id.btnPlus);
        btn.setOnClickListener(this);
        btn = (Button) findViewById(R.id.btnMinus);
        btn.setOnClickListener(this);
        btn = (Button) findViewById(R.id.btnMultiply);
        btn.setOnClickListener(this);
        btn = (Button) findViewById(R.id.btnDivide);
        btn.setOnClickListener(this);
        btn = (Button) findViewById(R.id.btnEqual);
        btn.setOnClickListener(this);
        btn = (Button) findViewById(R.id.btnDelete);
        btn.setOnClickListener(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {

        String strEdits = editText.getText().toString();
        String btnPressed = ((Button) view).getText().toString();
        //Button AllDigit
        if (numbers.contains(btnPressed)) {
/*
            if (strEdits.equals("0")) {
                editText.setText((btnPressed.equals(".")) ? ("0" + btnPressed) : (btnPressed));
            }

            else
*/
            if (btnPressed.equals(".")) {
                if (strEdits.equals("0")) editText.setText("0" + btnPressed);
                else editText.append(btnPressed);
            } else {
                if (operationWas == true) {
                    editText.setText(btnPressed);
                    exprFull = true;
                    operationWas = false;
                } else {
                    editText.append(btnPressed);
                }

            }
            //Button Delete
        } else if (view.getId() == R.id.btnDelete) {
            editText.setText(strEdits.length() <= 1 ? "" : strEdits.substring(0, strEdits.length() - 1));
            exprFull = false;
        }
        //Button Equal
        else if (view.getId() == R.id.btnEqual) {
            if (exprFull==false)
            mCalculatorEngine.setOperandHowLast();
            else
                mCalculatorEngine.setOperand(Double.parseDouble(strEdits));
                mCalculatorEngine.operationDo();
            if (new Double(mCalculatorEngine.getResult()).equals(0.0)) {
                editText.setText("" + 0);
            } else {
                editText.setText(df.format(mCalculatorEngine.getResult()));
            }
            exprFull = false;
            //Operations
        } else {
            if (strEdits.equals("") || strEdits.equals("-")) {
                if (btnPressed.equals("-")) editText.setText("-");
                if (btnPressed.equals("+")) editText.setText("");
            } else {
                if (exprFull == false) {
                    mCalculatorEngine.setParam(Double.parseDouble(strEdits), btnPressed);
                } else {
                    mCalculatorEngine.setOperand(Double.parseDouble(strEdits));
                    mCalculatorEngine.operationDo();
                    if (new Double(mCalculatorEngine.getResult()).equals(0.0)) {
                        editText.setText("" + 0);
                    } else {
                        editText.setText(df.format(mCalculatorEngine.getResult()));
                    }
                    mCalculatorEngine.setOperator(btnPressed);
                    exprFull = false;
                }
                operationWas = true;
            }
        }
    }

    /**
     * A placeholder fragment containing a simple view.
     */


}
