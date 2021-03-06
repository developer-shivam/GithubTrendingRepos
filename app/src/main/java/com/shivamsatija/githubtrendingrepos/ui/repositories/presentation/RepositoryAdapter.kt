package com.shivamsatija.githubtrendingrepos.ui.repositories.presentation

import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.ImageViewCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.shivamsatija.githubtrendingrepos.data.model.Repository
import com.shivamsatija.githubtrendingrepos.databinding.LayoutRepositoryItemBinding

class RepositoryAdapter(
    private val clickCallback: (String) -> Unit
) : RecyclerView.Adapter<RepositoryAdapter.RepositoryViewHolder>() {

    private val repositories = ArrayList<Repository>()

    private var selectedItemId: String? = null

    companion object {
        const val PAYLOAD_ITEM_SELECTION = 1
    }

    fun setList(repositories: List<Repository>) {
        this.repositories.clear()
        this.repositories.addAll(repositories)
        notifyDataSetChanged()
    }

    fun updateSelectedItem(id: String? = null) {
        // check if last selected item is in current list
        val position = repositories.indexOfFirst { it.getId() == selectedItemId }
        selectedItemId = id
        if (position != RecyclerView.NO_POSITION) {
            notifyItemChanged(position, PAYLOAD_ITEM_SELECTION)
        }
        val currentSelectedItemPosition = repositories.indexOfFirst { it.getId() == selectedItemId }
        notifyItemChanged(currentSelectedItemPosition, PAYLOAD_ITEM_SELECTION)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepositoryViewHolder {
        return RepositoryViewHolder(
            LayoutRepositoryItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            clickCallback
        )
    }

    override fun onBindViewHolder(holder: RepositoryViewHolder, position: Int) {
        holder.bind(repositories[position])
    }

    override fun onBindViewHolder(
        holder: RepositoryViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        if (payloads.isNotEmpty() && payloads[0] == PAYLOAD_ITEM_SELECTION) {
            holder.toggleSelection()
        }
        super.onBindViewHolder(holder, position, payloads)
    }

    override fun getItemCount(): Int = repositories.size

    inner class RepositoryViewHolder(
        private val itemBinding: LayoutRepositoryItemBinding,
        clickCallback: (String) -> Unit
    ) : RecyclerView.ViewHolder(itemBinding.root) {

        private var repository: Repository? = null

        init {
            itemBinding.root.setOnClickListener {
                repository?.let {
                    clickCallback(it.getId())
                }
            }
        }

        fun toggleSelection() {
            if (repository?.getId() == selectedItemId) {
                itemBinding.informationGroup.visibility = View.VISIBLE
            } else {
                itemBinding.informationGroup.visibility = View.GONE
            }
        }

        @SuppressLint("SetTextI18n")
        fun bind(repo: Repository) {
            this@RepositoryViewHolder.repository = repo
            repository?.run {
                username?.let {
                    itemBinding.tvOwnerName.text = "$it / "
                }
                repositoryName?.let {
                    itemBinding.tvRepositoryName.text = it
                }
                starsSince?.let {
                    itemBinding.tvStarsToday.text = "$it stars today"
                }
                description?.let {
                    itemBinding.tvRepositoryDescription.text = it
                }
                languageColor?.let {
                    ImageViewCompat.setImageTintList(
                        itemBinding.ivLanguageColor,
                        ColorStateList.valueOf(Color.parseColor(it))
                    )
                }
                language?.let {
                    itemBinding.tvLanguage.text = it
                }
                totalStars?.let {
                    itemBinding.tvTotalStars.text = "$it"
                }
                collaborators?.takeIf {
                    it.isNotEmpty()
                }?.also {
                    itemBinding.rvCollaborators.run {
                        adapter = CollaboratorAdapter(it)
                        layoutManager = LinearLayoutManager(
                            context,
                            LinearLayoutManager.HORIZONTAL,
                            false
                        )
                    }
                }
            }
            toggleSelection()
        }
    }

}