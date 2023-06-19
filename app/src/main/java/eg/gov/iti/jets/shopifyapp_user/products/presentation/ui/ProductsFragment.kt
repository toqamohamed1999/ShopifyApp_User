package eg.gov.iti.jets.shopifyapp_user.products.presentation.ui

import android.annotation.SuppressLint
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
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import eg.gov.iti.jets.shopifyapp_user.MainActivity
import eg.gov.iti.jets.shopifyapp_user.base.model.Product
import eg.gov.iti.jets.shopifyapp_user.databinding.FragmentProductsBinding
import eg.gov.iti.jets.shopifyapp_user.productdetails.presentation.ui.ProductDetailsFragmentDirections
import eg.gov.iti.jets.shopifyapp_user.products.data.model.ProductBrandState
import eg.gov.iti.jets.shopifyapp_user.products.data.remote.ProductsBrandRS
import eg.gov.iti.jets.shopifyapp_user.products.data.repo.ProductsBrandRepoImp
import eg.gov.iti.jets.shopifyapp_user.products.presentation.viewmodel.ProductFactoryViewModel
import eg.gov.iti.jets.shopifyapp_user.products.presentation.viewmodel.ProductsViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch

class ProductsFragment : Fragment(), OnClickProduct {

    private lateinit var binding: FragmentProductsBinding
    private lateinit var productsAdapter: ProductsAdapter
    private val args: ProductsFragmentArgs by navArgs()
    private var productsList:List<Product> = emptyList()

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

        binding.filterIcon.setOnClickListener {
            binding.rangeSlider.visibility = View.VISIBLE
        }
        val brand = args.brand

        //adapter and recyclerview
        if (brand != null) {
            viewModel.getProductsBrand(brand)
        }
        productsAdapter = ProductsAdapter(ArrayList(), requireActivity() , this)
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
                        if(it.productsList.isEmpty()){
                            binding.productsRecyclerView.visibility = View.GONE
                            binding.noItemsTextView.visibility = View.VISIBLE
                        }else{
                            binding.noItemsTextView.visibility = View.GONE
                            binding.productsRecyclerView.visibility = View.VISIBLE
                            productsAdapter.setProductList(it.productsList)
                            binding.productsRecyclerView.adapter = productsAdapter
                        }
                    }
                    else -> {
                        Log.i("TAG", "Errrrorrrr: $it")
                    }
                }
            }
        }

        binding.rangeSlider.addOnChangeListener { slider, value, fromUser ->
            viewModel.filterProducts(value)
        }

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
    override fun onClickFavIcon(product: Product) {
        TODO("Not yet implemented")
    }

    override fun onClickProductCard(product: Product) {
        val action = ProductsFragmentDirections.actionProductsFragmentToProductDetailsFragment(product)
        binding.root.findNavController().navigate(action)
    }

}