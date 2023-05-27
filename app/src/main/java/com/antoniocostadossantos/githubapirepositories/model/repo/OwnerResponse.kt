package com.antoniocostadossantos.githubapirepositories.model.repo

import com.google.gson.annotations.SerializedName

data class OwnerResponse(
    @SerializedName("avatar_url")
    val avatarUrl: String,

    @SerializedName("login")
    val login: String,

    )