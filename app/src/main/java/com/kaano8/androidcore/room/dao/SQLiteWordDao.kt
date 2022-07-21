package com.kaano8.androidcore.com.kaano8.androidcore.room.dao

import com.kaano8.androidcore.com.kaano8.androidcore.room.entity.SQLiteWord
import com.kaano8.androidcore.com.kaano8.androidcore.room.entity.Word

interface SQLiteWordDao {
    fun insert(word: SQLiteWord)
    fun getAllWords(): List<SQLiteWord>
}