package com.tta.dailytaskteamt.ui.main.calendar

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.tta.core_base.BaseFragment
import com.tta.dailytaskteamt.R
import com.tta.dailytaskteamt.databinding.FragmentCalendarBinding

class CalendarFragment(override var isTerminalBackKeyActive: Boolean = false) : BaseFragment<FragmentCalendarBinding>() {

    override fun getDataBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentCalendarBinding {
        return FragmentCalendarBinding.inflate(inflater, container, false)
    }
}