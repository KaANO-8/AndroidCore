package com.kaano8.androidcore.com.kaano8.androidcore.room.repository

import androidx.annotation.WorkerThread
import com.kaano8.androidcore.com.kaano8.androidcore.room.dao.WordDao
import com.kaano8.androidcore.com.kaano8.androidcore.room.entity.Word
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

// Declares the DAO as a private property in the constructor. Pass in the DAO
// instead of the whole database, because you only need access to the DAO
class WordRepository @Inject constructor(private val wordDao: WordDao) {

    // Room executes all queries on a separate thread.
    // Observed Flow will notify the observer when the data has changed.
    val allWords: Flow<List<Word>> = wordDao.getAlphabetizedWords()

    // By default Room runs suspend queries off the main thread, therefore, we don't need to
    // implement anything else to ensure we're not doing long running database work
    // off the main thread.
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(word: Word) {
        wordDao.insert(word)
    }

    @WorkerThread
    suspend fun deleteAll() {
        wordDao.deleteAll()
    }
}
