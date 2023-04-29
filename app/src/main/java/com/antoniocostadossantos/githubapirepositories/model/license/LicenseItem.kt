package com.antoniocostadossantos.githubapirepositories.model.license

data class LicenseItem(
    val key: String,
    val name: String,
    val node_id: String,
    val spdx_id: String,
    val url: String
)