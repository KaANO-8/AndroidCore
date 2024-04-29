package com.kaano8.androidcore.com.kaano8.androidcore.filedownloader.repository

import com.kaano8.androidcore.com.kaano8.androidcore.filedownloader.repository.model.DownloadStatus
import com.kaano8.androidcore.com.kaano8.androidcore.filedownloader.repository.model.DownloadTask
import kotlinx.coroutines.flow.Flow

interface FileDownloadRepository {

    suspend fun insertTask(task: DownloadTask): Long
    fun getAllTasks(): Flow<List<DownloadTask>>

    suspend fun deleteTask(downloadId: Long)

    fun updateStatus(id: Long, downloadStatus: DownloadStatus)

    fun updateProgress(id: Long, progress: Int)

    suspend fun clearAll()
}