package com.kaano8.androidcore.com.kaano8.androidcore.recyclerview.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.kaano8.androidcore.com.kaano8.androidcore.recyclerview.data.Actor
import com.kaano8.androidcore.databinding.ItemActorBinding

class ActorAdapter(private val actors: MutableList<Actor>) :
    RecyclerView.Adapter<ActorAdapter.ActorViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActorViewHolder {
        val binding = ItemActorBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ActorViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ActorViewHolder, position: Int) {
        holder.bind(actors[position])
    }

    override fun getItemCount(): Int = actors.size

    fun swapItems(actors: List<Actor>) {
        val diffCallback = ActorDiffCallback(this.actors, actors)
        val diffResult = DiffUtil.calculateDiff(diffCallback)

        this.actors.clear()
        this.actors.addAll(actors)
        // The magic is happening here
        diffResult.dispatchUpdatesTo(this)
        // If diffUtil would not be there, then we had only this option!
        //notifyDataSetChanged()
    }

    class ActorViewHolder(private val itemActorBinding: ItemActorBinding) :
        RecyclerView.ViewHolder(itemActorBinding.root) {
        fun bind(actor: Actor) {
            itemActorBinding.actorName.text = actor.name
        }
    }
}
