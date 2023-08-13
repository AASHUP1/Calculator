package com.example.calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import com.example.calculator.databinding.ActivityMainBinding
import net.objecthunter.exp4j.Expression
import net.objecthunter.exp4j.ExpressionBuilder

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    var lastNumeric = false
    var stateError = false
    var lastDot = false

    private lateinit var expression: Expression

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    fun onAllclearClick(view: View) {

        binding.Input.text = ""
        binding.Display.text = ""
        stateError = false
        lastDot = false
        lastNumeric = false
        binding.Display.visibility = View.GONE

    }


    fun onEqualClick(view: View) {

        onEqual()
        binding.Input.text = binding.Display.text.toString().drop(1)

    }


    fun onDigitClick(view: View) {

        if (stateError){

            binding.Input.text = (view as Button).text
            stateError = false

        } else{

            binding.Input.append((view as Button).text)

        }
        lastNumeric = true
        onEqual()

    }


    fun onOperatorClick(view: View) {

        if (!stateError && lastNumeric){

            binding.Input.append((view as Button).text)
            lastDot = false
            lastNumeric = false
            onEqual()

        }

    }



    fun onBackClick(view: View) {

        binding.Input.text = binding.Input.text.toString().dropLast(1)
        try {

            val lastchar = binding.Input.text.toString().last()

            if (lastchar.isDigit()){

                onEqual()
            }
        } catch (e : Exception){

            binding.Display.text = ""
            binding.Display.visibility = View.GONE
            Log.e( "last char error",e.toString())

        }

    }



    fun onClearClick(view: View) {

        binding.Input.text = ""
        lastNumeric = false

    }


   fun onEqual(){

       if (lastNumeric && !stateError){

           val txt = binding.Input.text.toString()

           expression = ExpressionBuilder(txt).build()

           try{

               val result= expression.evaluate()
               binding.Display.visibility = View.VISIBLE

               binding.Display.text= "=" + result.toString()

           } catch (ex : ArithmeticException){

               Log.e("Evaluate error",ex.toString())
               binding.Display.text = "Error"
               stateError = true
               lastNumeric = false
           }

       }

   }

}