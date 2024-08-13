package com.recipes_app.learningapp

import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import com.recipes_app.learningapp.databinding.ActivityLearnWordBinding

class MainActivity : AppCompatActivity() {
    private var _binding: ActivityLearnWordBinding? = null
    private val binding
        get() = _binding ?: throw IllegalStateException("Binding in MainActivity is null")
    private val questionGenerator: QuestionGenerator = QuestionGenerator()

    //private lateinit var binding: ActivityLearnWordBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_learn_word)

        _binding = ActivityLearnWordBinding.inflate(layoutInflater)
        setContentView(binding.root)

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
        }

//        val tvWordToTranslate: TextView = findViewById(R.id.tvWordToTranslate)
//        tvWordToTranslate.text = "GGGG"
//        tvWordToTranslate.setTextColor(Color.BLUE)
//        tvWordToTranslate.setTextColor(Color.parseColor("#FFFFFF"))
////        tvWordToTranslate.setTextColor(ContextCompat.getColor(this, R.color.textViewOptions))
//        with(binding) {
//            tvWordToTranslate.text = "Galaxy"
//            tvWordToTranslate.isVisible = true
//        }

    }

    private fun markAnswerNeutral(
        layoutAnswer: LinearLayout,
        tvVariantNumber: TextView,
        tvVariantValue: TextView,
    ) {

        layoutAnswer.background = ContextCompat.getDrawable(
            this@MainActivity,
            R.drawable.shape_rounded_container
        )



        tvVariantValue.setTextColor(ContextCompat.getColor(
            this@MainActivity,
            R.color.textViewOptions
        ))


        tvVariantNumber.apply {
            setTextColor(ContextCompat.getColor(
                this@MainActivity,
                R.color.textViewOptions
            ))

            background = ContextCompat.getDrawable(
                this@MainActivity,
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
            this@MainActivity,
            R.drawable.shape_rounded_container_correct
        )

        tvVariantNumber.background = ContextCompat.getDrawable(
            this@MainActivity,
            R.drawable.shape_rounded_variants_correct
        )

        tvVariantNumber.setTextColor(ContextCompat.getColor(
            this,
            R.color.white)
        )

        tvVariantValue.setTextColor(ContextCompat.getColor(
            this,
            R.color.correctGreenColor)
        )

    }

    private fun markAnswerWrong(
        layoutAnswer: LinearLayout,
        tvVariantNumber: TextView,
        tvVariantValue: TextView,
    ) {
        layoutAnswer.background = ContextCompat.getDrawable(
            this@MainActivity,
            R.drawable.shape_rounded_container_wrong
        )

        tvVariantNumber.background = ContextCompat.getDrawable(
            this@MainActivity,
            R.drawable.shape_rounded_variants_wrong
        )

        tvVariantNumber.setTextColor(ContextCompat.getColor(
            this,
            R.color.white)
        )

        tvVariantValue.setTextColor(ContextCompat.getColor(
            this,
            R.color.wrongRedColor)
        )
    }

    private fun showResultMessage(isCorrect: Boolean): Unit {
        val color: Int
        val messageText: String
        val resIconResource: Int

        if (isCorrect) {
            color = ContextCompat.getColor(this@MainActivity, R.color.correctGreenColor)
            messageText = ContextCompat.getString(this@MainActivity, R.string.message_correct)
            resIconResource = R.drawable.ic_correct
        } else {
            color = ContextCompat.getColor(this@MainActivity, R.color.wrongRedColor)
            messageText = ContextCompat.getString(this@MainActivity, R.string.message_wrong)
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
        val question: Question? = questionGenerator.getNextQuestion()

        with(binding) {
            if (question == null || question.variants.size < NUMBER_OF_ANSWERS) {
                tvWordToTranslate.isVisible = false
                layoutAnswers.isVisible = false
                btnSkip.isVisible = true
                btnSkip.text = "Поздравляем"
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
                if(questionGenerator.checkAnswer(0)) {
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
                if(questionGenerator.checkAnswer(1)) {
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
                if(questionGenerator.checkAnswer(2)) {
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
                if(questionGenerator.checkAnswer(3)) {
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
}