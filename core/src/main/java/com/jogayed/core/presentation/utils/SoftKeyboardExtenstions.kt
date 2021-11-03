package com.jogayed.core.presentation.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText

fun Activity.hideKeyboard() {
    val view = this.currentFocus
    if (view != null && view.windowToken != null) {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }
}

@SuppressLint("ClickableViewAccessibility")
fun Activity.hideKeyboardWhenClickingOut(view: View) {
    // Set up touch listener for non-text box views endDate hide keyboard.
    if (view !is EditText) {
        view.setOnTouchListener { _, _ ->
            hideKeyboard()
            false
        }
    }

    //If a layout container, iterate over children and seed recursion.
    if (view is ViewGroup) {
        for (i in 0 until (view as ViewGroup).childCount) {
            val innerView = (view as ViewGroup).getChildAt(i)
            hideKeyboardWhenClickingOut(innerView)
        }
    }
}