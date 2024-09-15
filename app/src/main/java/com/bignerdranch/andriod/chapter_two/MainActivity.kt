package com.bignerdranch.andriod.chapter_two

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bignerdranch.andriod.chapter_two.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var trueButton: Button
    private lateinit var falseButton: Button

    private lateinit var binding:ActivityMainBinding

    private val questionBank = listOf(
        Question(R.string.question_australia, true),
        Question(R.string.question_oceans, true),
        Question(R.string.question_mideast, false),
        Question(R.string.question_africa, false),
        Question(R.string.question_americas, true),
        Question(R.string.question_asia, true)

    )

    private var currentIndex = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // trueButton = findViewById(true_button)
        // falseButton = findViewById(false_button)


        binding.trueButton.setOnClickListener {
            /* Toast.makeText(
                 this,
                 R.string.correct_toast,
                 Toast.LENGTH_SHORT
             )
                 .show()

             */

            checkAnswer(true)
        }


        binding.falseButton.setOnClickListener {
            /* Toast.makeText(
                 this,
                 R.string.incorrect_toast,
                 Toast.LENGTH_SHORT
             )
                 .show()

             */

            checkAnswer(false)
        }

        binding.nextButton.setOnClickListener {
            currentIndex = (currentIndex + 1) % questionBank.size
            //val questionTextResId = questionBank[currentIndex].textResId
            //binding.questionTextView.setText(questionTextResId)
            updateQuestion()

        }

        // val questionTextResId = questionBank[currentIndex].textResId
        // binding.questionTextView.setText(questionTextResId)
        updateQuestion()
    }

    private fun updateQuestion(){
        val questionTextResId = questionBank[currentIndex].textResId
        binding.questionTextView.setText(questionTextResId)
    }

    private fun checkAnswer(userAnswer:Boolean){
        val correctAnswer = questionBank[currentIndex].answer

        val messageResId = if (userAnswer == correctAnswer){
            R.string.correct_toast
        } else{
            R.string.incorrect_toast
        }

        Toast.makeText( this,
            messageResId,
            Toast.LENGTH_SHORT)
            .show()
    }

}