package com.kaano8.androidcore.com.kaano8.androidcore.workmanager.workers

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import com.kaano8.androidcore.com.kaano8.androidcore.room.dao.WordDao
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first

private const val TAG = "SuggestionWorker"
@HiltWorker
class SuggestionWorker @AssistedInject constructor(
    @Assisted private val context: Context,
    @Assisted workParams: WorkerParameters,
    private val wordDao: WordDao
) : CoroutineWorker(context, workParams) {
    override suspend fun doWork(): Result {
        with(Dispatchers.IO) {
            return try {
                delay(5_000)
                val word = wordDao.getAlphabetizedWords().first().random().word
                val data = workDataOf(NEW_WORD to word)
                Log.d(TAG, "doWork: $word")
                Result.success(data)
            } catch (exception: Exception) {
                Log.d(TAG, "doWork: ${exception.message}")
                Result.failure()
            }
        }
    }

    companion object {
        const val NEW_WORD = "newWord"
    }
}