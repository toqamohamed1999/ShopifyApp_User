package eg.gov.iti.jets.shopifyapp_user.util

import android.app.AlertDialog
import android.content.Context
import android.net.ConnectivityManager
import android.os.Build
import android.util.Patterns
import androidx.annotation.RequiresApi
import eg.gov.iti.jets.shopifyapp_user.BuildConfig
import java.text.DecimalFormat
import eg.gov.iti.jets.shopifyapp_user.R
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

const val BASE_URL = "https://${BuildConfig.api_key}:${BuildConfig.api_password}@${BuildConfig.store_name}.myshopify.com/admin/api/${BuildConfig.api_version}/"
fun getTitleOfProduct(title: String): String {
    val parts = title.split("|").map { it.trim() }
    return if (parts.size >= 2) parts[1] else title
}
fun isConnected(context: Context): Boolean {
    val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    return connectivityManager.activeNetworkInfo != null && connectivityManager.activeNetworkInfo!!
        .isConnected
}

@RequiresApi(Build.VERSION_CODES.O)
fun convertDateTimeFormat(dateTimeString: String): String {
    val inputFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssXXX")
    val outputFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH:mm")

    val dateTime = LocalDateTime.parse(dateTimeString, inputFormat)
    return dateTime.format(outputFormat)
}

fun splitItemOrder(string: String): Pair<String, String> {
    val parts = string.split(")")
    val title = parts[0].trim()
    val id = parts[1].trim()
    return Pair(title, id)
}
fun String.isValidPassword(): Boolean {
    // Password should contain at least 8 characters, including one uppercase letter, one lowercase letter, one number, and one special character.
    val passwordRegex =
        Regex("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@\$!%*?&])[A-Za-z\\d@\$!%*?&]{8,}$")
    return passwordRegex.matches(this)
}

fun String.isValidEmail(): Boolean {
    return Patterns.EMAIL_ADDRESS.matcher(this).matches()
}
fun createAlertDialog(context: Context, msg : String): AlertDialog {
    val builder = AlertDialog.Builder(context)
    builder.setCancelable(false)
    builder.setView(R.layout.loading)

    return builder.create()
}

fun formatDecimal(decimal: Double): String {
    val decimalFormat = DecimalFormat("0.00")
    return decimalFormat.format(decimal)
}

