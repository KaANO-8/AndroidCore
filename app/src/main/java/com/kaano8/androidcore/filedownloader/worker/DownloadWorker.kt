package com.kaano8.androidcore.com.kaano8.androidcore.filedownloader.worker

import com.kaano8.androidcore.com.kaano8.androidcore.filedownloader.repository.FileDownloadRepository
import com.kaano8.androidcore.com.kaano8.androidcore.filedownloader.repository.model.DownloadStatus
import kotlinx.coroutines.Job
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import java.util.concurrent.CancellationException
import javax.inject.Inject

class DownloadWorker @Inject constructor(private val repository: FileDownloadRepository) {

    private val taskMap = mutableMapOf<Long, Job>()
    fun pause(taskId: Long) {
        if (taskId in taskMap) {
            taskMap[taskId]!!.cancel(CancellationException("PAUSE"))
        }
    }

    fun cancel(taskId: Long) {
        if (taskId in taskMap) {
            taskMap[taskId]!!.cancel(CancellationException("CANCELLED"))
        }
    }

    suspend fun download(taskId: Long) = coroutineScope {
        val job =  launch {
            repository.updateStatus(taskId, DownloadStatus.IN_PROGRESS)

            repeat(5) {
                repository.updateProgress(taskId, it * 10)
                // Mimic long running rule
                Thread.sleep(1_000)
            }
        }

        taskMap[taskId] = job

        job.invokeOnCompletion {
            launch(NonCancellable) {
                if (it == null)
                    repository.updateStatus(taskId, DownloadStatus.COMPLETE)

                when(it?.message) {
                    "PAUSE" -> repository.updateStatus(taskId, DownloadStatus.PAUSED)
                    "CANCELLED" -> repository.updateStatus(taskId, DownloadStatus.CANCELLED)
                }
            }
        }

    }
}