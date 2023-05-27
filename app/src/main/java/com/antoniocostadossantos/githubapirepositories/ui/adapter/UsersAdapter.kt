package com.antoniocostadossantos.githubapirepositories.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.antoniocostadossantos.githubapirepositories.databinding.ItemUserBinding
import com.antoniocostadossantos.githubapirepositories.model.user.UserResponse

class UsersAdapter(
    val accessUserClick: (UserResponse) -> Unit,
) :
    ListAdapter<UserResponse, UsersViewHolder>(UsersDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsersViewHolder {
        val binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UsersViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UsersViewHolder, position: Int) {
        holder.bind(currentList[position])
        holder.userAccessHome.setOnClickListener {
            accessUserClick(currentList[position])
        }
    }

    fun updateData(newList: List<UserResponse>) {
        val combinedList = ArrayList(currentList)
        combinedList.addAll(newList)
        submitList(combinedList)
    }
}

class UsersViewHolder(binding: ItemUserBinding) : RecyclerView.ViewHolder(binding.root) {
    val userName = binding.userName
    val userPhoto = binding.userPhoto
    val userAccessHome = binding.btnAccessUserHome

    fun bind(item: UserResponse) {
        userName.text = item.login
        userPhoto.load(item.avatarUrl)
    }
}

class UsersDiffUtil : DiffUtil.ItemCallback<UserResponse>() {
    override fun areItemsTheSame(oldItem: UserResponse, newItem: UserResponse): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: UserResponse, newItem: UserResponse): Boolean {
        return oldItem.name == newItem.name
    }
}