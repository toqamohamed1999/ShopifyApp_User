package eg.gov.iti.jets.shopifyapp_user.products.presentation.ui

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
import eg.gov.iti.jets.shopifyapp_user.databinding.FragmentProductsBinding
import eg.gov.iti.jets.shopifyapp_user.products.data.model.ProductBrandState
import eg.gov.iti.jets.shopifyapp_user.products.data.remote.ProductsBrandRS
import eg.gov.iti.jets.shopifyapp_user.products.data.repo.ProductsBrandRepoImp
import eg.gov.iti.jets.shopifyapp_user.products.domain.remote.ProductsBrandRSInterface
import eg.gov.iti.jets.shopifyapp_user.products.presentation.viewmodel.ProductFactoryViewModel
import eg.gov.iti.jets.shopifyapp_user.products.presentation.viewmodel.ProductsViewModel
import kotlinx.coroutines.launch

class ProductsFragment : Fragment() {

    private lateinit var binding: FragmentProductsBinding
    private lateinit var productsAdapter: ProductsAdapter
    private val args: ProductsFragmentArgs by navArgs()

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val brand = args.brand
        Log.i("Braaand", "brand =  $brand")

        //adapter and recyclerview
        if (brand != null) {
            viewModel.getProductsBrand(brand)
        }
        productsAdapter = ProductsAdapter(ArrayList(), requireActivity())
        val layoutManager = GridLayoutManager(requireContext(), 2)
        binding.productsRecyclerView.layoutManager = layoutManager

        lifecycleScope.launch {
            viewModel.productState.collect {
                when (it) {
                    is ProductBrandState.Loading -> {
                    }
                    is ProductBrandState.Success -> {
                        productsAdapter.setProductList(it.productsList)
                        Log.i("Counttttt22", "count =  ${it.productsList.size}")
                        binding.productsRecyclerView.adapter = productsAdapter
                    }
                    else -> {
                        Log.i("TAG", "Errrrorrrr: $it")
                    }
                }
            }
        }
    }

}