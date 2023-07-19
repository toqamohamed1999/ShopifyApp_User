package eg.gov.iti.jets.shopifyapp_user.splash.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import eg.gov.iti.jets.shopifyapp_user.MainActivity
import eg.gov.iti.jets.shopifyapp_user.databinding.SplashActivityBinding
import eg.gov.iti.jets.shopifyapp_user.settings.data.local.UserSettings
import kotlinx.coroutines.*

class SplashActivity:AppCompatActivity() {
   private lateinit var splashActivityBinding: SplashActivityBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        splashActivityBinding = SplashActivityBinding.inflate(layoutInflater)
        setContentView(splashActivityBinding.root)
        splashActivityBinding.splashAnim.playAnimation()
        CoroutineScope(Dispatchers.IO).launch {
                UserSettings.loadSettings()
                delay(3000)
            withContext(Dispatchers.Unconfined) {
                val intent = Intent(this@SplashActivity,MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }
}