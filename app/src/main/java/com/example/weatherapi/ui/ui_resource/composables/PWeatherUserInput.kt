package com.example.weatherapi.ui.ui_resource.composables

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RowScope.PWeatherUserInput(
    value: String,
    cityNameEmpty: Boolean = false,
    onValueChange: (String) -> Unit
) {
    TextField(
        value = value,
        onValueChange = onValueChange,

        modifier = Modifier
            .weight(1F),
        colors = TextFieldDefaults.textFieldColors(
            containerColor = Color.White,

            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            cursorColor = Color.Black
        ),
        shape = RoundedCornerShape(32.dp),

        textStyle = LocalTextStyle.current.copy(
            fontSize = 16.sp,
            color = Color.Black,
            fontWeight = FontWeight.Bold,
        ),
        placeholder = {
            Text(
                "City name",
                fontSize = 16.sp,
            )
        },
        supportingText = {
            if (cityNameEmpty) {
                Text(
                    text = "City Name must not be empty!!!",
                    fontSize = 12.sp,
                    color = Color.Red,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }, trailingIcon = {
            if (cityNameEmpty) {
                Icon(Icons.Filled.Error, "error", tint = MaterialTheme.colorScheme.error)
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RowScope.PWeatherUserInputTrailing(
    value: String,
    dateEmpty: Boolean,
    dateClicked: () -> Unit
) {
    TextField(
        value = value,
        onValueChange = {},
        modifier = Modifier
            .weight(1F),
        readOnly = true,
        colors = TextFieldDefaults.textFieldColors(
            containerColor = Color.White,

            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            cursorColor = Color.Black
        ),
        shape = RoundedCornerShape(32.dp),

        textStyle = LocalTextStyle.current.copy(fontSize = 16.sp),
        trailingIcon = {
            IconButton(onClick = dateClicked) {
                Icon(imageVector = Icons.Filled.CalendarMonth, contentDescription = "DateIcon")
            }
        },
        keyboardOptions = KeyboardOptions(),
        placeholder = {
            Text(
                text = "Date",
                fontSize = 16.sp,
                modifier = Modifier.padding(start = 8.dp)
            )
        },
        supportingText = {
            if (dateEmpty) {
                Text(
                    text = "Date must not be empty!!!",
                    fontSize = 12.sp,
                    color = Color.Red,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },

        )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ColumnScope.PWeatherUserInput(
    placeHolder: String,
    cityNameEmpty: Boolean,
    modifier: Modifier = Modifier
) {
    TextField(
        value = "",
        onValueChange = { },
        modifier = modifier,
        colors = TextFieldDefaults.textFieldColors(
            containerColor = Color.White,

            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            cursorColor = Color.Black
        ),
        shape = RoundedCornerShape(32.dp),

        textStyle = LocalTextStyle.current.copy(fontSize = 16.sp),
        placeholder = {
            Text(
                placeHolder,
                color = Color.Black,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(start = 8.dp)
            )
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ColumnScope.PWeatherUserInputTrailing(
    placeHolder: String,
    modifier: Modifier = Modifier,
) {
    TextField(
        value = "",
        onValueChange = { },
        modifier = modifier,
        colors = TextFieldDefaults.textFieldColors(
            containerColor = Color.White,

            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            cursorColor = Color.Black
        ),
        shape = RoundedCornerShape(32.dp),

        textStyle = LocalTextStyle.current.copy(fontSize = 16.sp),
        visualTransformation = if (true) VisualTransformation.None else PasswordVisualTransformation(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        trailingIcon = {
            val image = if (true)
                Icons.Filled.Visibility
            else Icons.Filled.VisibilityOff

            // Localized description for accessibility services
            val description = if (true) "Hide password" else "Show password"

            // Toggle button to hide or display password
            IconButton(onClick = { }) {
                Icon(imageVector = image, description)
            }
        },
        placeholder = {
            Text(
                text = placeHolder,
                color = Color.Black,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(start = 8.dp)
            )
        },
    )

    // TODO: Replace true with actual value
}