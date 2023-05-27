package com.antoniocostadossantos.githubapirepositories.model.license

import com.google.gson.annotations.SerializedName

data class LicenseItemResponse(
    @SerializedName("name")
    val name: String,
)