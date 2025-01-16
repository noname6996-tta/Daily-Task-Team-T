package com.tta.dailytaskteamt.ui.profile

import android.view.LayoutInflater
import android.view.ViewGroup
import com.tta.core_base.BaseFragment
import com.tta.dailytaskteamt.databinding.FragmentProfileBinding

class ProfileFragment(override var isTerminalBackKeyActive: Boolean = false) : BaseFragment<FragmentProfileBinding>() {

    override fun getDataBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentProfileBinding {
        return FragmentProfileBinding.inflate(inflater, container, false)
    }

}