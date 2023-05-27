package com.antoniocostadossantos.githubapirepositories.remote.api

import com.antoniocostadossantos.githubapirepositories.model.commit.CommitItemResponse
import com.antoniocostadossantos.githubapirepositories.model.issues.IssuesItemResponse
import com.antoniocostadossantos.githubapirepositories.model.license.LicenseResponse
import com.antoniocostadossantos.githubapirepositories.model.repo.ItemResponse
import com.antoniocostadossantos.githubapirepositories.model.repo.RepositoriesResponse
import com.antoniocostadossantos.githubapirepositories.model.user.UserResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GithubAPI {

    @GET("search/repositories?")
    suspend fun getRepositories(
        @Query("page") page: Int,
        @Query("per_page") perPage: Int,
        @Query("sort") sort: String = "stars",
        @Query("q") language: String
    ): Response<RepositoriesResponse>

    @GET("repos/{owner}/{repo}")
    suspend fun getRepository(
        @Path("owner") owner: String,
        @Path("repo") repositoryName: String,
    ): Response<ItemResponse>

    @GET("repos/{owner}/{repo}/commits")
    suspend fun getCommits(
        @Path("owner") owner: String,
        @Path("repo") repositoryName: String,
        @Query("per_page") perPage: Int = 20,
        @Query("page") page: Int,
    ): Response<List<CommitItemResponse>>

    @GET("repos/{owner}/{repo}/issues")
    suspend fun getIssues(
        @Path("owner") owner: String,
        @Path("repo") repositoryName: String,
        @Query("per_page") perPage: Int = 20,
        @Query("page") page: Int,
    ): Response<List<IssuesItemResponse>>

    @GET("repos/{owner}/{repo}/license")
    suspend fun getLicense(
        @Path("owner") owner: String,
        @Path("repo") repositoryName: String,
        @Query("per_page") perPage: Int = 20
    ): Response<LicenseResponse>

    @GET("users/{user}")
    suspend fun getUser(
        @Path("user") owner: String,
        @Query("per_page") perPage: Int = 20
    ): Response<UserResponse>
}