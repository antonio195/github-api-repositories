package com.antoniocostadossantos.githubapirepositories.repository

import com.antoniocostadossantos.githubapirepositories.remote.api.GithubAPI

class MainRepository(private val githubAPI: GithubAPI) {

    suspend fun getRepositories(
        language: String,
        page: Int,
    ) = githubAPI.getRepositories(
        language = language,
        page = page,
        perPage = 20
    )

    suspend fun getRepository(
        owner: String,
        repositoryName: String
    ) = githubAPI.getRepository(
        owner = owner,
        repositoryName = repositoryName
    )

    suspend fun getCommits(
        owner: String,
        repositoryName: String,
        page: Int
    ) = githubAPI.getCommits(
        owner = owner,
        repositoryName = repositoryName,
        page = page
    )

    suspend fun getIssues(
        owner: String,
        repositoryName: String,
        page: Int
    ) = githubAPI.getIssues(
        owner = owner,
        repositoryName = repositoryName,
        page = page
    )

    suspend fun getLicense(
        owner: String,
        repositoryName: String
    ) = githubAPI.getLicense(
        owner = owner,
        repositoryName = repositoryName
    )

    suspend fun getUser(
        owner: String,
    ) = githubAPI.getUser(
        owner = owner,
    )


}