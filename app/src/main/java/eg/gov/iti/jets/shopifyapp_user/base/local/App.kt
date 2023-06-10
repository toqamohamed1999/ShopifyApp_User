package eg.gov.iti.jets.shopifyapp_user.base.local

import android.app.Application
import eg.gov.iti.jets.shopifyapp_user.base.local.sharedpreferances.SharedOperations

class App:Application(){
    override fun onCreate() {
        super.onCreate()
        SharedOperations.initPrefs(applicationContext)
    }
}