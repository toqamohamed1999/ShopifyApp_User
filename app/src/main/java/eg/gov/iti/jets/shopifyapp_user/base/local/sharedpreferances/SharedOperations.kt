package eg.gov.iti.jets.shopifyapp_user.base.local.sharedpreferances

import android.content.Context
import android.content.SharedPreferences
 class SharedOperations private constructor(context:Context) {
     private var sharedPrefs:SharedPreferences

    init {
        sharedPrefs = context.applicationContext.getSharedPreferences("CurrentUser",Context.MODE_PRIVATE)
    }
     companion object{
         private var prefs:SharedOperations? = null
         fun initPrefs(context: Context){
             prefs = SharedOperations(context)
         }
         fun getInstance():SharedPreferences{
            return prefs!!.sharedPrefs
         }
     }
}