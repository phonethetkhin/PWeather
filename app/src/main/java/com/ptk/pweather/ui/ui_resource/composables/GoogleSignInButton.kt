package com.ptk.pweather.ui.ui_resource.composables

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import com.ptk.pweather.R
import com.ptk.pweather.ui.ui_resource.theme.Blue
import ir.kaaveh.sdpcompose.sdp
import ir.kaaveh.sdpcompose.ssp

@ExperimentalMaterial3Api
@Composable
fun GoogleSignInButton(
    text: String = "Sign In with Google",
    loadingText: String = "Signing in...",
    icon: Painter = painterResource(id = R.drawable.google_icon),
    isLoading: Boolean = false,
    shape: Shape = RoundedCornerShape(32.sdp),
    borderColor: Color = Color.LightGray,
    backgroundColor: Color = Color.White,
    progressIndicatorColor: Color = Blue,
    onClick: () -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.sdp, end = 16.sdp)
            .clickable(
                enabled = !isLoading,
                onClick = onClick
            ),
        shape = shape,
        border = BorderStroke(width = 1.sdp, color = borderColor),
        color = backgroundColor
    ) {
        Row(
            modifier = Modifier
                .padding(
                    start = 12.sdp,
                    end = 16.sdp,
                    top = 12.sdp,
                    bottom = 12.sdp
                ),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
        ) {
            Icon(
                painter = icon,
                contentDescription = "SignInButton",
                tint = Color.Unspecified,
                modifier = Modifier.size(32.sdp)
            )
            Spacer(modifier = Modifier.width(8.sdp))

            Text(
                text = if (isLoading) loadingText else text,
                color = Blue,
                fontSize = 14.ssp,
                fontWeight = FontWeight.Bold
            )
            if (isLoading) {
                Spacer(modifier = Modifier.width(14.sdp))
                CircularProgressIndicator(
                    modifier = Modifier
                        .height(14.sdp)
                        .width(14.sdp),
                    strokeWidth = 2.sdp,
                    color = progressIndicatorColor
                )
            }
        }
    }
}