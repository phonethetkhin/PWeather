package com.example.weatherapi.ui.ui_resource.composables

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun PWeatherButton(
    text: String,
    textColor: Color,
    enable:Boolean = true,
    buttonColor: ButtonColors,
    modifier: Modifier = Modifier,
    buttonClick: () -> Unit,
) {
    Button(
        onClick = buttonClick,
        modifier = modifier,
        enabled = enable,
        shape = RoundedCornerShape(8.dp),
        colors = buttonColor

    ) {
        Text(text, fontSize = 16.sp, fontWeight = FontWeight.Bold, color = textColor)
    }
}