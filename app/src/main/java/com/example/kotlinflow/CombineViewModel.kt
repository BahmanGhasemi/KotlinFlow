package com.example.kotlinflow

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.zip

class CombineViewModel : ViewModel() {

    val isAuthenticated = MutableStateFlow<Boolean>(true)
    val userFlow = MutableStateFlow<User?>(null)
    val postsFlow = MutableStateFlow(emptyList<Post>())

    private val _profileFlow = MutableStateFlow<UserProfile?>(null)
    val profileFlow = _profileFlow.asStateFlow()

    init {
        combine()
    }

    suspend fun updateUser(user: User) {
        userFlow.emit(user)
    }

    suspend fun updatePost(post: Post) {
        postsFlow.emit(listOf(post))
    }

    fun combine() {
        isAuthenticated.combine(userFlow) { isAuthenticated, user ->
            if (isAuthenticated) {
                user
            } else
                null
        }.combine(postsFlow) { user, posts ->
            _profileFlow.value = UserProfile(
                profileImageUrl = user?.imageUrl,
                username = user?.userName,
                posts = posts
            )
        }.launchIn(viewModelScope)
    }
}
