package com.recipes_app.learningapp.business_classes

import com.recipes_app.learningapp.models.questions.TestProgress

interface QuestionGenerator {
    var numOfQuestions: Int

    fun nextQuestion(): TestProgress

    fun answer(userAnswerIndex: Int): Boolean

    fun getThemes(): Set<String>

    fun startTest(numOfWords: Int, theme: String)
}