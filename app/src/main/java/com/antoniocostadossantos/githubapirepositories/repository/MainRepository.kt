package com.antoniocostadossantos.githubapirepositories.repository

import com.antoniocostadossantos.githubapirepositories.remote.RetrofitClient
import com.antoniocostadossantos.githubapirepositories.remote.api.GithubAPI

class MainRepository() {

    private val retrofitClient = RetrofitClient.getService(GithubAPI::class.java)

    suspend fun getRepositories(
        language: String,
        page: Int,
    ) = retrofitClient.getRepositories(
        language = language,
        page = page
    )

    suspend fun getRepository(
        owner: String,
        repositoryName: String
    ) = retrofitClient.getRepository(
        owner = owner,
        repositoryName = repositoryName
    )

    suspend fun getCommits(
        owner: String,
        repositoryName: String
    ) = retrofitClient.getCommits(
        owner = owner,
        repositoryName = repositoryName
    )

    suspend fun getIssues(
        owner: String,
        repositoryName: String
    ) = retrofitClient.getIssues(
        owner = owner,
        repositoryName = repositoryName
    )

    suspend fun getLicense(
        owner: String,
        repositoryName: String
    ) = retrofitClient.getLicense(
        owner = owner,
        repositoryName = repositoryName
    )


}