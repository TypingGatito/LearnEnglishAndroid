package com.recipes_app.learningapp.services.words

import com.recipes_app.learningapp.models.questions.Word
import com.recipes_app.learningapp.repositories.words.WordRepository

class WordService(private val wordRepository: WordRepository) {
    fun getAll(): List<Word> = wordRepository.getAll()

    fun addWords(words: List<Word>) {wordRepository.addWords(words)}

    fun addWord(word: Word) {wordRepository.addWord(word)}
}