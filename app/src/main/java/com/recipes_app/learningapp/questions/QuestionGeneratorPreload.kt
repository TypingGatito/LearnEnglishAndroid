package com.recipes_app.learningapp.questions


const val NUMBER_OF_ANSWERS: Int = 4


class QuestionGeneratorPreload :
    QuestionGenerator {

    private val dictionary = listOf(
        Word("Green", "Зеленый","Цвета"),
        Word("Red","Красный", "Цвета"),
        Word("Black","Черный", "Цвета"),
        Word("Yellow","Желтый", "Цвета"),

        Word("Cat","Кот", "Животные"),
        Word("Dog","Собака", "Животные"),
        Word("Mouse","Мышь", "Животные"),
        Word("Snake","Змея", "Животные"),
        Word("Owl","Сова", "Животные"),

        Word("House","Дом", "Здания"),
        Word("Nursing home","Дом престарелых", "Здания"),
        Word("Hospital","Больница", "Здания"),
        Word("School","Школа", "Здания"),
    )

    private var currentQuestion: Question? = null

    private var count: Int = 1

    private var curWords: List<Word> = dictionary

    override fun startTest(numOfWords: Int, theme: String) {

        curWords = dictionary.filter {
            word: Word -> word.theme == theme
        }

        count = if (curWords.size < numOfWords) curWords.size else numOfWords
    }

    override fun getNextQuestion(): Question? {

        val notLearnedList = curWords.filter { !it.learned }
        if (notLearnedList.isEmpty() || count == 0) return null

        val questionWords =
            if (curWords.size < NUMBER_OF_ANSWERS) {
                curWords.shuffled()
                    .take(NUMBER_OF_ANSWERS) + dictionary
                    .take(NUMBER_OF_ANSWERS - curWords.size)
            } else {
                curWords.shuffled().take(NUMBER_OF_ANSWERS)
            }.shuffled()

        val correctAnswer: Word = questionWords.random()

        currentQuestion = Question(
            variants = questionWords,
            correctAnswer = correctAnswer,
        )

        count--
        return currentQuestion
    }

    override fun checkAnswer(userAnswerIndex: Int?): Boolean {

        return currentQuestion?.let {
            val correctAnswerId = it.variants.indexOf(it.correctAnswer)
            if (correctAnswerId == userAnswerIndex) {
                it.correctAnswer.learned = true
                true
            } else {
                false
            }
        } ?: false
    }

    override fun getThemes(): Set<String> {
        return dictionary
            .map{ word: Word -> word.theme }
            .toSet()
    }

}
