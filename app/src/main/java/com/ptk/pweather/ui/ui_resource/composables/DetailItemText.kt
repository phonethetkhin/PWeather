package com.ptk.pweather.ui.ui_resource.composables

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ir.kaaveh.sdpcompose.sdp

@Composable
fun DetailItemText(titleText: String, valueText: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.sdp)
    ) {
        ListItemText(titleText,modifier = Modifier.weight(0.5F))
        ListItemText(":",modifier = Modifier.weight(0.1F))
        ListItemText(valueText,modifier = Modifier.weight(1F))
    }
}