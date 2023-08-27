package com.example.bmi_mmvm

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class BmiViewModel : ViewModel() {
    var heightInput: String by mutableStateOf(value = "")

    fun updateHeight(new: String) {
        heightInput = new
    }

    var weightInput: String by mutableStateOf(value = "")

    fun updateWeight(new: String) {
        weightInput = new
    }

    var bmi: Float = 0.0f
        get() {
            return if (weight * height > 0) weight / (height * height) else 0.0f
        }

    private val height: Float
        get() {
            return heightInput.toFloatOrNull() ?: 0.0f
        }

    private val weight: Float
        get() {
            return weightInput.toFloatOrNull() ?: 0.0f
        }
}
