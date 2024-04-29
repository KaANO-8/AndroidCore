package com.kaano8.androidcore.com.kaano8.androidcore.filedownloader.dispatcher

import com.kaano8.androidcore.com.kaano8.androidcore.filedownloader.repository.FileDownloadRepository
import com.kaano8.androidcore.com.kaano8.androidcore.filedownloader.repository.model.DownloadStatus
import com.kaano8.androidcore.com.kaano8.androidcore.filedownloader.repository.model.DownloadTask
import com.kaano8.androidcore.com.kaano8.androidcore.filedownloader.worker.DownloadWorker
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class FileDownloadDispatcher @Inject constructor(private val repository: FileDownloadRepository, private val worker: DownloadWorker) {

    @OptIn(ExperimentalCoroutinesApi::class)
    private val coroutineDispatcher = Dispatchers.IO.limitedParallelism(1)

    private val dispatcherJob = SupervisorJob()

    private val unlimitedScope = CoroutineScope(Dispatchers.Default + dispatcherJob)
    private val limitedConcurrencyScope = CoroutineScope(CoroutineName("FileDownloaderDispatcher") + dispatcherJob + coroutineDispatcher)
    private val taskQueue = MutableSharedFlow<Long>(extraBufferCapacity = 20)

    init {
        limitedConcurrencyScope.launch {
            taskQueue.collect {
                println("collecting task id = $it")
                worker.download(taskId = it)
            }
        }
    }

    fun dispatch(requestName: String) {
        unlimitedScope.launch {
            val taskId = repository.insertTask(DownloadTask(name = requestName, downloadStatus = DownloadStatus.QUEUED))
            println("emitting task id = $taskId")
            taskQueue.emit(taskId)
        }
    }

    fun pause(taskId: Long) = worker.pause(taskId = taskId)

    fun cancel(taskId: Long) = worker.cancel(taskId = taskId)

    fun cancelAll() = dispatcherJob.cancelChildren()
}