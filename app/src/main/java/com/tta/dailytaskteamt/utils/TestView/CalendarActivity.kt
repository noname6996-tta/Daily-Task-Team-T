package com.tta.dailytaskteamt.utils.TestView


import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.tta.dailytaskteamt.R
import com.tta.dailytaskteamt.databinding.ActivityCalendarBinding
import java.util.*

class CalendarActivity : AppCompatActivity() {

    private val binding: ActivityCalendarBinding by lazy {
        ActivityCalendarBinding.inflate(layoutInflater)
    }

    private val months = listOf(
        "January", "February", "March", "April", "May", "June",
        "July", "August", "September", "October", "November", "December"
    )

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)

        // Set the padding for system bars (e.g., status bar and navigation bar)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Initialize the calendar view
        binding.dayViewDp.apply {
            setMonth() // Set initial month and year
            setOnDayFocusChangeListener { dayItem ->
                // Handle the focused day
                println("Focused day: ${dayItem.day}, Month: ${dayItem.month}")
            }
        }



        binding.weekviewdp.setMonth(1)
        binding.weekviewdp.setOnDayFocusChangeListener { dayItem ->
            // Xử lý sự kiện ngày được chọn
            Toast.makeText(this, "Ngày được chọn: ${dayItem.day}/${dayItem.month}", Toast.LENGTH_SHORT).show()
        }




        // Initialize the Spinner with months
        val monthAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, months)
        monthAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.monthSpinner.adapter = monthAdapter

        // Listen for month selection in Spinner
        binding.monthSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                // Get selected month (position starts from 0, so add 1 for correct month number)
                val selectedMonth = position + 1
                val currentYear = Calendar.getInstance().get(Calendar.YEAR)

                // Update the calendar view with the selected month and current year
                binding.dayViewDp.setMonth(selectedMonth, currentYear)
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
                // No action needed
            }
        }
    }
}
