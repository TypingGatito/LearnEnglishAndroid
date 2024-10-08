package com.recipes_app.learningapp.presenters

import com.recipes_app.learningapp.models.questions.Answer

interface TestResultPresenter {
    fun onViewCreated(answers: List<Answer>?)
    fun showAnswers(answers: List<Answer>?)
    fun showResultText(answers: List<Answer>?)
}