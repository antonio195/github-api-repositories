package com.antoniocostadossantos.githubapirepositories.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.antoniocostadossantos.githubapirepositories.databinding.ItemIssueBinding
import com.antoniocostadossantos.githubapirepositories.model.issues.IssuesItem

class IssuesAdapter(
    private val context: Context,
    val accessIssueClick: (IssuesItem) -> Unit,
) :
    RecyclerView.Adapter<IssueViewHolder>() {

    private var items = mutableListOf<IssuesItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IssueViewHolder {
        val binding = ItemIssueBinding.inflate(LayoutInflater.from(context), parent, false)
        return IssueViewHolder(binding)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: IssueViewHolder, position: Int) {
        holder.bind(items[position])

        holder.repoIssueAcessBtn.setOnClickListener {
            accessIssueClick(items[position])
        }
    }

    fun setItems(list: List<IssuesItem>) {
        items = list.toMutableList()
        notifyDataSetChanged()
    }
}

class IssueViewHolder(binding: ItemIssueBinding) : RecyclerView.ViewHolder(binding.root) {

    val issueTitle = binding.issueTitle
    val issueAuthor = binding.repositoryAuthor
    val issueState = binding.issueState
    val issueCreate = binding.repositoryIssueCreated
    val repoIssueAcessBtn = binding.repositoryBtnAcessIssue

    fun bind(item: IssuesItem) {
        issueTitle.text = item.title
        issueAuthor.text = item.user.login
        issueState.text = item.state
        issueCreate.text = item.created_at
    }
}