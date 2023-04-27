package com.example.androidsession_03;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.androidsession_03.databinding.ActivityCalculatorBinding;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class CalculatorActivity extends AppCompatActivity implements View.OnClickListener {

    List<String> notation = new ArrayList<>();
    BigDecimal finalResult = new BigDecimal(0);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityCalculatorBinding binding = ActivityCalculatorBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        // 取消标题栏
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        // 绑定按钮
        binding.button1.setOnClickListener(this);
        binding.button2.setOnClickListener(this);
        binding.button3.setOnClickListener(this);
        binding.button4.setOnClickListener(this);
        binding.button5.setOnClickListener(this);
        binding.button6.setOnClickListener(this);
        binding.button7.setOnClickListener(this);
        binding.button8.setOnClickListener(this);
        binding.button9.setOnClickListener(this);
        binding.button10.setOnClickListener(this);
        binding.button11.setOnClickListener(this);
        binding.button12.setOnClickListener(this);
        binding.button13.setOnClickListener(this);
        binding.button14.setOnClickListener(this);
        binding.button15.setOnClickListener(this);
        binding.button16.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        if (v instanceof Button) {
            Button button = (Button) v;
            // 获取按钮上的字符
            String character = button.getText().toString();
            // 获取文本框组件
            TextView textView = findViewById(R.id.textView);
            // 显示文本框内容
            if (textView.getText().toString().equals("0")
                    || textView.getText().toString().equals("00")
                    || textView.getText().toString().equals(finalResult.toString())) {
                notation = new ArrayList<>();
                finalResult = new BigDecimal(0);
                textView.setText(character);
            } else {
                textView.append(character);
            }
            // 字符推入栈中
            isOperatorAndPop(character);
        }
    }

    /**
     * 将表达式推入栈中
     */
    public void isOperatorAndPop(String c) {
        if (!isOperator(c)) {
            String peek = "";
            if (notation.size() != 0) {
                // 判断连续的数字
                peek = notation.get(notation.size() - 1);
            }
            if (!isOperator(peek) && peek.length() != 0) {
                // 连续的数字字符
                peek += c;
                notation.set(notation.size() - 1, peek);
            } else {
                // 不连续的数字字符
                notation.add(c);
            }
        } else {
            if (isOperator(notation.get(notation.size() - 1))) {
                // 最后一个是操作符？更换操作符
                notation.set(notation.size() - 1, c);
            } else if (!c.equals("=")) {
                // 添加操作符
                notation.add(c);
            } else {
                // 等于则计算结果
                finalResult = calculate();
                TextView textView = findViewById(R.id.textView);
                textView.setText(String.valueOf(finalResult));
            }
        }
    }

    public boolean isOperator(String c) {
        switch (c) {
            case "+":
            case "-":
            case "×":
            case "÷":
            case "=":
                return true;
        }
        return false;
    }


    public BigDecimal calculate() {
        Stack<String> numbers = new Stack<>();
        List<String> notationList = reversePolishNotationList();
        // 遍历后缀表达式并开始运算
        for (String item : notationList) {
            if (!isOperator(item)) {
                // 将数字压入栈中
                numbers.push(item);
            } else {
                // 是操作符，pop 出两个数字进行计算并压入栈中
                BigDecimal num2 = BigDecimal.valueOf(Double.valueOf(numbers.pop()));
                BigDecimal num1 = BigDecimal.valueOf(Double.valueOf(numbers.pop()));
                BigDecimal result = null;
                if (item.equals("+")) {
                    result = num1.add(num2);
                } else if (item.equals("-")) {
                    result = num1.subtract(num2);
                } else if (item.equals("×")) {
                    result = num1.multiply(num2);
                } else if (item.equals("÷")) {
                    result = num1.divide(num2);
                } else {
                    throw new RuntimeException("运算符错误");
                }
                numbers.push(String.valueOf(result));
            }
        }
        // 遍历完成，栈中只剩下一个数字，即结果
        return BigDecimal.valueOf(Double.valueOf(numbers.pop()));
    }


    private List<String> reversePolishNotationList() {
        // 将中缀表达式转换为后缀表达式
        Stack<String> operators = new Stack<>();
        List<String> reversePolishNotationList = new ArrayList<>();
        for (String s : notation) {
            if (!isOperator(s)) {
                // 先添加数字
                reversePolishNotationList.add(s);
            } else {
                while (operators.size() != 0 && priority(operators.peek()) >= priority(s)) {
                    // 如果栈顶的操作符优先级比即将添加的操作符大，优先添加栈顶的操作符
                    reversePolishNotationList.add(operators.pop());
                }
                // 将操作符单独压入栈中
                operators.push(s);
            }
        }
        // 栈中还剩下操作符，pop 到集合中
        while (operators.size() != 0) {
            reversePolishNotationList.add(operators.pop());
        }
        return reversePolishNotationList;
    }

    /**
     * 运算符优先级
     *
     * @param operator 运算符
     * @return 返回优先级
     */
    public int priority(String operator) {
        int priority = 0;
        switch (operator) {
            case "+":
            case "-":
                priority = 1;
                break;
            case "×":
            case "÷":
                priority = 2;
                break;
            default:
                break;
        }
        return priority;
    }
}