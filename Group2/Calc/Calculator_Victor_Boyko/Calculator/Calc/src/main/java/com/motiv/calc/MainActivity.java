package com.motiv.calc;

import java.io.IOException;
import java.text.DecimalFormat;

import android.app.Activity;
import android.hardware.Camera;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends Activity implements OnClickListener,
        SurfaceHolder.Callback {
    private EditText etResult;
    private Boolean userIsInTheMiddleOfTypingANumber = false;
    private CalculatorBrain mCalculatorBrain;
    private static final String DIGITS = "0123456789.";
    DecimalFormat df = new DecimalFormat("@###########");
    Camera camera;
    SurfaceView surfaceView;
    SurfaceHolder surfaceHolder;
    boolean previewing = false;
    LayoutInflater controlInflater = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.transparent);
        // camera
        surfaceView = (SurfaceView) findViewById(R.id.surfaceView1);
        surfaceHolder = surfaceView.getHolder();
        if (surfaceHolder == null) throw new AssertionError();
        surfaceHolder.addCallback(this);

        // add button over SurfaceView
        controlInflater = LayoutInflater.from(getBaseContext());
        View viewControl = controlInflater.inflate(R.layout.activity_main, null);
        ViewGroup.LayoutParams layoutParamsControl = new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        this.addContentView(viewControl, layoutParamsControl);

        mCalculatorBrain = new CalculatorBrain();

        df.setMinimumFractionDigits(0);
        df.setMinimumIntegerDigits(1);
        df.setMaximumIntegerDigits(8);


        initialize();

    }

    public void initialize() {

        etResult = (EditText) findViewById(R.id.etResult);
        findViewById(R.id.btn0).setOnClickListener(this);
        findViewById(R.id.btn1).setOnClickListener(this);
        findViewById(R.id.btn2).setOnClickListener(this);
        findViewById(R.id.btn3).setOnClickListener(this);
        findViewById(R.id.btn4).setOnClickListener(this);
        findViewById(R.id.btn5).setOnClickListener(this);
        findViewById(R.id.btn6).setOnClickListener(this);
        findViewById(R.id.btn7).setOnClickListener(this);
        findViewById(R.id.btn8).setOnClickListener(this);
        findViewById(R.id.btn9).setOnClickListener(this);
        findViewById(R.id.btnAdd).setOnClickListener(this);
        findViewById(R.id.btnSubtract).setOnClickListener(this);
        findViewById(R.id.btnMultiply).setOnClickListener(this);
        findViewById(R.id.btnDivide).setOnClickListener(this);
        findViewById(R.id.btnDot).setOnClickListener(this);
        findViewById(R.id.btnResult).setOnClickListener(this);
        findViewById(R.id.btnClear).setOnClickListener(this);
        findViewById(R.id.btnClearMemory).setOnClickListener(this);
        findViewById(R.id.btnAddToMemory).setOnClickListener(this);
        findViewById(R.id.btnSubtractFromMemory).setOnClickListener(this);
        findViewById(R.id.buttonRecallMemory).setOnClickListener(this);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width,
                               int height) {
        if (previewing) {
            camera.stopPreview();
            previewing = false;
        }

        if (camera != null) {
            try {
                camera.setPreviewDisplay(surfaceHolder);
                camera.startPreview();
                previewing = true;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        camera = Camera.open();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        camera.stopPreview();
        camera.release();
        camera = null;
        previewing = false;
    }


    @Override
    public void onClick(View v) {
        final Animation animAlpha = AnimationUtils.loadAnimation(this, R.anim.alpha);

        String buttonPressed = ((Button) v).getText().toString();

        if (DIGITS.contains(buttonPressed)) {
            v.startAnimation(animAlpha);

            // digit was pressed
            if (userIsInTheMiddleOfTypingANumber) {

                if (buttonPressed.equals(".") && etResult.getText().toString().contains(".")) {

                } else {
                    etResult.append(buttonPressed);
                }

            } else {

                if (buttonPressed.equals(".")) {

                    etResult.setText(0 + buttonPressed);
                } else {
                    etResult.setText(buttonPressed);
                }

                userIsInTheMiddleOfTypingANumber = true;
            }

        } else {
            // operation was pressed
            if (userIsInTheMiddleOfTypingANumber) {

                mCalculatorBrain.setOperand(Double.parseDouble(etResult.getText().toString()));
                userIsInTheMiddleOfTypingANumber = false;
            }

            mCalculatorBrain.performOperation(buttonPressed);
            etResult.setText(df.format(mCalculatorBrain.getResult()));


        }

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // Save variables on screen orientation change
        outState.putDouble("OPERAND", mCalculatorBrain.getResult());
        outState.putDouble("MEMORY", mCalculatorBrain.getMemory());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        // Restore variables on screen orientation change
        mCalculatorBrain.setOperand(savedInstanceState.getDouble("OPERAND"));
        mCalculatorBrain.setMemory(savedInstanceState.getDouble("MEMORY"));
        etResult.setText(df.format(mCalculatorBrain.getResult()));
    }

}