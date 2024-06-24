package com.example.calculator.ui.theme

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.calculator.R
import android.view.View
import android.widget.TextView
import net.objecthunter.exp4j.ExpressionBuilder
import android.view.Window

class MainActivity : AppCompatActivity() {
    var btn_0: TextView? = null
    var btn_1: TextView? = null
    var btn_2: TextView? = null
    var btn_3: TextView? = null
    var btn_4: TextView? = null
    var btn_5: TextView? = null
    var btn_6: TextView? = null
    var btn_7: TextView? = null
    var btn_8: TextView? = null
    var btn_9: TextView? = null
    var sum_btn: TextView? = null
    var diff_btn: TextView? = null
    var mult_btn: TextView? = null
    var div_btn: TextView? = null
    var mod_btn: TextView? = null
    var convert_btn: TextView? = null
    var clear_btn: TextView? = null
    var calc_btn: TextView? = null
    var dot_btn: TextView? = null
    var math_operation: TextView? = null
    var result_text: TextView? = null

    var dotFlag: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar()?.hide()
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        btn_0 = findViewById(R.id.btn_0)
        btn_1 = findViewById(R.id.btn_1)
        btn_2 = findViewById(R.id.btn_2)
        btn_3 = findViewById(R.id.btn_3)
        btn_4 = findViewById(R.id.btn_4)
        btn_5 = findViewById(R.id.btn_5)
        btn_6 = findViewById(R.id.btn_6)
        btn_7 = findViewById(R.id.btn_7)
        btn_8 = findViewById(R.id.btn_8)
        btn_9 = findViewById(R.id.btn_9)
        sum_btn = findViewById(R.id.btn_sum)
        diff_btn = findViewById(R.id.btn_diff)
        mult_btn = findViewById(R.id.btn_mult)
        div_btn = findViewById(R.id.btn_div)
        mod_btn = findViewById(R.id.btn_mod)
        convert_btn = findViewById(R.id.btn_convert)
        clear_btn = findViewById(R.id.btn_clear)
        calc_btn = findViewById(R.id.btn_calc)
        dot_btn = findViewById(R.id.btn_dot)
        math_operation = findViewById(R.id.math_operation)
        result_text = findViewById(R.id.result_text)

        btn_0?.setOnClickListener { setTextFields("0") }
        btn_1?.setOnClickListener { setTextFields("1") }
        btn_2?.setOnClickListener { setTextFields("2") }
        btn_3?.setOnClickListener { setTextFields("3") }
        btn_4?.setOnClickListener { setTextFields("4") }
        btn_5?.setOnClickListener { setTextFields("5") }
        btn_6?.setOnClickListener { setTextFields("6") }
        btn_7?.setOnClickListener { setTextFields("7") }
        btn_8?.setOnClickListener { setTextFields("8") }
        btn_9?.setOnClickListener { setTextFields("9") }
        sum_btn?.setOnClickListener {funtionalButtonClick("+") }
        diff_btn?.setOnClickListener {funtionalButtonClick("-") }
        div_btn?.setOnClickListener {funtionalButtonClick("/") }
        mod_btn?.setOnClickListener {funtionalButtonClick("%")}
        mult_btn?.setOnClickListener {funtionalButtonClick("*")}

        convert_btn?.setOnClickListener {
            if(result_text?.text != "") {
                math_operation?.text = result_text?.text
                result_text?.text = ""
            }
            try{
                val parsedInt = math_operation?.text.toString().toInt()
                if(math_operation?.text.toString().first() == '-')
                    math_operation?.text = math_operation?.text.toString().replace("-", "")
                else
                    math_operation?.text = "-${math_operation?.text.toString()}"
            }
            catch (e:Exception) {
                var str = math_operation?.text.toString()
                if(str.length > 1 && (!isNotFunctional(str.last()) && isNotFunctional(str[str.length-2])))
                    setTextFields("-")
            }
        }

        clear_btn?.setOnClickListener {
            math_operation?.text = "0"
            result_text?.text = ""
        }

        dot_btn?.setOnClickListener {
            if(dotFlag == false && isNotFunctional(math_operation?.text.toString().last())){
                setTextFields(".")
                dotFlag = true
            }
        }
        calc_btn?.setOnClickListener {
            if(result_text?.text != "") {
                math_operation?.text = result_text?.text
                result_text?.text = ""
            }
            try {
                val ex = ExpressionBuilder(math_operation?.text.toString()).build()
                val result = ex.evaluate()

                val longRes = result.toLong()
                if(result == longRes.toDouble())
                    result_text?.text = longRes.toString()
                else
                    result_text?.text = result.toString()
            } catch (e:Exception) {
                Log.d("Ошибка", "сообщениеЖ ${e.message}")
            }
            if(!result_text?.text.toString().contains(".", ignoreCase = true))
                dotFlag = false
        }
    }

    fun setTextFields(str: String) {
        if(result_text?.text != "") {
            math_operation?.text = result_text?.text
            result_text?.text = ""
        }
        var mathStr = math_operation?.text.toString()
        if(mathStr.length == 1 && mathStr.last() == '0' && isNotFunctional(str.last()) && str.last() != '.')
            math_operation?.text = ""
        math_operation?.append(str)
    }

//    fun back(view: View) {
//        var math_operation: TextView = findViewById(R.id.math_operation)
//        var text = math_operation.text.toString()
//        if(text)
//    }
    
    fun funtionalButtonClick(str: String) {
        dotFlag = false
        if(isNotFunctional(math_operation?.text.toString().last()))
            setTextFields(str)
        else{
            var mathStr = math_operation?.text.toString()
            if(mathStr.length > 1 && (!isNotFunctional(mathStr.last()) && isNotFunctional(mathStr[mathStr.length-2]))){
                math_operation?.text = mathStr.substring(0, mathStr.length-1)
                math_operation?.append(str)
            }
        }
    }
    
    fun isNotFunctional(chr: Char): Boolean {
        if(chr == '/' || chr == '*' || chr == '%'
            || chr == '+' || chr == '-')
            return false
        else
            return true
    }
}