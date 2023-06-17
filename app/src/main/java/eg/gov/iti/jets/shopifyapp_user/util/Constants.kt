package eg.gov.iti.jets.shopifyapp_user.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import eg.gov.iti.jets.shopifyapp_user.BuildConfig

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