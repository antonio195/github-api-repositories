package com.antoniocostadossantos.githubapirepositories.model.issues

import com.google.gson.annotations.SerializedName

data class IssuesItemResponse(

    @SerializedName("created_at")
    val createdAt: String,

    @SerializedName("html_url")
    val htmlUrl: String,

    @SerializedName("id")
    val id: Long,

    @SerializedName("state")
    val state: String,

    @SerializedName("title")
    val title: String,

    @SerializedName("user")
    val user: UserIssueResponse
)