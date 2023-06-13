package eg.gov.iti.jets.shopifyapp_user

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import eg.gov.iti.jets.shopifyapp_user.cart.presentation.ui.BadgeDrawable
import eg.gov.iti.jets.shopifyapp_user.categories.presentation.CategoryFragment
import eg.gov.iti.jets.shopifyapp_user.databinding.ActivityMainBinding
import eg.gov.iti.jets.shopifyapp_user.home.presentation.ui.HomeFragment
import eg.gov.iti.jets.shopifyapp_user.util.BadgeChanger

class MainActivity : AppCompatActivity(), BadgeChanger {

    lateinit var bottomNav: BottomNavigationView
    private lateinit var binding: ActivityMainBinding
    lateinit var navController: NavController
    lateinit var backButton: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        backButton = binding.backButton

        bottomNav = findViewById(R.id.bottom_navigation)
        navController = Navigation.findNavController(this, R.id.nav_host_fragment)
        NavigationUI.setupWithNavController(bottomNav, navController)
        setUpNavBottom(navController)

        //Title of Fragments
        bottomNav.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.homeFragment -> {
                    binding.titleTextView.text = "Home"
                    true
                }
                R.id.categoryFragment -> {
                    binding.titleTextView.text = "Category"
                    true
                }
                R.id.favorite -> {
                    binding.titleTextView.text = "Favorite"
                    true
                }
                R.id.profile -> {
                    binding.titleTextView.text = "Profile"
                    true
                }
                else -> false
            }
        }
    }

    override fun changeBadgeCartCount(count: Int) {
        val badge = BadgeDrawable(this)
        badge.setCount((count).toString())
        binding.toolbar.findViewById<ImageView>(R.id.shoppingCart_icon).foreground = badge
    }

    private fun setUpNavBottom(navController: NavController) {
        navController.addOnDestinationChangedListener { _, navDestination, _ ->
            if (navDestination.id == R.id.productsFragment
            ) {
                bottomNav.visibility = View.GONE
                binding.backButton.visibility = View.VISIBLE
                binding.titleTextView.text = "Products"
            } else {
                bottomNav.visibility = View.VISIBLE
                backButton.visibility = View.GONE
                when (navDestination.id) {
                    R.id.homeFragment -> binding.titleTextView.text = "Home"
                    R.id.categoryFragment -> binding.titleTextView.text = "Category"
                    R.id.favorite -> binding.titleTextView.text = "Favorite"
                    R.id.profile -> binding.titleTextView.text = "Profile"
                }
            }
        }
    }
}
//hide back button
//        binding.backButton.visibility = View.GONE
//
//        loadFragment(HomeFragment())
//        bottomNav = findViewById(R.id.bottom_navigation)
//
//        navController = Navigation.findNavController(this, R.id.nav_host_fragment)
//        NavigationUI.setupWithNavController(bottomNav, navController)
//
//        bottomNav.setOnItemSelectedListener {
//            when (it.itemId) {
//                R.id.home -> {
//                    loadFragment(HomeFragment())
//                    binding.titleTextView.text = "Home"
//                    true
//                }
//                R.id.category -> {
//                    loadFragment(CategoryFragment())
//                    binding.titleTextView.text = "Category"
//                    true
//                }
//                R.id.favorite -> {
//                    //loadFragment(FavoriteFragment())
//                    binding.titleTextView.text = "Favorite"
//                    true
//                }
//                R.id.profile -> {
//                    //loadFragment(ProfileFragment())
//                    binding.titleTextView.text = "Profile"
//                    true
//                }
//                else -> false
//            }
//        }
//    }
//    private  fun loadFragment(fragment: Fragment){
//        val transaction = supportFragmentManager.beginTransaction()
//        transaction.replace(R.id.nav_host_fragment,fragment)
//        transaction.commit()
//    }
//}