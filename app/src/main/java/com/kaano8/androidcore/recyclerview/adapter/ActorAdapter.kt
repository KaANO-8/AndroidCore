package com.kaano8.androidcore.com.kaano8.androidcore.recyclerview.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.kaano8.androidcore.R
import com.kaano8.androidcore.com.kaano8.androidcore.recyclerview.data.Actor

class ActorAdapter(private val actors: MutableList<Actor>) :
    RecyclerView.Adapter<ActorAdapter.ActorViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActorViewHolder {
        return ActorViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_actor, parent, false)
        )
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

    class ActorViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val textView = view.findViewById<TextView>(R.id.actor_name)

        fun bind(actor: Actor) {
            textView.text = actor.name
        }
    }
}
