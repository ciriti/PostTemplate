package com.example.posttemplate.ui.screens.home

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.posttemplate.domain.models.Post
import com.example.posttemplate.ui.components.AppBar
import com.example.posttemplate.ui.components.LoadingIndicator
import com.example.posttemplate.ui.components.PostItem

@Composable
fun HomeScreen(
    state: HomeState,
    onRetry: () -> Unit,
    onNavigateToDetails: (Int) -> Unit
) {
    when {
        state.isLoading -> LoadingIndicator()
        state.errorMessage != null -> Text("Error: ${state.errorMessage}")
        else -> LazyColumn {
            items(state.posts) { post ->
                PostItem(post = post) { onNavigateToDetails(post.id) }
            }
        }
    }
}
