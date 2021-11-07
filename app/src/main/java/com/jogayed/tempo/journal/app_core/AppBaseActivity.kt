package com.jogayed.tempo.journal.app_core

import android.content.Context
import androidx.appcompat.widget.Toolbar
import com.jogayed.core.presentation.view.BaseActivity
import com.jogayed.tempo.journal.R


/**
 * This base contains the common behavior related to this app only
 */
abstract class AppBaseActivity : BaseActivity() {

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(App.localeManager!!.setLocale(base))
    }

    override fun getToolBar(): Toolbar? {
        return findViewById(R.id.app_toolbar)
    }
}