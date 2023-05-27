package com.antoniocostadossantos.githubapirepositories.model.issues

import com.google.gson.annotations.SerializedName

data class UserIssueResponse(

    @SerializedName("login")
    val login: String,
)