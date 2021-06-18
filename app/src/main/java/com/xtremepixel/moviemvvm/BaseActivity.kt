package com.xtremepixel.moviemvvm

import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.xtremepixel.moviemvvm.WindowUtils.setToolbarTopPadding
import com.xtremepixel.moviemvvm.WindowUtils.setTransparentStatusBar
import com.xtremepixel.moviemvvm.WindowUtils.setupDarkTheme
import com.xtremepixel.moviemvvm.WindowUtils.setupLightTheme

open class BaseActivity:AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setTransparentStatusBar(this)
        applyTheme()
    }

    @RequiresApi(Build.VERSION_CODES.KITKAT_WATCH)
    override fun setSupportActionBar(toolbar: Toolbar?) {
        super.setSupportActionBar(toolbar)

        toolbar?.let {
            setToolbarTopPadding(toolbar)
        }
    }

    fun clearStatusBar() {
        clearStatusBar()
    }

    private fun applyTheme() {
        when(resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) {
            Configuration.UI_MODE_NIGHT_YES -> {
                setupDarkTheme(this)
            }
            else -> {
                setupLightTheme(this)
            }
        }
    }
}