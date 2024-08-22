package com.recipes_app.learningapp.activities

import android.content.Intent
import android.os.Bundle
import android.provider.SyncStateContract.Constants
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import com.recipes_app.learningapp.R
import com.recipes_app.learningapp.databinding.ActivityLearnWordBinding
import com.recipes_app.learningapp.questions.Question
import com.recipes_app.learningapp.questions.QuestionGenerator
import com.recipes_app.learningapp.questions.QuestionGeneratorPreload
import com.recipes_app.learningapp.questions.TestProgress
import java.util.ArrayList

class LearnWordActivity : AppCompatActivity() {
    private var _binding: ActivityLearnWordBinding? = null
    private val binding
        get() = _binding ?: throw IllegalStateException("Binding in LearnWordActivity is null")
    private val questionGenerator: QuestionGenerator = QuestionGeneratorPreload()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_learn_word)

        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.learnWord)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        _binding = ActivityLearnWordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val numOfWords = intent.getIntExtra("numOfWords", 0)
        val theme: String = "" + intent.getStringExtra("theme")
        questionGenerator.startTest(numOfWords, theme)
        showNextQuestion(questionGenerator)

        with(binding) {
            btnContinue.setOnClickListener {
                layoutResult.isVisible = false
                markAnswerNeutral(layoutAnswer1,
                    tvVariantNumber1,
                    tvVariantValue1)
                markAnswerNeutral(layoutAnswer2,
                    tvVariantNumber2,
                    tvVariantValue2)
                markAnswerNeutral(layoutAnswer3,
                    tvVariantNumber3,
                    tvVariantValue3)
                markAnswerNeutral(layoutAnswer4,
                    tvVariantNumber4,
                    tvVariantValue4)
                showNextQuestion(questionGenerator)
            }

            btnSkip.setOnClickListener {
                showNextQuestion(questionGenerator)
            }

            btnClose.setOnClickListener {
                val intent: Intent = Intent(this@LearnWordActivity, MainScreenActivity::class.java)
                startActivity(intent)
            }

            pbQuestionProgress.apply {
                max = questionGenerator.numOfQuestions
                progress = 1
            }

        }

    }

    private fun markAnswerNeutral(
        layoutAnswer: LinearLayout,
        tvVariantNumber: TextView,
        tvVariantValue: TextView,
    ) {

        layoutAnswer.background = ContextCompat.getDrawable(
            this@LearnWordActivity,
            R.drawable.shape_rounded_container
        )



        tvVariantValue.setTextColor(ContextCompat.getColor(
            this@LearnWordActivity,
            R.color.textViewOptions
        ))


        tvVariantNumber.apply {
            setTextColor(ContextCompat.getColor(
                this@LearnWordActivity,
                R.color.textViewOptions
            ))

            background = ContextCompat.getDrawable(
                this@LearnWordActivity,
                R.drawable.shape_rounded_variants
            )
        }

    }

    private fun markAnswerCorrect(
        layoutAnswer: LinearLayout,
        tvVariantNumber: TextView,
        tvVariantValue: TextView,
    ) {
        layoutAnswer.background = ContextCompat.getDrawable(
            this@LearnWordActivity,
            R.drawable.shape_rounded_container_correct
        )

        tvVariantNumber.background = ContextCompat.getDrawable(
            this@LearnWordActivity,
            R.drawable.shape_rounded_variants_correct
        )

        tvVariantNumber.setTextColor(ContextCompat.getColor(
            this,
            R.color.white
        )
        )

        tvVariantValue.setTextColor(ContextCompat.getColor(
            this,
            R.color.correctGreenColor
        )
        )

    }

    private fun markAnswerWrong(
        layoutAnswer: LinearLayout,
        tvVariantNumber: TextView,
        tvVariantValue: TextView,
    ) {
        layoutAnswer.background = ContextCompat.getDrawable(
            this@LearnWordActivity,
            R.drawable.shape_rounded_container_wrong
        )

        tvVariantNumber.background = ContextCompat.getDrawable(
            this@LearnWordActivity,
            R.drawable.shape_rounded_variants_wrong
        )

        tvVariantNumber.setTextColor(ContextCompat.getColor(
            this,
            R.color.white
        )
        )

        tvVariantValue.setTextColor(ContextCompat.getColor(
            this,
            R.color.wrongRedColor
        )
        )
    }

    private fun showResultMessage(isCorrect: Boolean): Unit {
        val color: Int
        val messageText: String
        val resIconResource: Int

        if (isCorrect) {
            color = ContextCompat.getColor(this@LearnWordActivity, R.color.correctGreenColor)
            messageText = ContextCompat.getString(this@LearnWordActivity, R.string.message_correct)
            resIconResource = R.drawable.ic_correct
        } else {
            color = ContextCompat.getColor(this@LearnWordActivity, R.color.wrongRedColor)
            messageText = ContextCompat.getString(this@LearnWordActivity, R.string.message_wrong)
            resIconResource = R.drawable.ic_wrong
        }

        with(binding) {
            btnSkip.isVisible = false
            layoutResult.isVisible = true
            btnContinue.setTextColor(color)
            layoutResult.setBackgroundColor(color)
            ivResultIcon.setImageResource(resIconResource)
            tvAnswerDescription.setText(messageText)
        }
    }

    private fun showNextQuestion(questionGenerator: QuestionGenerator) {
        val testProgress: TestProgress = questionGenerator.nextQuestion()
        changeProgressBar(testProgress.numOfCurQuestion)
        val question: Question? = testProgress.curQuestion

        with(binding) {
            if (question == null) {
                val intent: Intent = Intent(this@LearnWordActivity, TestResultActivity::class.java)
                intent.putParcelableArrayListExtra("answers", ArrayList(testProgress.wrongAnswers))
                startActivity(intent)
            } else {
                tvWordToTranslate.isVisible = true
                layoutAnswers.isVisible = true
                btnSkip.isVisible = true
                tvWordToTranslate.text = question.correctAnswer.original

                tvVariantValue1.text = question.variants[0].translate
                tvVariantValue2.text = question.variants[1].translate
                tvVariantValue3.text = question.variants[2].translate
                tvVariantValue4.text = question.variants[3].translate
            }

            layoutAnswer1.setOnClickListener{
                if(questionGenerator.answer(0)) {
                    markAnswerCorrect(layoutAnswer1,
                        tvVariantNumber1,
                        tvVariantValue1)
                    showResultMessage(true)
                } else {
                    markAnswerWrong(layoutAnswer1,
                        tvVariantNumber1,
                        tvVariantValue1)
                    showResultMessage(false)
                }
            }

            layoutAnswer2.setOnClickListener{
                if(questionGenerator.answer(1)) {
                    markAnswerCorrect(layoutAnswer2,
                        tvVariantNumber2,
                        tvVariantValue2)
                    showResultMessage(true)
                } else {
                    markAnswerWrong(layoutAnswer2,
                        tvVariantNumber2,
                        tvVariantValue2)
                    showResultMessage(false)
                }
            }

            layoutAnswer3.setOnClickListener{
                if(questionGenerator.answer(2)) {
                    markAnswerCorrect(layoutAnswer3,
                        tvVariantNumber3,
                        tvVariantValue3)
                    showResultMessage(true)
                } else {
                    markAnswerWrong(layoutAnswer3,
                        tvVariantNumber3,
                        tvVariantValue3)
                    showResultMessage(false)
                }
            }

            layoutAnswer4.setOnClickListener{
                if(questionGenerator.answer(3)) {
                    markAnswerCorrect(layoutAnswer4,
                        tvVariantNumber4,
                        tvVariantValue4)
                    showResultMessage(true)
                } else {
                    markAnswerWrong(layoutAnswer4,
                        tvVariantNumber4,
                        tvVariantValue4)
                    showResultMessage(false)
                }
            }
        }
    }

    private fun changeProgressBar(progress: Int) {
        binding.pbQuestionProgress.progress = progress
        binding.tvProgressText.text = progress.toString()
    }
}