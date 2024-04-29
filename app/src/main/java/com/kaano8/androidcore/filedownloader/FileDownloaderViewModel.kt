package com.kaano8.androidcore.filedownloader

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kaano8.androidcore.com.kaano8.androidcore.filedownloader.repository.FileDownloadRepository
import com.kaano8.androidcore.com.kaano8.androidcore.filedownloader.repository.model.DownloadTask
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FileDownloaderViewModel @Inject constructor(private val fileDownloadRepository: FileDownloadRepository) :
    ViewModel() {
    fun getAllItems(): Flow<List<DownloadTask>> = fileDownloadRepository.getAllTasks()

    fun clearAll() {
        viewModelScope.launch {
            fileDownloadRepository.clearAll()
        }
    }
}