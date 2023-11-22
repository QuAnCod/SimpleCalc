package com.example.simplecalc;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView display;
    private double firstNumber = 0;
    private double secondNumber = 0;
    private String operation = "";
    private boolean isOperationPressed = false;
    private boolean isSecondNumberStarted = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        display = findViewById(R.id.display);

        setUpNumberButtons();
        setUpOperationButtons();
    }

    private void setUpNumberButtons() {
        int[] numberIds = {
                R.id.button0, R.id.button1, R.id.button2, R.id.button3, R.id.button4,
                R.id.button5, R.id.button6, R.id.button7, R.id.button8, R.id.button9
        };

        View.OnClickListener numberListener = v -> {
            Button b = (Button) v;
            if (isSecondNumberStarted) {
                display.append(b.getText());
            } else {
                display.setText(display.getText().toString() + b.getText());
                isSecondNumberStarted = true;
            }
        };

        for (int id : numberIds) {
            findViewById(id).setOnClickListener(numberListener);
        }
    }

    private void setUpOperationButtons() {
        int[] operationIds = {
                R.id.buttonAdd, R.id.buttonSubtract, R.id.buttonMultiply, R.id.buttonDivide, R.id.buttonExponent, R.id.buttonRoot
        };

        View.OnClickListener operationListener = v -> {
            Button b = (Button) v;
            if (!isOperationPressed) {
                firstNumber = Double.parseDouble(display.getText().toString());
                operation = b.getText().toString();
                display.append(" " + operation + " ");
                isOperationPressed = true;
                isSecondNumberStarted = operation.equals("√") ? false : true;
            }
        };

        for (int id : operationIds) {
            findViewById(id).setOnClickListener(operationListener);
        }

        findViewById(R.id.buttonEqual).setOnClickListener(v -> calculateResult());
        findViewById(R.id.buttonClear).setOnClickListener(v -> clearCalculator());
    }

    private void calculateResult() {
        if (!operation.isEmpty() && isSecondNumberStarted) {
            String[] parts = display.getText().toString().split(" ");
            if (parts.length >= 3) {
                secondNumber = Double.parseDouble(parts[2]);
            }

            double result = 0;

            switch (operation) {
                case "+":
                    result = firstNumber + secondNumber;
                    break;
                case "-":
                    result = firstNumber - secondNumber;
                    break;
                case "*":
                    result = firstNumber * secondNumber;
                    break;
                case "/":
                    if (secondNumber != 0) {
                        result = firstNumber / secondNumber;
                    } else {
                        display.setText("Error");
                        return;
                    }
                    break;
                case "^":
                    result = Math.pow(firstNumber, secondNumber);
                    break;
                case "√":
                    result = Math.sqrt(firstNumber);
                    break;
            }

            display.setText(String.valueOf(result));
            operation = "";
            isOperationPressed = false;
            isSecondNumberStarted = false;
        }
    }

    private void clearCalculator() {
        display.setText("");
        firstNumber = 0;
        secondNumber = 0;
        operation = "";
        isOperationPressed = false;
        isSecondNumberStarted = false;
    }
}