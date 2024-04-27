package com.kaano8.androidcore.com.kaano8.androidcore.filedownloader

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kaano8.androidcore.com.kaano8.androidcore.filedownloader.repository.FileDownloadRepository
import com.kaano8.androidcore.filedownloader.FileDownloaderViewModel
import javax.inject.Inject

@Suppress("UNCHECKED_CAST")
class FileDownloadViewModelFactory @Inject constructor(private val fileDownloadRepository: FileDownloadRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return FileDownloaderViewModel(fileDownloadRepository) as T
    }
}