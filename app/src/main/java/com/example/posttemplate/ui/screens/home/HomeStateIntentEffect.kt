package com.example.posttemplate.ui.screens.home

import com.example.posttemplate.domain.models.Post

data class HomeState(
    val isLoading: Boolean = false,
    val posts: List<Post> = emptyList(),
    val errorMessage: String? = null
)

sealed class HomeIntent {
    object LoadPosts : HomeIntent()
    data class SelectPost(val postId: Int) : HomeIntent()
}

sealed class HomeEffect {
    data class ShowError(val message: String) : HomeEffect()
    data class NavigateToPostDetails(val postId: Int) : HomeEffect()
}
