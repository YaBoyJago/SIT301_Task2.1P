# Travel Companion Converter App

## Overview

This Android application is a simple and user-friendly unit conversion tool designed to assist with common travel-related calculations. It allows users to convert between different units across three main categories: currency, fuel efficiency & distance, and temperature.

The app is built using Kotlin and follows a straightforward structure, combining a clean user interface with clearly separated conversion logic.

---

## Features

* Convert between multiple **currencies** (USD, AUD, EUR, JPY, GBP)
* Convert **fuel efficiency and distance units** (mpg, km/L, gallons, liters, nautical miles, kilometers)
* Convert **temperature units** (Celsius, Fahrenheit, Kelvin)
* Dynamic dropdowns that update based on selected category
* Input validation with user feedback
* Simple and intuitive UI

---

## User Interface Design

The layout is built using a `ConstraintLayout`, ensuring a responsive and structured design.

### UI Components

* **Category Spinner (Top)**

  * Allows selection between Currency, Fuel Efficiency & Distance, and Temperature
* **Source Spinner**

  * Displays units based on selected category
* **Destination Spinner**

  * Displays units for conversion output
* **EditText Input Field**

  * Accepts numeric input (decimal supported)
* **Convert Button**

  * Triggers the conversion process
* **Result TextView**

  * Displays the converted value clearly (bold and centered)

### UI Behaviour

* When a category is selected, both unit spinners update dynamically
* Input field restricts entries to numbers only
* Output is displayed immediately after pressing the convert button

---

## Application Logic

### 1. Initialisation

All UI components are declared using `lateinit` and connected in `onCreate()` using `findViewById`.

A list of categories and corresponding unit lists are defined:

* `categories`
* `currencyUnits`
* `fuelUnits`
* `tempUnits`

---

### 2. Dynamic Spinner Update

An `onItemSelectedListener` is attached to the category spinner.

* When a category is selected:

  * A `when` statement determines which unit list to use
  * A new `ArrayAdapter` is created
  * Both source and destination spinners are updated

---

### 3. Input Handling

The `convert()` function:

* Retrieves selected category, units, and input value
* Validates input:

  * Checks for empty input
  * Ensures the value is numeric using `toDoubleOrNull()`
* Displays error messages using Toasts if validation fails

---

### 4. Conversion Logic

#### Currency Conversion

* Uses a map of exchange rates relative to USD
* Conversion process:

  1. Convert source currency to USD
  2. Convert USD to destination currency
* This simplifies handling multiple currencies using a common base

#### Fuel & Distance Conversion

* Uses direct conversion factors between specific unit pairs
* Examples:

  * mpg ↔ km/L
  * gallons ↔ liters
  * nautical miles ↔ kilometers
* If no matching conversion is found, the original value is returned

#### Temperature Conversion

* Handles all combinations between:

  * Celsius
  * Fahrenheit
  * Kelvin
* Uses standard mathematical formulas for each case
* Returns original value if units are the same

---

### 5. Output Display

After conversion:

* The result is formatted as:

  ```
  Result: <value> <unit>
  ```
* Displayed in the `TextView`

---

## How to Run

1. Open the project in Android Studio
2. Sync Gradle files
3. Run the app on:

   * Emulator OR
   * Physical Android device
4. Select a category, choose units, enter a value, and press **Convert**

---

## Future Improvements

* Add real-time currency exchange rates via API
* Improve UI with Material Design components
* Add more unit categories (e.g. weight, time)
* Format output values (rounding/precision)
* Add error highlighting for input field

---

## Summary

This app demonstrates:

* Effective use of Android UI components (Spinner, EditText, Button, TextView)
* Dynamic UI updates based on user interaction
* Clear separation of concerns between UI and logic
* Implementation of multiple conversion algorithms in a structured way

It provides a solid foundation for building more advanced utility or travel-based applications.
