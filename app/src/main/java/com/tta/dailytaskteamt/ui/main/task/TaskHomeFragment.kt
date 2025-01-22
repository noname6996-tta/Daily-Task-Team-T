package com.tta.dailytaskteamt.ui.main.task

import android.animation.ValueAnimator
import android.view.LayoutInflater
import android.view.ViewGroup
import com.tta.core_base.BaseFragment
import com.tta.dailytaskteamt.databinding.FragmentTaskHomeBinding

class TaskHomeFragment(override var isTerminalBackKeyActive: Boolean = false) : BaseFragment<FragmentTaskHomeBinding>() {
    override fun getDataBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentTaskHomeBinding {
        return FragmentTaskHomeBinding.inflate(inflater, container, false)
    }

    override fun initView() {
        super.initView()
        binding.circularProgress.setProgress(85f)
        val animator = ValueAnimator.ofFloat(0f, 85f)
        animator.duration = 3000 // Thời gian 1 giây
        animator.addUpdateListener { animation ->
            val progress = animation.animatedValue as Float
            binding.circularProgress.setProgress(progress)
        }
        animator.start()
    }
}