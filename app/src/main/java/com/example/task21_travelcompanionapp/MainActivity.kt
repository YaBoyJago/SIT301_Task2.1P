package com.example.task21_travelcompanionapp

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    // UI components (initialised in onCreate)
    private lateinit var spinnerCategory: Spinner
    private lateinit var spinnerSource: Spinner
    private lateinit var spinnerDest: Spinner
    private lateinit var etValue: EditText
    private lateinit var btnConvert: Button
    private lateinit var tvResult: TextView

    // Available options for each category
    private val categories = listOf("Currency", "Fuel Efficiency & Distance", "Temperature")
    private val currencyUnits = listOf("USD", "AUD", "EUR", "JPY", "GBP")
    private val fuelUnits = listOf("mpg (US)", "km/L", "US Gallon", "Liters", "Nautical Mile", "Kilometers")
    private val tempUnits = listOf("Celsius", "Fahrenheit", "Kelvin")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Link UI elements
        spinnerCategory = findViewById(R.id.spinnerCategory)
        spinnerSource = findViewById(R.id.spinnerSource)
        spinnerDest = findViewById(R.id.spinnerDest)
        etValue = findViewById(R.id.etValue)
        btnConvert = findViewById(R.id.btnConvert)
        tvResult = findViewById(R.id.tvResult)

        // Set up category dropdown
        val categoryAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, categories)
        spinnerCategory.adapter = categoryAdapter

        // Update unit dropdowns based on selected category
        spinnerCategory.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: android.view.View?, position: Int, id: Long) {
                val units = when (categories[position]) {
                    "Currency" -> currencyUnits
                    "Fuel Efficiency & Distance" -> fuelUnits
                    else -> tempUnits
                }
                val adapter = ArrayAdapter(this@MainActivity, android.R.layout.simple_spinner_dropdown_item, units)
                spinnerSource.adapter = adapter
                spinnerDest.adapter = adapter
            }
            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

        // Run conversion on button click
        btnConvert.setOnClickListener { convert() }
    }

    // Handles input validation and routes conversion
    private fun convert() {
        val category = spinnerCategory.selectedItem.toString()
        val source = spinnerSource.selectedItem.toString()
        val dest = spinnerDest.selectedItem.toString()
        val inputStr = etValue.text.toString()

        // Prevent empty input
        if (inputStr.isBlank()) {
            Toast.makeText(this, "Please enter a value", Toast.LENGTH_SHORT).show()
            return
        }

        val value = inputStr.toDoubleOrNull() ?: run {
            Toast.makeText(this, "Please enter a valid number", Toast.LENGTH_SHORT).show()
            return
        }

        // Select conversion type
        val result = when (category) {
            "Currency" -> convertCurrency(source, dest, value)
            "Fuel Efficiency & Distance" -> convertFuel(source, dest, value)
            else -> convertTemperature(source, dest, value)
        }

        tvResult.text = "Result: $result $dest"
    }

    // Currency conversion using USD as base
    private fun convertCurrency(from: String, to: String, value: Double): Double {
        if (from == to) return value
        val usdRates = mapOf("USD" to 1.0, "AUD" to 1.55, "EUR" to 0.92, "JPY" to 148.50, "GBP" to 0.78)
        val inUsd = value / usdRates[from]!!
        return inUsd * usdRates[to]!!
    }

    // Fuel and distance conversions
    private fun convertFuel(from: String, to: String, value: Double): Double {
        if (from == to) return value
        return when {
            from == "mpg (US)" && to == "km/L" -> value * 0.425
            from == "km/L" && to == "mpg (US)" -> value / 0.425
            from == "US Gallon" && to == "Liters" -> value * 3.785
            from == "Liters" && to == "US Gallon" -> value / 3.785
            from == "Nautical Mile" && to == "Kilometers" -> value * 1.852
            from == "Kilometers" && to == "Nautical Mile" -> value / 1.852
            else -> value
        }
    }

    // Temperature conversions
    private fun convertTemperature(from: String, to: String, value: Double): Double {
        if (from == to) return value
        return when {
            from == "Celsius" && to == "Fahrenheit" -> value * 1.8 + 32
            from == "Fahrenheit" && to == "Celsius" -> (value - 32) / 1.8
            from == "Celsius" && to == "Kelvin" -> value + 273.15
            from == "Kelvin" && to == "Celsius" -> value - 273.15
            from == "Fahrenheit" && to == "Kelvin" -> (value - 32) / 1.8 + 273.15
            from == "Kelvin" && to == "Fahrenheit" -> (value - 273.15) * 1.8 + 32
            else -> value
        }
    }
}