package com.example.weatherapi.util

import android.content.Context
import android.content.ContextWrapper
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import android.util.Patterns
import android.widget.Toast
import androidx.activity.ComponentActivity

fun String?.isEmailFormat() =
    !isNullOrEmpty() && Patterns.EMAIL_ADDRESS.matcher(this).matches()

fun Context.showToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun String?.nonNullString(): String {
    return this ?: "-"
}

fun String.stringToByteArray(): ByteArray {
    return Base64.decode(this, Base64.DEFAULT)
}

fun ByteArray.byteArrayToBitmap(): Bitmap {
    return BitmapFactory.decodeByteArray(this, 0, this.size)
}

fun Context.getComponentActivity(): ComponentActivity? {
    var currentContext = this
    while (currentContext is ContextWrapper) {
        if (currentContext is ComponentActivity) {
            return currentContext
        }
        currentContext = currentContext.baseContext
    }
    return null
}

