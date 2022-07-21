package com.kaano8.androidcore.com.kaano8.androidcore.room.dao

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.kaano8.androidcore.com.kaano8.androidcore.room.entity.SQLiteWord

class SQLiteWordDaoImpl(context: Context) : SQLiteOpenHelper(context, WORD_DATABASE, null, 1), SQLiteWordDao {
    override fun onCreate(db: SQLiteDatabase?) {
        TODO("Not yet implemented")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("Not yet implemented")
    }

    override fun insert(word: SQLiteWord) {
        TODO("Not yet implemented")
    }

    override fun getAllWords(): List<SQLiteWord> {
        TODO("Not yet implemented")
    }

    companion object {
        private const val WORD_DATABASE = "WORD_DATABASE"
    }
}