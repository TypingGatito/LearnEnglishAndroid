package com.recipes_app.learningapp.presenters

import com.recipes_app.learningapp.models.questions.Answer

interface LearnWordPresenter {
    fun showNextQuestion()

    fun startTest(numOfWords: Int, theme: String)

    fun option1Clicked()

    fun option2Clicked()

    fun option3Clicked()

    fun option4Clicked()
}