package eg.gov.iti.jets.shopifyapp_user.productdetails.presentation.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import androidx.viewpager2.widget.ViewPager2
import eg.gov.iti.jets.shopifyapp_user.R
import eg.gov.iti.jets.shopifyapp_user.Reviews.ReviewsAdapter
import eg.gov.iti.jets.shopifyapp_user.base.model.Product
import eg.gov.iti.jets.shopifyapp_user.base.model.Review
import eg.gov.iti.jets.shopifyapp_user.base.model.toLineItem
import eg.gov.iti.jets.shopifyapp_user.base.remote.AppRetrofit
import eg.gov.iti.jets.shopifyapp_user.cart.data.remote.DraftOrderRemoteSourceImpl
import eg.gov.iti.jets.shopifyapp_user.cart.data.repo.CartRepositoryImpl
import eg.gov.iti.jets.shopifyapp_user.cart.domain.remote.DraftOrderNetworkServices
import eg.gov.iti.jets.shopifyapp_user.databinding.FragmentProductDetailsBinding
import eg.gov.iti.jets.shopifyapp_user.productdetails.presentation.viewmodel.ProductDetailsViewModel
import eg.gov.iti.jets.shopifyapp_user.productdetails.presentation.viewmodel.ProductDetailsViewModelFactory
import eg.gov.iti.jets.shopifyapp_user.settings.data.local.UserSettings
import eg.gov.iti.jets.shopifyapp_user.util.BadgeChanger
import eg.gov.iti.jets.shopifyapp_user.util.Dialogs
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class ProductDetailsFragment : Fragment() {
    lateinit var binding: FragmentProductDetailsBinding
    private var product:Product?=null
    private val viewModel by viewModels<ProductDetailsViewModel> {
        ProductDetailsViewModelFactory(CartRepositoryImpl(DraftOrderRemoteSourceImpl(AppRetrofit.retrofit.create(DraftOrderNetworkServices::class.java))))
    }
   private val args: ProductDetailsFragmentArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentProductDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
         product = args.product
        binding.btnAddToBag.setOnClickListener {
            var quantity = 0
            product?.variants?.forEach {variant->
                quantity+=variant.inventoryQuantity?:0
            }
            viewModel.addProductToCart(product?.toLineItem(),quantity)
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
        val reviews = listOf(
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
                "https://www.shutterstock.com/image-photo/photo-cheerful-minded-lady-interested-260nw-2047307549.jpg",
                "This product is fantastic! It's incredibly easy to use and has saved me a lot of time and effort. I highly recommend it."
            )
        )
        if (product != null) {
            createDots(product!!.images.size)
            updateDots(0)
            binding.viewPagerImages.registerOnPageChangeCallback(object :
                ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    updateDots(position)
                }
            })
            binding.progressBar.visibility = View.GONE
            binding.txtProductPrice.text = product!!.variants[0].price
            binding.txtProductName.text = product!!.title
            binding.txtViewDescription.text = product!!.bodyHtml
            binding.viewPagerImages.adapter = ProductImageViewPagerAdapter(product!!.images)
            binding.reviewsRecycler.adapter=ReviewsAdapter(requireContext(),reviews)

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