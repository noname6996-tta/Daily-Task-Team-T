package com.tta.dailytaskteamt.ui.menu

import android.view.LayoutInflater
import android.view.ViewGroup
import com.tta.core_base.BaseFragment
import com.tta.core_utils.extension.toast
import com.tta.core_utils.uitls.PermissionUtils
import com.tta.dailytaskteamt.databinding.FragmentMenuBinding
import com.tta.dailytaskteamt.ui.menu.adapter.TestAdapter


class MenuFragment(override var isTerminalBackKeyActive: Boolean = false) :
    BaseFragment<FragmentMenuBinding>() {

    private val testList = arrayListOf<String>()
    private lateinit var testAdapter: TestAdapter

    override fun getDataBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ): FragmentMenuBinding {
        return FragmentMenuBinding.inflate(inflater, container, false)
    }

    override fun initView() {
        super.initView()

        for (i in 0 until 30) {
            testList.add(i.toString())
        }
        testAdapter = TestAdapter(testList)
        binding.rcv.adapter = testAdapter
        testAdapter.itemClickListener = { data, _ ->
            activity?.toast("Data: $data")
        }

        // Demo request permission
        activity?.let {
            PermissionUtils.requestNotification(it) {
                // Permission granted
            }
        }

    }

}