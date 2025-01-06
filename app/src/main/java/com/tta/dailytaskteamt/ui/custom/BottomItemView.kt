package com.tta.dailytaskteamt.ui.custom

import android.view.View

class BottomItemView(var frameFragment: View, var inactiveView: View, var activeView: View) {
    fun toggle(active: Boolean) {
        frameFragment.visibility = if (active) View.VISIBLE else View.INVISIBLE
        inactiveView.visibility = if (active) View.INVISIBLE else View.VISIBLE
        activeView.visibility = if (active) View.VISIBLE else View.INVISIBLE
    }
}