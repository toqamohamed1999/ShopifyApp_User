package eg.gov.iti.jets.shopifyapp_user.cart.presentation.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import eg.gov.iti.jets.shopifyapp_user.R
import eg.gov.iti.jets.shopifyapp_user.base.remote.AppRetrofit
import eg.gov.iti.jets.shopifyapp_user.cart.data.local.CartSharedPrefsOperations
import eg.gov.iti.jets.shopifyapp_user.cart.data.remote.DraftOrderAPIState
import eg.gov.iti.jets.shopifyapp_user.cart.data.remote.DraftOrderRemoteSourceImpl
import eg.gov.iti.jets.shopifyapp_user.cart.data.repo.CartRepositoryImpl
import eg.gov.iti.jets.shopifyapp_user.cart.domain.model.LineItem
import eg.gov.iti.jets.shopifyapp_user.cart.domain.remote.DraftOrderNetworkServices
import eg.gov.iti.jets.shopifyapp_user.cart.presentation.model.OrderPaymentDetails
import eg.gov.iti.jets.shopifyapp_user.cart.presentation.viewmodel.CartViewModel
import eg.gov.iti.jets.shopifyapp_user.cart.presentation.viewmodel.CartViewModelFactory
import eg.gov.iti.jets.shopifyapp_user.databinding.FragmentCartBinding
import eg.gov.iti.jets.shopifyapp_user.payment.presentation.ui.FragmentPaymentMethod
import eg.gov.iti.jets.shopifyapp_user.settings.data.local.UserSettings
import eg.gov.iti.jets.shopifyapp_user.util.Dialogs
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


class CartFragment : Fragment(),CartPaymentDataCollector,CartItemListener {
    private  var binding: FragmentCartBinding? = null
    private val viewModel: CartViewModel by lazy {
        val factory = CartViewModelFactory(CartRepositoryImpl(DraftOrderRemoteSourceImpl(AppRetrofit.retrofit.create(DraftOrderNetworkServices::class.java))
        ))
         ViewModelProvider(this,factory)[CartViewModel::class.java]
    }
    private lateinit var cartAdapter:CartProductAdapter
    private var productsIncCard:MutableList<LineItem> = mutableListOf()
    private var orderPaymentDetails: OrderPaymentDetails = OrderPaymentDetails()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCartBinding.inflate(inflater,container,false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        /*setUpArticleRecyclerView()
        observeData()
        setUpActions()
        viewModel.getCartProducts()*/
    }

    private fun observeData() {
        lifecycleScope.launch {
            viewModel.cartOrder.collectLatest {
                when(it){
                    is DraftOrderAPIState.Success->{
                        viewModel.setCartDraftOrder(it.order)
                        productsIncCard = mutableListOf()
                        productsIncCard.addAll(it.order?.draft_order?.line_items?: listOf())
                        cartAdapter.submitList(productsIncCard)
                    }
                    is DraftOrderAPIState.Error->{
                        Dialogs.SnakeToast(requireView(),it.errorMessage)
                    }
                    else->{
                        Dialogs.SnakeToast(requireView(),"Loading Cart...")
                    }
                }
            }
        }

    }

    private fun setUpActions() {
        binding?.cartCheckoutBtn?.setOnClickListener {
            //check network
            //SHOW info page
        }
    }

    private fun setUpArticleRecyclerView() {
        cartAdapter = CartProductAdapter(this)
        cartAdapter.submitList(productsIncCard)
        binding?.cartItemsRecyclerView?.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = cartAdapter
        }
    }

    override fun getOrderPaymentMethod(paymentMethod: Int) {
        if(paymentMethod==0)//cashOnDelivery
        {

        }else if(paymentMethod==1){//use GooglePay

        }
    }

    override fun getOrderPaymentDetails(shippingAddress: String, phone: String,saveForEveryTime:Boolean) {
        if(saveForEveryTime)
        {
            UserSettings.shippingAddress=shippingAddress
            UserSettings.phoneNumber =phone
            UserSettings.saveSettings()
        }
        orderPaymentDetails.paymentAddress = shippingAddress
        orderPaymentDetails.paymentPhone = phone
        val fragmentMethod = FragmentPaymentMethod()
        fragmentMethod.isCancelable = false
        fragmentMethod.show(childFragmentManager,null)
    }

    override fun increaseProductInCart(product: LineItem) {
        viewModel.updateProduct(1,product)
    }

    override fun decreaseProductInCart(product: LineItem) {
        viewModel.updateProduct(-1,product)
    }

    override fun removerProduct(product: LineItem) {
        viewModel.removeProductFromCart(product)
    }
}