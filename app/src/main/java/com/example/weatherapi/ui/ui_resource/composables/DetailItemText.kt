package com.example.weatherapi.ui.ui_resource.composables

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun DetailItemText(titleText: String, valueText: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        ListItemText(titleText,modifier = Modifier.weight(1F))
        ListItemText(":",modifier = Modifier.weight(0.1F))
        ListItemText(valueText,modifier = Modifier.weight(1F))
    }
}