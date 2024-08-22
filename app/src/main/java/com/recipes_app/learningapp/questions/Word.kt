package com.recipes_app.learningapp.questions

import android.os.Parcel
import android.os.Parcelable

data class Word(
    val original: String,
    val translate: String,
    val theme: String,
    var learned: Boolean = false,
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readByte() != 0.toByte() // Преобразуем Byte в Boolean
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(original)
        parcel.writeString(translate)
        parcel.writeString(theme)
        parcel.writeByte(if (learned) 1 else 0) // Преобразуем Boolean в Byte
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Word> {
        override fun createFromParcel(parcel: Parcel): Word {
            return Word(parcel)
        }

        override fun newArray(size: Int): Array<Word?> {
            return arrayOfNulls(size)
        }
    }
}