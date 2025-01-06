package com.tta.dailytaskteamt.ui.main

import android.view.MenuItem
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.blankj.utilcode.util.ScreenUtils
import com.blankj.utilcode.util.ToastUtils
import com.google.android.material.navigation.NavigationBarView
import com.tta.core_base.BaseActivity
import com.tta.dailytaskteamt.R
import com.tta.dailytaskteamt.databinding.ActivityMainBinding
import com.tta.dailytaskteamt.ui.nav_menu.NavMenuFragment
import timber.log.Timber

class MainActivity : BaseActivity<ActivityMainBinding>() {

    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration
    private var mNavMenuFragment: NavMenuFragment? = null

    override fun getDataBinding(): ActivityMainBinding {
        return ActivityMainBinding.inflate(layoutInflater)
    }

    override fun initView() {
        super.initView()
        initBottomNavigation()
        initNavMenu()

        // Setup AppBarConfiguration với DrawerLayout
        appBarConfiguration = AppBarConfiguration(
            setOf(R.id.taskFragment, R.id.calenderFragment, R.id.profileFragment),
            binding.drawerLayout
        )
    }

    private fun initBottomNavigation() {
        // Setup Navigation Controller
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.container) as NavHostFragment
        navController = navHostFragment.navController

        val mNavigationItemSelected = object : NavigationBarView.OnItemSelectedListener {
            override fun onNavigationItemSelected(item: MenuItem): Boolean {
                Timber.d("MainActivity Clicked on: ${item.itemId}")
                ToastUtils.showShort("Clicked on: ${item.itemId}")
                when (item.itemId) {
                    R.id.menuFragment -> {
                        openNavigationMenu()
                        return true
                    }

                    R.id.taskFragment -> {
                        NavigationUI.onNavDestinationSelected(
                            item,
                            navController
                        )
                        return true
                    }

                    R.id.calenderFragment -> {
                        NavigationUI.onNavDestinationSelected(
                            item,
                            navController
                        )
                        return true
                    }

                    R.id.profileFragment -> {
                        NavigationUI.onNavDestinationSelected(
                            item,
                            navController
                        )
                        return true
                    }

                    else -> {
                        return false
                    }
                }
            }
        }

        binding.bottomNavigation.setupWithNavController(navController)
        binding.bottomNavigation.apply {
            setOnItemSelectedListener(mNavigationItemSelected)
        }

        // Setup bottom navigation
        binding.bottomNavigation.setupWithNavController(navController)
    }

    private fun initNavMenu() {
        // Set width cho NavigationView
        val drawerLayoutParams = binding.navView.layoutParams as DrawerLayout.LayoutParams
        drawerLayoutParams.width = (ScreenUtils.getScreenWidth() * 0.8f).toInt()
        binding.navView.layoutParams = drawerLayoutParams

        // Attach NavMenuFragment
        mNavMenuFragment = NavMenuFragment()
        supportFragmentManager.beginTransaction().apply {
            add(R.id.fr_nav_menu_container, mNavMenuFragment!!, "NavMenuFragment")
        }.commitNow()
    }

    fun openNavigationMenu() {
        binding.drawerLayout.openDrawer(GravityCompat.START)
    }

    override fun onSupportNavigateUp(): Boolean {
        return (NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp())
    }
}