package com.example.dobcalc

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.icu.util.Calendar
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.TimePicker
import android.widget.Toast
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {
    private var selectedDateText : TextView? = null
    private var selectedTimeText : TextView? = null
    private var AgeInMinutesText : TextView? = null
    private var selectedDateString : String? = null
    private var selectedTimeString : String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnDatePicker : Button = findViewById(R.id.btnDatePicker)
        btnDatePicker.setOnClickListener {
            clickDatePicker()
        }

        val btnTimePicker : Button = findViewById(R.id.btnTimePicker)
        btnTimePicker.setOnClickListener {
            clickTimePicker()
        }

        selectedDateText = findViewById(R.id.selectedDateText)
        AgeInMinutesText = findViewById(R.id.MinutesDiff)
        selectedTimeText = findViewById(R.id.selectedTimeText)

        selectedTimeString = selectedTimeText?.text.toString()
        selectedDateString = selectedDateText?.text.toString()
    }


    private fun clickTimePicker()
    {
        TimePickerDialog(
            this, TimePickerDialog.OnTimeSetListener {
                    _: TimePicker?, selectedHourOfDay: Int, selectedMinute: Int ->
                Toast.makeText(this, "Select time", Toast.LENGTH_LONG).show()

                val CurrselectedTimeString = "$selectedHourOfDay:$selectedMinute"

                selectedTimeText?.text = CurrselectedTimeString
                selectedTimeString = CurrselectedTimeString

                val selectedDateTime = selectedDateString + ' ' + selectedTimeString

                selectedDateText?.text = selectedDateString

                val sdf = SimpleDateFormat("yyyy.MM.dd HH:mm", Locale.ENGLISH)
                val theDate = sdf.parse(selectedDateTime)

                theDate?.let {
                    val theSelectedDateInMinutes = theDate.time / 60_000
                    val currDate = sdf.parse(sdf.format(System.currentTimeMillis()))

                    currDate?.let {
                        val differenceInMinutes = currDate.time/60_000 - theSelectedDateInMinutes

                        AgeInMinutesText?.text = differenceInMinutes.toString()
                    }
                }


            },
            12,
            0,
            true
        ).show()
    }

    private fun clickDatePicker(){

        val myCalendar = java.util.Calendar.getInstance()
        val year = myCalendar.get(Calendar.YEAR)
        val month = myCalendar.get(Calendar.MONTH)
        val day = myCalendar.get(Calendar.DAY_OF_MONTH)





        val dpd = DatePickerDialog(this,//DataPickerDialog.OnDateSetListener can be omitted
            DatePickerDialog.OnDateSetListener { _, selectedYear, selectedMonth, selectedDayOfMonth ->
                Toast.makeText(this, "The selected date $selectedYear.${selectedMonth+1}.$selectedDayOfMonth",
                    Toast.LENGTH_LONG).show()

                selectedDateString = "$selectedYear.${selectedMonth+1}.$selectedDayOfMonth"

                val selectedDateTime = selectedDateString + ' ' + selectedTimeString

                selectedDateText?.text = selectedDateString

                val sdf = SimpleDateFormat("yyyy.MM.dd HH:mm", Locale.ENGLISH)
                val theDate = sdf.parse(selectedDateTime)

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