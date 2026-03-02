package com.example.calculator

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var display: TextView
    private var firstOperand: Double? = null
    private var pendingOperation: String? = null
    private var shouldClearDisplay = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        display = findViewById(R.id.display)

        setupNumberButtons()
        setupOperatorButtons()
        setupUtilityButtons()
    }

    private fun setupNumberButtons() {
        val numberButtonIds = listOf(
            R.id.btn_0,
            R.id.btn_1,
            R.id.btn_2,
            R.id.btn_3,
            R.id.btn_4,
            R.id.btn_5,
            R.id.btn_6,
            R.id.btn_7,
            R.id.btn_8,
            R.id.btn_9
        )

        numberButtonIds.forEach { id ->
            findViewById<Button>(id).setOnClickListener {
                val number = (it as Button).text.toString()
                appendToDisplay(number)
            }
        }

        findViewById<Button>(R.id.btn_dot).setOnClickListener {
            if (shouldClearDisplay) {
                display.text = "0"
                shouldClearDisplay = false
            }
            if (!display.text.contains('.')) {
                display.text = "${display.text}."
            }
        }
    }

    private fun setupOperatorButtons() {
        val operatorMap = mapOf(
            R.id.btn_add to "+",
            R.id.btn_subtract to "-",
            R.id.btn_multiply to "*",
            R.id.btn_divide to "/"
        )

        operatorMap.forEach { (id, op) ->
            findViewById<Button>(id).setOnClickListener {
                calculateIfPossible()
                firstOperand = display.text.toString().toDoubleOrNull()
                pendingOperation = op
                shouldClearDisplay = true
            }
        }

        findViewById<Button>(R.id.btn_equals).setOnClickListener {
            calculateIfPossible()
            pendingOperation = null
        }
    }

    private fun setupUtilityButtons() {
        findViewById<Button>(R.id.btn_clear).setOnClickListener {
            display.text = "0"
            firstOperand = null
            pendingOperation = null
            shouldClearDisplay = false
        }
    }

    private fun appendToDisplay(value: String) {
        if (display.text == "0" || shouldClearDisplay) {
            display.text = value
            shouldClearDisplay = false
        } else {
            display.text = "${display.text}$value"
        }
    }

    private fun calculateIfPossible() {
        val first = firstOperand
        val operation = pendingOperation
        val second = display.text.toString().toDoubleOrNull()

        if (first == null || operation == null || second == null) return

        val result = when (operation) {
            "+" -> first + second
            "-" -> first - second
            "*" -> first * second
            "/" -> if (second == 0.0) Double.NaN else first / second
            else -> return
        }

        display.text = formatResult(result)
        firstOperand = result
        shouldClearDisplay = true
    }

    private fun formatResult(value: Double): String {
        if (value.isNaN()) return getString(R.string.error_divide_by_zero)
        return if (value % 1.0 == 0.0) value.toLong().toString() else value.toString()
    }
}
