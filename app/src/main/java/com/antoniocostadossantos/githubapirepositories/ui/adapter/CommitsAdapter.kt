package com.antoniocostadossantos.githubapirepositories.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.antoniocostadossantos.githubapirepositories.databinding.ItemCommitBinding
import com.antoniocostadossantos.githubapirepositories.model.commit.CommitItem

class CommitsAdapter(
    private val context: Context,
    val acessCommitClick: (CommitItem) -> Unit,
) :
    RecyclerView.Adapter<CommitsViewHolder>() {

    private var items = mutableListOf<CommitItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommitsViewHolder {
        val binding = ItemCommitBinding.inflate(LayoutInflater.from(context), parent, false)
        return CommitsViewHolder(binding)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: CommitsViewHolder, position: Int) {
        holder.bind(items[position])

        holder.repoCommitAcessBtn.setOnClickListener {
            acessCommitClick(items[position])
        }
    }

    fun setItems(list: List<CommitItem>) {
        items = list.toMutableList()
        notifyDataSetChanged()
    }
}

class CommitsViewHolder(binding: ItemCommitBinding) : RecyclerView.ViewHolder(binding.root) {

    val repoAuthor = binding.repositoryAuthor
    val repoCommitMessage = binding.repositoryCommitText
    val repoCommitCreate = binding.repositoryCommitCreated
    val repoCommitAcessBtn = binding.repositoryBtnAcessCommit

    fun bind(item: CommitItem) {
        repoAuthor.text = item.commit.author.name
        repoCommitCreate.text = item.commit.author.date
        repoCommitMessage.text = item.commit.message
    }

}