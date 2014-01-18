package com.ua.android_helper.calc1;

/**
 * Created by andriistakhov on 12.10.13.
 */
public class CalcHelper {

    public enum OperatorsType {
        none, addition, subtraction, multiplication, division
    }

    private static double _firsValue;

    private static OperatorsType currentType = OperatorsType.none;


    public static OperatorsType getCurrentType() {
        return currentType;
    }

    public static void clear() {
        _firsValue = 0.0f;
        currentType = OperatorsType.none;
    }

    public static void addition(String value) {
        _firsValue = Double.valueOf(value);
        currentType = OperatorsType.addition;
    }

    public static void subtraction(String value) {
        _firsValue = Double.valueOf(value);
        currentType = OperatorsType.subtraction;
    }

    public static void multiplication(String value) {
        _firsValue = Double.valueOf(value);
        currentType = OperatorsType.multiplication;
    }

    public static void division(String value) {
        _firsValue = Double.valueOf(value);
        currentType = OperatorsType.division;
    }

    public static String summary(String value) {
        double secondValue = Double.valueOf(value);
        double summary = _firsValue;
        try {
            switch (currentType) {
                case addition:
                    summary += secondValue;
                    break;
                case subtraction:
                    summary -= secondValue;
                    break;
                case multiplication:
                    summary *= secondValue;
                    break;
                case division:
                    if (secondValue == 0)
                        return "Жаль, но делить нельзя!";
                    summary /= secondValue;
                    break;
                default:
                    return "Давайте уже считать!";
            }
        } finally {
            currentType = OperatorsType.none;
        }


        return String.valueOf(summary);
    }


}
