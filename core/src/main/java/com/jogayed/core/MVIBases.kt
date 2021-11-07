package com.jogayed.core

/**
 * Interface definition of the Action(Intent in MVI)
 */
interface Action

/**
 * Interface definition of the ViewStare(Model in MVI)
 */
interface ViewState


/**
 * Interface definition of the Result that will be reduced to [ViewState]
 */
interface Result<VS : ViewState> {
    fun reduce(defaultState: VS, oldState: VS): VS
}