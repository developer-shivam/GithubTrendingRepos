package com.shivamsatija.githubtrendingrepos.ui.repositories.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.shivamsatija.githubtrendingrepos.data.model.Collaborator
import com.shivamsatija.githubtrendingrepos.databinding.LayoutCollaboratorItemBinding

class CollaboratorAdapter(
    private val collaborators: List<Collaborator>
) : RecyclerView.Adapter<CollaboratorAdapter.CollaboratorViewHolder>() {

    override fun getItemCount(): Int = collaborators.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CollaboratorViewHolder {
        return CollaboratorViewHolder(
            LayoutCollaboratorItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: CollaboratorViewHolder, position: Int) {
        holder.bind(collaborators[position])
    }

    inner class CollaboratorViewHolder(
        private val binding: LayoutCollaboratorItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(collaborator: Collaborator) {
            with(collaborator) {
                avatar?.let {
                    Glide.with(itemView)
                        .load(collaborator.avatar)
                        .circleCrop()
                        .into(binding.root)
                }
            }
        }
    }
}