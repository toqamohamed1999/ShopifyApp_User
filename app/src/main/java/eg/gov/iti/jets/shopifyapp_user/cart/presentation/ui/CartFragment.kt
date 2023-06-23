package eg.gov.iti.jets.shopifyapp_user.cart.presentation.ui

import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import eg.gov.iti.jets.shopifyapp_user.R
import eg.gov.iti.jets.shopifyapp_user.base.remote.AppRetrofit
import eg.gov.iti.jets.shopifyapp_user.cart.data.model.LineItem
import eg.gov.iti.jets.shopifyapp_user.cart.data.remote.DraftOrderAPIState
import eg.gov.iti.jets.shopifyapp_user.cart.data.remote.DraftOrderRemoteSourceImpl
import eg.gov.iti.jets.shopifyapp_user.cart.data.remote.VariantRemoteSourceImpl
import eg.gov.iti.jets.shopifyapp_user.cart.data.repo.CartRepositoryImpl
import eg.gov.iti.jets.shopifyapp_user.cart.domain.remote.DraftOrderNetworkServices
import eg.gov.iti.jets.shopifyapp_user.cart.presentation.viewmodel.CartViewModel
import eg.gov.iti.jets.shopifyapp_user.cart.presentation.viewmodel.CartViewModelFactory
import eg.gov.iti.jets.shopifyapp_user.databinding.FragmentCartBinding
import eg.gov.iti.jets.shopifyapp_user.settings.data.local.UserSettings
import eg.gov.iti.jets.shopifyapp_user.util.BadgeChanger
import eg.gov.iti.jets.shopifyapp_user.util.Dialogs
import eg.gov.iti.jets.shopifyapp_user.util.UserStates
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlin.math.roundToInt


class CartFragment : Fragment(),CartItemListener {
    private  var binding: FragmentCartBinding? = null
    private var totalPrice=0.0
    var falg=0
    private val viewModel: CartViewModel by lazy {
        val factory = CartViewModelFactory(CartRepositoryImpl(DraftOrderRemoteSourceImpl(AppRetrofit.retrofit.create(DraftOrderNetworkServices::class.java))
        ,VariantRemoteSourceImpl()))
         ViewModelProvider(this,factory)[CartViewModel::class.java]
    }
    private lateinit var cartAdapter:CartProductAdapter
    private var productsIncCard:MutableList<LineItem> = mutableListOf()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCartBinding.inflate(inflater,container,false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
Log.e("","................${UserSettings.userName}.................${UserSettings.cartDraftOrderId}........................................")
        setUpArticleRecyclerView()
        observeData()
        setUpActions()
        viewModel.getCartProducts()
    }

    private fun observeData() {
        lifecycleScope.launch {
            viewModel.cartOrder.collectLatest {
                when(it){
                    is DraftOrderAPIState.Success->{
                        binding?.cartAnim?.cancelAnimation()
                        binding?.cartAnim?.visibility = View.INVISIBLE
                        productsIncCard = mutableListOf()
                        productsIncCard.addAll(it.order?.draft_order?.line_items?.takeLast(((it.order.draft_order?.line_items?.size?:1)-1))?: listOf())
                        if(productsIncCard.size==0) {
                            binding?.cartAnim?.setAnimation("error_cart.json")
                            binding?.cartAnim?.visibility = View.VISIBLE
                            binding?.cartAnim?.repeatCount=0
                            binding?.cartAnim?.playAnimation()
                            Dialogs.SnakeToast(requireView(),"Empty Cart")
                        }
                        calcTotalPrice()
                        cartAdapter.submitList(productsIncCard)
                        cartAdapter.notifyDataSetChanged()
                    }
                    is DraftOrderAPIState.Error->{
                        Log.e("",it.errorMessage)
                        binding?.cartAnim?.setAnimation("error_cart.json")
                        binding?.cartAnim?.visibility = View.VISIBLE
                        binding?.cartAnim?.repeatCount=0
                        binding?.cartAnim?.playAnimation()
                        Dialogs.SnakeToast(requireView(),"Error Can't Load Cart")
                    }
                    else->{
                        binding?.cartAnim?.visibility = View.VISIBLE
                        binding?.cartAnim?.playAnimation()
                    }
                }
            }
        }
        lifecycleScope.launch {
            viewModel.isAvailable.observe(viewLifecycleOwner, Observer {
                when(it.first){
                    1->{
                        binding?.cartItemsRecyclerView?.background = ResourcesCompat.getDrawable(resources,R.drawable.cart_item_available,null)
                        viewModel.crearIsAvailable()
                    }
                    2->{
                        Dialogs.SnakeToast(requireView(),"No More Available Items In Store !!")
                        viewModel.crearIsAvailable()
                    }
                    3->{
                        binding?.cartItemsRecyclerView?.background =
                            ResourcesCompat.getDrawable(resources,R.drawable.cart_item_no_available,null)

                        this@CartFragment.falg+=1
                        viewModel.crearIsAvailable()
                    }
                    else->{

                    }
                }
            })
        }
    }

