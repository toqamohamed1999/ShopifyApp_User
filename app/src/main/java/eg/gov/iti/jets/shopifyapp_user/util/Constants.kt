package eg.gov.iti.jets.shopifyapp_user.util

import android.os.Build
import androidx.annotation.RequiresApi
import eg.gov.iti.jets.shopifyapp_user.BuildConfig
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

const val BASE_URL = "https://${BuildConfig.api_key}:${BuildConfig.api_password}@${BuildConfig.store_name}.myshopify.com/admin/api/${BuildConfig.api_version}/"
fun getTitleOfProduct(title: String): String {
    val parts = title.split("|").map { it.trim() }
    return if (parts.size >= 2) parts[1] else title
}

@RequiresApi(Build.VERSION_CODES.O)
fun convertDateTimeFormat(dateTimeString: String): String {
    val inputFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssXXX")
    val outputFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH:mm")

    val dateTime = LocalDateTime.parse(dateTimeString, inputFormat)
    return dateTime.format(outputFormat)
}