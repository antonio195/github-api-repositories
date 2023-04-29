package com.antoniocostadossantos.githubapirepositories.model.commit

data class CommitItem(
    val author: Author,
    val comments_url: String,
    val commit: CommitDetails,
    val committer: Committer,
    val html_url: String,
    val node_id: String,
    val parents: List<Parent>,
    val sha: String,
    val url: String
)