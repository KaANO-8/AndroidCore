package com.kaano8.androidcore.com.kaano8.androidcore.filedownloader.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.kaano8.androidcore.com.kaano8.androidcore.filedownloader.repository.model.DownloadTask

class FileDownloadAdapter(private val onPause: (Int) -> Unit, private val onStop: (Int) -> Unit) :
    ListAdapter<DownloadTask, RecyclerView.ViewHolder>(FileDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return FileDownloadViewHolder.create(parent = parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as? FileDownloadViewHolder)?.bind(
            downloadTask = getItem(position),
            onPause = onPause,
            onStop = onStop
        )
    }

    class FileDiffUtil : DiffUtil.ItemCallback<DownloadTask>() {
        override fun areItemsTheSame(oldItem: DownloadTask, newItem: DownloadTask): Boolean =
            oldItem.id == newItem.id


        override fun areContentsTheSame(oldItem: DownloadTask, newItem: DownloadTask): Boolean =
            oldItem == newItem
    }
}