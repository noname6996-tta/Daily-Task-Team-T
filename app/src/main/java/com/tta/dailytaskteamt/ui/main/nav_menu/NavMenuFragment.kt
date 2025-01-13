package com.tta.dailytaskteamt.ui.main.nav_menu

import android.view.LayoutInflater
import android.view.ViewGroup
import com.tta.core_base.BaseFragment
import com.tta.dailytaskteamt.databinding.FragmentNavMenuBinding

class NavMenuFragment : BaseFragment<FragmentNavMenuBinding>()  {
    override var isTerminalBackKeyActive: Boolean = false

    override fun getDataBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentNavMenuBinding {
        return FragmentNavMenuBinding.inflate(inflater, container, false)
    }
}