package com.xtremepixel.moviemvvm.common

import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.ui.setupActionBarWithNavController
import com.xtremepixel.moviemvvm.R
import com.xtremepixel.moviemvvm.databinding.ActivityMainBinding
import com.xtremepixel.moviemvvm.setupWithNavController
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity() {
    lateinit var activityMainBinding: ActivityMainBinding
    private var currentNavController: LiveData<NavController>? = null

    @RequiresApi(Build.VERSION_CODES.KITKAT_WATCH)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)

        setSupportActionBar(activityMainBinding.toolbar)
        setupBottomNavigation()
    }
//    private fun setupBottomNavigation() {
//        activityMainBinding.bottomNavigation.setupWithNavController(navController)
//
//    }

    private fun setupBottomNavigation() {
        val controller = activityMainBinding.bottomNavigation.setupWithNavController(
            listOf(R.navigation.navigation_popular, R.navigation.navigation_upcoming, R.navigation.navigation_favorite),
            supportFragmentManager,
            R.id.nav_host_container,
            intent
        )

        // Whenever the selected controller changes, setup the action bar.
        controller.observe(this, Observer { navController ->
            setupActionBarWithNavController(navController)
            navController.addOnDestinationChangedListener { controller, destination, arguments ->
                when (destination.id) {
                    R.id.nav_popular -> showBottomNav()
                    R.id.nav_upcoming -> showBottomNav()
                    R.id.nav_favorite -> showBottomNav()
                    else -> hideBottomNav()
                }
            }
        })
        currentNavController = controller


    }

    private fun showBottomNav() {
        activityMainBinding.bottomNavigation.visibility = View.VISIBLE
        activityMainBinding.toolbar.visibility = View.VISIBLE

    }

    private fun hideBottomNav() {
        activityMainBinding.bottomNavigation.visibility = View.GONE
        activityMainBinding.toolbar.visibility = View.GONE

    }
    override fun onSupportNavigateUp() = currentNavController?.value?.navigateUp() ?: false
}