package com.kaano8.androidcore.com.kaano8.androidcore.filedownloader.repository.typeconvertors

import androidx.room.TypeConverter
import com.kaano8.androidcore.com.kaano8.androidcore.filedownloader.repository.model.DownloadStatus

class FileDownloadTypeConvertor {
    @TypeConverter
    fun toDownloadStatus(value: String): DownloadStatus = enumValueOf<DownloadStatus>(value)

    @TypeConverter
    fun fromDownloadStatus(status: DownloadStatus): String = status.name
}