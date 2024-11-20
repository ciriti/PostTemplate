package com.example.posttemplate.ui.components

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow

abstract class BaseViewModel<State, Effect, Intent> : ViewModel() {
    abstract val state: StateFlow<State>
    abstract val effect: SharedFlow<Effect>
    abstract fun handleIntent(intent: Intent)
}
