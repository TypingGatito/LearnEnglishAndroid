package com.recipes_app.learningapp.questions

interface QuestionGenerator {
    fun nextQuestion(): TestProgress

    fun answer(userAnswerIndex: Int): Boolean

    fun getThemes(): Set<String>

    fun startTest(numOfWords: Int, theme: String)
}