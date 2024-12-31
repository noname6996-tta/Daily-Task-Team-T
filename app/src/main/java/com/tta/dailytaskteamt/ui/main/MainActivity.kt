package com.tta.dailytaskteamt.ui.main

import androidx.core.view.GravityCompat
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.tta.core_base.BaseActivity
import com.tta.dailytaskteamt.R
import com.tta.dailytaskteamt.databinding.ActivityMainBinding

class MainActivity : BaseActivity<ActivityMainBinding>() {

    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun getDataBinding(): ActivityMainBinding {
        return ActivityMainBinding.inflate(layoutInflater)
    }

    override fun initView() {
        super.initView()
        // Setup Navigation Controller
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.container) as NavHostFragment
        navController = navHostFragment.navController

        // Setup AppBarConfiguration for DrawerLayout and NavigationView
        appBarConfiguration = AppBarConfiguration(
            setOf(R.id.nav_home, R.id.nav_settings),
            binding.drawerLayout
        )

        // Setup bottom navigation
        binding.bottomNavigation.setupWithNavController(navController)

        // Handle Navigation Drawer item clicks
//        binding.bottomNavigation.setOnNavigationItemSelectedListener { menuItem ->
//            when (menuItem.itemId) {
//                R.id.nav_home -> {
//                    navController.navigate(R.id.nav_home)
//                    true
//                }
//
//                R.id.nav_settings -> {
//                    navController.navigate(R.id.nav_settings)
//                    true
//                }
//
//                else -> false
//            }.also {
//                binding.drawerLayout.closeDrawer(GravityCompat.START)
//            }
//        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return (NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp())
    }
}