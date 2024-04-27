package com.kaano8.androidcore.com.kaano8.androidcore.filedownloader.repository

import com.kaano8.androidcore.com.kaano8.androidcore.filedownloader.repository.dao.FileDownloadDao
import com.kaano8.androidcore.com.kaano8.androidcore.filedownloader.repository.model.DownloadTask
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class FileDownloadRepositoryImpl @Inject constructor(private val fileDownloadDao: FileDownloadDao) :
    FileDownloadRepository {

    override suspend fun insertTask(task: DownloadTask) {
        withContext(Dispatchers.IO) {
            fileDownloadDao.insertDownloadLog(task)
        }
    }

    override fun getAllTasks(): Flow<List<DownloadTask>> =
        fileDownloadDao.getAllDownloadLogs()

    override suspend fun deleteTask(downloadId: Int) {
        fileDownloadDao.deleteTask(downloadId)
    }
}