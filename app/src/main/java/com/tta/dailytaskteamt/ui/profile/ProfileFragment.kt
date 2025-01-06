package com.tta.dailytaskteamt.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import com.tta.core_base.BaseFragment
import com.tta.dailytaskteamt.databinding.FragmentProfileBinding
import com.tta.dailytaskteamt.ui.calender.CalenderFragment

class ProfileFragment(override var isTerminalBackKeyActive: Boolean = false) : BaseFragment<FragmentProfileBinding>() {

    companion object {
        fun newInstance(): ProfileFragment {
            val bundle = Bundle()
            val profileFragment = ProfileFragment()
            profileFragment.arguments = bundle
            return profileFragment
        }
    }

    override fun getDataBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentProfileBinding {
        return FragmentProfileBinding.inflate(inflater, container, false)
    }

}