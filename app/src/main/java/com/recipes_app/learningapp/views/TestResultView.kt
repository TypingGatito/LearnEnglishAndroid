package com.recipes_app.learningapp.views

import com.recipes_app.learningapp.models.questions.Answer

interface TestResultView {
    fun showAnswers(answers: List<Answer>?)
    fun showResultText(answers: List<Answer>?)
}