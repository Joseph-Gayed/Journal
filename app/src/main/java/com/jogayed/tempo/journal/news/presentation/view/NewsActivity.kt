package com.jogayed.tempo.journal.news.presentation.view

import com.jogayed.tempo.journal.R
import com.jogayed.tempo.journal.app_core.AppBaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewsActivity : AppBaseActivity() {
    override fun getLayoutResId(): Int {
        return R.layout.activity_news
    }

    override fun init() {
        super.init()
        setScreenTitle(getString(R.string.app_name))
    }
}