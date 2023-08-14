package com.ptk.pweather.util

import android.content.Context
import android.provider.Settings.Global.getString
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.ptk.pweather.R
import java.math.BigDecimal
import java.math.RoundingMode
import java.text.SimpleDateFormat

/*fun calculateDistanceFromYangon(lat1: Double, lon1: Double, lat2: Double, lon2: Double): Double? {
    //Haversine Formula
    return try {
        val earthRadius = 6378.137; // Radius of earth in KM
        val dLat = lat2 * Math.PI / 180 - lat1 * Math.PI / 180;
        val dLon = lon2 * Math.PI / 180 - lon1 * Math.PI / 180;
        val a = sin(dLat / 2) * sin(dLat / 2) +
                cos(lat1 * Math.PI / 180) * cos(lat2 * Math.PI / 180) *
                sin(dLon / 2) * sin(dLon / 2);
        val c = 2 * atan2(sqrt(a), sqrt(1 - a));
        val d = earthRadius * c;
        return d * 1000; // meters
    } catch (e: Exception) {
        null
    }

}*/
fun calculateDistanceFromYangon(
    lat1: Double,
    lon1: Double,
    lat2: Double,
    lon2: Double
): BigDecimal? {
    return try {
        val results = FloatArray(1)
        android.location.Location.distanceBetween(lat1, lon1, lat2, lon2, results)
//        kotlin.math.ceil(results[0].toDouble())
//            results[0].toDouble().toBigDecimal().setScale(2, RoundingMode.CEILING).toDouble()
        results[0].toBigDecimal().setScale(2, RoundingMode.CEILING)
    } catch (e: Exception) {
        null
    }

}

fun calculateTimeDifference(time1: String, time2: String): String? {
    return try {
        val simpleDateFormat = SimpleDateFormat("hh:mm a")
        val date1 = simpleDateFormat.parse(time1)
        val date2 = simpleDateFormat.parse(time2)

        val diff = date1.time - date2.time;

        val diffSeconds = diff / 1000 % 60;
        val diffMinutes = diff / (60 * 1000) % 60;
        val diffHours = diff / (60 * 60 * 1000) % 24;
        val diffDays = diff / (24 * 60 * 60 * 1000);

        "$diffHours hours $diffMinutes minutes"
    } catch (e: Exception) {
        null
    }
}

fun getGoogleLoginAuth(context: Context): GoogleSignInClient {
    val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestEmail()
        .requestId()
        .requestProfile()
        .build()
    return GoogleSignIn.getClient(context, gso)
}