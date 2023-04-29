package com.antoniocostadossantos.githubapirepositories.remote.api

import com.antoniocostadossantos.githubapirepositories.model.commit.Commit
import com.antoniocostadossantos.githubapirepositories.model.issues.Issues
import com.antoniocostadossantos.githubapirepositories.model.license.LicenseRequest
import com.antoniocostadossantos.githubapirepositories.model.repo.BaseRequest
import com.antoniocostadossantos.githubapirepositories.model.repo.Item
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GithubAPI {

    @GET("search/repositories?")
    suspend fun getRepositories(
        @Query("page") page: Int = 1,
        @Query("per_page") perPage: Int = 20,
        @Query("sort") sort: String = "stars",
        @Query("q") language: String
    ): Response<BaseRequest>

    @GET("repos/{owner}/{repo}")
    suspend fun getRepository(
        @Path("owner") owner: String,
        @Path("repo") repositoryName: String,
    ): Response<Item>

    @GET("repos/{owner}/{repo}/commits")
    suspend fun getCommits(
        @Path("owner") owner: String,
        @Path("repo") repositoryName: String,
        @Query("per_page") perPage: Int = 20
    ): Response<Commit>

    @GET("repos/{owner}/{repo}/issues")
    suspend fun getIssues(
        @Path("owner") owner: String,
        @Path("repo") repositoryName: String,
        @Query("per_page") perPage: Int = 20
    ): Response<Issues>

    @GET("repos/{owner}/{repo}/license")
    suspend fun getLicense(
        @Path("owner") owner: String,
        @Path("repo") repositoryName: String,
        @Query("per_page") perPage: Int = 20
    ): Response<LicenseRequest>
}