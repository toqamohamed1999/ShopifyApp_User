package eg.gov.iti.jets.shopifyapp_user.subcategory.presentation.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import eg.gov.iti.jets.shopifyapp_user.databinding.FragmentSubCategoryBinding
import eg.gov.iti.jets.shopifyapp_user.R
import eg.gov.iti.jets.shopifyapp_user.base.model.Product
import eg.gov.iti.jets.shopifyapp_user.products.data.model.ProductBrandState
import eg.gov.iti.jets.shopifyapp_user.products.presentation.ui.OnClickProduct
import eg.gov.iti.jets.shopifyapp_user.products.presentation.ui.ProductsAdapter
import eg.gov.iti.jets.shopifyapp_user.subcategory.data.model.SubCategoryState
import eg.gov.iti.jets.shopifyapp_user.subcategory.data.remote.SubCategoryRS
import eg.gov.iti.jets.shopifyapp_user.subcategory.data.repo.SubCategoryRepoImp
import eg.gov.iti.jets.shopifyapp_user.subcategory.presentation.viewmodel.SubCategoryFactoryVM
import eg.gov.iti.jets.shopifyapp_user.subcategory.presentation.viewmodel.SubCategoryViewModel
import kotlinx.coroutines.launch

class SubCategoryFragment : Fragment() , OnClickProduct {

    private lateinit var binding: FragmentSubCategoryBinding
    private lateinit var productsAdapter: ProductsAdapter
    private val args: SubCategoryFragmentArgs by navArgs()

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
        }

        //adapter and recyclerview
        productsAdapter = ProductsAdapter(ArrayList(), requireActivity() , this)
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
                    }
                    is SubCategoryState.Success -> {
                        productsAdapter.setProductList(it.productsList)
                        Log.i("Counttttt22", "count =  ${it.productsList.size}")
                        binding.productsRecyclerview.adapter = productsAdapter
                    }
                    else -> {
                        Log.i("TAG", "Errrrorrrr: $it")
                    }
                }
            }
        }

    }

    private fun resetButtonBackgrounds() {
        binding.shoesTextView.setBackgroundResource(0)
        binding.tshirtTextView.setBackgroundResource(0)
        binding.accessoriesTextView.setBackgroundResource(0)
    }

    override fun onClickFavIcon(product: Product) {
        TODO("Not yet implemented")
    }

    override fun onClickProductCard(product: Product) {
        TODO("Not yet implemented")
    }

}