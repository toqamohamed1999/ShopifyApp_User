package eg.gov.iti.jets.shopifyapp_user.subcategory.presentation.ui

import android.os.Bundle
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
import eg.gov.iti.jets.shopifyapp_user.databinding.FragmentSubCategoryBinding
import eg.gov.iti.jets.shopifyapp_user.R
import eg.gov.iti.jets.shopifyapp_user.auth.data.remote.ResponseState
import eg.gov.iti.jets.shopifyapp_user.base.model.*
import eg.gov.iti.jets.shopifyapp_user.products.data.model.ProductBrandState
import eg.gov.iti.jets.shopifyapp_user.products.presentation.ui.OnClickProduct
import eg.gov.iti.jets.shopifyapp_user.products.presentation.ui.ProductsAdapter
import eg.gov.iti.jets.shopifyapp_user.products.presentation.ui.ProductsFragmentDirections
import eg.gov.iti.jets.shopifyapp_user.subcategory.data.model.SubCategoryState
import eg.gov.iti.jets.shopifyapp_user.subcategory.data.remote.SubCategoryRS
import eg.gov.iti.jets.shopifyapp_user.subcategory.data.repo.SubCategoryRepoImp
import eg.gov.iti.jets.shopifyapp_user.subcategory.presentation.viewmodel.SubCategoryFactoryVM
import eg.gov.iti.jets.shopifyapp_user.subcategory.presentation.viewmodel.SubCategoryViewModel
import eg.gov.iti.jets.shopifyapp_user.util.isConnected
import kotlinx.coroutines.launch

class SubCategoryFragment : Fragment() , OnClickProduct {

    private lateinit var binding: FragmentSubCategoryBinding
    private lateinit var productsAdapter: ProductsAdapter
    private val args: SubCategoryFragmentArgs by navArgs()
    private var productsList: List<Product> = emptyList()
    private var favList: List<FavRoomPojo> = emptyList()
    private var isFav: Boolean = false
    private var favDraftOrderResponse: FavDraftOrderResponse = FavDraftOrderResponse()

    private val viewModel: SubCategoryViewModel by lazy {
        val factory = SubCategoryFactoryVM(
            SubCategoryRepoImp.getInstance(SubCategoryRS())!!
        )
        ViewModelProvider(this, factory)[SubCategoryViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSubCategoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val categoryID = args.categoryID?.toLong()
        var productType = "SHOES"
        if (categoryID != null) {
            viewModel.getProductSubCategory(productType , categoryID)
            viewModel.getAllFavProduct()
        }

        //adapter and recyclerview
        productsAdapter = ProductsAdapter(ArrayList(),ArrayList(), requireActivity() , this)
        val layoutManager = GridLayoutManager(requireContext(), 2)
        binding.productsRecyclerview.layoutManager = layoutManager

        binding.shoesTextView.setOnClickListener {
            productType = "SHOES"
            resetButtonBackgrounds()
            binding.shoesTextView.setBackgroundResource(R.drawable.rounded_button_background)
            if (categoryID != null) {
                viewModel.getProductSubCategory(productType , categoryID)

            }
        }

        binding.tshirtTextView.setOnClickListener {
            productType = "T-SHIRTS"
            resetButtonBackgrounds()
            binding.tshirtTextView.setBackgroundResource(R.drawable.rounded_button_background)
            if (categoryID != null) {
                viewModel.getProductSubCategory(productType , categoryID)
            }
        }

        binding.accessoriesTextView.setOnClickListener {
            productType = "ACCESSORIES"
            resetButtonBackgrounds()
            binding.accessoriesTextView.setBackgroundResource(R.drawable.rounded_button_background)
            if (categoryID != null) {
                viewModel.getProductSubCategory(productType , categoryID)
            }
        }

        lifecycleScope.launch {
            viewModel.productState.collect {
                when (it) {
                    is SubCategoryState.Loading -> {
                        binding.productsRecyclerview.visibility = View.GONE
                        binding.noItemsTextView.visibility = View.GONE
                    }
                    is SubCategoryState.Success -> {
                        if(it.productsList.isEmpty()){
                            binding.productsRecyclerview.visibility = View.GONE
                            binding.noItemsTextView.visibility = View.VISIBLE
                        }else{
                            binding.noItemsTextView.visibility = View.GONE
                            binding.productsRecyclerview.visibility = View.VISIBLE
                            productsAdapter.setProductList(it.productsList)
                            //binding.productsRecyclerview.adapter = productsAdapter

                        }
                    }
                    else -> {
                        Log.i("TAG", "Errrrorrrr: $it")
                    }
                }
            }
        }
        lifecycleScope.launch {
            viewModel.favProduct.collect {
                when (it) {
                    is ResponseState.Loading -> {
                        binding.productsRecyclerview.visibility = View.GONE
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
        binding.productsRecyclerview.adapter = productsAdapter

    }

    private fun resetButtonBackgrounds() {
        binding.shoesTextView.setBackgroundResource(0)
        binding.tshirtTextView.setBackgroundResource(0)
        binding.accessoriesTextView.setBackgroundResource(0)
    }

    override fun onClickFavIcon(product_Id : Long) {
        if (isConnected(requireContext())) {
            if (productsAdapter.getIsFav()) {
                viewModel.deleteFavProductWithId(product_Id!!)

//                favDraftOrderResponse.draft_order?.lineItems?.removeIf { e -> e.productId == product_Id }
//                viewModel.updateFavDraftOrder(
//                    UserSettings.favoriteDraftOrderId.toLong(),
//                    favDraftOrderResponse
//                )
            } else {
                val product: Product? = findProductById(product_Id, productsList)
                viewModel.insertFavProduct(product?.toFavRoomPojo()!!)
                favDraftOrderResponse.draft_order?.lineItems?.add(product!!.toLineItems()!!)
//                viewModel.updateFavDraftOrder(
//                    UserSettings.favoriteDraftOrderId.toLong(),
//                    favDraftOrderResponse
//                )
            }
        } else {
            Snackbar.make(binding.root, R.string.noInternetConnection, Snackbar.LENGTH_LONG)
                .show()
        }
    }

    override fun onClickProductCard(product_Id: Long) {
        val action = SubCategoryFragmentDirections.actionSubCategoryFragmentToProductDetailsFragment(product_Id)
        binding.root.findNavController().navigate(action)
    }

    fun findProductById(productId: Long, productList: List<Product>): Product? {
        return productList.find { it.id == productId }
    }

}