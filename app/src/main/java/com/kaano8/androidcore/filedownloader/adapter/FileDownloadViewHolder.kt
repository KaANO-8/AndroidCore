package com.kaano8.androidcore.com.kaano8.androidcore.filedownloader.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kaano8.androidcore.com.kaano8.androidcore.filedownloader.repository.model.DownloadTask
import com.kaano8.androidcore.databinding.DownloadListItemBinding
import java.util.zip.Inflater

class FileDownloadViewHolder(private val binding: DownloadListItemBinding): RecyclerView.ViewHolder(binding.root) {

    fun bind(downloadTask: DownloadTask, onPause: (Int) -> Unit, onStop: (Int) -> Unit) {
        with(binding) {
            progressBar.progress = downloadTask.progress
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