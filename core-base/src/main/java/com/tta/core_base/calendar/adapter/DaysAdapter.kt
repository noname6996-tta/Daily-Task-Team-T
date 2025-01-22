package com.example.calendardp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

import com.example.calendardp.model.DayItem
import com.tta.core_base.R

class DaysAdapter(private val days: MutableList<DayItem>) : RecyclerView.Adapter<DaysAdapter.DayViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DayViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_day, parent, false)
        return DayViewHolder(view)
    }

    override fun onBindViewHolder(holder: DayViewHolder, position: Int) {
        val dayItem = days[position]
        holder.bind(dayItem)
    }

    override fun getItemCount(): Int = days.size

    fun getItemAt(position: Int): DayItem {
        return days[position]
    }
    fun findPositionByDay(day: Int): Int {
        return days.indexOfFirst { it.day == day }
    }



    class DayViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvDayOfWeek: TextView = itemView.findViewById(R.id.tvDayOfWeek)
        private val tvDay: TextView = itemView.findViewById(R.id.tvDay)
        private val tvMonth: TextView = itemView.findViewById(R.id.tvMonth)

        // Mảng chứa tên viết tắt các tháng bằng tiếng Anh
        private val months = arrayOf(
            "Jan", "Feb", "Mar", "Apr", "May", "Jun",
            "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"
        )

        fun bind(dayItem: DayItem) {
            // Set text for non-empty items, leave empty items blank
            tvDayOfWeek.text = if (dayItem.dayOfWeek.isNotEmpty()) dayItem.dayOfWeek else ""
            tvDay.text = if (dayItem.day > 0) dayItem.day.toString() else ""
            tvMonth.text = if (dayItem.month in 1..12) months[dayItem.month - 1] else ""
        }
    }


}
