package com.recipes_app.learningapp.activities

import android.content.Intent
import android.os.Bundle
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
import com.recipes_app.learningapp.models.questions.Answer
import com.recipes_app.learningapp.presenters.LearnWordPresenter
import com.recipes_app.learningapp.presenters.LearnWordPresenterMain
import com.recipes_app.learningapp.views.LearnWordView
import java.util.ArrayList

class LearnWordActivity : AppCompatActivity(), LearnWordView {
    private var _binding: ActivityLearnWordBinding? = null
    private val binding
        get() = _binding ?: throw IllegalStateException("Binding in LearnWordActivity is null")

    private lateinit var presenter: LearnWordPresenter



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

        presenter = LearnWordPresenterMain(this, this)


        val numOfWords = intent.getIntExtra("numOfWords", 0)
        val theme: String = "" + intent.getStringExtra("theme")
        presenter.startTest(numOfWords, theme)

        showNextQuestion()
        setStartOnClicks()
    }

    private fun showNextQuestion() {
        presenter.showNextQuestion()

        with(binding) {
            layoutAnswer1.setOnClickListener{
                presenter.option1Clicked()
            }

            layoutAnswer2.setOnClickListener{
                presenter.option2Clicked()
            }

            layoutAnswer3.setOnClickListener{
                presenter.option3Clicked()
            }

            layoutAnswer4.setOnClickListener{
                presenter.option4Clicked()
            }
        }
    }

    private fun setStartOnClicks() {
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
                showNextQuestion()
            }

            btnSkip.setOnClickListener {
                showNextQuestion()
            }

            btnClose.setOnClickListener {
                val intent: Intent = Intent(this@LearnWordActivity, MainScreenActivity::class.java)
                startActivity(intent)
            }

        }
    }

    override fun showAnswerResult(isCorrect: Boolean, optionNumber: Int) {
        with(binding) {
            when (optionNumber) {
                1 -> {
                    if (isCorrect) {
                        markAnswerCorrect(layoutAnswer1, tvVariantNumber1, tvVariantValue1)
                    } else {
                        markAnswerWrong(layoutAnswer1, tvVariantNumber1, tvVariantValue1)
                    }
                }
                2 -> {
                    if (isCorrect) {
                        markAnswerCorrect(layoutAnswer2, tvVariantNumber2, tvVariantValue2)
                    } else {
                        markAnswerWrong(layoutAnswer2, tvVariantNumber2, tvVariantValue2)
                    }
                }
                3 -> {
                    if (isCorrect) {
                        markAnswerCorrect(layoutAnswer3, tvVariantNumber3, tvVariantValue3)
                    } else {
                        markAnswerWrong(layoutAnswer3, tvVariantNumber3, tvVariantValue3)
                    }
                }
                4 -> {
                    if (isCorrect) {
                        markAnswerCorrect(layoutAnswer4, tvVariantNumber4, tvVariantValue4)
                    } else {
                        markAnswerWrong(layoutAnswer4, tvVariantNumber4, tvVariantValue4)
                    }
                }
            }
        }

        disableOptionsOnClicks()
    }

    override fun setProgressBar(value: Int) {
        binding.pbQuestionProgress.apply {
            max = value
            progress = 1
        }
    }


    override fun showResultMessage(isCorrect: Boolean): Unit {
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

    override fun changeProgressBar(progress: Int) {
        binding.pbQuestionProgress.progress = progress
        binding.tvProgressText.text = progress.toString()
    }

    override fun toMain(wrongAnswers: List<Answer>) {
        val intent: Intent = Intent(this@LearnWordActivity, TestResultActivity::class.java)
        intent.putParcelableArrayListExtra("wrong_answers", ArrayList(wrongAnswers))
        startActivity(intent)
    }

    override fun setTranslationOptions(v1: String, v2: String, v3: String, v4: String) {
        with(binding) {
            tvVariantValue1.text = v1
            tvVariantValue2.text = v2
            tvVariantValue3.text = v3
            tvVariantValue4.text = v4
        }
    }

    override fun setWordToTranslate(word: String) {
        with (binding) {
            tvWordToTranslate.isVisible = true
            layoutAnswers.isVisible = true
            btnSkip.isVisible = true
            tvWordToTranslate.text = word
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
            R.drawable.shape_rounded_container_wrong)

        tvVariantNumber.background = ContextCompat.getDrawable(
            this@LearnWordActivity,
            R.drawable.shape_rounded_variants_wrong)

        tvVariantNumber.setTextColor(ContextCompat.getColor(
            this,
            R.color.white)
        )

        tvVariantValue.setTextColor(ContextCompat.getColor(
            this,
            R.color.wrongRedColor)
        )
    }

    private fun disableOptionsOnClicks() {
        with(binding) {
            layoutAnswer1.setOnClickListener(null)

            layoutAnswer2.setOnClickListener(null)

            layoutAnswer3.setOnClickListener(null)

            layoutAnswer4.setOnClickListener(null)
        }
    }

}