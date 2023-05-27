package com.antoniocostadossantos.githubapirepositories.model.user

import com.google.gson.annotations.SerializedName

data class UserResponse(
    @SerializedName("avatar_url")
    val avatarUrl: String,

    @SerializedName("html_url")
    val htmlUrl: String,

    @SerializedName("id")
    val id: Int,

    @SerializedName("login")
    val login: String,

    @SerializedName("name")
    val name: String,
)