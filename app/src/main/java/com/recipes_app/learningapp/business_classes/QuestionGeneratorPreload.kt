package com.recipes_app.learningapp.business_classes

import com.recipes_app.learningapp.models.questions.Answer
import com.recipes_app.learningapp.models.questions.Question
import com.recipes_app.learningapp.models.questions.TestProgress
import com.recipes_app.learningapp.models.questions.Word
import com.recipes_app.learningapp.services.words.WordService


const val NUMBER_OF_ANSWERS: Int = 4


class QuestionGeneratorPreload (val wordService: WordService) :
    QuestionGenerator {

    private val dictionary = wordService.getAll()

    private var curWords: List<Word> = dictionary

    private var testProgress: TestProgress = TestProgress()

    override var numOfQuestions: Int = 0
        get() = field
        set(numOfQuestions) {field = if (numOfQuestions < 0) 0 else numOfQuestions}

    override fun startTest(numOfWords: Int, theme: String) {

        curWords = dictionary.filter {
            word: Word -> word.theme == theme
        }

        numOfQuestions = if (curWords.size < numOfWords) curWords.size else numOfWords

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
