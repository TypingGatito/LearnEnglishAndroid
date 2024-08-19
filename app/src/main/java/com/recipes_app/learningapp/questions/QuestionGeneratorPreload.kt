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
        Word("Church","Церковь", "Здания"),
        Word("Church", "Церковь", "Здания"),
        Word("Cathedral", "Собор", "Здания"),
        Word("Chapel", "Часовня", "Здания"),
        Word("Basilica", "Базилика", "Здания"),
        Word("Monastery", "Монастырь", "Здания"),
        Word("Palace", "Дворец", "Здания"),
        Word("Mansion", "Особняк", "Здания"),
        Word("Temple", "Храм", "Здания"),
        Word("Library", "Библиотека", "Здания"),
        Word("Museum", "Музей", "Здания"),
        Word("Airport", "Аэропорт", "Здания"),
        Word("Station", "Вокзал", "Здания"),

        Word("Bread", "Хлеб", "Еда"),
        Word("Cheese", "Сыр", "Еда"),
        Word("Butter", "Масло", "Еда"),
        Word("Meat", "Мясо", "Еда"),
        Word("Fruit", "Фрукты", "Еда"),
        Word("Vegetable", "Овощи", "Еда"),

        Word("Milk", "Молоко", "Напитки"),
        Word("Juice", "Сок", "Напитки"),
        Word("Tea", "Чай", "Напитки"),
        Word("Coffee", "Кофе", "Напитки"),

        Word("Train", "Поезд", "Транспорт"),
        Word("Bus", "Автобус", "Транспорт"),
        Word("Airplane", "Самолет", "Транспорт"),
        Word("Ship", "Корабль", "Транспорт"),
        Word("Bicycle", "Велосипед", "Транспорт"),

        Word("Ticket", "Билет", "Документы"),
        Word("Passport", "Паспорт", "Документы"),
        Word("Luggage", "Багаж", "Документы"),
    )

    private var curWords: List<Word> = dictionary

    private var testProgress: TestProgress = TestProgress()

    override fun startTest(numOfWords: Int, theme: String) {

        curWords = dictionary.filter {
            word: Word -> word.theme == theme
        }

        val numOfQuestions = if (curWords.size < numOfWords) curWords.size else numOfWords

        testProgress.setAll(numOfQuestions)
    }

    override fun nextQuestion(): TestProgress {
        if (!testProgress.needsQuestions()) {
            testProgress.curQuestion = null
            return testProgress
        }

        val questionWords =
            if (curWords.size < NUMBER_OF_ANSWERS) {
                curWords.shuffled()
                    .take(NUMBER_OF_ANSWERS) + dictionary
                    .take(NUMBER_OF_ANSWERS - curWords.size)
            } else {
                curWords.shuffled().take(NUMBER_OF_ANSWERS)
            }.shuffled()

        val correctAnswer: Word = questionWords.random()


        testProgress.curQuestion = Question(
            variants = questionWords,
            correctAnswer = correctAnswer,
        )
        testProgress.nextQuestion()
        return testProgress
    }

    override fun answer(userAnswerIndex: Int): Boolean {
        val isCorrect: Boolean = testProgress.curQuestion?.let {
            val correctAnswerId = it.variants.indexOf(it.correctAnswer)
            if (correctAnswerId == userAnswerIndex) {
                it.correctAnswer.learned = true
                true
            } else {
                false
            }
        } ?: false

        val ans: Answer = Answer(testProgress.curQuestion, userAnswerIndex, testProgress.numOfCurQuestion)
        if (!isCorrect) testProgress.wrongAnswers.add(ans)

        return isCorrect
    }

    override fun getThemes(): Set<String> {
        return dictionary
            .map{ word: Word -> word.theme }
            .toSet()
    }

}
