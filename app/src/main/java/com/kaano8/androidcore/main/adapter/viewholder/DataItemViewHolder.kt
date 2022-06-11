package com.kaano8.androidcore.com.kaano8.androidcore.adapter.viewholder

import android.view.View
import android.widget.TextView
import com.kaano8.androidcore.R
import com.kaano8.androidcore.com.kaano8.androidcore.adapter.data.MainListData

class DataItemViewHolder(itemView: View) : BaseViewHolder(itemView) {

    private val title = itemView.findViewById<TextView>(R.id.title)
    private val detail = itemView.findViewById<TextView>(R.id.detail)

    override fun bind(item: MainListData) {
        if (item !is MainListData.DataItem)
            throw IllegalArgumentException("Incorrect data item sent!")

        title.text = item.title
        detail.text = item.detail
        itemView.setOnClickListener { item.action() }
    }
}
