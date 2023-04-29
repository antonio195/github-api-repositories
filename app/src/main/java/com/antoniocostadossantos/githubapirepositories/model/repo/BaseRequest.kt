package com.antoniocostadossantos.githubapirepositories.model.repo

data class BaseRequest(
    val incomplete_results: Boolean,
    val items: List<Item>,
    val total_count: Int
)