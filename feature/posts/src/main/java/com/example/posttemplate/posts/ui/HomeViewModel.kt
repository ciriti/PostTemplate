package com.example.posttemplate.posts.ui

import androidx.lifecycle.viewModelScope
import com.example.posttemplate.posts.domain.service.PostService
import com.example.posttemplate.ui.components.BaseViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeViewModel(
    private val service: PostService
) : BaseViewModel<HomeState, HomeEffect, HomeIntent>() {

    private val _state = MutableStateFlow(HomeState())
    override val state: StateFlow<HomeState> = _state

    private val _effect = MutableSharedFlow<HomeEffect>()
    override val effect: SharedFlow<HomeEffect> = _effect

    override fun handleIntent(intent: HomeIntent) {
        when (intent) {
            is HomeIntent.LoadPosts -> loadPosts()
            is HomeIntent.SelectPost -> selectPost(intent.postId)
        }
    }

    private fun loadPosts() {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true)
            service.getPosts().fold(
                { throwable ->
                    _state.value =
                        _state.value.copy(isLoading = false, errorMessage = throwable.message)
                    _effect.emit(HomeEffect.ShowError(throwable.message ?: "Unknown error"))
                },
                { posts ->
                    _state.value =
                        _state.value.copy(isLoading = false, posts = posts, errorMessage = null)
                }
            )
        }
    }

    private fun selectPost(postId: Int) {
        viewModelScope.launch {
            _effect.emit(HomeEffect.NavigateToPostDetails(postId))
        }
    }
}
