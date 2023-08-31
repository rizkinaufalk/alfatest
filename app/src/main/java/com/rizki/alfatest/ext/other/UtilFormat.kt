package com.skollaedutech.skolla.ext.other

import android.content.res.Resources
import android.os.Build
import android.util.Base64
import com.rizki.alfatest.ext.constant.Const
import java.math.BigInteger
import java.nio.charset.StandardCharsets
import java.text.*
import java.util.*

fun Int.dpToPx(): Int {
    return this * Resources.getSystem().displayMetrics.density.toInt()
}

fun Float.getDistanceMerchant(): String {
    return DecimalFormat("#.##").format(this)
}

fun Int?.orZero():Int = this ?: 0

fun Int?.plus(value :Int?):Int {
    if(this == null) return 0
    if(value == null) return this
    return this + value
}

fun String.hexToByte(): ByteArray {
    return BigInteger(this, 16).toByteArray()
}

fun String.getDecodedJWT(): String {
    return try {
        val pieces = this.split("\\.".toRegex()).toTypedArray()
        val b64payload = pieces[1]
        String(
            Base64.decode(b64payload, Base64.URL_SAFE or Base64.NO_WRAP or Base64.NO_PADDING),
            StandardCharsets.UTF_8
        )
    } catch (e: Exception) {
        ""
    }
}

fun String.decodeBase64(): String {
    val decoded: String = try {
        val data = Base64.decode(this, Base64.DEFAULT)
        String(data, StandardCharsets.UTF_8)
    } catch (e: Exception) {
        ""
    }
    return decoded
}

// price format using italy locale because same preferences
fun Int.formatPrice(): String {
    return "Rp " + NumberFormat.getInstance(Locale.ITALY).format(this.toLong())
}

fun Double.formatPrice(): String {
    return "Rp " + NumberFormat.getInstance(Locale.ITALY).format(this)
}

fun Long.getDateFromTimestamp(): Date? {
    val sdf = SimpleDateFormat(Const.LOCAL_DATE_TIME_FORMAT)
    sdf.timeZone = TimeZone.getTimeZone("GMT+7")
    return sdf.format(this / 1000).getDateFromString(Const.LOCAL_DATE_TIME_FORMAT)
}


fun String.getDateFromString(format: String?): Date? {
    return try {
        val locale = Locale("in", "ID")
        val sdf = SimpleDateFormat(format, locale)
        sdf.parse(this)
    } catch (e: ParseException) {
        e.printStackTrace()
        null
    }
}

fun String.getDateFromString(): Date? {
    return this.getDateFromString(Const.SERVER_TIME_FORMAT)
}

fun String.convertDateFromServer(format: String): String {
    return this.getDateFromString()!!.getStringFormatDate(format)
}

fun String.convertDateStringToString(format: String, newFormat: String): String {
    return this.getDateFromString(format)!!.getStringFormatDate(newFormat)
}

fun Date.getStringFormatDate(format: String): String {
    val locale = Locale("in", "ID")
    val sdf = SimpleDateFormat(format, locale)
    sdf.timeZone = TimeZone.getTimeZone("GMT+07:00")
    return sdf.format(this)
}

fun Long.getStringFormatDate(format: String?): String {
    val c = Calendar.getInstance()
    c.timeInMillis = this * 1000L
    val d = c.time
    // SimpleDateFormat sdf = new SimpleDateFormat("dd MMM");
    val sdf = SimpleDateFormat(format)
    return sdf.format(d)
}

fun Date.getTimeFormat(): String {
    return this.getStringFormatDate("HH:mm:dd")
}

fun Date.getTimeFormatHM(): String {
    return this.getStringFormatDate("HH:mm")
}

fun Date.toCalendar(): Calendar? {
    val cal = Calendar.getInstance()
    cal.time = this
    return cal
}

fun String.toGmt7HM(): String {
    return try {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.mmmz")
        val inputDate = inputFormat.parse(this)
        val outputFormat: DateFormat = SimpleDateFormat("HH:mm", Locale.ENGLISH)
        outputFormat.timeZone = TimeZone.getTimeZone("GMT+07:00")
        outputFormat.format(inputDate)
    } catch (e: Exception) {
        e.printStackTrace()
        ""
    }
}

fun String.fromHMStoHM(): String {
    return this.substring(0, this.length - 3)
}

fun Long.getTimeFormatHM(): String {
    return Date(this).getStringFormatDate( "HH:mm")
}

fun getStartDateTime(startDate: Date?, startTime: String): Long {
    val startDateTime: String = if (startDate == null) {
        Date().getStringFormatDate(Const.SERVER_TIME_FORMAT) + " " + startTime
    } else {
        startDate.getStringFormatDate(Const.SERVER_TIME_FORMAT) + " " + startTime
    }
    return Objects.requireNonNull<Date>(
        startDateTime.getDateFromString(Const.TIMESTAMP_FORMAT)
    ).time
}

fun Int.getMinutesFormatFromSecond(): String {
    val hours = this / 3600
    val remains = this - hours * 3600
    val mins = remains / 60
    val seconds = remains - mins * 60
    val sum = mins + hours * 60
    return "$sum menit $seconds detik"
}

fun Int.getHoursFormatFromSecond(): String {
    val hours = this / 3600
    val remains = this - hours * 3600
    val mins = remains / 60
    val seconds = remains - mins * 60
    return String.format(Locale.US, "%d:%02d:%02d", hours, mins, seconds)
}

fun Long.getHoursFormatFromSecond(): String {
    val hours = this / 3600
    val remains = this - hours * 3600
    val mins = remains / 60
    val seconds = remains - mins * 60
    return String.format(Locale.US, "%d:%02d:%02d", hours, mins, seconds)
}

fun Int.getHoursLongFormatFromSecond(): String {
    val hours = this / 3600
    val remains = this - hours * 3600
    val mins = remains / 60
    val seconds = remains - mins * 60
    return String.format(Locale.US, "%1\$d jam %2$02d menit %3$02d detik", hours, mins, seconds)
}

fun Long.getHoursLongFormatFromMillis(): String {
    val second = this / 1000
    val hours = second / 3600
    val remains = second - hours * 3600
    val mins = remains / 60
    val seconds = remains - mins * 60
    return String.format(Locale.US, "%1\$d jam %2$02d menit %3$02d detik", hours, mins, seconds)
}

fun String.getDateFormatFromString(): Date? {
    return try {
        val inputFormat: SimpleDateFormat = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
        } else {
            SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ")
        }
        inputFormat.parse(this)
    } catch (e: Exception) {
        null
    }
}

fun String.getDateFormatFromString(formatDate: String): Date? {
    return try {
        val inputFormat = SimpleDateFormat(formatDate)
        inputFormat.parse(this)
    } catch (e: Exception) {
        null
    }
}