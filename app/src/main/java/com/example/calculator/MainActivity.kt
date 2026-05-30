package com.example.calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import android.os.Build

class MainActivity : AppCompatActivity() {
    
    private lateinit var tvResult: TextView
    private var currentNumber = ""
    private var firstNumber = ""
    private var operation = ""
    private var isNewNumber = true
    private lateinit var vibrator: Vibrator
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        
        vibrator = getSystemService(VIBRATOR_SERVICE) as Vibrator
        
        tvResult = findViewById(R.id.tv_result)
        tvResult.text = "0"
        
        // تحميل التأثيرات
        val pressAnim = AnimationUtils.loadAnimation(this, R.anim.button_press)
        val releaseAnim = AnimationUtils.loadAnimation(this, R.anim.button_release)
        
        // إعداد الأزرار مع التأثيرات
        val numbers = mapOf(
            R.id.btn_0 to "0", R.id.btn_1 to "1", R.id.btn_2 to "2",
            R.id.btn_3 to "3", R.id.btn_4 to "4", R.id.btn_5 to "5",
            R.id.btn_6 to "6", R.id.btn_7 to "7", R.id.btn_8 to "8", R.id.btn_9 to "9"
        )
        
        numbers.forEach { id, value ->
            val button = findViewById<Button>(id)
            button.setOnTouchListener { view, event ->
                when (event.action) {
                    android.view.MotionEvent.ACTION_DOWN -> {
                        view.startAnimation(pressAnim)
                        vibrate(50)
                    }
                    android.view.MotionEvent.ACTION_UP -> {
                        view.startAnimation(releaseAnim)
                        appendNumber(value)
                    }
                }
                true
            }
        }
        
        // أزرار العمليات
        setupInteractiveButton(R.id.btn_plus) { setOperation("+") }
        setupInteractiveButton(R.id.btn_minus) { setOperation("-") }
        setupInteractiveButton(R.id.btn_multiply) { setOperation("×") }
        setupInteractiveButton(R.id.btn_divide) { setOperation("÷") }
        setupInteractiveButton(R.id.btn_equal) { calculate() }
        setupInteractiveButton(R.id.btn_clear) { clear() }
        setupInteractiveButton(R.id.btn_decimal) { addDecimal() }
    }
    
    private fun setupInteractiveButton(buttonId: Int, action: () -> Unit) {
        val pressAnim = AnimationUtils.loadAnimation(this, R.anim.button_press)
        val releaseAnim = AnimationUtils.loadAnimation(this, R.anim.button_release)
        val button = findViewById<Button>(buttonId)
        
        button.setOnTouchListener { view, event ->
            when (event.action) {
                android.view.MotionEvent.ACTION_DOWN -> {
                    view.startAnimation(pressAnim)
                    vibrate(50)
                }
                android.view.MotionEvent.ACTION_UP -> {
                    view.startAnimation(releaseAnim)
                    action()
                }
            }
            true
        }
    }
    
    private fun vibrate(duration: Long) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            vibrator.vibrate(VibrationEffect.createOneShot(duration, VibrationEffect.DEFAULT_AMPLITUDE))
        } else {
            vibrator.vibrate(duration)
        }
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
    
    private fun clear() {
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
