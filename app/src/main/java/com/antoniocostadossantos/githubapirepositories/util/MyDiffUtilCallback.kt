package com.antoniocostadossantos.githubapirepositories.util

import androidx.recyclerview.widget.DiffUtil
import com.antoniocostadossantos.githubapirepositories.model.repo.Item

class MyDiffUtilCallback(private val oldList: List<Item>, private val newList: List<Item>) :
    DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].id == newList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }
}