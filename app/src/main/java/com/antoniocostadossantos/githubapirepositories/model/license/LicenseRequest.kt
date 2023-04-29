package com.antoniocostadossantos.githubapirepositories.model.license

data class LicenseRequest(
    val _links: Links,
    val content: String,
    val download_url: String,
    val encoding: String,
    val git_url: String,
    val html_url: String,
    val license: LicenseItem,
    val name: String,
    val path: String,
    val sha: String,
    val size: Int,
    val type: String,
    val url: String
)