package com.kaano8.androidcore.com.kaano8.androidcore.filedownloader.repository.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.kaano8.androidcore.com.kaano8.androidcore.filedownloader.repository.model.DownloadStatus
import com.kaano8.androidcore.com.kaano8.androidcore.filedownloader.repository.model.DownloadTask
import kotlinx.coroutines.flow.Flow


@Dao
interface FileDownloadDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDownloadLog(downloadTask: DownloadTask): Long

    @Query("SELECT * FROM file_download")
    fun getAllDownloadLogs(): Flow<List<DownloadTask>>

    @Query("DELETE FROM file_download WHERE ID = :id")
    suspend fun deleteTask(id: Long)

    @Query("UPDATE file_download SET downloadStatus = :downloadStatus WHERE id = :id")
    fun updateDownloadStatus(id: Long, downloadStatus: DownloadStatus)

    @Query("UPDATE file_download SET progress = :progress WHERE id = :id")
    fun updateProgress(id: Long, progress: Int)

    @Query("DELETE FROM file_download")
    suspend fun clearAll()
}