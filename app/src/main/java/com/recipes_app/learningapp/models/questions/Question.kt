package com.recipes_app.learningapp.models.questions

import android.os.Parcel
import android.os.Parcelable

data class Question(
    val variants: List<Word>,
    val correctAnswer: Word,
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.createTypedArrayList(Word) ?: emptyList(),
        parcel.readParcelable(Word::class.java.classLoader)!!
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeTypedList(variants)
        parcel.writeParcelable(correctAnswer, flags)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Question> {
        override fun createFromParcel(parcel: Parcel): Question {
            return Question(parcel)
        }

        override fun newArray(size: Int): Array<Question?> {
            return arrayOfNulls(size)
        }
    }
}