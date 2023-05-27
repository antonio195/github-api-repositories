package com.antoniocostadossantos.githubapirepositories.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.antoniocostadossantos.githubapirepositories.databinding.ItemCommitBinding
import com.antoniocostadossantos.githubapirepositories.model.commit.CommitItemResponse

class CommitsAdapter(
    val acessCommitClick: (CommitItemResponse) -> Unit,
) :
    ListAdapter<CommitItemResponse, CommitsViewHolder>(CommitDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommitsViewHolder {
        val binding = ItemCommitBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CommitsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CommitsViewHolder, position: Int) {
        holder.bind(currentList[position])

        holder.repoCommitAcessBtn.setOnClickListener {
            acessCommitClick(currentList[position])
        }
    }

    fun updateData(newList: List<CommitItemResponse>) {
        val combinedList = ArrayList(currentList)
        combinedList.addAll(newList)
        submitList(combinedList)
    }
}

class CommitsViewHolder(binding: ItemCommitBinding) : RecyclerView.ViewHolder(binding.root) {

    val repoAuthor = binding.repositoryAuthor
    val repoCommitMessage = binding.repositoryCommitText
    val repoCommitCreate = binding.repositoryCommitCreated
    val repoCommitAcessBtn = binding.repositoryBtnAcessCommit

    fun bind(item: CommitItemResponse) {
        repoAuthor.text = item.commit.author.name
        repoCommitCreate.text = item.commit.author.date
        repoCommitMessage.text = item.commit.message
    }

}

class CommitDiffUtil : DiffUtil.ItemCallback<CommitItemResponse>() {
    override fun areItemsTheSame(
        oldItem: CommitItemResponse,
        newItem: CommitItemResponse
    ): Boolean {
        return oldItem.htmlUrl == newItem.htmlUrl
    }

    override fun areContentsTheSame(
        oldItem: CommitItemResponse,
        newItem: CommitItemResponse
    ): Boolean {
        return oldItem.htmlUrl == newItem.htmlUrl
    }

}