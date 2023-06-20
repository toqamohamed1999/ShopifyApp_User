package eg.gov.iti.jets.shopifyapp_user

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.google.android.material.bottomnavigation.BottomNavigationView
import eg.gov.iti.jets.shopifyapp_user.base.local.database.ShopifyDatabase
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
        ShopifyDatabase.context = this
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        backButton = binding.backButton
        binding.toolbar.findViewById<ImageView>(R.id.shoppingCart_icon).setOnClickListener {
            navController.navigate(R.id.cartFragment)
        }
        binding.toolbar.findViewById<ImageView>(R.id.setting_icon).setOnClickListener {
            navController.navigate(R.id.settingsFragment)
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
                binding.settingIcon.visibility = View.GONE
            } else if (navDestination.id == R.id.cartFragment
            ) {
                bottomNav.visibility = View.GONE
                binding.shoppingCartIcon.visibility=View.GONE
                binding.backButton.visibility = View.VISIBLE
                binding.titleTextView.text = "Cart"
                binding.settingIcon.visibility = View.VISIBLE
            } else if (navDestination.id == R.id.subCategoryFragment
            ) {
                bottomNav.visibility = View.GONE
                binding.backButton.visibility = View.VISIBLE
                binding.titleTextView.text = "Category"
                binding.settingIcon.visibility = View.GONE
            } else if (navDestination.id == R.id.fragmentPaymentInfo
            ) {
                binding.titleTextView.text = "Order Information"
                binding.shoppingCartIcon.visibility=View.INVISIBLE
                bottomNav.visibility = View.GONE
                binding.backButton.visibility = View.VISIBLE
                binding.settingIcon.visibility = View.GONE

            } else if (navDestination.id == R.id.settingsFragment) {
                binding.titleTextView.text = "Setting"
                bottomNav.visibility = View.GONE
                binding.backButton.visibility = View.VISIBLE
                binding.settingIcon.visibility = View.GONE
                binding.shoppingCartIcon.visibility = View.VISIBLE
            } else if (navDestination.id == R.id.allOrdersFragment) {
                bottomNav.visibility = View.GONE
                binding.backButton.visibility = View.VISIBLE
                binding.titleTextView.text = "All Orders"
                binding.settingIcon.visibility = View.GONE
            } else if (navDestination.id == R.id.orderDetailsFragment
            ) {
                bottomNav.visibility = View.GONE
                binding.backButton.visibility = View.VISIBLE
                binding.titleTextView.text = "Order Details"
                binding.settingIcon.visibility = View.GONE
            } else if (navDestination.id == R.id.productDetailsFragment) {
                bottomNav.visibility = View.GONE
                binding.backButton.visibility = View.VISIBLE
                binding.titleTextView.text = "Product Details"
                binding.settingIcon.visibility = View.GONE
            } else {
                bottomNav.visibility = View.VISIBLE
                binding.toolbar.visibility = View.VISIBLE
                backButton.visibility = View.GONE
                binding.settingIcon.visibility = View.GONE
                binding.shoppingCartIcon.visibility = View.VISIBLE
                when (navDestination.id) {
                    R.id.homeFragment -> binding.titleTextView.text = "Home"
                    R.id.categoryFragment -> binding.titleTextView.text = "Category"
                    R.id.favoriteFragment -> binding.titleTextView.text = "Favorite"
                    R.id.profileFragment -> {
                        binding.titleTextView.text = "Profile"
                        binding.settingIcon.visibility = View.VISIBLE
                    }
                }
            }
        }
    }
}
