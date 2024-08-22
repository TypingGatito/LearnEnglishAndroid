package com.recipes_app.learningapp.questions

import android.os.Parcel
import android.os.Parcelable

data class Answer(
    private val question: Question?,
    private val indexOfUsersAns: Int,
    private val id: Int,
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readParcelable(Question::class.java.classLoader),
        parcel.readInt(),
        parcel.readInt()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeParcelable(question, flags)
        parcel.writeInt(indexOfUsersAns)
        parcel.writeInt(id)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Answer> {
        override fun createFromParcel(parcel: Parcel): Answer {
            return Answer(parcel)
        }

        override fun newArray(size: Int): Array<Answer?> {
            return arrayOfNulls(size)
        }
    }

    fun getUsersAnswer(): String = question?.let {
        it.variants.getOrNull(indexOfUsersAns)?.translate ?: "Unknown"
    } ?: "Unknown"

    fun getQuestionWord(): String = question?.correctAnswer?.original ?: "Unknown"

    fun getRightAnswer(): String = question?.correctAnswer?.translate ?: "Unknown"
}