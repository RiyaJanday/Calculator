package com.example.calculator;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    TextView tvResult, tvHint;

    double firstNumber = 0;
    String operator = "";
    boolean isNewInput = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvResult = findViewById(R.id.tvresult);
        tvHint = findViewById(R.id.tvhint);

        tvResult.setText("0");

        // Number buttons
        int[] numberButtons = {
                R.id.btn0, R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4,
                R.id.btn5, R.id.btn6, R.id.btn7, R.id.btn8, R.id.btn9
        };

        for (int id : numberButtons) {
            findViewById(id).setOnClickListener(v ->
                    appendNumber(((Button) v).getText().toString())
            );
        }

        // Operators
        findViewById(R.id.btnadd).setOnClickListener(v -> setOperator("+"));
        findViewById(R.id.btnsub).setOnClickListener(v -> setOperator("-"));
        findViewById(R.id.btnmul).setOnClickListener(v -> setOperator("×"));
        findViewById(R.id.btndiv).setOnClickListener(v -> setOperator("÷"));

        findViewById(R.id.btneq).setOnClickListener(v -> calculate());

        findViewById(R.id.btnac).setOnClickListener(v -> clear());

        findViewById(R.id.btndec).setOnClickListener(v -> appendDecimal());

        findViewById(R.id.btncan).setOnClickListener(v -> backspace());
    }

    /* ---------- METHODS ---------- */

    private void appendNumber(String number) {
        if (isNewInput) {
            tvResult.setText(number);
            isNewInput = false;
        } else {
            tvResult.append(number);
        }
    }

    private void appendDecimal() {
        if (!tvResult.getText().toString().contains(".")) {
            tvResult.append(".");
        }
    }

    private void setOperator(String op) {
        try {
            firstNumber = Double.parseDouble(tvResult.getText().toString());
            operator = op;
            tvHint.setText(firstNumber + " " + operator);
            isNewInput = true;
        } catch (Exception e) {
            Toast.makeText(this, "Invalid input", Toast.LENGTH_SHORT).show();
        }
    }

    private void calculate() {
        try {
            double secondNumber = Double.parseDouble(tvResult.getText().toString());
            double result = 0;

            switch (operator) {
                case "+":
                    result = firstNumber + secondNumber;
                    break;
                case "-":
                    result = firstNumber - secondNumber;
                    break;
                case "×":
                    result = firstNumber * secondNumber;
                    break;
                case "÷":
                    if (secondNumber == 0) {
                        tvResult.setText("Error");
                        return;
                    }
                    result = firstNumber / secondNumber;
                    break;
            }

            tvResult.setText(removeDotZero(result));
            tvHint.setText(firstNumber + " " + operator + " " + secondNumber + " =");
            isNewInput = true;

        } catch (Exception e) {
            tvResult.setText("Error");
        }
    }

    private void clear() {
        tvResult.setText("0");
        tvHint.setText("");
        firstNumber = 0;
        operator = "";
        isNewInput = true;
    }

    private void backspace() {
        String text = tvResult.getText().toString();
        if (text.length() > 1) {
            tvResult.setText(text.substring(0, text.length() - 1));
        } else {
            tvResult.setText("0");
        }
    }

    private String removeDotZero(double value) {
        String result = String.valueOf(value);
        return result.endsWith(".0") ? result.replace(".0", "") : result;
    }
}
