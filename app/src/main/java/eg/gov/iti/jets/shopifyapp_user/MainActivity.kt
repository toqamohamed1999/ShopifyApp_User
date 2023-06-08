package eg.gov.iti.jets.shopifyapp_user

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toolbar
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import eg.gov.iti.jets.shopifyapp_user.categories.presentation.CategoryFragment
import eg.gov.iti.jets.shopifyapp_user.databinding.ActivityMainBinding
import eg.gov.iti.jets.shopifyapp_user.home.presentation.ui.HomeFragment

class MainActivity : AppCompatActivity() {

    lateinit var bottomNav : BottomNavigationView
    private lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        //hide back button
        binding.backButton.visibility = View.GONE

        loadFragment(HomeFragment())
        bottomNav = findViewById(R.id.bottom_navigation) as BottomNavigationView
        bottomNav.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.home -> {
                    loadFragment(HomeFragment())
                    binding.titleTextView.text = "Home"
                    true
                }
                R.id.category -> {
                    loadFragment(CategoryFragment())
                    binding.titleTextView.text = "Category"
                    true
                }
                R.id.favorite -> {
                    //loadFragment(FavoriteFragment())
                    binding.titleTextView.text = "Favorite"
                    true
                }
                R.id.profile -> {
                    //loadFragment(ProfileFragment())
                    binding.titleTextView.text = "Profile"
                    true
                }
                else -> false
            }
        }
    }
    private  fun loadFragment(fragment: Fragment){
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container,fragment)
        transaction.commit()
    }

}