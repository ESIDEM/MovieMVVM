package com.xtremepixel.moviemvvm
import android.app.Activity
import android.content.Intent
import com.xtremepixel.moviemvvm.common.MainActivity
fun Activity.navigateToHome() {
    this.startActivity(Intent(this, MainActivity::class.java))
    this.finish()
}