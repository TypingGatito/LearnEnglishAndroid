package com.recipes_app.learningapp.presenters

import com.recipes_app.learningapp.models.questions.Answer
import com.recipes_app.learningapp.views.TestResultView

class TestResultPresenterMain (private val view: TestResultView) : TestResultPresenter {

    override fun onViewCreated(answers: List<Answer>?) {
        view.showAnswers(answers)
        view.showResultText(answers)
    }

    override fun showAnswers(answers: List<Answer>?) {
        view.showAnswers(answers)
    }

    override fun showResultText(answers: List<Answer>?) {
        view.showResultText(answers)
    }

}