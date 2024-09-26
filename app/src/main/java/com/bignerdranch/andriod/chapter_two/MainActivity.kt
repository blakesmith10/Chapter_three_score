package com.bignerdranch.andriod.chapter_two

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bignerdranch.andriod.chapter_two.databinding.ActivityMainBinding

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {

    private val questionBank = listOf(
        Question(R.string.question_australia, true),
        Question(R.string.question_oceans, true),
        Question(R.string.question_mideast, false),
        Question(R.string.question_africa, false),
        Question(R.string.question_americas, true),
        Question(R.string.question_asia, true)
    )

    private var currentIndex = 0
    private var correctAnswers = 0
    private lateinit var binding: ActivityMainBinding

    private fun isAnswered(index: Int) {
        if (questionBank[index].answered) {
            binding.trueButton.isEnabled = false
            binding.falseButton.isEnabled = false
        } else {
            binding.trueButton.isEnabled = true
            binding.falseButton.isEnabled = true
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Log.d(TAG, "onCreate(Bundle) called")

        // Create a common click listener for advancing to the next question
        val nextQuestionListener = View.OnClickListener {
            currentIndex = (currentIndex + 1) % questionBank.size
            isAnswered(currentIndex)
            updateQuestion()
        }

        val previousQuestionListener = View.OnClickListener {
            currentIndex = if (currentIndex - 1 < 0) {
                questionBank.size - 1
            } else {
                currentIndex - 1
            }
            isAnswered(currentIndex)
            updateQuestion()
        }

        // Set click listeners for buttons
        binding.trueButton.setOnClickListener {
            binding.trueButton.isEnabled = false
            binding.falseButton.isEnabled = false
            questionBank[currentIndex].answered = true
            checkAnswer(true)
            if (isQuizComplete() || currentIndex == questionBank.size - 1) showScore()
        }

        binding.falseButton.setOnClickListener {
            binding.trueButton.isEnabled = false
            binding.falseButton.isEnabled = false
            questionBank[currentIndex].answered = true
            checkAnswer(false)
            if (isQuizComplete() || currentIndex == questionBank.size - 1) showScore()
        }

        binding.nextButton.setOnClickListener(nextQuestionListener)

        binding.previousButton.setOnClickListener(previousQuestionListener)

        // Set click listener for the TextView to advance to the next question
        binding.questionTextView.setOnClickListener(nextQuestionListener)

        updateQuestion()
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart() called")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume() called")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause() called")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop() called")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy() called")
    }

    private fun updateQuestion() {
        val questionTextResId = questionBank[currentIndex].textResId
        binding.questionTextView.setText(questionTextResId)
    }

    private fun checkAnswer(userAnswer: Boolean) {
        val correctAnswer = questionBank[currentIndex].answer
        if (userAnswer == correctAnswer) {
            correctAnswers++
            Toast.makeText(this, R.string.correct_toast, Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, R.string.incorrect_toast, Toast.LENGTH_SHORT).show()
        }
    }

    private fun isQuizComplete(): Boolean {
        return questionBank.all { it.answered }
    }

    private fun showScore() {
        val score = (correctAnswers.toDouble() / questionBank.size) * 100
        val scoreString = String.format("%.1f %%", score)
        Toast.makeText(this, "Your score: $scoreString", Toast.LENGTH_LONG).show()
        resetQuiz()
    }

    private fun resetQuiz() {
        correctAnswers = 0
        for (question in questionBank) {
            question.answered = false
        }
        currentIndex = 0
        updateQuestion()
        isAnswered(currentIndex)
    }
}


