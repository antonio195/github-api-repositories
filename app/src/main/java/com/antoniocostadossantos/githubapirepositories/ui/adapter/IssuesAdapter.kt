package com.antoniocostadossantos.githubapirepositories.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.antoniocostadossantos.githubapirepositories.databinding.ItemIssueBinding
import com.antoniocostadossantos.githubapirepositories.model.issues.IssuesItemResponse

class IssuesAdapter(
    val accessIssueClick: (IssuesItemResponse) -> Unit,
) :
    ListAdapter<IssuesItemResponse, IssueViewHolder>(IssuesDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IssueViewHolder {
        val binding = ItemIssueBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return IssueViewHolder(binding)
    }

    override fun onBindViewHolder(holder: IssueViewHolder, position: Int) {
        holder.bind(currentList[position])

        holder.repoIssueAcessBtn.setOnClickListener {
            accessIssueClick(currentList[position])
        }
    }

    fun updateData(newList: List<IssuesItemResponse>) {
        val combinedList = ArrayList(currentList)
        combinedList.addAll(newList)
        submitList(combinedList)
    }
}

class IssueViewHolder(binding: ItemIssueBinding) : RecyclerView.ViewHolder(binding.root) {

    val issueTitle = binding.issueTitle
    val issueAuthor = binding.repositoryAuthor
    val issueState = binding.issueState
    val issueCreate = binding.repositoryIssueCreated
    val repoIssueAcessBtn = binding.repositoryBtnAcessIssue

    fun bind(item: IssuesItemResponse) {
        issueTitle.text = item.title
        issueAuthor.text = item.user.login
        issueState.text = item.state
        issueCreate.text = item.createdAt
    }
}

class IssuesDiffUtil : DiffUtil.ItemCallback<IssuesItemResponse>() {
    override fun areItemsTheSame(
        oldItem: IssuesItemResponse,
        newItem: IssuesItemResponse
    ): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
        oldItem: IssuesItemResponse,
        newItem: IssuesItemResponse
    ): Boolean {
        return oldItem.htmlUrl == newItem.htmlUrl
    }

}