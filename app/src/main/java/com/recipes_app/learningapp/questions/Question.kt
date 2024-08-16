package com.recipes_app.learningapp.questions

data class Question(
    val variants: List<Word>,
    val correctAnswer: Word,
)