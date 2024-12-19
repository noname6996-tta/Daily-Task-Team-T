package com.tta.core_utils

import android.os.SystemClock
import timber.log.Timber

/**
 * Utils check xem nút có bị click hai lần trong một khoảng thời gian không .
 *
 * @param isAvailableClick Gọi luôn biến này thì thời gian check là 500 ml giây
 * @param isAvailableClick fun thì truyền vào giá trị thời gian dãn ra muốn check
 *
 * Cách dùng
 *              if (!MultiClickUtils.instance?.isAvailableClick(300)!!) {
 *                 return
 *             }
 *
 */

const val TIME_DELAY_CLICK_IN_MILISECONDS: Long = 500

class MultiClickUtils private constructor() {
    private var mLastClickTime: Long
    val isAvailableClick: Boolean
        get() {
            val currentTime = SystemClock.elapsedRealtime()
            if (currentTime - mLastClickTime < TIME_DELAY_CLICK_IN_MILISECONDS) {
                Timber.d("Can not apply click!")
                return false
            }
            mLastClickTime = currentTime
            Timber.d("Apply click!")
            return true
        }

    fun isAvailableClick(timeDelay: Long): Boolean {
        val currentTime = SystemClock.elapsedRealtime()
        if (currentTime - mLastClickTime < timeDelay) {
            Timber.d("Can not apply click!")
            return false
        }
        mLastClickTime = currentTime
        Timber.d("Apply click!")
        return true
    }

    companion object {
        var instance: MultiClickUtils? = null
            get() {
                if (field == null) field = MultiClickUtils()
                return field
            }
            private set
    }

    init {
        mLastClickTime = -1
    }
}