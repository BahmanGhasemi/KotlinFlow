package com.example.kotlinflow

data class UserProfile(
    val profileImageUrl: String?,
    val username: String?,
    val posts: List<Post>?
)
