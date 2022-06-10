package com.kaano8.androidcore.com.kaano8.androidcore.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.kaano8.androidcore.R
import com.kaano8.androidcore.com.kaano8.androidcore.adapter.data.MainDataHolder

class MainAdapter : ListAdapter<MainDataHolder, MainViewHolder>(MainDiffUtilCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.main_list_item, parent, false)
        return MainViewHolder(view)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    class MainDiffUtilCallback : DiffUtil.ItemCallback<MainDataHolder>() {
        override fun areItemsTheSame(oldItem: MainDataHolder, newItem: MainDataHolder): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: MainDataHolder, newItem: MainDataHolder): Boolean {
            return oldItem.title == newItem.title
        }
    }
}