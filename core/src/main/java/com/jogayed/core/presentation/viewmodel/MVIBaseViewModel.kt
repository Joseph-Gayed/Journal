package com.jogayed.core.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import com.jogayed.core.Action
import com.jogayed.core.Result
import com.jogayed.core.ViewState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

/**
 * Base class for all view models that follow the MVI architecture
 */
@ExperimentalCoroutinesApi
abstract class MVIBaseViewModel<A : Action, R : Result<VS>, VS : ViewState> : BaseViewModel() {

    //default state
    abstract val defaultInternalViewState: VS

    // stream of actions (intents)
    private val actionsChannel = Channel<A>(Channel.UNLIMITED)

    // steam of view states
    val viewStates: StateFlow<VS> by lazy {

        actionsChannel.consumeAsFlow()
            .flatMapLatest { action: A ->
                handleAction(action)
            }
            .map { result: R ->
                reduce(result)
            }
            .distinctUntilChanged()
            .stateIn(
                viewModelScope,
                SharingStarted.Lazily,
                defaultInternalViewState
            )
    }


    fun executeAction(action: A) {
        viewModelScope.launch {
            actionsChannel.send(action)
        }
    }

    abstract fun handleAction(action: A): Flow<R>


    open fun reduce(result: R): VS {
        return result.reduce(defaultState = defaultInternalViewState, oldState = viewStates.value)
    }
}