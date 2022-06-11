package com.kaano8.androidcore.com.kaano8.androidcore.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.kaano8.androidcore.R
import com.kaano8.androidcore.com.kaano8.androidcore.adapter.data.MainListData
import com.kaano8.androidcore.com.kaano8.androidcore.adapter.viewholder.BaseViewHolder
import com.kaano8.androidcore.com.kaano8.androidcore.adapter.viewholder.DataItemViewHolder
import com.kaano8.androidcore.com.kaano8.androidcore.adapter.viewholder.HeaderViewHolder

class MainAdapter : ListAdapter<MainListData, BaseViewHolder>(MainDiffUtilCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val holder: BaseViewHolder? = when (viewType) {
            R.layout.header_row -> {
                HeaderViewHolder(
                    LayoutInflater.from(parent.context).inflate(viewType, parent, false)
                )
            }
            R.layout.main_list_item -> {
                DataItemViewHolder(
                    LayoutInflater.from(parent.context).inflate(viewType, parent, false)
                )
            }
            else -> null
        }
        return holder!!
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is MainListData.HeaderItem -> R.layout.header_row
            is MainListData.DataItem -> R.layout.main_list_item
        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    /**
     * Need to check more on heterogeneous data
     */
    class MainDiffUtilCallback : DiffUtil.ItemCallback<MainListData>() {
        override fun areItemsTheSame(oldItem: MainListData, newItem: MainListData): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: MainListData, newItem: MainListData): Boolean {
            return when {
                oldItem is MainListData.HeaderItem && newItem is MainListData.HeaderItem -> oldItem.header == newItem.header
                oldItem is MainListData.DataItem && newItem is MainListData.DataItem -> oldItem.title == newItem.title
                else -> false
            }
        }
    }
}
