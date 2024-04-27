package com.kaano8.androidcore.com.kaano8.androidcore.filedownloader.repository.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "file_download")
data class DownloadTask(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo("name") val name: String,
    @ColumnInfo("time") val time: Int,
    @ColumnInfo("progress") val progress: Int
)

enum class DownloadStatus {
    INIT, COMPLETE, INPROGRESS, PAUSED, FAILED
}
