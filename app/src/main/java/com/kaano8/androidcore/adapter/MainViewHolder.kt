package com.kaano8.androidcore.com.kaano8.androidcore.adapter

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.kaano8.androidcore.R
import com.kaano8.androidcore.com.kaano8.androidcore.adapter.data.MainDataHolder

class MainViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

    private val title = itemView.findViewById<TextView>(R.id.title)
    private val detail = itemView.findViewById<TextView>(R.id.detail)

    fun bind(item: MainDataHolder) {
        title.text = item.title
        detail.text = item.detail
        itemView.setOnClickListener { item.action() }
    }
}
