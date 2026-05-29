package com.example.calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    
    private lateinit var tvResult: TextView
    private var currentNumber = ""
    private var firstNumber = ""
    private var operation = ""
    private var isNewNumber = true
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        
        tvResult = findViewById(R.id.tv_result)
        tvResult.text = "0"
        
        findViewById<Button>(R.id.btn_0).setOnClickListener { appendNumber("0") }
        findViewById<Button>(R.id.btn_1).setOnClickListener { appendNumber("1") }
        findViewById<Button>(R.id.btn_2).setOnClickListener { appendNumber("2") }
        findViewById<Button>(R.id.btn_3).setOnClickListener { appendNumber("3") }
        findViewById<Button>(R.id.btn_4).setOnClickListener { appendNumber("4") }
        findViewById<Button>(R.id.btn_5).setOnClickListener { appendNumber("5") }
        findViewById<Button>(R.id.btn_6).setOnClickListener { appendNumber("6") }
        findViewById<Button>(R.id.btn_7).setOnClickListener { appendNumber("7") }
        findViewById<Button>(R.id.btn_8).setOnClickListener { appendNumber("8") }
        findViewById<Button>(R.id.btn_9).setOnClickListener { appendNumber("9") }
        
        findViewById<Button>(R.id.btn_plus).setOnClickListener { setOperation("+") }
        findViewById<Button>(R.id.btn_minus).setOnClickListener { setOperation("-") }
        findViewById<Button>(R.id.btn_multiply).setOnClickListener { setOperation("×") }
        findViewById<Button>(R.id.btn_divide).setOnClickListener { setOperation("÷") }
        findViewById<Button>(R.id.btn_equal).setOnClickListener { calculate() }
        findViewById<Button>(R.id.btn_clear).setOnClickListener { clearAll() }
        findViewById<Button>(R.id.btn_decimal).setOnClickListener { addDecimal() }
    }
    
    private fun appendNumber(num: String) {
        if (isNewNumber) {
            currentNumber = ""
            isNewNumber = false
        }
        currentNumber += num
        tvResult.text = currentNumber
    }
    
    private fun setOperation(op: String) {
        if (currentNumber.isNotEmpty()) {
            firstNumber = currentNumber
            operation = op
            isNewNumber = true
        }
    }
    
    private fun calculate() {
        if (firstNumber.isEmpty() || currentNumber.isEmpty() || operation.isEmpty()) {
            Toast.makeText(this, "أدخل عملية كاملة", Toast.LENGTH_SHORT).show()
            return
        }
        
        val first = firstNumber.toDoubleOrNull()
        val second = currentNumber.toDoubleOrNull()
        
        if (first == null || second == null) {
            Toast.makeText(this, "خطأ في الأرقام", Toast.LENGTH_SHORT).show()
            return
        }
        
        val result = when (operation) {
            "+" -> first + second
            "-" -> first - second
            "×" -> first * second
            "÷" -> if (second != 0.0) first / second else {
                Toast.makeText(this, "لا تقسم على صفر!", Toast.LENGTH_SHORT).show()
                return
            }
            else -> return
        }
        
        val resultText = if (result == result.toLong().toDouble()) {
            result.toLong().toString()
        } else {
            result.toString()
        }
        
        tvResult.text = resultText
        currentNumber = resultText
        firstNumber = ""
        operation = ""
        isNewNumber = true
    }
    
    private fun clearAll() {
        currentNumber = ""
        firstNumber = ""
        operation = ""
        isNewNumber = true
        tvResult.text = "0"
    }
    
    private fun addDecimal() {
        if (!currentNumber.contains(".")) {
            currentNumber += if (currentNumber.isEmpty()) "0." else "."
            tvResult.text = currentNumber
            isNewNumber = false
        }
    }
}
