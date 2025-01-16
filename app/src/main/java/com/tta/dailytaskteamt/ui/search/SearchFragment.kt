package com.tta.dailytaskteamt.ui.search

import android.view.LayoutInflater
import android.view.ViewGroup
import com.tta.core_base.BaseFragment
import com.tta.dailytaskteamt.databinding.FragmentSearchBinding


class SearchFragment(override var isTerminalBackKeyActive: Boolean = false) : BaseFragment<FragmentSearchBinding>() {

    override fun getDataBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentSearchBinding {
        return FragmentSearchBinding.inflate(inflater, container, false)
    }
}