package com.kaano8.androidcore.com.kaano8.androidcore.filedownloader.repository

import com.kaano8.androidcore.com.kaano8.androidcore.filedownloader.repository.model.DownloadTask
import kotlinx.coroutines.flow.Flow

interface FileDownloadRepository {

    suspend fun insertTask(task: DownloadTask)
    fun getAllTasks(): Flow<List<DownloadTask>>

    suspend fun deleteTask(downloadId: Int)
}