package eg.gov.iti.jets.shopifyapp_user.profile.presentation.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import eg.gov.iti.jets.shopifyapp_user.base.model.orders.Order
import eg.gov.iti.jets.shopifyapp_user.databinding.FragmentProfileBinding
import eg.gov.iti.jets.shopifyapp_user.profile.data.model.OrderState
import eg.gov.iti.jets.shopifyapp_user.profile.data.remote.ProfileRemoteSource
import eg.gov.iti.jets.shopifyapp_user.profile.data.repo.ProfileRepoImp
import eg.gov.iti.jets.shopifyapp_user.profile.presentation.viewmodel.ProfileFactoryViewModel
import eg.gov.iti.jets.shopifyapp_user.profile.presentation.viewmodel.ProfileViewModel
import kotlinx.coroutines.launch
import eg.gov.iti.jets.shopifyapp_user.R
import eg.gov.iti.jets.shopifyapp_user.home.presentation.ui.HomeFragmentDirections
import eg.gov.iti.jets.shopifyapp_user.settings.data.local.UserSettings

class ProfileFragment : Fragment(), OnClickOrder {

    private lateinit var binding: FragmentProfileBinding
    private lateinit var orderAdapter: OrderAdapter
    private var orderList: List<Order> = emptyList()

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
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //OrderAdapter and recyclerview
        viewModel.getOrders(UserSettings.userAPI_Id.toLong())
        orderAdapter = OrderAdapter(ArrayList(), requireActivity(), this)
        val layoutManager = LinearLayoutManager(requireContext())
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        binding.ordersRecyclerView.layoutManager = layoutManager

        lifecycleScope.launch {
            viewModel.orderState.collect {
                when (it) {
                    is OrderState.Loading -> {
                    }
                    is OrderState.Success -> {
                        orderList = if (it.orderList.size >= 2) {
                            it.orderList.take(2)
                        } else {
                            it.orderList
                        }
                        orderAdapter.setOrderList(orderList)
                        binding.ordersRecyclerView.adapter = orderAdapter
                    }
                    else -> {
                        Log.i("TAG", "Errrrorrrr: $it")
                    }
                }
            }
        }

        binding.txtMoreOrders.setOnClickListener {
            binding.root.findNavController().navigate(R.id.action_profileFragment_to_allOrdersFragment)
        }
    }

    override fun onClickOrder(order: Order) {
        Log.i("orrrrder", "${order.order_number}")
        val action = ProfileFragmentDirections.actionProfileFragmentToOrderDetailsFragment(order)
        binding.root.findNavController().navigate(action)
    }

}