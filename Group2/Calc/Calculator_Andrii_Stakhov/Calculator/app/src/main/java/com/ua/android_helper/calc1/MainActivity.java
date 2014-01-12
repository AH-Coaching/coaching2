package com.ua.android_helper.calc1;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends Activity implements View.OnClickListener {

    private EditText editText;
    private Button btn6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = (EditText) findViewById(R.id.edtInputField);
        Integer.valueOf(editText.getText().toString());

        btn6 = (Button) findViewById(R.id.btn6);
        btn6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.print("btn 6");
            }
        });

        Button btn = (Button) findViewById(R.id.btn5);
        btn.setOnClickListener(this);
        btn = (Button) findViewById(R.id.btn1);
        btn.setOnClickListener(this);
        btn = (Button) findViewById(R.id.btn2);
        btn.setOnClickListener(this);
        btn = (Button) findViewById(R.id.btn3);
        btn.setOnClickListener(this);
        btn = (Button) findViewById(R.id.btn8);
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

    public void btn7Click(View view) {
        System.out.print("btn 7");
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn5) {
            System.out.print("btn 5");
        } else if (v.getId() == R.id.btn1) {
            System.out.print("btn 1");
        } else if (v.getId() == R.id.btn2) {
            System.out.print("btn 2");
        } else if (v.getId() == R.id.btn3) {
            System.out.print("btn 3");
        } else if (v.getId() == R.id.btn4) {
            System.out.print("btn 4");
        }
    }
}
