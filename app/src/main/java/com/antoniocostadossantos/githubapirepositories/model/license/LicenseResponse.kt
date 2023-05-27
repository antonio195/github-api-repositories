package com.antoniocostadossantos.githubapirepositories.model.license

import com.google.gson.annotations.SerializedName

data class LicenseResponse(

    @SerializedName("html_url")
    val htmlUrl: String,

    @SerializedName("license")
    val license: LicenseItemResponse,
)