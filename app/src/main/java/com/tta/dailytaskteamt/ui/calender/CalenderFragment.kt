package com.tta.dailytaskteamt.ui.calender

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import com.tta.core_base.BaseFragment
import com.tta.dailytaskteamt.databinding.FragmentHomeBinding

class CalenderFragment : BaseFragment<FragmentHomeBinding>() {
    override var isTerminalBackKeyActive: Boolean = false

    companion object {
        fun newInstance(): CalenderFragment {
            val bundle = Bundle()
            val profileFragment = CalenderFragment()
            profileFragment.arguments = bundle
            return profileFragment
        }
    }

    override fun getDataBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentHomeBinding {
        return FragmentHomeBinding.inflate(inflater, container, false)
    }
}