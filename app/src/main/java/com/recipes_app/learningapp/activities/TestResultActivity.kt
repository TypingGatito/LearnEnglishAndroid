package com.recipes_app.learningapp.activities

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.recipes_app.learningapp.R
import com.recipes_app.learningapp.adapters.AnswerAdapter
import com.recipes_app.learningapp.databinding.ActivityTestResultBinding
import com.recipes_app.learningapp.models.questions.Answer
import com.recipes_app.learningapp.presenters.TestResultPresenter
import com.recipes_app.learningapp.presenters.TestResultPresenterMain

class TestResultActivity : AppCompatActivity(), TestResultView {
    private var _binding: ActivityTestResultBinding? = null
    private val binding
        get() = _binding ?: throw IllegalStateException("Binding in TestResultActivity is null")

    private lateinit var presenter: TestResultPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_test_result)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        _binding = ActivityTestResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setOnClicks()
        showAnswers()
        showResultText()

        presenter = TestResultPresenterMain(this)
        presenter.onViewCreated()
    }

    private fun setOnClicks() {
        with(binding) {
            btnBackToMain.setOnClickListener {
                val intent: Intent = Intent(this@TestResultActivity, MainScreenActivity::class.java)
                startActivity(intent)
            }
        }
    }

    override fun showAnswers() {
        with(binding) {
            val answers: List<Answer>? = intent.getParcelableArrayListExtra("answers")
            rvWrongAnswers.adapter = AnswerAdapter(answers!!)

            rvWrongAnswers.layoutManager = LinearLayoutManager(this@TestResultActivity)
        }
    }

    override fun showResultText() {
        val answers: List<Answer>? = intent.getParcelableArrayListExtra("answers")
        with(binding) {
            tvResultDescription.text = ContextCompat.getString(this@TestResultActivity, R.string.desc_result_pref) +
                    " " + (answers?.size ?: 0) + " " + ContextCompat.getString(this@TestResultActivity, R.string.desc_result_post)
        }
    }
}