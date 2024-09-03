package com.recipes_app.learningapp.models.questions


class TestProgress {
    var numOfQuestions: Int = 0
        get() = field
        set(value) {field = value}

    var wrongAnswers: MutableList<Answer> = ArrayList<Answer>()
        get() = field
        set(value) {field = value}

    var curQuestion: Question? = null
        get() = field
        set(value) {field = value}

    var numOfCurQuestion: Int = 0
        get() = field
        set(value) {field = value}

    private fun setToDefault() {
        numOfQuestions = 0
        wrongAnswers = ArrayList<Answer>()
        curQuestion = null
        numOfCurQuestion = 0
    }

    fun setAll(numOfQuestions: Int) {
        setToDefault()
        this.numOfQuestions = numOfQuestions
    }

    fun needsQuestions(): Boolean = (numOfCurQuestion < numOfQuestions)

    fun nextQuestion() { numOfCurQuestion += 1 }
}
