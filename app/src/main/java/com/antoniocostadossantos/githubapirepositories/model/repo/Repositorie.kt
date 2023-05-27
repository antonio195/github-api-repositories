package com.antoniocostadossantos.githubapirepositories.model.repo

data class Repositorie(
    private var name: String = "",
    private var fullName: String = "",
    private var ownerName: String = "",
    private var description: String = "",
    private var openIssues: Int = 0,
    private var whatchers: Int = 0,
    private var stars: Int = 0,
    private var score: Double = 0.0,
    private var forks: Int = 0,
    private var avatarUrl: String = "",
)
