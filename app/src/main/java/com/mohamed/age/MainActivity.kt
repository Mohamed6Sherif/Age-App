package com.mohamed.age

import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.DatePicker
import android.widget.ImageButton
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.time.LocalDate
import java.time.Period
import java.util.Locale

class MainActivity : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val datePicker = findViewById<DatePicker>(R.id.date_picker_DatePicker)
        val calculateAgeButton = findViewById<Button>(R.id.calculateAge_btn)
        val resultTextView = findViewById<TextView>(R.id.result_txt)
        val changeLanguageButton = findViewById<ImageButton>(R.id.changeLanguage_Img_btn)

        // Set a click listener for the calculateAgeButton
        calculateAgeButton.setOnClickListener {
            // Get the selected date from the DatePicker
            val year = datePicker.year
            val month = datePicker.month + 1 // Months are zero-based
            val day = datePicker.dayOfMonth

            // Calculate the age based on the selected date
            val birthDate = LocalDate.of(year, month, day)
            val currentDate = LocalDate.now()
            // Calculate the age using the Period class
            val age = Period.between(birthDate,currentDate)
            // Display the age in the resultTextView
            resultTextView.text = "Age : ${age.years} years , ${age.months} months , ${age.days} days"




        }

        // Set a click listener for the changeLanguageButton
        changeLanguageButton.setOnClickListener {
            showLanguageDialog()
        }
    }

    private fun showLanguageDialog() {
        val languages = arrayOf("English", "العربية")
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Select Language")
        builder.setItems(languages) { _, which ->
            when (which) {
                0 -> changeLanguage("en") // الإنجليزية
                1 -> changeLanguage("ar") // العربية
            }
        }
        builder.show()
    }

    private fun changeLanguage(languageCode: String) {
        val locale = Locale(languageCode)
        Locale.setDefault(locale)
        val config = Configuration()
        config.setLocale(locale)
        baseContext.resources.updateConfiguration(config, baseContext.resources.displayMetrics)
        recreate() // لإعادة تحميل الـ Activity بالتحديث الجديد
    }
}