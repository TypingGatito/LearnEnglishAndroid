package com.recipes_app.learningapp.presenters

import com.recipes_app.learningapp.activities.TestResultView

class TestResultPresenterMain (private val view: TestResultView) : TestResultPresenter {

    override fun onBackToMainClicked() {
    }

    override fun onViewCreated() {
        view.showAnswers()
        view.showResultText()
    }
}