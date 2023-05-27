package com.antoniocostadossantos.githubapirepositories.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.antoniocostadossantos.githubapirepositories.databinding.ItemRepositoryBinding
import com.antoniocostadossantos.githubapirepositories.model.repo.ItemResponse

class RepositoriesAdapter(
    val itemClick: (ItemResponse) -> Unit,
) :
    ListAdapter<ItemResponse, RepositoryViewHolder>(RepositoryDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepositoryViewHolder {
        val binding =
            ItemRepositoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RepositoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RepositoryViewHolder, position: Int) {
        holder.bind(currentList[position])

        holder.itemView.setOnClickListener {
            itemClick(currentList[position])
        }
    }

    fun updateData(newList: List<ItemResponse>) {
        val combinedList = ArrayList(currentList)
        combinedList.addAll(newList)
        submitList(combinedList)
    }
}

class RepositoryViewHolder(binding: ItemRepositoryBinding) : RecyclerView.ViewHolder(binding.root) {

    val repoImage = binding.repositoryImage
    val repoName = binding.repositoryName
    val repoAuthor = binding.repositoryAuthor
    val repoStarsCount = binding.repositoryStars
    val repoForksCount = binding.repositoryForks

    fun bind(item: ItemResponse) {
        repoImage.load(item.owner.avatarUrl)
        repoName.text = item.name
        repoAuthor.text = item.owner.login
        repoStarsCount.text = item.starsCount.toString()
        repoForksCount.text = item.forksCount.toString()
    }
}

class RepositoryDiffUtil : DiffUtil.ItemCallback<ItemResponse>() {
    override fun areItemsTheSame(oldItem: ItemResponse, newItem: ItemResponse): Boolean {
        return oldItem.name == newItem.name
    }

    override fun areContentsTheSame(oldItem: ItemResponse, newItem: ItemResponse): Boolean {
        return oldItem.name == newItem.name
    }

}
