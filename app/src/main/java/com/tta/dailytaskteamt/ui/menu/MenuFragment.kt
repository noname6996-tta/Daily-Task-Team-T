package com.tta.dailytaskteamt.ui.menu

import android.view.LayoutInflater
import android.view.ViewGroup
import com.tta.core_base.BaseFragment
import com.tta.dailytaskteamt.databinding.FragmentMenuBinding


class MenuFragment(override var isTerminalBackKeyActive: Boolean = false) : BaseFragment<FragmentMenuBinding>() {

    override fun getDataBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentMenuBinding {
        return FragmentMenuBinding.inflate(inflater, container, false)
    }

    override fun initView() {
        super.initView()
    }

}