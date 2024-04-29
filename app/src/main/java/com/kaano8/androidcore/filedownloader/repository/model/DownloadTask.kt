package com.kaano8.androidcore.com.kaano8.androidcore.filedownloader.repository.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "file_download")
data class DownloadTask(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    @ColumnInfo("name") val name: String,
    @ColumnInfo("progress") val progress: Int = 0,
    @ColumnInfo("downloadStatus") val downloadStatus: DownloadStatus
)

enum class DownloadStatus {
    QUEUED, COMPLETE, IN_PROGRESS, PAUSED, CANCELLED
}
