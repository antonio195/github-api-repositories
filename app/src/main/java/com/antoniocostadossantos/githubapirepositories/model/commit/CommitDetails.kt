package com.antoniocostadossantos.githubapirepositories.model.commit

data class CommitDetails(
    val author: AuthorDescription,
    val comment_count: Int,
    val committer: CommitterDescription,
    val message: String,
    val tree: Tree,
    val url: String,
    val verification: Verification
)