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
import com.recipes_app.learningapp.questions.Answer

class TestResultActivity : AppCompatActivity() {
    private var _binding: ActivityTestResultBinding? = null
    private val binding
        get() = _binding ?: throw IllegalStateException("Binding in TestResultActivity is null")

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
        setAnswersRecyclerView()
        setText()
    }

    private fun setOnClicks() {
        with(binding) {
            btnBackToMain.setOnClickListener {
                val intent: Intent = Intent(this@TestResultActivity, MainScreenActivity::class.java)
                startActivity(intent)
            }
        }
    }

    private fun setAnswersRecyclerView() {
        with(binding) {
            val answers: List<Answer>? = intent.getParcelableArrayListExtra("answers")
            rvWrongAnswers.adapter = AnswerAdapter(answers!!)

            rvWrongAnswers.layoutManager = LinearLayoutManager(this@TestResultActivity)
        }
    }

    private fun setText() {
        val answers: List<Answer>? = intent.getParcelableArrayListExtra("answers")
        with(binding) {
            tvResultDescription.text = ContextCompat.getString(this@TestResultActivity, R.string.desc_result_pref) +
                    " " + (answers?.size ?: 0) + " " + ContextCompat.getString(this@TestResultActivity, R.string.desc_result_post)
        }
    }
}