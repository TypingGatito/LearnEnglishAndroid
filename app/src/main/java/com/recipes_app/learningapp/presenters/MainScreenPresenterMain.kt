package com.recipes_app.learningapp.presenters

import android.content.Context
import com.recipes_app.learningapp.business_classes.QuestionGeneratorPreload
import com.recipes_app.learningapp.repositories.words.WordRepositorySqlite
import com.recipes_app.learningapp.services.words.WordService
import com.recipes_app.learningapp.views.MainScreenView

class MainScreenPresenterMain(private val view: MainScreenView, private val context: Context) : MainScreenPresenter {
    private val questionGenerator = QuestionGeneratorPreload(WordService(WordRepositorySqlite(context, null)))
    override fun setSeekBarAdapter() {
        view.setSeekBarAdapter(questionGenerator.getThemes())
    }
}