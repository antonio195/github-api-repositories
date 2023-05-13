package com.antoniocostadossantos.githubapirepositories.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.antoniocostadossantos.githubapirepositories.databinding.ItemRepositoryBinding
import com.antoniocostadossantos.githubapirepositories.model.repo.Item
import com.antoniocostadossantos.githubapirepositories.util.MyDiffUtilCallback

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

    fun clearItems() {
        items.clear()
    }

    fun updateData(newList: List<Item>) {
        val diffResult = DiffUtil.calculateDiff(MyDiffUtilCallback(items, newList))
        items.addAll(newList)
        diffResult.dispatchUpdatesTo(this)
        notifyDataSetChanged()
    }

    fun setItems(list: List<Item>) {
        items.addAll(list.toMutableList())
        notifyItemRangeInserted(items.lastIndex, list.size)
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
