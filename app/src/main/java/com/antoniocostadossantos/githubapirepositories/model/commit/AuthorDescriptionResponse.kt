package com.antoniocostadossantos.githubapirepositories.model.commit

import com.google.gson.annotations.SerializedName

data class AuthorDescriptionResponse(
    @SerializedName("date")
    val date: String,
    val email: String,
    @SerializedName("name")
    val name: String
)