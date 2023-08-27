package com.example.calories

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import com.example.calories.ui.theme.CaloriesTheme
import kotlin.math.exp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CaloriesTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    CalorieScreen()
                }
            }
        }
    }
}

@Composable
fun Heading(title: String) {
    Text(
        text = title,
        fontSize = 24.sp,
        textAlign = TextAlign.Center,
        color = MaterialTheme.colorScheme.primary,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp, bottom = 16.dp),
    )
}

@Composable
fun WeightField(weightInput: String, onValueChange: (String) -> Unit) {
    OutlinedTextField(
        value = weightInput,
        onValueChange = onValueChange,
        label = { Text(text = "Enter weight") },
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        modifier = Modifier.fillMaxWidth(),
    )
}

@ExperimentalMaterial3Api
@Composable
fun GenderChoices(male: Boolean, setGenderMale: (Boolean) -> Unit) {
    Column(Modifier.selectableGroup()) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            RadioButton(selected = male, onClick = { setGenderMale(true) })
            Text(text = "Male")
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            RadioButton(selected = !male, onClick = { setGenderMale(!true) })
            Text(text = "Female")
        }
    }

}

@Composable
fun IntensityList(onClick: (Float) -> Unit) {
    var expanded: Boolean by remember { mutableStateOf(false) }
    var selectedText: String by remember { mutableStateOf("Light") }
    var textFieldSize: Size by remember { mutableStateOf(Size.Zero) }
//    val items = listOf<String>("Light", "Usual", "Moderate", "Hard", "Very hard")
    val intensities = mapOf(
        Pair("Light", 1.3f),
        Pair("Usual", 1.5f),
        Pair("Moderate", 1.7f),
        Pair("Hard", 2f),
        Pair("Very hard", 2.2f),
    )

    val icon = if (expanded)
        Icons.Filled.KeyboardArrowUp
    else
        Icons.Filled.KeyboardArrowDown

    Column {
        OutlinedTextField(
            readOnly = true,
            value = selectedText,
            onValueChange = { selectedText = it },
            modifier = Modifier
                .fillMaxWidth()
                .onGloballyPositioned { coordinates ->
                    textFieldSize = coordinates.size.toSize()
                },
            label = { Text("Select intensity") },
            trailingIcon = {
                Icon(
                    icon,
                    contentDescription = "contentDescription",
                    modifier = Modifier.clickable { expanded = !expanded },
                )
            },
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.width(with(LocalDensity.current) { textFieldSize.width.toDp() })
        ) {
            for (item in intensities.keys) {
                DropdownMenuItem(text = { Text(text = item) }, onClick = {
                    selectedText = item

                    var intensity: Float = intensities.getOrDefault(item, 0.0f)
                    onClick(intensity)
                    expanded = false
                })
            }
        }
    }
}

@Composable
fun Calculation(male: Boolean, weight: Int, intensity: Float, setResult: (Int) -> Unit) {
    Button(
        onClick = {
            if (male) {
                setResult(((879 + 10.2 * weight) * intensity).toInt())
            } else {
                setResult(((795 + 7.18 * weight) * intensity).toInt())
            }
        },
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(text = "Calculate")
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalorieScreen() {
    var weightInput: String by remember { mutableStateOf("") }
    var genderInput: Boolean by remember { mutableStateOf(true) }
    var intensityInput: Float by remember { mutableStateOf(0.0f) }
    var result by remember { mutableStateOf(0) }

    Column(
        modifier = Modifier.padding(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Heading(title = "Calories calculator")
        WeightField(weightInput = weightInput, onValueChange = { weightInput = it })
        GenderChoices(male = genderInput, setGenderMale = { genderInput = it })
        IntensityList(onClick = { intensityInput = it })
        Text(
            text = result.toString(),
            color = MaterialTheme.colorScheme.secondary,
            fontWeight = FontWeight.Bold
        )
        Calculation(
            male = genderInput,
            weight = weightInput.toIntOrNull() ?: 0,
            intensity = intensityInput,
            setResult = { result = it })
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    CalorieScreen()
}