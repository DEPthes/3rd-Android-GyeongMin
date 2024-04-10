package com.example.android_basic_study_01

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.view.View
import android.widget.Button
import android.widget.Toast
import com.example.android_basic_study_01.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), View.OnClickListener {
    lateinit var binding: ActivityMainBinding

    private var isOperator = false
    private var hasOperator = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 버튼 클릭 리스너 등록
        listOf(binding.bt0, binding.bt1, binding.bt2, binding.bt3, binding.bt4, binding.bt5, binding.bt6, binding.bt7, binding.bt8, binding.bt9,
            binding.C, binding.add, binding.sub, binding.mul, binding.div, binding.mod, binding.equal).forEach {
            it.setOnClickListener(this)
        }

        // 클리어 버튼 클릭 이벤트 처리
        binding.C.setOnClickListener { clearFields() }

    }
    override fun onClick(v: View?) {
        v as Button? // 안전한 타입 변환
        when (v) {
            binding.bt0 -> numberButtonClicked("0")
            binding.bt1 -> numberButtonClicked("1")
            binding.bt2 -> numberButtonClicked("2")
            binding.bt3 -> numberButtonClicked("3")
            binding.bt4 -> numberButtonClicked("4")
            binding.bt5 -> numberButtonClicked("5")
            binding.bt6 -> numberButtonClicked("6")
            binding.bt7 -> numberButtonClicked("7")
            binding.bt8 -> numberButtonClicked("8")
            binding.bt9 -> numberButtonClicked("9")

            binding.add -> operatorButtonClicked("+")
            binding.sub -> operatorButtonClicked("-")
            binding.mul -> operatorButtonClicked("X")
            binding.div -> operatorButtonClicked("/")
            binding.mod -> operatorButtonClicked("%")
            binding.equal -> resultButtonClicked()
        }
    }

    private fun numberButtonClicked(number: String) {
        if (isOperator) {
            binding.expression.append(" ")
        }
        isOperator = false

        val expressionText = binding.expression.text.split(" ")
        if (expressionText.isNotEmpty() && expressionText.last().length >= 15) {
            Toast.makeText(this, "15자리 까지만 사용할 수 있습니다.", Toast.LENGTH_SHORT).show()
            return
        } else if (expressionText.last().isEmpty() && number == "0") {
            Toast.makeText(this, "0은 제일 앞에 올 수 없습니다.", Toast.LENGTH_SHORT).show()
            return
        }
        binding.expression.append(number)
        binding.result.text = calculateExpression()
    }

    private fun operatorButtonClicked(operator: String) {
        if (binding.expression.text.isEmpty()) {
            return
        }

        when {
            isOperator -> {
                val text = binding.expression.text.toString()
                val newText = text.substring(0, text.length - 1) + operator
                binding.expression.setText(newText)

            }
            hasOperator -> {
                Toast.makeText(this, "연산자는 한번만 사용할 수 있습니다.", Toast.LENGTH_SHORT).show()
                return
            }
            else -> {
                binding.expression.append(" $operator")
            }

        }
        val ssb = SpannableStringBuilder(binding.expression.text)
        ssb.setSpan(
            ForegroundColorSpan(getColor(R.color.green)),
            binding.expression.text.length - 1, binding.expression.text.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        binding.expression.text = ssb
        isOperator = true
        hasOperator = true
    }

    fun resultButtonClicked() {
        val expressionTexts = binding.expression.text.split(" ")
        if (binding.expression.text.isEmpty() || expressionTexts.size == 1) {
            return
        }
        if (expressionTexts.size != 3 && hasOperator) {
            Toast.makeText(this, "수식을 완성해주세요.", Toast.LENGTH_SHORT).show()
            return
        }
        if (expressionTexts[0].isNumber().not() || expressionTexts[2].isNumber().not()) {
            Toast.makeText(this, "오류가 발생했습니다.", Toast.LENGTH_SHORT).show()

            return
        }
        val resultText = calculateExpression()

        // expression에 결과 표시
        val editableResult = SpannableStringBuilder(resultText ?: "")
        binding.expression.text = editableResult
        binding.result.text = "result: "

        isOperator = false
        hasOperator = false


    }


    private fun calculateExpression(): String {
        val expressionTexts = binding.expression.text.split(" ")

        if (hasOperator.not() || expressionTexts.size != 3) {
            return ""
        } else if (expressionTexts[0].isNumber().not() || expressionTexts[2].isNumber().not()) {
            return ""
        }
        val exp1 = expressionTexts[0].toBigInteger()
        val exp2 = expressionTexts[2].toBigInteger()
        val op = expressionTexts[1]

        return when (op) {
            "+" -> (exp1 + exp2).toString()
            "-" -> (exp1 - exp2).toString()
            "X" -> (exp1 * exp2).toString()
            "%" -> (exp1 % exp2).toString()
            "/" -> (exp1 / exp2).toString()
            else -> ""
        }
    }

    fun String.isNumber(): Boolean {
        return try {
            this.toBigInteger()
            true
        } catch (e: NumberFormatException) {
            false
        }
    }

    // 필드 초기화 메소드
    private fun clearFields() {
        binding.expression.text.clear()
        binding.result.text = "Result: "
        isOperator = false
        hasOperator = false
    }
}