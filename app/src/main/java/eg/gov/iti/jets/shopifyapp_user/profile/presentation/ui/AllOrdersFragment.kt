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
import eg.gov.iti.jets.shopifyapp_user.databinding.FragmentAllOrdersBinding
import eg.gov.iti.jets.shopifyapp_user.profile.data.model.OrderState
import eg.gov.iti.jets.shopifyapp_user.profile.data.remote.ProfileRemoteSource
import eg.gov.iti.jets.shopifyapp_user.profile.data.repo.ProfileRepoImp
import eg.gov.iti.jets.shopifyapp_user.profile.presentation.viewmodel.ProfileFactoryViewModel
import eg.gov.iti.jets.shopifyapp_user.profile.presentation.viewmodel.ProfileViewModel
import eg.gov.iti.jets.shopifyapp_user.settings.data.local.UserSettings
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class AllOrdersFragment : Fragment(), OnClickOrder {

    private lateinit var binding: FragmentAllOrdersBinding
    private lateinit var orderAdapter: OrderAdapter

    private val viewModel: ProfileViewModel by lazy {
        val factory = ProfileFactoryViewModel(
            ProfileRepoImp.getInstance(ProfileRemoteSource())!!
        )
        ViewModelProvider(this, factory)[ProfileViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAllOrdersBinding.inflate(inflater, container, false)
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
            viewModel.orderState.collectLatest {
                when (it) {
                    is OrderState.Loading -> {
                        binding.progressBar.visibility = View.VISIBLE
                    }
                    is OrderState.Success -> {
                        binding.progressBar.visibility = View.GONE
                        orderAdapter.setOrderList(it.orderList)
                        binding.ordersRecyclerView.adapter = orderAdapter
                    }
                    else -> {
                        Log.i("TAG", "Errrrorrrr: $it")
                    }
                }
            }
        }
    }

    override fun onClickOrder(order: Order) {
        val action = AllOrdersFragmentDirections.actionAllOrdersFragmentToOrderDetailsFragment(order)
        binding.root.findNavController().navigate(action)
    }

}