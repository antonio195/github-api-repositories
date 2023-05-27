package com.antoniocostadossantos.githubapirepositories.model.repo

import com.google.gson.annotations.SerializedName

data class RepositoriesResponse(
    @SerializedName("items")
    val items: List<ItemResponse>,
)