package com.example.alpro

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.widget.Button
import android.widget.TextView
import kotlin.math.pow
import kotlin.math.sqrt

class MainActivity : AppCompatActivity() {
    val angkaNol: Int = 0
    val angkaSatu: Int = 1
    val angkaDua: Int = 2
    val angkaTiga: Int = 3
    val angkaEmpat: Int = 4
    val angkaLima: Int = 5
    val angkaEnam: Int = 6
    val angkaTujuh: Int = 7
    val angkaDelapan: Int = 8
    val angkaSembilan: Int = 9
    var koma: String = ","
    val tambah: String = "+"
    val kurang: String = "-"
    val kali: String = "×"
    val bagi: String = "÷"
    val persen: String = "%"
    val akar: String = "√"
    val pangkat: String = "^"
    val bukaKurung: String = "("
    val tutupKurung: String = ")"
    val satuPer: String = "⅟"

    private lateinit var textView: TextView
    private val textList = mutableListOf<String>()

    // memanggil antarmuka atau tampilan
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        textView = findViewById(R.id.text_view)
        val button0: Button = findViewById(R.id.btn_0)
        val button1: Button = findViewById(R.id.btn_1)
        val button2: Button = findViewById(R.id.btn_2)
        val button3: Button = findViewById(R.id.btn_3)
        val button4: Button = findViewById(R.id.btn_4)
        val button5: Button = findViewById(R.id.btn_5)
        val button6: Button = findViewById(R.id.btn_6)
        val button7: Button = findViewById(R.id.btn_7)
        val button8: Button = findViewById(R.id.btn_8)
        val button9: Button = findViewById(R.id.btn_9)
        val buttonKoma: Button = findViewById(R.id.btn_koma)
        val buttonC: Button = findViewById(R.id.btn_c)
        val buttonTambah: Button = findViewById(R.id.btn_tambah)
        val buttonKurang: Button = findViewById(R.id.btn_kurang)
        val buttonKali: Button = findViewById(R.id.btn_kali)
        val buttonBagi: Button = findViewById(R.id.btn_bagi)
        val buttonPersen: Button = findViewById(R.id.btn_persen)
        val buttonAkar: Button = findViewById(R.id.btn_akar)
        val buttonPangkat: Button = findViewById(R.id.btn_pangkat)
        val buttonBK: Button = findViewById(R.id.btn_bukaKurung)
        val buttonTK: Button = findViewById(R.id.btn_tutupKurung)
        val buttonSP: Button = findViewById(R.id.btn_satuPer)
        val buttonE: Button = findViewById(R.id.btn_hapus)
        val buttonSamaDengan: Button = findViewById(R.id.btn_samaDengan)

        button0.setOnClickListener {
            appendText(angkaNol.toString())
        }

        button1.setOnClickListener {
            appendText(angkaSatu.toString())
        }

        button2.setOnClickListener {
            appendText(angkaDua.toString())
        }

        button3.setOnClickListener {
            appendText(angkaTiga.toString())
        }

        button4.setOnClickListener {
            appendText(angkaEmpat.toString())
        }

        button5.setOnClickListener {
            appendText(angkaLima.toString())
        }

        button6.setOnClickListener {
            appendText(angkaEnam.toString())
        }

        button7.setOnClickListener {
            appendText(angkaTujuh.toString())
        }

        button8.setOnClickListener {
            appendText(angkaDelapan.toString())
        }

        button9.setOnClickListener {
            appendText(angkaSembilan.toString())
        }

        buttonKoma.setOnClickListener {
            appendText(koma.toString())
        }

        buttonTambah.setOnClickListener {
            appendText(tambah.toString())
        }

        buttonKurang.setOnClickListener {
            appendText(kurang.toString())
        }

        buttonKali.setOnClickListener {
            appendText(kali.toString())
        }

        buttonBagi.setOnClickListener {
            appendText(bagi.toString())
        }

        buttonPersen.setOnClickListener {
            performImmediateOperation(persen.toString())
        }

        buttonAkar.setOnClickListener {
            performImmediateOperation(akar.toString())
        }

        buttonPangkat.setOnClickListener {
            appendText(pangkat.toString())
        }

        buttonBK.setOnClickListener {
            appendText(bukaKurung.toString())
        }

        buttonTK.setOnClickListener {
            appendText(tutupKurung.toString())
        }

        buttonSP.setOnClickListener {
            appendText(satuPer.toString())
        }

        buttonE.setOnClickListener {
            eraseText()
        }

        buttonC.setOnClickListener {
            clearText()
        }

        buttonSamaDengan.setOnClickListener {
            calculateResult()
        }
        // Kode ini digunakan untuk menyesuaikan tata letak (layout) aplikasi dengan area layar yang dipengaruhi oleh "window insets," seperti status bar, navigation bar, atau area lain yang menghalangi tampilan konten
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun appendText(newText: String) {
        textList.add(newText)
        updateTextView()
    }

