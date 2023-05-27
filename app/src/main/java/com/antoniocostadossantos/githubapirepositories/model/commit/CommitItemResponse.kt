package com.antoniocostadossantos.githubapirepositories.model.commit

import com.google.gson.annotations.SerializedName

data class CommitItemResponse(
    @SerializedName("commit")
    val commit: CommitDetailsResponse,
    @SerializedName("html_url")
    val htmlUrl: String,
)