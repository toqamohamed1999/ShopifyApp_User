package eg.gov.iti.jets.shopifyapp_user

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.google.android.material.bottomnavigation.BottomNavigationView
import eg.gov.iti.jets.shopifyapp_user.cart.presentation.ui.BadgeDrawable
import eg.gov.iti.jets.shopifyapp_user.databinding.ActivityMainBinding
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
        binding.toolbar.findViewById<ImageView>(R.id.shoppingCart_icon).setOnClickListener {
         navController.navigate(R.id.cartFragment)
        }
        binding.backButton.setOnClickListener {
            navController.popBackStack()
        }
        bottomNav = findViewById(R.id.bottom_navigation)
        navController = Navigation.findNavController(this, R.id.nav_host_fragment)
        NavigationUI.setupWithNavController(bottomNav, navController)
        setUpNavBottom(navController)

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
            }else if (navDestination.id == R.id.cartFragment
            ) {
                bottomNav.visibility = View.GONE
                binding.toolbar.visibility = View.GONE
                binding.backButton.visibility = View.VISIBLE
                binding.titleTextView.text = "Cart"
            } else if (navDestination.id == R.id.subCategoryFragment
            ) {
                bottomNav.visibility = View.GONE
                binding.backButton.visibility = View.VISIBLE
                binding.titleTextView.text = "Category"
            }else if (navDestination.id == R.id.fragmentPaymentInfo
            ) {
                binding.toolbar.visibility = View.GONE
                bottomNav.visibility = View.GONE
                binding.backButton.visibility = View.VISIBLE

            }else if (navDestination.id == R.id.settingsFragment
            ) {
                binding.toolbar.visibility = View.GONE
                bottomNav.visibility = View.GONE
                binding.backButton.visibility = View.VISIBLE

            }else if (navDestination.id == R.id.fragmentLocationDetector
            ) {
                bottomNav.visibility = View.GONE
                binding.backButton.visibility = View.VISIBLE
            } else {
                bottomNav.visibility = View.VISIBLE
                binding.toolbar.visibility = View.VISIBLE
                backButton.visibility = View.GONE
                when (navDestination.id) {
                    R.id.homeFragment -> binding.titleTextView.text = "Home"
                    R.id.categoryFragment -> binding.titleTextView.text = "Category"
                    R.id.favoriteFragment -> binding.titleTextView.text = "Favorite"
                    R.id.profileFragment -> binding.titleTextView.text = "Profile"
                }
            }
        }
    }
}
