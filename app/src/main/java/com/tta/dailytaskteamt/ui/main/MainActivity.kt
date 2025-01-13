package com.tta.dailytaskteamt.ui.main

import android.app.AlertDialog
import android.os.Build
import android.os.Bundle
import androidx.activity.addCallback
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.blankj.utilcode.util.ToastUtils
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import com.tta.core_base.BaseActivity
import com.tta.core_utils.uitls.PermissionUtils
import com.tta.dailytaskteamt.R
import com.tta.dailytaskteamt.databinding.ActivityMainBinding
import com.tta.dailytaskteamt.ui.main.nav_menu.NavMenuFragment

class MainActivity : BaseActivity<ActivityMainBinding>() {
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun getDataBinding(): ActivityMainBinding {
        return ActivityMainBinding.inflate(layoutInflater)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)

        drawerLayout = findViewById(R.id.drawer_layout)
        val bottomNavigationView: BottomNavigationView = findViewById(R.id.bottom_navigation)
        val navView: NavigationView = findViewById(R.id.nav_view)

        // Thiết lập NavController
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.main_content) as NavHostFragment
        val navController = navHostFragment.navController

        // Liên kết BottomNavigationView với NavController
        bottomNavigationView.setupWithNavController(navController)

        // Áp dụng màu sắc cho từng item
        val menu = bottomNavigationView.menu
        menu.findItem(R.id.nav_home).iconTintList = ContextCompat.getColorStateList(this, R.color.color_home_selected)
        menu.findItem(R.id.nav_calender).iconTintList = ContextCompat.getColorStateList(this, R.color.color_search_selected)
        menu.findItem(R.id.nav_profile).iconTintList = ContextCompat.getColorStateList(this, R.color.color_profile_selected)

        // Xử lý nút Menu trong Bottom Navigation
        bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_drawer -> {
                    // Mở Navigation Drawer
                    drawerLayout.openDrawer(GravityCompat.START)
                    true
                }
                R.id.nav_home -> {
                    navController.navigate(R.id.taskHomeFragment)
                    true
                }
                R.id.nav_calender -> {
                    navController.navigate(R.id.calendarFragment)
                    true
                }
                R.id.nav_profile -> {
                    navController.navigate(R.id.profileFragment)
                    true
                }
                else -> {
                    // Điều hướng giữa các Fragment
                    NavigationUI.onNavDestinationSelected(item, navController)
                    true
                }
            }
        }

        // Đặt item thứ hai là mặc định (giả sử item thứ hai có ID là R.id.search)
        bottomNavigationView.selectedItemId = R.id.nav_home

        // Hiển thị Fragment trong Navigation Drawer
        supportFragmentManager.beginTransaction().replace(R.id.fr_nav_menu_container, NavMenuFragment()).commit()

        // Xử lý Navigation Drawer
        navView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.nav_drawer -> {
                    ToastUtils().show("dsdsdads")
                }
            }
            drawerLayout.closeDrawer(GravityCompat.START)
            true
        }

        onBackPressedDispatcher.addCallback(this) {
            if (drawerLayout.isDrawerOpen(drawerLayout)) {
                drawerLayout.closeDrawer(GravityCompat.START)
                return@addCallback
            }

            // Hiển thị dialog xác nhận thoát ứng dụng
            AlertDialog.Builder(this@MainActivity).setTitle("Thoát ứng dụng").setMessage("Bạn có chắc chắn muốn thoát không?")
                .setPositiveButton("Có") { _, _ ->
                    finish() // Thoát ứng dụng
                }.setNegativeButton("Không") { dialog, _ ->
                    dialog.dismiss() // Đóng dialog, không thoát
                }.show()
        }

        // Request permission
        PermissionUtils.requestNotification(
            this,
            granted = {
                // permission POST_NOTIFICATION is granted, handle logic
            },
            denied = {
                // handle logic when user denied permission
            },
        )
    }

    override fun onSupportNavigateUp(): Boolean {
        return (NavigationUI.navigateUp(navController, appBarConfiguration) || super.onSupportNavigateUp())
    }
}