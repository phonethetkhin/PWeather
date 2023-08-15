package com.ptk.pweather.ui.ui_resource.composables

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ptk.pweather.ui.ui_resource.theme.DarkBlue
import ir.kaaveh.sdpcompose.sdp

@Composable
fun ListHeader(titleName: String) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = DarkBlue,
        ), modifier = Modifier.padding(start = 16.sdp, end = 16.sdp),
        shape = RoundedCornerShape(16.sdp)
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(12.sdp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            ListItemText(
                titleName, modifier = Modifier
                    .weight(1F)
                    .padding(start = 8.dp, end = 8.dp)
            )
            ListItemText(
                "Country", modifier = Modifier
                    .weight(1F)
                    .padding(start = 8.dp, end = 8.dp)
            )
            Spacer(modifier = Modifier.width(24.sdp))
        }
    }
}

