package com.example.calendardp

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.calendardp.adapter.DaysAdapter

import com.example.calendardp.model.DayItem
import com.tta.core_base.databinding.CalendarView2Binding
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.*

class DayViewDp @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private val binding: CalendarView2Binding
    private var onDayFocusChangeListener: ((DayItem) -> Unit)? = null
    private var snapHelper: LinearSnapHelper = LinearSnapHelper()

    init {
        // Inflate layout with ViewBinding
        binding = CalendarView2Binding.inflate(LayoutInflater.from(context), this, true)

        // Set up RecyclerView with horizontal layout manager
        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.recyclerViewDays.layoutManager = layoutManager

        // Attach SnapHelper for auto-centering
        snapHelper.attachToRecyclerView(binding.recyclerViewDays)

        binding.recyclerViewDays.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)

                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    val focusedView = snapHelper.findSnapView(layoutManager)
                    focusedView?.let {
                        val position = binding.recyclerViewDays.getChildAdapterPosition(it)
                        if (position != RecyclerView.NO_POSITION) {
                            val adapter = binding.recyclerViewDays.adapter as? DaysAdapter
                            adapter?.let {
                                val dayItem = it.getItemAt(position)
                                onDayFocusChangeListener?.invoke(dayItem)
                            }
                        }
                    }
                    applyFocusEffect()  // Reapply the focus effect after snapping
                }
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                applyFocusEffect()  // Apply focus effect during scrolling
            }
        })
    }

    fun setMonth(month: Int? = null, year: Int? = null) {
        // Lấy tháng và năm hiện tại nếu không truyền vào
        val currentMonth = month ?: (Calendar.getInstance().get(Calendar.MONTH) + 1)
        val currentYear = year ?: Calendar.getInstance().get(Calendar.YEAR)

        val days = generateDays(currentMonth, currentYear)
        val adapter = DaysAdapter(days.toMutableList())
        binding.recyclerViewDays.adapter = adapter

        // Lấy ngày hiện tại
        val currentDay = Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        // Tìm vị trí của ngày hiện tại
        val position = adapter.findPositionByDay(currentDay + 2)
        if (position != -1) {
            binding.recyclerViewDays.post {
                (binding.recyclerViewDays.layoutManager as? LinearLayoutManager)?.scrollToPositionWithOffset(
                    position,
                    binding.recyclerViewDays.width / 2
                )
            }
        }
    }



    @SuppressLint("NewApi")
    private fun generateDays(month: Int, year: Int): List<DayItem> {
        val days = mutableListOf<DayItem>()
        val monthDays = LocalDate.of(year, month, 1).lengthOfMonth()

        for (day in 1..monthDays) {
            val date = LocalDate.of(year, month, day)
            val dayOfWeek = date.dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.getDefault())
            days.add(DayItem(dayOfWeek, day, month))
        }

        return days
    }

    private fun applyFocusEffect() {
        val centerX = binding.recyclerViewDays.width / 2

        for (i in 0 until binding.recyclerViewDays.childCount) {
            val child = binding.recyclerViewDays.getChildAt(i)
            val childCenterX = (child.left + child.right) / 2
            val distanceFromCenter = Math.abs(centerX - childCenterX)

            // Adjust scale or other visual effects based on distance
            val scale = 1f - (distanceFromCenter.toFloat() / centerX)
            child.scaleX = scale
            child.scaleY = scale
        }
    }

    fun setOnDayFocusChangeListener(listener: (DayItem) -> Unit) {
        onDayFocusChangeListener = listener
    }
}
