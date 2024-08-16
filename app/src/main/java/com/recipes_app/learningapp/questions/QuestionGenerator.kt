package com.recipes_app.learningapp.questions

interface QuestionGenerator {
    fun getNextQuestion(): Question?

    fun checkAnswer(userAnswerIndex: Int?): Boolean

    fun getThemes(): Set<String>

    fun startTest(numOfWords: Int, theme: String)
}