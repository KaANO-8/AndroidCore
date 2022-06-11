package com.kaano8.androidcore.com.kaano8.androidcore.adapter.viewholder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.kaano8.androidcore.com.kaano8.androidcore.adapter.data.MainListData

abstract class BaseViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    abstract fun bind(item: MainListData)
}