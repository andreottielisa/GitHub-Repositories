package com.andreotti.github.ui.adapter

import androidx.recyclerview.widget.DiffUtil
import com.andreotti.github.domain.vo.RepoVO

internal class ReposDiff : DiffUtil.ItemCallback<RepoVO>() {

    override fun areItemsTheSame(oldItem: RepoVO, newItem: RepoVO): Boolean =
        oldItem.username == newItem.username

    override fun areContentsTheSame(oldItem: RepoVO, newItem: RepoVO): Boolean =
        oldItem == newItem
}