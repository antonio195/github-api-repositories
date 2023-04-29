package com.antoniocostadossantos.githubapirepositories.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.antoniocostadossantos.githubapirepositories.databinding.ItemRepositoryBinding
import com.antoniocostadossantos.githubapirepositories.model.repo.Item

class RepositoriesAdapter(
    private val context: Context,
    val itemClick: (Item) -> Unit,
) :
    RecyclerView.Adapter<RepositoryViewHolder>() {

    private var items = mutableListOf<Item>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepositoryViewHolder {
        val binding = ItemRepositoryBinding.inflate(LayoutInflater.from(context), parent, false)
        return RepositoryViewHolder(binding)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: RepositoryViewHolder, position: Int) {
        holder.bind(items[position])

        holder.itemView.setOnClickListener {
            itemClick(items[position])
        }
    }

    fun setItems(list: List<Item>) {
        items = list.toMutableList()
        notifyDataSetChanged()
    }
}

class RepositoryViewHolder(binding: ItemRepositoryBinding) : RecyclerView.ViewHolder(binding.root) {

    val repoImage = binding.repositoryImage
    val repoName = binding.repositoryName
    val repoAuthor = binding.repositoryAuthor
    val repoStarsCount = binding.repositoryStars
    val repoForksCount = binding.repositoryForks

    fun bind(item: Item) {
        repoImage.load(item.owner.avatar_url)
        repoName.text = item.name
        repoAuthor.text = item.owner.login
        repoStarsCount.text = item.stargazers_count.toString()
        repoForksCount.text = item.forks_count.toString()
    }

}
