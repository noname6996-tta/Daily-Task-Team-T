package com.tta.dailytaskteamt.ui.main.calendar

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import com.tta.core_base.BaseFragment
import com.tta.dailytaskteamt.databinding.FragmentCalenderBinding

class CalenderFragment(override var isTerminalBackKeyActive: Boolean = false) : BaseFragment<FragmentCalenderBinding>() {
    companion object {
        fun newInstance(): CalenderFragment {
            val bundle = Bundle()
            val taskFragment = CalenderFragment()
            taskFragment.arguments = bundle
            return taskFragment
        }
    }

    override fun getDataBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentCalenderBinding {
        return FragmentCalenderBinding.inflate(inflater, container, false)
    }
}