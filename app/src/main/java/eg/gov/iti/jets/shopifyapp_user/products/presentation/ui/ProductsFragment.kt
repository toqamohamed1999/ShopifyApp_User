package eg.gov.iti.jets.shopifyapp_user.products.presentation.ui

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.snackbar.Snackbar
import eg.gov.iti.jets.shopifyapp_user.R
import eg.gov.iti.jets.shopifyapp_user.auth.data.remote.ResponseState
import eg.gov.iti.jets.shopifyapp_user.base.model.*
import eg.gov.iti.jets.shopifyapp_user.databinding.FragmentProductsBinding
import eg.gov.iti.jets.shopifyapp_user.products.data.model.ProductBrandState
import eg.gov.iti.jets.shopifyapp_user.products.data.remote.ProductsBrandRS
import eg.gov.iti.jets.shopifyapp_user.products.data.repo.ProductsBrandRepoImp
import eg.gov.iti.jets.shopifyapp_user.products.presentation.viewmodel.ProductFactoryViewModel
import eg.gov.iti.jets.shopifyapp_user.products.presentation.viewmodel.ProductsViewModel
import eg.gov.iti.jets.shopifyapp_user.settings.data.local.UserSettings
import eg.gov.iti.jets.shopifyapp_user.util.isConnected
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class ProductsFragment : Fragment(), OnClickProduct {

    private lateinit var binding: FragmentProductsBinding
    private lateinit var productsAdapter: ProductsAdapter
    private val args: ProductsFragmentArgs by navArgs()
    private var productsList: List<Product> = emptyList()
    private var favList: List<FavRoomPojo> = emptyList()
    private var isFav: Boolean = false
    private var favDraftOrderResponse: FavDraftOrderResponse = FavDraftOrderResponse()
    private var isRangeVisible = false

    private val viewModel: ProductsViewModel by lazy {
        val factory = ProductFactoryViewModel(
            ProductsBrandRepoImp.getInstance(ProductsBrandRS())!!
        )
        ViewModelProvider(this, factory)[ProductsViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProductsBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val brand = args.brand

        //adapter and recyclerview
        if (brand != null) {
            viewModel.getProductsBrand(brand)
            viewModel.getAllFavProduct()
        }

        binding.filterIcon.setOnClickListener {
            if (isRangeVisible) {
                binding.rangeSlider.visibility = View.GONE
                viewModel.getProductsBrand(brand.toString())
                viewModel.getAllFavProduct()
            } else {
                binding.rangeSlider.visibility = View.VISIBLE
            }
            isRangeVisible = !isRangeVisible
        }

        productsAdapter = ProductsAdapter(ArrayList(), ArrayList(), requireActivity(), this)
        val layoutManager = GridLayoutManager(requireContext(), 2)
        binding.productsRecyclerView.layoutManager = layoutManager

        lifecycleScope.launch {
            viewModel.productState.collect {
                when (it) {
                    is ProductBrandState.Loading -> {
                        binding.productsRecyclerView.visibility = View.GONE
                        binding.noItemsTextView.visibility = View.GONE
                    }
                    is ProductBrandState.Success -> {
                        if (it.productsList.isEmpty()) {
                            binding.productsRecyclerView.visibility = View.GONE
                            binding.noItemsTextView.visibility = View.VISIBLE
                        } else {
                            productsList = it.productsList
                            binding.noItemsTextView.visibility = View.GONE
                            binding.productsRecyclerView.visibility = View.VISIBLE
                            productsAdapter.setProductList(it.productsList)
                            //binding.productsRecyclerView.adapter = productsAdapter
                        }
                    }
                    else -> {
                        Log.i("TAG", "Errrrorrrr: $it")
                    }
                }
            }
        }
        viewModel.getFavRemoteProducts(UserSettings.favoriteDraftOrderId.toLong())
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
        lifecycleScope.launch {
            viewModel.favProduct.collect {
                when (it) {
                    is ResponseState.Loading -> {
                        binding.productsRecyclerView.visibility = View.GONE
                        binding.noItemsTextView.visibility = View.GONE
                    }
                    is ResponseState.Success -> {
                        productsAdapter.setFavList(it.data)
                    }
                    else -> {
                        Log.i("TAG", "Errrrorrrr: $it")
                    }
                }
            }
        }

        //set adapter to recyclerview
        binding.productsRecyclerView.adapter = productsAdapter

        binding.rangeSlider.addOnChangeListener { slider, value, fromUser ->
            productsAdapter.setProductList(productsList.filter {
                val values = binding.rangeSlider.values
                val price: Float? = it.variants[0].price?.toFloat()
                price!! >= values[0] && price <= values[1]
            })
            productsAdapter.notifyDataSetChanged()
        }
        val textWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // This method is called before the text is changed.
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val filteredList = filteredMyListWithSequence(s.toString())
                showNoMatchingResultIfFilteredListIsEmpty(filteredList)
                if (filteredList != null) {
                    productsAdapter.setProductList(filteredList)
                }
            }

            override fun afterTextChanged(s: Editable?) {
                // This method is called after the text has been changed.
            }
        }

        binding.searchEditText.addTextChangedListener(textWatcher)

    }

    private fun filteredMyListWithSequence(s: String): List<Product>? {

        return productsList?.filter { it.title!!.lowercase().contains(s.lowercase()) }
    }

    private fun showNoMatchingResultIfFilteredListIsEmpty(filteredList: List<Product>?) {
        if (filteredList.isNullOrEmpty()) {
            binding.txtNoResults.visibility = View.VISIBLE
            binding.productsRecyclerView.visibility = View.GONE
        } else {

            binding.txtNoResults.visibility = View.GONE
            binding.productsRecyclerView.visibility = View.VISIBLE
        }
    }

    override fun onClickFavIcon(product_Id: Long) {
        if (isConnected(requireContext())) {
            if (productsAdapter.getIsFav()) {
                viewModel.deleteFavProductWithId(product_Id!!)

                favDraftOrderResponse.draft_order?.lineItems?.removeIf { e -> e.productId == product_Id }
                viewModel.updateFavDraftOrder(
                    UserSettings.favoriteDraftOrderId.toLong(),
                    favDraftOrderResponse
                )
            } else {
                val product: Product? = findProductById(product_Id, productsList)
                viewModel.insertFavProduct(product?.toFavRoomPojo()!!)
                favDraftOrderResponse.draft_order?.lineItems?.add(product!!.toLineItems()!!)
                viewModel.updateFavDraftOrder(
                    UserSettings.favoriteDraftOrderId.toLong(),
                    favDraftOrderResponse
                )
            }
        } else {
            Snackbar.make(binding.root, R.string.noInternetConnection, Snackbar.LENGTH_LONG)
                .show()
        }

    }

    override fun onClickProductCard(product_Id: Long) {
        val action =
            ProductsFragmentDirections.actionProductsFragmentToProductDetailsFragment(product_Id)
        binding.root.findNavController().navigate(action)
    }

    fun findProductById(productId: Long, productList: List<Product>): Product? {
        return productList.find { it.id == productId }
    }

}