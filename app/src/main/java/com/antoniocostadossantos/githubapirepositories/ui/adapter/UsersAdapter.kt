package com.antoniocostadossantos.githubapirepositories.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.antoniocostadossantos.githubapirepositories.databinding.ItemUserBinding
import com.antoniocostadossantos.githubapirepositories.model.user.User

class UsersAdapter(
    private val context: Context,
    val accessUserClick: (User) -> Unit,
) :
    RecyclerView.Adapter<UsersViewHolder>() {

    private var items = mutableListOf<User>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsersViewHolder {
        val binding = ItemUserBinding.inflate(LayoutInflater.from(context), parent, false)
        return UsersViewHolder(binding)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: UsersViewHolder, position: Int) {
        holder.bind(items[position])

        holder.userAccessHome.setOnClickListener {
            accessUserClick(items[position])
        }
    }

    fun setItems(list: List<User>) {
        items = list.toMutableList()
        notifyDataSetChanged()
    }
}

class UsersViewHolder(binding: ItemUserBinding) : RecyclerView.ViewHolder(binding.root) {

    val userName = binding.userName
    val userPhoto = binding.userPhoto
    val userAccessHome = binding.btnAccessUserHome

    fun bind(item: User) {

        userName.text = item.login
        userPhoto.load(item.avatar_url)

    }

}