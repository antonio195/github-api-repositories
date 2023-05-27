package com.antoniocostadossantos.githubapirepositories.model.commit

import com.google.gson.annotations.SerializedName

data class CommitDetailsResponse(
    val author: AuthorDescriptionResponse,
    @SerializedName("message")
    val message: String,

    )