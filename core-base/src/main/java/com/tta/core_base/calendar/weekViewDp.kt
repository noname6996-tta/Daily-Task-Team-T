package com.example.calendardp

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.calendardp.adapter.DaysAdapter
import com.example.calendardp.adapter.DaysAdapter2

import com.example.calendardp.model.DayItem
import com.tta.core_base.databinding.CalendarWeekBinding
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.*
@SuppressLint("ClickableViewAccessibility")
@RequiresApi(Build.VERSION_CODES.O)
class weekViewDp @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private val binding: CalendarWeekBinding
    private var onDayFocusChangeListener: ((DayItem) -> Unit)? = null
    private var currentDate: LocalDate = LocalDate.now()
    private lateinit var days: MutableList<DayItem>
    private lateinit var adapter: DaysAdapter2

    init {
        // Inflate layout
        binding = CalendarWeekBinding.inflate(LayoutInflater.from(context), this, true)

        // Set up RecyclerView with horizontal layout
        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.recyclerViewDays.layoutManager = layoutManager


        // Handle arrow click
        binding.arrowLeft.setOnClickListener { navigateDays(-1) }
        binding.arrowRight.setOnClickListener { navigateDays(1) }

        // Initialize days and adapter
        updateRecyclerView()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun setMonth(month: Int? = null, year: Int? = null) {
        // Cập nhật ngày hiện tại với tháng và năm được truyền vào
        currentDate = LocalDate.of(
            year ?: currentDate.year,
            month ?: currentDate.monthValue,
            currentDate.dayOfMonth
        )
        updateRecyclerView()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun generateDays(month: Int, year: Int): MutableList<DayItem> {
        val days = mutableListOf<DayItem>()
        val monthDays = LocalDate.of(year, month, 1).lengthOfMonth()

        for (day in 1..monthDays) {
            val date = LocalDate.of(year, month, day)
            val dayOfWeek = date.dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.getDefault())
            days.add(DayItem(dayOfWeek, day, month))
        }
        return days
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun navigateDays(offset: Int) {
        // Cập nhật ngày hiện tại dựa trên offset (-1 hoặc +1)
        currentDate = currentDate.plusDays(offset.toLong())

        // Nếu vượt qua tháng hiện tại, cập nhật lại tháng
        if (currentDate.dayOfMonth == 1 && offset < 0) {
            currentDate = currentDate.minusMonths(1).withDayOfMonth(
                currentDate.lengthOfMonth()
            )
            updateRecyclerView()
        } else if (currentDate.dayOfMonth == currentDate.lengthOfMonth() && offset > 0) {
            currentDate = currentDate.plusMonths(1).withDayOfMonth(1)
            updateRecyclerView()
        }

        // Tính toán vị trí mới trong RecyclerView
        val position = currentDate.dayOfMonth - 1
        binding.recyclerViewDays.smoothScrollToPosition(position)
        // Gọi callback nếu cần
        onDayFocusChangeListener?.invoke(
            DayItem(
                currentDate.dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.getDefault()),
                currentDate.dayOfMonth,
                currentDate.monthValue
            )
        )
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun updateRecyclerView() {
        days = generateDays(currentDate.monthValue, currentDate.year)
        adapter = DaysAdapter2(days) { dayItem ->
            // Gọi callback khi ngày được nhấn
            onDayFocusChangeListener?.invoke(dayItem)
        }
        binding.recyclerViewDays.adapter = adapter

        // Cuộn đến ngày hiện tại
        val position = currentDate.dayOfMonth - 1
        binding.recyclerViewDays.scrollToPosition(position)
    }


    fun setOnDayFocusChangeListener(listener: (DayItem) -> Unit) {
        this.onDayFocusChangeListener = listener
    }
}

