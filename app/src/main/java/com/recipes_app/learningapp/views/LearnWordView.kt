package com.recipes_app.learningapp.views

import com.recipes_app.learningapp.models.questions.Answer

interface LearnWordView {
    fun changeProgressBar(progress: Int)

    fun toMain(wrongAnswers: List<Answer>)

    fun setTranslationOptions(v1: String, v2: String, v3: String, v4: String,)

    fun setWordToTranslate(word: String)

    fun showResultMessage(isCorrect: Boolean)

    fun showAnswerResult(isCorrect: Boolean, optionNumber: Int)

    fun setProgressBar(value: Int)

}