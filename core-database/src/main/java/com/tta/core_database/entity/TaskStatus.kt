package com.tta.core_database.entity

import com.tta.core_database.R

enum class TaskStatus(val status: String, color: Int ) {
    IN_PROCESS("IN_PROCESS", R.color.in_process_color),
    TODO("TODO", R.color.to_do_color),
    DONE("DONE", R.color.done_color),
}