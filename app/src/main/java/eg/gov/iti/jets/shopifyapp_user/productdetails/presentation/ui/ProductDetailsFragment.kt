package eg.gov.iti.jets.shopifyapp_user.productdetails.presentation.ui

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.snackbar.Snackbar
import eg.gov.iti.jets.shopifyapp_user.R
import eg.gov.iti.jets.shopifyapp_user.Reviews.ReviewsAdapter
import eg.gov.iti.jets.shopifyapp_user.auth.data.remote.ResponseState
import eg.gov.iti.jets.shopifyapp_user.base.model.*
import eg.gov.iti.jets.shopifyapp_user.base.remote.AppRetrofit
import eg.gov.iti.jets.shopifyapp_user.cart.data.remote.DraftOrderRemoteSourceImpl
import eg.gov.iti.jets.shopifyapp_user.cart.data.repo.CartRepositoryImpl
import eg.gov.iti.jets.shopifyapp_user.cart.domain.remote.DraftOrderNetworkServices
import eg.gov.iti.jets.shopifyapp_user.databinding.FragmentProductDetailsBinding
import eg.gov.iti.jets.shopifyapp_user.productdetails.data.model.SingleProductState
import eg.gov.iti.jets.shopifyapp_user.productdetails.presentation.viewmodel.ProductDetailsViewModel
import eg.gov.iti.jets.shopifyapp_user.productdetails.presentation.viewmodel.ProductDetailsViewModelFactory
import eg.gov.iti.jets.shopifyapp_user.settings.data.local.UserSettings
import eg.gov.iti.jets.shopifyapp_user.util.BadgeChanger
import eg.gov.iti.jets.shopifyapp_user.util.Dialogs
import eg.gov.iti.jets.shopifyapp_user.util.formatDecimal
import eg.gov.iti.jets.shopifyapp_user.util.isConnected
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class ProductDetailsFragment : Fragment() {
    lateinit var binding: FragmentProductDetailsBinding
    private val viewModel by viewModels<ProductDetailsViewModel> {
        ProductDetailsViewModelFactory(
            CartRepositoryImpl(
                DraftOrderRemoteSourceImpl(
                    AppRetrofit.retrofit.create(
                        DraftOrderNetworkServices::class.java
                    )
                )
            )
        )
    }
    private val args: ProductDetailsFragmentArgs by navArgs()
    private var receivedProduct: Product? = null
    private var product_Id: Long? = null
    private var isFav: Boolean = false
    private var reviews = listOf<Review>()
    private var favDraftOrderResponse :FavDraftOrderResponse= FavDraftOrderResponse()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentProductDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        product_Id = args.productId
        lifecycleScope.launch {

            viewModel.favProducts.collectLatest {
                when (it) {
                    is ResponseState.Loading -> {

                    }
                    is ResponseState.Success -> {
                       favDraftOrderResponse=it.data!!
                    }
                    is ResponseState.Error -> {
                        println("Draft order Error ${it.exception}")
                    }
                }
            }
        }

        viewModel.getCartProducts()
        binding.btnAddToBag.setOnClickListener {

            val product = receivedProduct
            var quantity = 0
            product?.variants?.forEach { variant ->
                quantity += variant.inventoryQuantity ?: 0
            }
            viewModel.addProductToCart(product?.toLineItem(), quantity)
        }

        lifecycleScope.launch {
            viewModel.addedToCart.observe(viewLifecycleOwner) {
                when (it) {
                    1 -> {
                        Dialogs.SnakeToast(requireView(), "Product Added To Cart")
                        (requireActivity() as BadgeChanger).changeBadgeCartCount(UserSettings.cartQuantity)
                        viewModel.resetAddToCart()
                    }
                    -1 -> {
                        Dialogs.SnakeToast(requireView(), "Fail To Add to Cart")
                        viewModel.resetAddToCart()
                    }
                    else -> {

                    }
                }
            }
        }
        reviews = listOf(
            Review(
                "John Smith",
                4.5,
                "https://images.pexels.com/photos/614810/pexels-photo-614810.jpeg?cs=srgb&dl=pexels-simon-robben-614810.jpg&fm=jpg",
                "I really enjoyed using this product. It exceeded my expectations and I would definitely recommend it to anyone looking for a reliable solution."
            ),
            Review(
                "Jane Doe",
                3.0,
                "https://t4.ftcdn.net/jpg/03/83/25/83/360_F_383258331_D8imaEMl8Q3lf7EKU2Pi78Cn0R7KkW9o.jpg",
                "The product was okay, but it didn't quite meet my needs. I found it a bit difficult to use and would have liked more guidance."
            ),
            Review(
                "David Lee",
                5.0,
                "https://d34u8crftukxnk.cloudfront.net/slackpress/prod/sites/6/E12KS1G65-W0168RE00G7-133faf432639-512.jpeg",
                "This product is fantastic! It's incredibly easy to use and has saved me a lot of time and effort. I highly recommend it."
            )
        )
        if (isConnected(requireContext())) {
            if (UserSettings.userAPI_Id.isNullOrEmpty()) {
                binding.cardViewIsFavorite.visibility = View.GONE
                binding.btnAddToBag.visibility = View.GONE
            } else {
                viewModel.getFavRemoteProducts(UserSettings.favoriteDraftOrderId.toLong())
                binding.cardViewIsFavorite.visibility = View.VISIBLE
                binding.btnAddToBag.visibility = View.VISIBLE
            }
            viewModel.getSingleProductById(product_Id!!)
            lifecycleScope.launch {
                viewModel.product.collectLatest { result ->
                    when (result) {
                        is SingleProductState.Loading -> {
                            binding.progressBar.visibility = View.VISIBLE
                            binding.linearContainer.visibility = View.GONE
                        }
                        is SingleProductState.Success -> {
                            binding.progressBar.visibility = View.GONE
                            binding.linearContainer.visibility = View.VISIBLE
                            receivedProduct = result.product.product
                            println("/////////receivedProduct//////////////${receivedProduct!!.id}")
                            viewModel.getFavProductWithId(receivedProduct?.id!!)
                            bindDataToView(receivedProduct!!)
                        }
                        is SingleProductState.Error -> {
                            println("/////////Error ${result.error}")
                        }
                    }
                }
            }
            lifecycleScope.launch {
                viewModel.favProduct.collectLatest { result ->
                    when (result) {
                        is ResponseState.Loading -> {

                        }
                        is ResponseState.Success -> {
                            if (result.data?.productId != null) {
                                isFav = true
                                binding.imgViewFavoriteIcon.setImageResource(R.drawable.favorite_icon)
                            }
                        }
                        is ResponseState.Error -> {

                        }
                    }
                }
            }

        } else {
            Snackbar.make(binding.root, R.string.noInternetConnection, Snackbar.LENGTH_LONG)
                .show()
        }
        binding.cardViewIsFavorite.setOnClickListener {
            if (isConnected(requireContext())) {


                if (isFav) {
                    val alertDialog = AlertDialog.Builder(context)

                    alertDialog.apply {
                        setIcon(R.drawable.baseline_delete_24)
                        setTitle("Delete")
                        setMessage("Are you sure you want to delete the Product from favorite?")
                        setPositiveButton("Yes") { _: DialogInterface?, _: Int ->
                            isFav = false
                            viewModel.deleteFavProductWithId(product_Id!!)
                            favDraftOrderResponse.draft_order?.lineItems?.removeIf {e->e.productId==receivedProduct?.id }
                            viewModel.updateFavDraftOrder(UserSettings.favoriteDraftOrderId.toLong(),favDraftOrderResponse)
                            binding.imgViewFavoriteIcon.setImageResource(R.drawable.favorite_border_icon)

                            Snackbar.make(
                                binding.root,
                                R.string.delete_MSG_from_favorites,
                                Snackbar.LENGTH_LONG
                            ).show()
                        }
                        setNegativeButton("Cancel") { _, _ ->

                        }


                    }.create().show()
                } else {
                    isFav = true
                    viewModel.insertFavProduct(receivedProduct?.toFavRoomPojo()!!)
                    favDraftOrderResponse.draft_order?.lineItems?.add(receivedProduct!!.toLineItems()!!)
                    viewModel.updateFavDraftOrder(UserSettings.favoriteDraftOrderId.toLong(),favDraftOrderResponse)
                    binding.imgViewFavoriteIcon.setImageResource(R.drawable.favorite_icon)
                }
            } else {
                Snackbar.make(binding.root, R.string.noInternetConnection, Snackbar.LENGTH_LONG)
                    .show()
            }
        }

    }

    private fun bindDataToView(product: Product) {
        if (product != null) {
            createDots(product.images.size)
            updateDots(0)
            binding.viewPagerImages.registerOnPageChangeCallback(object :
                ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    updateDots(position)
                }
            })
            binding.progressBar.visibility = View.GONE
            binding.txtProductPrice.text = formatDecimal(product.variants[0].price!!.toDouble() * UserSettings.currentCurrencyValue) + " ${UserSettings.currencyCode}"
            binding.txtProductName.text = product.title
            binding.txtViewDescription.text = product.bodyHtml
            binding.viewPagerImages.adapter = ProductImageViewPagerAdapter(product.images)
            binding.reviewsRecycler.adapter = ReviewsAdapter(requireContext(), reviews)

            binding.txtSeeMoreReviews.setOnClickListener {
                val action =
                    ProductDetailsFragmentDirections.actionProductDetailsFragmentToReviewsFragment(
                        product.title!!,
                        product.images[0].src!!
                    )
                binding.root.findNavController().navigate(action)
            }
        }
    }

    private fun createDots(numDots: Int) {
        for (i in 0 until numDots) {
            val dot = View(context)
            val dotSize = 16
            val dotMargin = 8
            val dotParams = LinearLayout.LayoutParams(dotSize, dotSize)
            dotParams.setMargins(dotMargin, 0, dotMargin, 0)
            dot.layoutParams = dotParams
            dot.background = ContextCompat.getDrawable(requireContext(), R.drawable.dot_item)
            binding.dotsLayout.addView(dot)

        }
    }

    private fun updateDots(currentPosition: Int) {
        val numDots = binding.dotsLayout.childCount
        for (i in 0 until numDots) {
            val dot = binding.dotsLayout.getChildAt(i)
            val drawable = if (i == currentPosition) {
                ContextCompat.getDrawable(requireContext(), R.drawable.dot_selected)
            } else {
                ContextCompat.getDrawable(requireContext(), R.drawable.dot_unsetected)
            }
            dot.background = drawable
        }
    }

}