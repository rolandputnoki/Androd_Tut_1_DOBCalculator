package com.example.dobcalc

import android.app.DatePickerDialog
import android.icu.util.Calendar
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {
    private var selectedDateText : TextView? = null
    private var AgeInMinutesText : TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnDatePicker : Button = findViewById(R.id.btnDatePicker)
        btnDatePicker.setOnClickListener {
            clickDatePicker()
        }

        selectedDateText = findViewById<TextView>(R.id.selectedDateText)
        AgeInMinutesText = findViewById<TextView>(R.id.MinutesDiff)
    }

    private fun clickDatePicker(){

        val myCalendar = java.util.Calendar.getInstance()
        val year = myCalendar.get(Calendar.YEAR)
        val month = myCalendar.get(Calendar.MONTH)
        val day = myCalendar.get(Calendar.DAY_OF_MONTH)


        val dpd = DatePickerDialog(this,//DataPickerDialog.OnDateSetListener can be omitted
            DatePickerDialog.OnDateSetListener { view, selectedYear, selectedMonth, selectedDayOfMonth ->
                Toast.makeText(this, "The selected date $selectedYear.${selectedMonth+1}.$selectedDayOfMonth",
                    Toast.LENGTH_LONG).show()

                val selectedDateString = "$selectedYear.${selectedMonth+1}.$selectedDayOfMonth"

                selectedDateText?.text = selectedDateString

                val sdf = SimpleDateFormat("yyyy.MM.dd", Locale.ENGLISH)
                val theDate = sdf.parse(selectedDateString)

                theDate?.let {
                    val theSelectedDateInMinutes = theDate.time / 60_000
                    val currDate = sdf.parse(sdf.format(System.currentTimeMillis()))

                    currDate?.let {
                        val differenceInMinutes = currDate.time/60_000 - theSelectedDateInMinutes

                        AgeInMinutesText?.text = differenceInMinutes.toString()
                    }
                }
            },
            year,
            month,
            day
        )
        dpd.datePicker.maxDate = System.currentTimeMillis() - 86400000
        dpd.show()
    }
}