package com.tta.dailytaskteamt.utils

import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import com.tta.dailytaskteamt.R
import com.tta.dailytaskteamt.databinding.DialogConfirmBinding

/**
 * Using this object to call static function show dialog
 * */
object DialogUtils {

    fun showConfirmDialog(
        context: Context,
        title: String,
        content: String,
        confirm: () -> Unit
    ): Dialog {
        val dialog = Dialog(context, R.style.CustomStyleDialog)
        val binding = DialogConfirmBinding.inflate(LayoutInflater.from(context))
        dialog.setContentView(binding.root)

        binding.tvTitle.text = title
        binding.tvContent.text = content

        binding.btnCancel.setOnClickListener {
            dialog.dismiss()
        }

        binding.btnConfirm.setOnClickListener {
            confirm()
            dialog.dismiss()
        }

        return dialog
    }


}