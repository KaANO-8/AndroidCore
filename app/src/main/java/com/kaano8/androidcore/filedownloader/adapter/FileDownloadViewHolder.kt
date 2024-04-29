package com.kaano8.androidcore.com.kaano8.androidcore.filedownloader.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kaano8.androidcore.com.kaano8.androidcore.filedownloader.repository.model.DownloadTask
import com.kaano8.androidcore.databinding.DownloadListItemBinding

class FileDownloadViewHolder(private val binding: DownloadListItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(downloadTask: DownloadTask, onPause: (Long) -> Unit, onStop: (Long) -> Unit) {
        with(binding) {
            progressBar.progress = downloadTask.progress
            taskId.text = downloadTask.id.toString()
            taskName.text = downloadTask.name
            taskStatus.text = downloadTask.downloadStatus.name
            pauseButton.setOnClickListener { onPause(downloadTask.id) }
            stopButton.setOnClickListener { onStop(downloadTask.id) }
        }
    }

    companion object {
        fun create(parent: ViewGroup): FileDownloadViewHolder {
            val view = DownloadListItemBinding.inflate(LayoutInflater.from(parent.context))
            return FileDownloadViewHolder(view)
        }
    }
}