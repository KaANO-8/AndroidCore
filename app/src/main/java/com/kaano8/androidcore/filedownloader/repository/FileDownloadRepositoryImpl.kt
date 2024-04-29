package com.kaano8.androidcore.com.kaano8.androidcore.filedownloader.repository

import com.kaano8.androidcore.com.kaano8.androidcore.filedownloader.repository.dao.FileDownloadDao
import com.kaano8.androidcore.com.kaano8.androidcore.filedownloader.repository.model.DownloadStatus
import com.kaano8.androidcore.com.kaano8.androidcore.filedownloader.repository.model.DownloadTask
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FileDownloadRepositoryImpl @Inject constructor(private val fileDownloadDao: FileDownloadDao) :
    FileDownloadRepository {

    override suspend fun insertTask(task: DownloadTask): Long {
        return fileDownloadDao.insertDownloadLog(task)
    }

    override fun getAllTasks(): Flow<List<DownloadTask>> =
        fileDownloadDao.getAllDownloadLogs()

    override suspend fun deleteTask(downloadId: Long) {
        fileDownloadDao.deleteTask(downloadId)
    }

    override fun updateStatus(id: Long, downloadStatus: DownloadStatus) {
        fileDownloadDao.updateDownloadStatus(id, downloadStatus)
    }

    override fun updateProgress(id: Long, progress: Int) {
        fileDownloadDao.updateProgress(id, progress)
    }

    override suspend fun clearAll() {
        fileDownloadDao.clearAll()
    }
}