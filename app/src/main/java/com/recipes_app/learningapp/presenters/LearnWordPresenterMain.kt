package com.recipes_app.learningapp.presenters

import android.content.Context
import com.recipes_app.learningapp.business_classes.QuestionGeneratorPreload
import com.recipes_app.learningapp.models.questions.Question
import com.recipes_app.learningapp.models.questions.TestProgress
import com.recipes_app.learningapp.repositories.words.WordRepositorySqlite
import com.recipes_app.learningapp.services.words.WordService
import com.recipes_app.learningapp.views.LearnWordView

class LearnWordPresenterMain(private val view: LearnWordView, private val context: Context) : LearnWordPresenter {
    private val questionGenerator = QuestionGeneratorPreload(WordService(WordRepositorySqlite(context, null)))
    override fun showNextQuestion() {
        val testProgress: TestProgress = questionGenerator.nextQuestion()
        view.changeProgressBar(testProgress.numOfCurQuestion)
        val question: Question? = testProgress.curQuestion

        if (question == null) {
            view.toMain(testProgress.wrongAnswers)
        } else {
            view.setWordToTranslate(question.correctAnswer.original)

            view.setTranslationOptions(question.variants[0].translated,
                question.variants[1].translated,
                question.variants[2].translated,
                question.variants[3].translated)
        }
    }

    override fun option1Clicked() {
        if(questionGenerator.answer(0)) {
            view.showAnswerResult(true, 1)
            view.showResultMessage(true)
        } else {
            view.showAnswerResult(false, 1)
            view.showResultMessage(false)
        }
    }

    override fun option2Clicked() {
        if(questionGenerator.answer(1)) {
            view.showAnswerResult(true, 2)
            view.showResultMessage(true)
        } else {
            view.showAnswerResult(false, 2)
            view.showResultMessage(false)
        }
    }

    override fun option3Clicked() {
        if(questionGenerator.answer(2)) {
            view.showAnswerResult(true, 3)
            view.showResultMessage(true)
        } else {
            view.showAnswerResult(false, 3)
            view.showResultMessage(false)
        }
    }

    override fun option4Clicked() {
        if(questionGenerator.answer(3)) {
            view.showAnswerResult(true, 4)
            view.showResultMessage(true)
        } else {
            view.showAnswerResult(false, 4)
            view.showResultMessage(false)
        }
    }

    override fun startTest(numOfWords: Int, theme: String) {
        questionGenerator.startTest(numOfWords, theme)
        view.setProgressBar(questionGenerator.numOfQuestions)
    }

}