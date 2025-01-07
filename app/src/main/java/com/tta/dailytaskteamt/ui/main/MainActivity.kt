package com.tta.dailytaskteamt.ui.main

import android.annotation.SuppressLint
import android.view.LayoutInflater
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationItemView
import com.google.android.material.bottomnavigation.BottomNavigationMenuView
import com.tta.core_base.BaseActivity
import com.tta.dailytaskteamt.R
import com.tta.dailytaskteamt.databinding.ActivityMainBinding
import com.tta.dailytaskteamt.databinding.CustomLayoutMenuBinding

class MainActivity : BaseActivity<ActivityMainBinding>() {

    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun getDataBinding(): ActivityMainBinding {
        return ActivityMainBinding.inflate(layoutInflater)
    }

    @SuppressLint("RestrictedApi")
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

        // Custom item menu of bottom nav
        val bottomMenu = binding.bottomNavigation.getChildAt(0) as BottomNavigationMenuView
        val itemMenu = bottomMenu.getChildAt(0) as BottomNavigationItemView
        val customMenuBinding = CustomLayoutMenuBinding.inflate(LayoutInflater.from(this), bottomMenu, false)
        itemMenu.addView(customMenuBinding.root)

        // Setup bottom navigation
        binding.bottomNavigation.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            val icon = if(destination.id == R.id.fragment_menu) R.drawable.ic_menu_checked else R.drawable.ic_menu_unchecked
            customMenuBinding.iconMenu.setImageResource(icon)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return (NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp())
    }
}