package com.tta.dailytaskteamt.ui.main.task

import android.view.LayoutInflater
import android.view.ViewGroup
import com.tta.core_base.BaseFragment
import com.tta.dailytaskteamt.databinding.FragmentTaskBinding


class TaskFragment(override var isTerminalBackKeyActive: Boolean = false) : BaseFragment<FragmentTaskBinding>() {

    override fun getDataBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentTaskBinding {
        return FragmentTaskBinding.inflate(inflater, container, false)
    }
}