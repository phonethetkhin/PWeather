package com.example.weatherapi.ui.ui_resource.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.weatherapi.ui.ui_resource.theme.DarkBlue

@Composable
fun ListHeader() {
    Row(
        Modifier
            .fillMaxWidth()
            .background(DarkBlue)
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        ListItemText("Name")
        ListItemText("Country")
    }
}

