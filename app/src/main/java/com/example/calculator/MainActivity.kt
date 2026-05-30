package com.example.calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import android.util.Log

class MainActivity : AppCompatActivity() {
    
    private lateinit var tvResult: TextView
    private var currentNumber = ""
    private var firstNumber = ""
    private var operation = ""
    private var isNewNumber = true
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        try {
            setContentView(R.layout.activity_main)
            
            tvResult = findViewById(R.id.tv_result)
            if (tvResult == null) {
                Log.e("Calculator", "tvResult is null!")
                return
            }
            tvResult.text = "0"
            
            // أزرار الأرقام
            val numbers = mapOf(
                R.id.btn_0 to "0", R.id.btn_1 to "1", R.id.btn_2 to "2",
                R.id.btn_3 to "3", R.id.btn_4 to "4", R.id.btn_5 to "5",
                R.id.btn_6 to "6", R.id.btn_7 to "7", R.id.btn_8 to "8", 
                R.id.btn_9 to "9"
            )
            
            numbers.forEach { id, value ->
                val button = findViewById<Button>(id)
                if (button != null) {
                    button.setOnClickListener { appendNumber(value) }
                }
            }
            
            // أزرار العمليات
            findViewById<Button>(R.id.btn_plus)?.setOnClickListener { setOperation("+") }
            findViewById<Button>(R.id.btn_minus)?.setOnClickListener { setOperation("-") }
            findViewById<Button>(R.id.btn_multiply)?.setOnClickListener { setOperation("×") }
            findViewById<Button>(R.id.btn_divide)?.setOnClickListener { setOperation("÷") }
            findViewById<Button>(R.id.btn_equal)?.setOnClickListener { calculate() }
            findViewById<Button>(R.id.btn_clear)?.setOnClickListener { clear() }
            findViewById<Button>(R.id.btn_decimal)?.setOnClickListener { addDecimal() }
            
        } catch (e: Exception) {
            Log.e("Calculator", "Error in onCreate: ${e.message}")
            Toast.makeText(this, "خطأ في التشغيل: ${e.message}", Toast.LENGTH_LONG).show()
        }
    }
    
    private fun appendNumber(num: String) {
        try {
            if (isNewNumber) {
                currentNumber = ""
                isNewNumber = false
            }
            currentNumber += num
            tvResult.text = currentNumber
        } catch (e: Exception) {
            Toast.makeText(this, "خطأ: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }
    
    private fun setOperation(op: String) {
        try {
            if (currentNumber.isNotEmpty()) {
                firstNumber = currentNumber
                operation = op
                isNewNumber = true
            }
        } catch (e: Exception) {
            Toast.makeText(this, "خطأ: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }
    
    private fun calculate() {
        try {
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
            
        } catch (e: Exception) {
            Toast.makeText(this, "خطأ في الحساب: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }
    
    private fun clear() {
        currentNumber = ""
        firstNumber = ""
        operation = ""
        isNewNumber = true
        tvResult.text = "0"
    }
    
    private fun addDecimal() {
        try {
            if (!currentNumber.contains(".")) {
                currentNumber += if (currentNumber.isEmpty()) "0." else "."
                tvResult.text = currentNumber
                isNewNumber = false
            }
        } catch (e: Exception) {
            Toast.makeText(this, "خطأ: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }
}
