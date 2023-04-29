package com.antoniocostadossantos.githubapirepositories.model.issues

import com.google.gson.annotations.SerializedName

data class Reactions(
    @SerializedName("+1")
    val onePlus: Int,
    @SerializedName("-1")
    val oneLess: Int,
    val confused: Int,
    val eyes: Int,
    val heart: Int,
    val hooray: Int,
    val laugh: Int,
    val rocket: Int,
    val total_count: Int,
    val url: String
)