    private fun clearText() {
        textList.clear()
        updateTextView()
    }

    private fun eraseText() {
        if (textList.isNotEmpty()) {
            textList.removeAt(textList.size - 1)
            updateTextView()
        }
    }

    private fun calculateResult() {
        // mengubah textList menjadi string tunggal
        val expression = textList.joinToString("")

        try {
            // menghitung hasil penjumlahan, pengurangan, perkalian, pembagian, perkalian satu, dan tanda kurung
            val result = evaluateExpression(expression)
            textList.clear()
            textList.add(result.toString())
            updateTextView()
        } catch (e: Exception) {
            // menampilkan error jika expression tidak bisa di evaluate
            textView.text = "Error"
        }
    }

    private fun evaluateExpression(expression: String): Double {
        // Fungsi ini menangani tanda kurung dan memanggil fungsi pembantu untuk mengevaluasi ekspresi bagian dalam
        val operators = listOf('+', '-', '×', '÷', '⅟', '^')
        val tokens = tokenize(expression)
        return evaluateTokens(tokens)
    }

    private fun tokenize(expression: String): List<String> {
        val operators = setOf('+', '-', '×', '÷', '⅟', '^', '(', ')')
        val tokens = mutableListOf<String>()
        var currentNumber = StringBuilder()

        for (char in expression) {
            if (char in operators) {
                if (currentNumber.isNotEmpty()) {
                    tokens.add(currentNumber.toString())
                    currentNumber = StringBuilder()
                }
                tokens.add(char.toString())
            } else {
                currentNumber.append(char)
            }
        }
        if (currentNumber.isNotEmpty()) {
            tokens.add(currentNumber.toString())
        }
        return tokens
    }

    private fun evaluateTokens(tokens: List<String>): Double {
        val stack = mutableListOf<Any>()
        val operatorPrecedence = mapOf('+' to 1, '-' to 1, '×' to 2, '÷' to 2, '⅟' to 3, '^' to 4)

        var i = 0
        while (i < tokens.size) {
            when (val token = tokens[i]) {
                "(" -> {
                    // menemukan tanda kurung penutup yang sesuai
                    var count = 1
                    val subExpression = mutableListOf<String>()
                    i++
                    while (i < tokens.size && count > 0) {
                        if (tokens[i] == "(") count++
                        if (tokens[i] == ")") count--
                        if (count > 0) subExpression.add(tokens[i])
                        i++
                    }
                    stack.add(evaluateTokens(subExpression))
                }
                in operatorPrecedence.keys.map { it.toString() } -> stack.add(token)
                else -> stack.add(token.toDouble())
            }
            i++
        }

        return evaluateStack(stack, operatorPrecedence)
    }

    private fun evaluateStack(stack: MutableList<Any>, operatorPrecedence: Map<Char, Int>): Double {
        // Fungsi ini mengevaluasi daftar token datar
        var i = 0
        while (i < stack.size) {
            if (stack[i] == "⅟") {
                val result = 1 / (stack[i + 1] as Double)
                stack[i] = result
                stack.removeAt(i + 1)
            } else {
                i++
            }
        }

        var j = 0
        while (j < stack.size) {
            if (stack[j] == "^") {
                val result = (stack[j - 1] as Double).pow(stack[j + 1] as Double)
                stack[j - 1] = result
                stack.removeAt(j)
                stack.removeAt(j)
            } else {
                j++
            }
        }

        var k = 0
        while (k < stack.size) {
            if (stack[k] == "×" || stack[k] == "÷") {
                val result = when (stack[k]) {
                    "×" -> (stack[k - 1] as Double) * (stack[k + 1] as Double)
                    "÷" -> (stack[k - 1] as Double) / (stack[k + 1] as Double)
                    else -> throw IllegalArgumentException("Invalid operator")
                }
                stack[k - 1] = result
                stack.removeAt(k)
                stack.removeAt(k)
            } else {
                k++
            }
        }

        var result = stack[0] as Double
        var l = 1
        while (l < stack.size) {
            val operator = stack[l] as String
            val nextNumber = stack[l + 1] as Double
            result = when (operator) {
                "+" -> result + nextNumber
                "-" -> result - nextNumber
                else -> throw IllegalArgumentException("Invalid operator")
            }
            l += 2
        }

        return result
    }

    private fun performImmediateOperation(operation: String) {
        val currentNumber = textList.joinToString("")
        if (currentNumber.isNotEmpty()) {
            try {
                val number = currentNumber.toDouble()
                val result = when (operation) {
                    "%" -> number / 100
                    "√" -> sqrt(number)
                    else -> throw IllegalArgumentException("Unknown operation")
                }
                textList.clear()
                textList.add(result.toString())
                updateTextView()
            } catch (e: Exception) {
                textView.text = "Error"
            }
        }
    }

    private fun updateTextView() {
        textView.text = textList.joinToString("")
    }
}
