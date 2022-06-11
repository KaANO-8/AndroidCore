package com.kaano8.androidcore.com.kaano8.androidcore.recyclerview.adapter

import androidx.recyclerview.widget.DiffUtil
import com.kaano8.androidcore.com.kaano8.androidcore.recyclerview.data.Actor

class ActorDiffCallback(private val oldList: List<Actor>, private val newList: List<Actor>): DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldList[oldItemPosition].id == newList[newItemPosition].id

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldList[oldItemPosition]
        val newItem = newList[newItemPosition]

        return oldItem.name == newItem.name
    }
}