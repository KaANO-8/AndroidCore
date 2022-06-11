package com.kaano8.androidcore.com.kaano8.androidcore.adapter.viewholder

import android.view.View
import android.widget.TextView
import com.kaano8.androidcore.com.kaano8.androidcore.adapter.data.MainListData

class HeaderViewHolder(private val itemView: View): BaseViewHolder(itemView) {

    override fun bind(item: MainListData) {
        if (item !is MainListData.HeaderItem)
            throw IllegalArgumentException("Incorrect data item sent!")

        (itemView as? TextView)?.text = item.header
    }

}