package com.kaano8.androidcore.com.kaano8.androidcore.filedownloader.repository.typeconvertors

import androidx.room.TypeConverter
import com.kaano8.androidcore.com.kaano8.androidcore.filedownloader.repository.model.DownloadStatus

class FileDownloadTypeConvertor {
    @TypeConverter
    fun fromDownloadStatus(value: String): DownloadStatus {
        return DownloadStatus.valueOf(value)
    }

    @TypeConverter
    fun dateToTimestamp(status: DownloadStatus): String {
        return status.name
    }
}