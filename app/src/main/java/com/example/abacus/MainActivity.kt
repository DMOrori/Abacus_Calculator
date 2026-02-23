package com.example.abacus

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var display: EditText
    private var firstNumber = 0.0
    private var operator = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        display = findViewById(R.id.display)

        val buttons = listOf(
            "0","1","2","3","4","5","6","7","8","9",
            "+","−","×","÷","=","AC"
        )

        buttons.forEach { text ->
            val btn = findButtonByText(text)
            btn?.setOnClickListener {
                handleInput(text)
            }
        }
    }

    private fun findButtonByText(text: String): Button? {
        val root = findViewById<android.view.ViewGroup>(android.R.id.content)
        return findButtonRecursive(root, text)
    }

    private fun findButtonRecursive(view: android.view.View, text: String): Button? {
        if (view is Button && view.text == text) return view
        if (view is android.view.ViewGroup) {
            for (i in 0 until view.childCount) {
                val result = findButtonRecursive(view.getChildAt(i), text)
                if (result != null) return result
            }
        }
        return null
    }

    private fun handleInput(value: String) {

        try {

            when(value) {

                "AC" -> {
                    display.setText("")
                    firstNumber = 0.0
                    operator = ""
                }

                "+","−","×","÷" -> {
                    if(display.text.isEmpty()) return
                    firstNumber = display.text.toString().toDouble()
                    operator = value
                    display.setText("")
                }

                "=" -> {
                    if(display.text.isEmpty()) return
                    val secondNumber = display.text.toString().toDouble()

                    val result = when(operator) {
                        "+" -> firstNumber + secondNumber
                        "−" -> firstNumber - secondNumber
                        "×" -> firstNumber * secondNumber
                        "÷" -> {
                            if(secondNumber == 0.0) {
                                display.setText("Error")
                                return
                            } else {
                                firstNumber / secondNumber
                            }
                        }
                        else -> 0.0
                    }

                    display.setText(result.toString())
                }

                else -> {
                    display.append(value)
                }
            }

        } catch (e: Exception) {
            display.setText("Error")
        }
    }
}