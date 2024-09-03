package com.recipes_app.learningapp.repositories.words

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.recipes_app.learningapp.models.questions.Word

class WordRepositorySqlite (private val context: Context, private val factory: SQLiteDatabase.CursorFactory?)
    : SQLiteOpenHelper(context, "app", factory, 1),
    WordRepository {

    // WordRepository
    override fun getAll(): List<Word> {
        val query: String = "SELECT * FROM words"

        val db = this.readableDatabase

        val cursor: Cursor = db.rawQuery(query, null)

        val wordsList = mutableListOf<Word>()

        val originalIndex = cursor.getColumnIndex("original")
        val translatedIndex = cursor.getColumnIndex("translated")
        val themeIndex = cursor.getColumnIndex("theme")


        if (originalIndex < 0 || translatedIndex < 0 || themeIndex < 0) return wordsList

        if (cursor.moveToFirst()) {
            do {
                val original = cursor.getString(originalIndex)
                val translated = cursor.getString(translatedIndex)
                val theme = cursor.getString(themeIndex)
                val word = Word(original, translated, theme)
                wordsList.add(word)
            } while (cursor.moveToNext())
        }

        cursor.close()

        Log.v("Selected words", "${wordsList.size}")

        return wordsList
    }

    override fun addWords(words: List<Word>) {
        val db = this.writableDatabase
        db.beginTransaction()

        val sql = "INSERT INTO words (original, translated, theme) VALUES (?, ?, ?)"

        val statement = db.compileStatement(sql)


        for (word in words) {
            statement.clearBindings()
            statement.bindString(1, word.original)
            statement.bindString(2, word.translated)
            statement.bindString(3, word.theme)
            statement.executeInsert()
        }

        db.setTransactionSuccessful()
        db.endTransaction()
    }

    override fun addWord(word: Word) {
        val db = this.writableDatabase

        val values = ContentValues()
        values.put("original", word.original)
        values.put("translated", word.translated)
        values.put("theme", word.theme)
        db.insert("words", null, values)
    }

    // SQLiteOpenHelper
    override fun onCreate(db: SQLiteDatabase?) {
        val query: String = "CREATE TABLE words (id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "original TEXT, translated TEXT, theme TEXT)"

        db!!.execSQL(query)

        // delete later
        db.beginTransaction()

        val sql = "INSERT INTO words (original, translated, theme) VALUES (?, ?, ?)"

        val statement = db.compileStatement(sql)


        for (word in dictionary) {
            statement.clearBindings()
            statement.bindString(1, word.original)
            statement.bindString(2, word.translated)
            statement.bindString(3, word.theme)
            statement.executeInsert()
        }

        db.setTransactionSuccessful()
        db.endTransaction()
    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS words")
        onCreate(db)
    }

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
}