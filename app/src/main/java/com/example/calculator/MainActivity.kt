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
        
        // أزرار الأرقام
        findViewById<Button>(R.id.btn_0).setOnClickListener { addNumber("0") }
        findViewById<Button>(R.id.btn_1).setOnClickListener { addNumber("1") }
        findViewById<Button>(R.id.btn_2).setOnClickListener { addNumber("2") }
        findViewById<Button>(R.id.btn_3).setOnClickListener { addNumber("3") }
        findViewById<Button>(R.id.btn_4).setOnClickListener { addNumber("4") }
        findViewById<Button>(R.id.btn_5).setOnClickListener { addNumber("5") }
        findViewById<Button>(R.id.btn_6).setOnClickListener { addNumber("6") }
        findViewById<Button>(R.id.btn_7).setOnClickListener { addNumber("7") }
        findViewById<Button>(R.id.btn_8).setOnClickListener { addNumber("8") }
        findViewById<Button>(R.id.btn_9).setOnClickListener { addNumber("9") }
        
        findViewById<Button>(R.id.btn_clear).setOnClickListener { 
            currentNumber = ""
            firstNumber = ""
            operation = ""
            isNewNumber = true
            tvResult.text = "0"
        }
        
        findViewById<Button>(R.id.btn_plus).setOnClickListener { 
            if (currentNumber.isNotEmpty()) {
                firstNumber = currentNumber
                operation = "+"
                isNewNumber = true
            }
        }
        
        findViewById<Button>(R.id.btn_equal).setOnClickListener {
            if (firstNumber.isNotEmpty() && currentNumber.isNotEmpty()) {
                val first = firstNumber.toDouble()
                val second = currentNumber.toDouble()
                val result = when (operation) {
                    "+" -> first + second
                    else -> 0.0
                }
                tvResult.text = result.toString()
                currentNumber = result.toString()
                firstNumber = ""
                operation = ""
                isNewNumber = true
            }
        }
    }
    
    private fun addNumber(num: String) {
        if (isNewNumber) {
            currentNumber = ""
            isNewNumber = false
        }
        currentNumber += num
        tvResult.text = currentNumber
    }
}
