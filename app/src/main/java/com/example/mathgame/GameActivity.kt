package com.example.mathgame

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.mathgame.databinding.ActivityGameBinding

class GameActivity : AppCompatActivity() {

    private lateinit var gameBinding: ActivityGameBinding
    private var firstExpressionAnswer : Double? = 0.0
    private var secondExpressionAnswer : Double? = 0.0
    private lateinit var countDownTimer : CountDownTimer
    private var timeLeft : Long = 50000
    private var correctAnswers = 0
    private var correctAnswersAtAll = 0
    private var incorrectAnswersAtAll = 0
    private var gameStarted = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        gameBinding = DataBindingUtil.setContentView(this, R.layout.activity_game)

        gameBinding.constraintResult.visibility = View.INVISIBLE

        getExpressions()

        updateCountDown(timeLeft/1000)

        gameBinding.greaterButton.setOnClickListener {
            if (!gameStarted){
                startGame()
            }
            if (firstExpressionAnswer!! > secondExpressionAnswer!!){
                toastMessage(true)
                correctAnswers++
                correctAnswersAtAll++
            }else{
                toastMessage(false)
                incorrectAnswersAtAll++
            }
            getExpressions()
        }

        gameBinding.equalsButton.setOnClickListener {
            if (!gameStarted){
                startGame()
            }
            if (firstExpressionAnswer!! == secondExpressionAnswer!!){
                toastMessage(true)
                correctAnswers++
                correctAnswersAtAll++
            }else{
                toastMessage(false)
                incorrectAnswersAtAll++
            }
            getExpressions()
        }
        gameBinding.lessButton.setOnClickListener {
            if (!gameStarted){
                startGame()
            }
            if (firstExpressionAnswer!! < secondExpressionAnswer!!){
                toastMessage(true)
                correctAnswers++
                correctAnswersAtAll++
            }else{
                toastMessage(false)
                incorrectAnswersAtAll++
            }
            getExpressions()
        }

    }



    private fun startGame(){
        countDownTimer = object : CountDownTimer(timeLeft, 1000){
            override fun onTick(millis: Long) {
                val time = millis/1000
                timeLeft = millis
                updateCountDown(time)
                Log.d("correct", "$correctAnswers")
                if (correctAnswers == 5){
                    correctAnswers = 0
                    countDownTimer.cancel()
                    timeLeft += 10000
                    Log.d("time", "$timeLeft")
                    startGame()
                }
            }

            override fun onFinish() {
                stopGame()
            }

        }.start()
        gameStarted = true
    }

    private fun pauseGame(){
        countDownTimer.cancel()
        gameStarted = false
    }

    private fun updateCountDown(time : Long) {
        gameBinding.countdownText.setText("Time Left: $time")
    }

    private fun stopGame() {
        gameBinding.greaterButton.isEnabled = false
        gameBinding.equalsButton.isEnabled = false
        gameBinding.lessButton.isEnabled = false
        gameBinding.constraintResult.visibility = View.VISIBLE
        gameBinding.resultText.text = "Result : Correct - $correctAnswersAtAll, Incorrect - $incorrectAnswersAtAll"

    }


    private fun getExpressions(){
        gameBinding.expressionFirst.text = MathExpressionGenerator().generateNumbers()
        gameBinding.expressionSecond.text = MathExpressionGenerator().generateNumbers()
        firstExpressionAnswer = CalculateClass().evaluate(gameBinding.expressionFirst.text.toString())
        secondExpressionAnswer = CalculateClass().evaluate(gameBinding.expressionSecond.text.toString())
        Log.d("answer", "$firstExpressionAnswer $secondExpressionAnswer")
    }

    private fun toastMessage(answer : Boolean){
        if (answer){
            val toast = Toast.makeText(this, "CORRECT!", Toast.LENGTH_SHORT)
            val view = toast.view
            val tv = view?.findViewById<TextView>(android.R.id.message)
            tv?.setTextColor(Color.GREEN)
            toast.show()
        }else{
            val toast = Toast.makeText(this, "WRONG!", Toast.LENGTH_SHORT)
            val view = toast.view
            val tv = view?.findViewById<TextView>(android.R.id.message)
            tv?.setTextColor(Color.RED)
            toast.show()
        }
    }






    override fun onSaveInstanceState(outState: Bundle) {
        outState.run {
            putString("expressionFirst", gameBinding.expressionFirst.text.toString())
            putString("expressionSecond", gameBinding.expressionSecond.text.toString())
            putLong("timerLeft", timeLeft)
            putInt("correctAnswers", correctAnswers)
            putInt("correctAnswersAtAll", correctAnswersAtAll)
            putInt("incorrectAnswersAtAll", incorrectAnswersAtAll)
            putBoolean("gameStarted", gameStarted)
        }
        super.onSaveInstanceState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)

        gameBinding.expressionFirst.text = savedInstanceState.getString("expressionFirst")
        gameBinding.expressionSecond.text = savedInstanceState.getString("expressionSecond")
        timeLeft = savedInstanceState.getLong("timerLeft")
        correctAnswers = savedInstanceState.getInt("correctAnswers")
        correctAnswersAtAll = savedInstanceState.getInt("correctAnswersAtAll")
        incorrectAnswersAtAll = savedInstanceState.getInt("incorrectAnswersAtAll")
        gameStarted = savedInstanceState.getBoolean("gameStarted")
        updateCountDown(timeLeft/1000)
        if (gameStarted){
            startGame()
        }
    }

}