package com.recipes_app.learningapp.repositories.words

import com.recipes_app.learningapp.models.questions.Question
import com.recipes_app.learningapp.models.questions.Word

interface WordRepository {

    fun getAll(): List<Word>

    fun addWords(words: List<Word>)

    fun addWord(word: Word)
}