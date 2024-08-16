package com.recipes_app.learningapp.questions

data class Word(
    val original: String,
    val translate: String,
    val theme: String,
    var learned: Boolean = false,
)