package com.kaano8.androidcore.filedownloader.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import com.kaano8.androidcore.com.kaano8.androidcore.filedownloader.dispatcher.FileDownloadDispatcher
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class FileDownloaderService : Service() {

    @Inject
    lateinit var fileDownloadDispatcher: FileDownloadDispatcher
    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        super.onStartCommand(intent, flags, startId)
        intent?.extras?.let {
            val action =  it.getString(ACTION)
            val requestName = it.getString(REQUEST_NAME) ?: ""
            val taskId = it.getLong(TASK_ID)

            when (action) {
                Action.START.name -> fileDownloadDispatcher.dispatch(requestName)
                Action.PAUSE.name -> fileDownloadDispatcher.pause(taskId)
                Action.CANCEL.name -> fileDownloadDispatcher.cancel(taskId)
                Action.CANCEL_ALL.name -> fileDownloadDispatcher.cancelAll()
            }

        }

        return START_NOT_STICKY
    }

    companion object {
        enum class Action {
            START, PAUSE, CANCEL, CANCEL_ALL
        }
        const val ACTION = "Action"
        const val REQUEST_NAME = "requestName"
        const val TASK_ID = "taskId"
    }
}