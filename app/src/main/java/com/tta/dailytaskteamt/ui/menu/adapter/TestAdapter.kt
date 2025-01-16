package com.tta.dailytaskteamt.ui.menu.adapter

import android.annotation.SuppressLint
import androidx.databinding.ViewDataBinding
import com.tta.core_base.BaseAdapter
import com.tta.dailytaskteamt.R
import com.tta.dailytaskteamt.databinding.ItemTestAdapterBinding
/** Base Adapter demo: how to using base adapter */
class TestAdapter(mList: ArrayList<String>): BaseAdapter<String>(mList) {

    override fun getLayoutId(): Int = R.layout.item_test_adapter

    @SuppressLint("SetTextI18n")
    override fun bindData(binding: ViewDataBinding, data: String, pos: Int) {
        if (binding is ItemTestAdapterBinding) {
            binding.tvTitle.text = "Item $data"
            binding.frameMain.setOnClickListener {
                itemClickListener?.invoke(data, pos)
            }
        }
    }
}