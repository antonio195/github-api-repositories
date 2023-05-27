package com.antoniocostadossantos.githubapirepositories.model.repo

import com.google.gson.annotations.SerializedName

data class ItemResponse(
    @SerializedName("description")
    val description: String,

    @SerializedName("forks_count")
    val forksCount: Int,

    @SerializedName("full_name")
    val fullName: String,

    @SerializedName("name")
    val name: String,

    @SerializedName("open_issues_count")
    val openIssuesCount: Int,

    @SerializedName("owner")
    val owner: OwnerResponse,

    @SerializedName("score")
    val score: Double,

    @SerializedName("stargazers_count")
    val starsCount: Int,

    @SerializedName("watchers_count")
    val watchersCount: Int,
)