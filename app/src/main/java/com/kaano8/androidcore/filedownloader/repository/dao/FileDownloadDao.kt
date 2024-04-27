package com.kaano8.androidcore.com.kaano8.androidcore.filedownloader.repository.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.kaano8.androidcore.com.kaano8.androidcore.filedownloader.repository.model.DownloadTask
import kotlinx.coroutines.flow.Flow
import retrofit2.http.DELETE


@Dao
interface FileDownloadDao {

    @Insert
    fun insertDownloadLog(downloadTask: DownloadTask)

    @Query("SELECT * FROM file_download")
    fun getAllDownloadLogs(): Flow<List<DownloadTask>>

    @Query("DELETE FROM file_download WHERE ID = :id")
    suspend fun deleteTask(id: Int)
}