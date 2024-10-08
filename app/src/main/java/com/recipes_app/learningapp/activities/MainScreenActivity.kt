package com.recipes_app.learningapp.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.SeekBar
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.recipes_app.learningapp.R
import com.recipes_app.learningapp.adapters.ThemesSpinnerAdapter
import com.recipes_app.learningapp.databinding.ActivityMainScreenBinding
import com.recipes_app.learningapp.business_classes.QuestionGenerator
import com.recipes_app.learningapp.business_classes.QuestionGeneratorPreload
import com.recipes_app.learningapp.presenters.MainScreenPresenter
import com.recipes_app.learningapp.presenters.MainScreenPresenterMain
import com.recipes_app.learningapp.presenters.TestResultPresenterMain
import com.recipes_app.learningapp.repositories.words.WordRepositorySqlite
import com.recipes_app.learningapp.services.words.WordService
import com.recipes_app.learningapp.views.MainScreenView
import com.recipes_app.learningapp.workers.UserActivityWorker
import java.util.concurrent.TimeUnit

class MainScreenActivity : AppCompatActivity(), MainScreenView {
    private var _binding: ActivityMainScreenBinding? = null
    private val binding
        get() = _binding ?: throw IllegalStateException("Binding in MainScreen is null")

    private lateinit var presenter: MainScreenPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main_screen)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        _binding = ActivityMainScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        presenter = MainScreenPresenterMain(this@MainScreenActivity, this@MainScreenActivity)

        setStartTestButtonOnClick()

        val sharedPreferences = getSharedPreferences("AppPrefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putLong("lastLaunchTime", System.currentTimeMillis())
        editor.apply()

        setNotifications()
        setStartTestButtonOnClick()
        setSeekBarOnClick()

        presenter.setSeekBarAdapter()
    }

    override fun setSeekBarAdapter(availableThemes: Set<String>) {
        val adapter = ThemesSpinnerAdapter(this@MainScreenActivity,
            availableThemes.toList())

        binding.spinnerThemes.adapter = adapter
    }

    private fun setStartTestButtonOnClick() {
        with (binding) {
            btnStartTest.setOnClickListener {
                val numOfWords: Int = sbNumOfWords.progress
                val theme: String = spinnerThemes.selectedItem.toString()

                val intent: Intent = Intent(this@MainScreenActivity, LearnWordActivity::class.java)
                intent.putExtra("numOfWords", numOfWords)
                intent.putExtra("theme", theme)

                startActivity(intent)
            }

        }
    }

    private fun setSeekBarOnClick() {
        with(binding) {
            sbNumOfWords.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                    tvNumOfWords.text = "Количество слов: $progress"
                }

                override fun onStartTrackingTouch(seekBar: SeekBar) {
                }

                override fun onStopTrackingTouch(seekBar: SeekBar) {
                }
            })
        }
    }

    private fun setNotifications() {
        val checkUserActivityWorkRequest = PeriodicWorkRequestBuilder<UserActivityWorker>(24, TimeUnit.HOURS)
            .build()

        WorkManager.getInstance(this).enqueue(checkUserActivityWorkRequest)
    }

}