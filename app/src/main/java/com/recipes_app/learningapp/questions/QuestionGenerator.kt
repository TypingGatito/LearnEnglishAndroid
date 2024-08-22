package com.recipes_app.learningapp.questions

interface QuestionGenerator {
    var numOfQuestions: Int
        get() { return  1}
        set(v) {}

    fun nextQuestion(): TestProgress

    fun answer(userAnswerIndex: Int): Boolean

    fun getThemes(): Set<String>

    fun startTest(numOfWords: Int, theme: String)
}