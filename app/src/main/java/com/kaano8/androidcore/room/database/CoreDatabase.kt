package com.kaano8.androidcore.com.kaano8.androidcore.room.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.kaano8.androidcore.com.kaano8.androidcore.filedownloader.repository.dao.FileDownloadDao
import com.kaano8.androidcore.com.kaano8.androidcore.filedownloader.repository.model.DownloadTask
import com.kaano8.androidcore.com.kaano8.androidcore.filedownloader.repository.typeconvertors.FileDownloadTypeConvertor
import com.kaano8.androidcore.com.kaano8.androidcore.room.dao.WordDao
import com.kaano8.androidcore.com.kaano8.androidcore.room.entity.Word
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

// Annotates class to be a Room Database with a table (entity) of the Word class
@Database(entities = [Word::class, DownloadTask::class], version = 2, exportSchema = false)
abstract class CoreDatabase : RoomDatabase() {

    abstract fun wordDao(): WordDao

    abstract fun fileDownloadDao(): FileDownloadDao

    private class WordDatabaseCallback(private val coroutineScope: CoroutineScope) :
        RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { wordRoomDatabase ->
                coroutineScope.launch {
                    val wordDao = wordRoomDatabase.wordDao()

                    // Delete all content here.
                    wordDao.deleteAll()

                    // Add sample words.
                    var word = Word(word = "Hello")
                    wordDao.insert(word)
                    word = Word(word = "World!")
                    wordDao.insert(word)

                    // TODO: Add your own words!
                    word = Word(word = "TODO!")
                    wordDao.insert(word)
                }

            }
        }
    }

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: CoreDatabase? = null

        fun getDatabase(context: Context, coroutineScope: CoroutineScope): CoreDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    CoreDatabase::class.java,
                    "word_database"
                ).addCallback(WordDatabaseCallback(coroutineScope)).build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }
}
