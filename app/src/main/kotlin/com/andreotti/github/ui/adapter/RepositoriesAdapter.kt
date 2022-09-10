package com.andreotti.github.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat.setAccessibilityHeading
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.andreotti.github.R
import com.andreotti.github.commons.loadUrl
import com.andreotti.github.databinding.ItemRepositoryListBinding
import com.andreotti.github.domain.vo.RepoVO

internal class RepositoriesAdapter :
    PagingDataAdapter<RepoVO, RepositoriesAdapter.RepositoryViewHolder>(ReposDiff()) {

    inner class RepositoryViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ItemRepositoryListBinding.bind(view)

        init {
            setAccessibilityHeading(binding.itemRepoName, true)
        }

        fun bind(item: RepoVO) = with(binding) {
            itemUserAvatar.loadUrl(item.avatar)
            itemUserName.text = item.username
            itemRepoName.text = item.nameRepo
            itemNumForks.text = item.forksAmount
            itemNumStarts.text = item.stargazersAmount
            itemNumForks.contentDescription = item.forksAmountDescription
            itemNumStarts.contentDescription = item.stargazersAmountDescription
            itemUserName.contentDescription = item.usernameDescription
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepositoryViewHolder =
        RepositoryViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_repository_list, parent, false)
        )

    override fun onBindViewHolder(holder: RepositoryViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it)
        }
    }
}