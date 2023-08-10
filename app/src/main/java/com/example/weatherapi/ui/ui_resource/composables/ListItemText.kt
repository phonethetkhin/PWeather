package com.example.weatherapi.ui.ui_resource.composables

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

@Composable
fun RowScope.ListItemText(text:String, modifier: Modifier = Modifier) {
    Text(
        text = text,
        modifier= modifier.weight(1F),
        fontSize = 16.sp,
        fontWeight = FontWeight.Bold,
        color = Color.White
    )
}
@Composable
fun ColumnScope.ListItemText(text:String, modifier: Modifier = Modifier) {
    Text(
        text = text,
        modifier= modifier.weight(1F),
        fontSize = 16.sp,
        fontWeight = FontWeight.Bold,
        color = Color.White
    )
}