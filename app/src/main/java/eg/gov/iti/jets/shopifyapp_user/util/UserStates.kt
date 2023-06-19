package eg.gov.iti.jets.shopifyapp_user.util

import android.content.Context
import android.net.ConnectivityManager

object UserStates {
    fun checkConnectionState(context: Context): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val nInfo = cm.activeNetworkInfo
        return nInfo != null && nInfo.isAvailable && nInfo.isConnected
    }
}