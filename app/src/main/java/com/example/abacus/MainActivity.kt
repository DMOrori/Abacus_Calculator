package com.example.abacus

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.abacus.databinding.ActivityMainBinding
import com.google.android.material.button.MaterialButton
import net.objecthunter.exp4j.ExpressionBuilder // Add this to dependencies

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set up click listeners for all buttons
        setupButtons()
    }

    private fun setupButtons() {
        val grid = binding.grid
        for (i in 0 until grid.childCount) {
            val view = grid.getChildAt(i)
            if (view is MaterialButton) {
                view.setOnClickListener { onButtonClick(view.text.toString()) }
            }
        }
    }

    private fun onButtonClick(value: String) {
        when (value) {
            "AC" -> binding.display.setText("") // Task 3e: Clear
            "⌫" -> {
                val currentText = binding.display.text.toString()
                if (currentText.isNotEmpty()) {
                    binding.display.setText(currentText.dropLast(1))
                }
            }
            "=" -> calculateResult() // Task 3a-d
            "×" -> binding.display.append("*")
            "÷" -> binding.display.append("/")
            "−" -> binding.display.append("-")
            "( )" -> handleParentheses()
            else -> binding.display.append(value)
        }
    }

    private fun calculateResult() {
        val content = binding.display.text.toString()
        try {
            // Replace visual symbols with math symbols
            val expressionStr = content.replace("×", "*").replace("÷", "/")

            // Using a simple builder to evaluate
            val expression = ExpressionBuilder(expressionStr).build()
            val result = expression.evaluate()

            // Displaying result (handling decimals)
            val longResult = result.toLong()
            if (result == longResult.toDouble()) {
                binding.display.setText(longResult.toString())
            } else {
                binding.display.setText(result.toString())
            }
        } catch (e: Exception) {
            binding.display.setText("Error")
        }
    }

    private fun handleParentheses() {
        val text = binding.display.text.toString()
        val openCount = text.count { it == '(' }
        val closeCount = text.count { it == ')' }

        if (openCount == closeCount || text.endsWith("(")) {
            binding.display.append("(")
        } else {
            binding.display.append(")")
        }
    }
}