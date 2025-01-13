package com.tta.dailytaskteamt.ui.main

import android.app.AlertDialog
import android.os.Bundle
import android.view.View
import androidx.activity.addCallback
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.blankj.utilcode.util.ScreenUtils
import com.tta.core_base.BaseActivity
import com.tta.core_utils.uitls.PermissionUtils
import com.tta.dailytaskteamt.R
import com.tta.dailytaskteamt.databinding.ActivityMainBinding
import com.tta.dailytaskteamt.databinding.LayoutFragmentMainBinding
import com.tta.dailytaskteamt.ui.custom.BottomTabController
import com.tta.dailytaskteamt.ui.nav_menu.NavMenuFragment
import com.tta.dailytaskteamt.utils.Constants
import com.tta.dailytaskteamt.utils.DialogUtils

class MainActivity : BaseActivity<ActivityMainBinding>(), BottomTabController.ActiveListener {

    private var mNavMenuFragment: NavMenuFragment? = null
    private var currentSelectedTab: Int = 0
    private val mBottomTabController = BottomTabController()

    override fun getDataBinding(): ActivityMainBinding {
        return ActivityMainBinding.inflate(layoutInflater)
    }

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        initBottomNavigation(savedInstanceState)
        initNavMenu()

        onBackPressedDispatcher.addCallback(this) {
            if (mustCloseNavigationMenu()) {
                return@addCallback
            }

            // Hiển thị dialog xác nhận thoát ứng dụng
            AlertDialog.Builder(this@MainActivity)
                .setTitle("Thoát ứng dụng")
                .setMessage("Bạn có chắc chắn muốn thoát không?")
                .setPositiveButton("Có") { _, _ ->
                    finish() // Thoát ứng dụng
                }
                .setNegativeButton("Không") { dialog, _ ->
                    dialog.dismiss() // Đóng dialog, không thoát
                }
                .show()
        }

        // Request permission
        PermissionUtils.requestNotification(
            this,
            granted = {
                // permission POST_NOTIFICATION is granted, handle logic
            },
            denied = {
                // handle logic when user denied permission
                DialogUtils.showConfirmDialog(
                    context = this,
                    title = getString(R.string.title_post_notification_permission),
                    content = getString(R.string.msg_post_notification_permission)
                ) {
                    // ....
                }.show()
            },
        )
    }

    private fun initBottomNavigation(savedInstanceState: Bundle?) {
        val layoutBottomMain = binding.bottomNavigation
        val layoutFragmentMain = LayoutFragmentMainBinding.bind(binding.root)

        if (savedInstanceState != null) {
            currentSelectedTab = savedInstanceState.getInt(Constants.CURRENT_TAB_MAIN)
            if (currentSelectedTab > 0) {
                mBottomTabController.mCurrentSelectedTab = 0
            }
        }

        mBottomTabController.init(
            layoutFragmentMain.fragmentTabTask,
            layoutBottomMain.tabTaskInactive,
            layoutBottomMain.tabTaskActive,

            layoutFragmentMain.fragmentTabCalendar,
            layoutBottomMain.tabCalenderInactive,
            layoutBottomMain.tabCalenderActive,

            layoutFragmentMain.fragmentTabProfile,
            layoutBottomMain.tabProfileInactive,
            layoutBottomMain.tabProfileActive
        )
        mBottomTabController.initFragmentTab(supportFragmentManager)
        mBottomTabController.selectTab(if (currentSelectedTab > 0) currentSelectedTab else BottomTabController.INDEX_TAB_TASK)
        mBottomTabController.setActiveListener(this@MainActivity)
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

        binding.bottomNavigation.navMenu.setOnClickListener {
            openNavigationMenu()
        }
    }

    private fun mustCloseNavigationMenu(): Boolean {
        if (binding.root.findViewById<DrawerLayout>(R.id.drawer_layout).isDrawerOpen(GravityCompat.START)) {
            closeNavigationMenu()
            return true
        }
        return false
    }

    private fun openNavigationMenu() {
        binding.root.findViewById<DrawerLayout>(R.id.drawer_layout).openDrawer(GravityCompat.START)
    }

    private fun closeNavigationMenu() {
        binding.root.findViewById<DrawerLayout>(R.id.drawer_layout).closeDrawer(GravityCompat.START)
    }

    fun onBottomTabClick(v: View?) {
        mBottomTabController.handleOnTabClick(v!!)
    }

    override fun onActive(currentSelectedTab: Int) {
        this.currentSelectedTab = currentSelectedTab
    }

    override fun onInactive(unselectedTab: Int) {

    }

    override fun onAdd(id: Int) {

    }

    override fun onDestroy() {
        super.onDestroy()
        mBottomTabController.destroy()
    }
}