    override fun onStop() {
        super.onStop()
        viewModel.clearOrder()
    }

    private fun calcTotalPrice() {
        var total = 0.0
        UserSettings.cartQuantity = 0
        binding?.cartCheckoutBtn?.isEnabled = productsIncCard.size != 0
        productsIncCard.forEach {
            total += it.price.toDouble()*it.quantity
            UserSettings.cartQuantity+=it.quantity
        }
        totalPrice =(total*UserSettings.currentCurrencyValue*100)/100.0
        UserSettings.saveSettings()
        (requireActivity() as BadgeChanger).changeBadgeCartCount(UserSettings.cartQuantity)
        binding?.cartTotalPrice?.text = "Total Price : ${((total*UserSettings.currentCurrencyValue*100).roundToInt()/100.0)} ${UserSettings.currencyCode}"
    }

    private fun setUpActions() {
        binding?.cartCheckoutBtn?.setOnClickListener {
            if(UserStates.checkConnectionState(requireContext())) {
                    if(productsIncCard.size>0) {

                        for(i in productsIncCard.indices){
                            viewModel.checkAllAvailability(productsIncCard[i],i)
                        }
                        if(this@CartFragment.falg==0) {
                            val action =
                                CartFragmentDirections.actionCartFragmentToFragmentPaymentInfo(
                                    totalPrice.toString()
                                )
                            binding?.root?.findNavController()?.navigate(action)
                        }else{
                            Dialogs.SnakeToast(requireView(),"There is some products are not Available")
                            this@CartFragment.falg = 0
                        }
                    }else{
                       Dialogs.SnakeToast(requireView(),"Cart Empty !!")
                    }
            }else{
                Dialogs.SnakeToast(requireView(),getString(R.string.networkNotAvilable))
            }
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

    override fun increaseProductInCart(product: LineItem) {
        viewModel.updateProduct(1,product,productsIncCard.indexOf(product))
    }

    override fun decreaseProductInCart(product: LineItem) {
        val index= productsIncCard.indexOf(product)
        if(product.quantity==1)
        {
            Dialogs.alertDialogBuilder(requireContext(),"Alert","Delete This Item ?")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton("Yes") { _: DialogInterface?, _: Int ->
                    viewModel.updateProduct(-1,product,index)
                }
                .setNegativeButton("No") { i: DialogInterface?, _: Int ->
                    i?.dismiss()
                }.create().show()
        }else{
            viewModel.updateProduct(-1,product,index)
        }
    }

    override fun removerProduct(product: LineItem) {
        viewModel.removeProductFromCart(product)
    }

    override fun gotoDetails(productId: String) {
        binding?.root?.findNavController()?.navigate(CartFragmentDirections.actionCartFragmentToProductDetailsFragment(productId.toLong()))
    }

}