package com.tta.dailytaskteamt.ui.custom

import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.tta.dailytaskteamt.R
import com.tta.dailytaskteamt.ui.calender.CalenderFragment
import com.tta.dailytaskteamt.ui.profile.ProfileFragment
import com.tta.dailytaskteamt.ui.task.TaskFragment
import timber.log.Timber

class BottomTabController {
    var mCurrentSelectedTab = INDEX_TAB_NONE
    private val mListBottomItemViews = ArrayList<BottomItemView>()
    private var mActiveListener: ActiveListener? = null

    interface ActiveListener {
        fun onActive(currentSelectedTab: Int)
        fun onInactive(unselectedTab: Int)
        fun onAdd(id: Int)
    }

    /**
     * List item theo thứ tự lần lượt fragmentViewSpeedTest, inactiveSpeedTest, activeSpeedTest,
     * fragmentViewHistory, inactiveHistory, activeHistory,....
     *
     * @param listViews : list các item.
     * @param listViews size : tăng lên theo kiểu nếu là 4 tab thì 4 * 3 , 3 tab là 3 * 3
     */
    fun init(vararg listViews: View?) {
        if (listViews.size != 9) throw RuntimeException("Danh sách View chuyền vào không đủ : inactiveA->activeA->inactiveB->activeB....")
        for (i in 0 until listViews.size / 3) {
            mListBottomItemViews.add(
                BottomItemView(
                    listViews[i * 3]!!,
                    listViews[i * 3 + 1]!!,
                    listViews[i * 3 + 2]!!
                )
            )
        }
    }

    fun initFragmentTab(fm: FragmentManager) {
        Timber.d("init")
        for (i in mListBottomItemViews.size - 1 downTo 0) {
            val fragment: Fragment = when (i) {
                INDEX_TAB_TASK -> TaskFragment.newInstance()
                INDEX_TAB_CALENDAR -> CalenderFragment.newInstance()
                INDEX_TAB_PROFILE -> ProfileFragment.newInstance()
                else -> TaskFragment.newInstance()
            }
            //Bug re-init fragment!
            (mListBottomItemViews[i].frameFragment as ViewGroup).removeAllViews()

            fm.beginTransaction().replace(
                mListBottomItemViews[i].frameFragment.id,
                fragment, i.toString()
            ).disallowAddToBackStack().commitAllowingStateLoss()
        }
    }

    fun destroy() {
        mListBottomItemViews.clear()
        for (i in mListBottomItemViews.size - 1 downTo 0) {
            (mListBottomItemViews[i].frameFragment as ViewGroup).removeAllViews()
        }
        mActiveListener = null
    }

    fun setActiveListener(listener: ActiveListener?) {
        mActiveListener = listener
    }

    fun selectTab(indexTab: Int) {
        if (indexTab == mCurrentSelectedTab) return
        deselectTab(mCurrentSelectedTab)
        if (indexTab == INDEX_TAB_NONE) return
        mCurrentSelectedTab = indexTab
        mListBottomItemViews[indexTab].toggle(true)
        mActiveListener?.onActive(indexTab)
    }

    private fun deselectTab(indexTab: Int) {
        if (indexTab == INDEX_TAB_NONE) return
        mListBottomItemViews[indexTab].toggle(false)
        mCurrentSelectedTab = INDEX_TAB_NONE
        mActiveListener?.onInactive(indexTab)
    }

    fun handleOnTabClick(v: View) {
        when (v.id) {
            R.id.navTask -> {
                selectTab(INDEX_TAB_TASK)
            }

            R.id.navCalender -> {
                selectTab(INDEX_TAB_CALENDAR)
            }

            R.id.navProfile -> {
                selectTab(INDEX_TAB_PROFILE)
            }
        }
    }

    companion object {
        const val INDEX_TAB_NONE = -1
        const val INDEX_TAB_TASK = 0
        const val INDEX_TAB_CALENDAR = 1
        const val INDEX_TAB_PROFILE = 2
    }
}