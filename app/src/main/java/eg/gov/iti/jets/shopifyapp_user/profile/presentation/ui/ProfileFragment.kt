package eg.gov.iti.jets.shopifyapp_user.profile.presentation.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import eg.gov.iti.jets.shopifyapp_user.R
import eg.gov.iti.jets.shopifyapp_user.base.model.FavRoomPojo
import eg.gov.iti.jets.shopifyapp_user.base.model.orders.Order
import eg.gov.iti.jets.shopifyapp_user.databinding.FragmentProfileBinding
import eg.gov.iti.jets.shopifyapp_user.profile.data.model.OrderState
import eg.gov.iti.jets.shopifyapp_user.profile.data.remote.ProfileRemoteSource
import eg.gov.iti.jets.shopifyapp_user.profile.data.repo.ProfileRepoImp
import eg.gov.iti.jets.shopifyapp_user.profile.domain.local.OnWishListClick
import eg.gov.iti.jets.shopifyapp_user.profile.presentation.viewmodel.ProfileFactoryViewModel
import eg.gov.iti.jets.shopifyapp_user.profile.presentation.viewmodel.ProfileViewModel
import eg.gov.iti.jets.shopifyapp_user.settings.data.local.UserSettings
import eg.gov.iti.jets.shopifyapp_user.util.isConnected
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.collectLatest

class ProfileFragment : Fragment(), OnClickOrder, OnWishListClick {

    private lateinit var binding: FragmentProfileBinding
    private lateinit var orderAdapter: OrderAdapter
    private var orderList: List<Order> = emptyList()
    private lateinit var favAdapter: WishListAdapter
    private var favList: List<FavRoomPojo> = emptyList()

    private val viewModel: ProfileViewModel by lazy {
        val factory = ProfileFactoryViewModel(
            ProfileRepoImp.getInstance(ProfileRemoteSource())!!
        )
        ViewModelProvider(this, factory)[ProfileViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        favAdapter = WishListAdapter(
            ArrayList(), requireActivity(), this
        )
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (isConnected(requireContext())) {
            println("//////////////////////////////${UserSettings.userAPI_Id}")
            if (UserSettings.userAPI_Id.isEmpty()) {
                binding.noInternetContainer.visibility = View.GONE
                binding.fragmentContainer.visibility = View.GONE
                binding.loggedOutContainer.visibility = View.VISIBLE
                binding.btnlogin.setOnClickListener {
                    Navigation.findNavController(requireView())
                        .navigate(R.id.action_profileFragment_to_loginFragment)
                }
            } else {
                binding.noInternetContainer.visibility = View.GONE
                binding.fragmentContainer.visibility = View.VISIBLE
                binding.loggedOutContainer.visibility = View.GONE
                binding.txtWelcomeMessage.text =
                    "${resources.getString(R.string.welcome)} ${UserSettings.userName}"
                binding.wishListRecyclerView.adapter = favAdapter
                lifecycleScope.launch {
                    viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                        viewModel.favorites.collectLatest {
                            viewModel.list = it
                            favList = it.take(4)
                            if (it.isEmpty()) {
                                binding.txtMoreWishList.visibility = View.GONE
                                binding.animationEmptyWish.visibility = View.VISIBLE

                            } else if (it.size <= 4) {
                                binding.txtMoreWishList.visibility = View.GONE
                                binding.animationEmptyWish.visibility = View.GONE

                            } else {
                                binding.txtMoreWishList.visibility = View.VISIBLE
                                binding.animationEmptyWish.visibility = View.GONE
                            }
                            favAdapter.setProductList(favList)
                        }
                    }
                }
                binding.txtMoreWishList.setOnClickListener {
                    Navigation.findNavController(requireView())
                        .navigate(R.id.action_profileFragment_to_favoriteFragment)
                }
                //OrderAdapter and recyclerview
                viewModel.getOrders(UserSettings.userAPI_Id.toLong())
                orderAdapter = OrderAdapter(ArrayList(), requireActivity(), this)
                val layoutManager = LinearLayoutManager(requireContext())
                layoutManager.orientation = LinearLayoutManager.VERTICAL
                binding.ordersRecyclerView.layoutManager = layoutManager

                lifecycleScope.launch {
                    viewModel.orderState.collectLatest {
                        when (it) {
                            is OrderState.Loading -> {
                                binding.txtMoreOrders.visibility = View.GONE
                                binding.txtOrder.visibility = View.GONE
                            }
                            is OrderState.Success -> {
                                if (it.orderList.isEmpty()) {
                                    binding.txtMoreOrders.visibility = View.GONE
                                    binding.txtOrder.visibility = View.GONE
                                } else {
                                    binding.txtOrder.visibility = View.VISIBLE
                                    if (it.orderList.size > 2) {
                                        binding.txtMoreOrders.visibility = View.VISIBLE
                                        orderList = it.orderList.take(2)
                                    } else {
                                        binding.txtMoreOrders.visibility = View.GONE
                                        orderList = it.orderList
                                    }
                                    orderAdapter.setOrderList(orderList)
                                    binding.ordersRecyclerView.adapter = orderAdapter
                                }
                            }
                            else -> {
                                Log.i("TAG", "Errrrorrrr: $it")
                            }
                        }
                    }
                }

                binding.txtMoreOrders.setOnClickListener {
                    binding.root.findNavController()
                        .navigate(R.id.action_profileFragment_to_allOrdersFragment)
                }
//        binding.imageButtongotToSetting.setOnClickListener {
//            binding.root.findNavController().navigate(R.id.settingsFragment)
//        }
            }
        } else {
            binding.noInternetContainer.visibility = View.VISIBLE
            binding.fragmentContainer.visibility = View.GONE
            binding.loggedOutContainer.visibility = View.GONE

        }
    }

    override fun onClickOrder(order: Order) {
        Log.i("orrrrder", "${order.order_number}")
        val action =
            ProfileFragmentDirections.actionProfileFragmentToOrderDetailsFragment(order)
        binding.root.findNavController().navigate(action)
    }

    override fun onClickWishItem(product_Id: Long) {
        val action =
            ProfileFragmentDirections.actionProfileFragmentToProductDetailsFragment(product_Id)
        binding.root.findNavController().navigate(action)
    }

}