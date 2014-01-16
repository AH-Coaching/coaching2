package com.motiv.calc;


    import android.app.Activity;
    import android.os.Bundle;
    import android.view.Gravity;
    import android.view.Menu;
    import android.view.View;
    import android.widget.Button;
    import android.widget.EditText;
    import android.widget.Toast;
    import java.util.EnumMap;

    import com.motiv.calc.enums.ActionType;
    import com.motiv.calc.enums.OperationType;
    import com.motiv.calc.enums.Symbol;

public class MainActivity extends Activity {

    private EditText txtResult;

    private Button btnAdd;
    private Button btnDivide;
    private Button btnMultiply;
    private Button btnSubtract;

    private OperationType operType;

    private EnumMap<Symbol, Object> commands = new EnumMap<Symbol, Object>(
            Symbol.class); // хранит все введенные данные пользователя

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtResult = (EditText) findViewById(R.id.etResult);

        btnAdd = (Button) findViewById(R.id.btnAdd);
        btnDivide = (Button) findViewById(R.id.btnDivide);
        btnMultiply = (Button) findViewById(R.id.btnMultiply);
        btnSubtract = (Button) findViewById(R.id.btnSubtract);

        // к каждой кнопке добавить тип операции
        btnAdd.setTag(OperationType.ADD);
        btnDivide.setTag(OperationType.DIVIDE);
        btnMultiply.setTag(OperationType.MULTIPLY);
        btnSubtract.setTag(OperationType.SUBTRACT);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    private void showToastMessage(int messageId) {
        Toast toastMessage = Toast.makeText(this, messageId, Toast.LENGTH_LONG);
        toastMessage.setGravity(Gravity.TOP, 0, 100);
        toastMessage.show();
    }

    private ActionType lastAction;

    public void buttonClick(View view) {

        switch (view.getId()) {
            case R.id.btnAdd:
            case R.id.btnSubtract:
            case R.id.btnDivide:
            case R.id.btnMultiply: {// кнопка - одна из операций

                operType = (OperationType) view.getTag();// получить тип операции из кнопки

                if (lastAction == ActionType.OPERAION) {
                    commands.put(Symbol.OPERATION, operType);
                    return;
                }

                if (!commands.containsKey(Symbol.OPERATION)) {

                    if (!commands.containsKey(Symbol.FIRST_DIGIT)) {
                        commands.put(Symbol.FIRST_DIGIT, txtResult.getText());
                    }

                    commands.put(Symbol.OPERATION, operType);
                } else if (!commands.containsKey(Symbol.SECOND_DIGIT)) {
                    commands.put(Symbol.SECOND_DIGIT, txtResult.getText());
                    doCalc();
                    commands.put(Symbol.OPERATION, operType);
                    commands.remove(Symbol.SECOND_DIGIT);
                }

                lastAction = ActionType.OPERAION;

                break;
            }


            case R.id.btnResult: {// кнопка посчитать результат

                if (lastAction == ActionType.CALCULATION) return;

                if (commands.containsKey(Symbol.FIRST_DIGIT)
                        && commands.containsKey(Symbol.OPERATION)) {// если ввели число, нажали
                    // знак операции и ввели второе число
                    commands.put(Symbol.SECOND_DIGIT, txtResult.getText());

                    doCalc(); // посчитать

                    commands.clear();
                }

                lastAction = ActionType.CALCULATION;

                break;
            }

            case R.id.btnClear: { // кнопка удаления последнего символа
                txtResult.setText(txtResult.getText().delete(
                        txtResult.getText().length() - 1,
                        txtResult.getText().length()));

                if (txtResult.getText().toString().trim().length() == 0) {
                    txtResult.setText("0");
                }

                lastAction = ActionType.DELETE;

                break;
            }

            case R.id.btnDot: { // кнопка для ввода десятичного числа

                if (commands.containsKey(Symbol.FIRST_DIGIT)
                        && getDouble(txtResult.getText().toString()) == getDouble(commands
                        .get(Symbol.FIRST_DIGIT).toString())

                        ) {

                    txtResult
                            .setText("0" + view.getContentDescription().toString());
                }

                if (!txtResult.getText().toString().contains(",")) {
                    txtResult.setText(txtResult.getText() + ",");
                }

                lastAction = ActionType.COMMA;

                break;
            }


            default: { // все остальные кнопки (цифры)

                if (txtResult.getText().toString().equals("0")
                        ||

                        (commands.containsKey(Symbol.FIRST_DIGIT) && getDouble(txtResult
                                .getText()) == getDouble(commands
                                .get(Symbol.FIRST_DIGIT)))// если вводится второе число - то нужно
                        // сбросить текстовое поле

                        || (lastAction == ActionType.CALCULATION)

                        ) {

                    txtResult.setText(view.getContentDescription().toString());
                } else {
                    txtResult.setText(txtResult.getText()
                            + view.getContentDescription().toString());
                }

                lastAction = ActionType.DIGIT;

            }

        }
    }

    private double getDouble(Object value) {
        double result = 0;
        try {
            result = Double.valueOf(value.toString().replace(',', '.'))
                    .doubleValue();// замена запятой на точку
        } catch (Exception e) {
            e.printStackTrace();
            result = 0;
        }

        return result;
    }

    private void doCalc() {

        OperationType operTypeTmp = (OperationType) commands
                .get(Symbol.OPERATION);

        double result = 0;

        try {
            result = calc(operTypeTmp,
                    getDouble(commands.get(Symbol.FIRST_DIGIT)),
                    getDouble(commands.get(Symbol.SECOND_DIGIT)));

        } catch (DivisionByZeroException e) {
            showToastMessage(R.string.division_zero);
            return;
        }

        if (result % 1 == 0) {
            txtResult.setText(String.valueOf((int) result));// отсекать нули
            // после запятой
        } else {
            txtResult.setText(String.valueOf(result));// отсекать нули после запятой
        }

        commands.put(Symbol.FIRST_DIGIT, result);// записать полученный результат в первое число,
        // чтобы можно было сразу выполнять новые операции

    }

    private Double calc(OperationType operType, double a, double b) {
        switch (operType) {
            case ADD: {
                return CalcOperations.add(a, b);
            }
            case DIVIDE: {
                return CalcOperations.divide(a, b);
            }
            case MULTIPLY: {
                return CalcOperations.multiply(a, b);
            }
            case SUBTRACT: {
                return CalcOperations.subtract(a, b);
            }
        }

        return null;
    }

}