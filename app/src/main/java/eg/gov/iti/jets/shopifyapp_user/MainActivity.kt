package eg.gov.iti.jets.shopifyapp_user

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.google.android.material.bottomnavigation.BottomNavigationView
import eg.gov.iti.jets.shopifyapp_user.base.local.database.ShopifyDatabase
import eg.gov.iti.jets.shopifyapp_user.cart.presentation.ui.BadgeDrawable
import eg.gov.iti.jets.shopifyapp_user.databinding.ActivityMainBinding
import eg.gov.iti.jets.shopifyapp_user.settings.data.local.UserSettings
import eg.gov.iti.jets.shopifyapp_user.util.BadgeChanger
import eg.gov.iti.jets.shopifyapp_user.util.MyNetworkStatus
import eg.gov.iti.jets.shopifyapp_user.util.NetworkConnectivityObserver
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import eg.gov.iti.jets.shopifyapp_user.util.Dialogs
import eg.gov.iti.jets.shopifyapp_user.util.UserStates

class MainActivity : AppCompatActivity(), BadgeChanger {

    lateinit var bottomNav: BottomNavigationView
    private lateinit var binding: ActivityMainBinding
    lateinit var navController: NavController
    lateinit var backButton: ImageView
    lateinit var fragment : View
    var navDestination  : Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ShopifyDatabase.context = this
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        fragment = findViewById(R.id.nav_host_fragment)
        NetworkConnectivityObserver.initNetworkConnectivity(applicationContext)

        NetworkConnectivityObserver.observeNetworkConnection().onEach {
            if (it == MyNetworkStatus.Available) {
                binding.noInternetContainer.visibility = View.GONE
                navController.navigate(navDestination)
            } else {
                fragment.visibility = View.GONE
                binding.noInternetContainer.visibility = View.VISIBLE
//                if(navDestination == R.id.favoriteFragment){
//                    fragment.visibility = View.VISIBLE
//                    binding.noInternetContainer.visibility = View.GONE
//                }
            }
        }.launchIn(lifecycleScope)

        backButton = binding.backButton
        binding.toolbar.findViewById<ImageView>(R.id.shoppingCart_icon).setOnClickListener {

            if(UserStates.checkConnectionState(this))
            {
                if(UserSettings.userAPI_Id.isNotEmpty()){
                    navController.navigate(R.id.cartFragment)
                }else{
                    navController.navigate(R.id.loginFragment)
                }
            }else{
                Dialogs.SnakeToast(binding.root,getString(R.string.noInternetConnection))
            }
        }
        binding.toolbar.findViewById<ImageView>(R.id.setting_icon).setOnClickListener {
            if(UserStates.checkConnectionState(this))
            {
                if(UserSettings.userAPI_Id.isNotEmpty()){
                    navController.navigate(R.id.settingsFragment)
                }else{
                    navController.navigate(R.id.loginFragment)
                }
            }else{
                Dialogs.SnakeToast(binding.root,getString(R.string.noInternetConnection))
            }

        }
        binding.backButton.setOnClickListener {
            navController.popBackStack()
        }
        bottomNav = findViewById(R.id.bottom_navigation)
        navController = Navigation.findNavController(this, R.id.nav_host_fragment)
        NavigationUI.setupWithNavController(bottomNav, navController)
        setUpNavBottom(navController)
        if(UserSettings.userAPI_Id.isEmpty()){
            navController.navigate(R.id.loginFragment)
        }
    }

    override fun changeBadgeCartCount(count: Int) {
        val badge = BadgeDrawable(this)
        badge.setCount((count).toString())
        binding.toolbar.findViewById<ImageView>(R.id.shoppingCart_icon).foreground = badge
    }

    private fun setUpNavBottom(navController: NavController) {
        navController.addOnDestinationChangedListener { _, navDestination, _ ->
            this.navDestination = navDestination.id
            when (navDestination.id) {
                R.id.productsFragment -> {
                    bottomNav.visibility = View.GONE
                    binding.backButton.visibility = View.VISIBLE
                    binding.titleTextView.text = getString(R.string.products)
                    binding.settingIcon.visibility = View.GONE
                    binding.shoppingCartIcon.visibility = View.VISIBLE
                }
                R.id.cartFragment -> {
                    bottomNav.visibility = View.GONE
                    binding.shoppingCartIcon.visibility=View.GONE
                    binding.backButton.visibility = View.VISIBLE
                    binding.titleTextView.text = getString(R.string.cart)
                    binding.settingIcon.visibility = View.VISIBLE
                }
                R.id.subCategoryFragment -> {
                    bottomNav.visibility = View.GONE
                    binding.backButton.visibility = View.VISIBLE
                    binding.titleTextView.text = getString(R.string.category_header)
                    binding.settingIcon.visibility = View.GONE
                    binding.shoppingCartIcon.visibility = View.VISIBLE
                }
                R.id.fragmentPaymentInfo -> {
                     binding.titleTextView.text = getString(R.string.orderInfo)
                    binding.shoppingCartIcon.visibility=View.GONE
                    bottomNav.visibility = View.GONE
                    binding.backButton.visibility = View.VISIBLE
                    binding.settingIcon.visibility = View.GONE

                }
                R.id.settingsFragment -> {
                    binding.titleTextView.text = getString(R.string.settings)
                    bottomNav.visibility = View.GONE
                    binding.backButton.visibility = View.VISIBLE
                    binding.settingIcon.visibility = View.GONE
                    binding.shoppingCartIcon.visibility = View.VISIBLE
                }
                R.id.allOrdersFragment -> {
                    bottomNav.visibility = View.GONE
                    binding.backButton.visibility = View.VISIBLE
                    binding.titleTextView.text = getString(R.string.all_orders)
                    binding.settingIcon.visibility = View.GONE
                }
                R.id.orderDetailsFragment -> {
                    bottomNav.visibility = View.GONE
                    binding.backButton.visibility = View.VISIBLE
                    binding.titleTextView.text = getString(R.string.order_details)
                    binding.settingIcon.visibility = View.GONE
                }
                R.id.productDetailsFragment -> {
                    bottomNav.visibility = View.GONE
                    binding.backButton.visibility = View.VISIBLE
                    binding.titleTextView.text = getString(R.string.product_details)
                    binding.settingIcon.visibility = View.GONE
                    binding.shoppingCartIcon.visibility = View.VISIBLE
                }
                R.id.loginFragment->{
                    bottomNav.visibility = View.GONE
                    binding.toolbar.visibility = View.GONE

                }
                R.id.signUpFragment->{
                    bottomNav.visibility = View.GONE
                    binding.toolbar.visibility = View.GONE
                }
                else -> {
                    bottomNav.visibility = View.VISIBLE
                    binding.toolbar.visibility = View.VISIBLE
                    backButton.visibility = View.GONE
                    binding.settingIcon.visibility = View.GONE
                    binding.shoppingCartIcon.visibility = View.VISIBLE
                    when (navDestination.id) {
                        R.id.homeFragment -> binding.titleTextView.text = getString(R.string.home_header)
                        R.id.categoryFragment -> binding.titleTextView.text = getString(R.string.category_header)
                        R.id.favoriteFragment -> binding.titleTextView.text = getString(R.string.favorite_header)
                        R.id.profileFragment -> {
                            binding.titleTextView.text = getString(R.string.profile_header)
                            binding.settingIcon.visibility = View.VISIBLE
                        }
                    }
                }
            }
        }
    }
}
