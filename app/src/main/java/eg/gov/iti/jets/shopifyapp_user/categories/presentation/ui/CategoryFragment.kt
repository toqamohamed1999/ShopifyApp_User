package eg.gov.iti.jets.shopifyapp_user.categories.presentation.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import eg.gov.iti.jets.shopifyapp_user.R
import eg.gov.iti.jets.shopifyapp_user.categories.data.model.CategoryState
import eg.gov.iti.jets.shopifyapp_user.categories.data.remote.CategoryRemoteSource
import eg.gov.iti.jets.shopifyapp_user.categories.data.repo.CategoryRepoImp
import eg.gov.iti.jets.shopifyapp_user.categories.presentation.viewmodel.CategoryFactoryViewModel
import eg.gov.iti.jets.shopifyapp_user.categories.presentation.viewmodel.CategoryViewModel
import eg.gov.iti.jets.shopifyapp_user.databinding.FragmentCategoryBinding
import eg.gov.iti.jets.shopifyapp_user.util.MyNetworkStatus
import eg.gov.iti.jets.shopifyapp_user.util.NetworkConnectivityObserver
import eg.gov.iti.jets.shopifyapp_user.util.isConnected
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class CategoryFragment : Fragment(), OnClickCategory {

    private lateinit var binding: FragmentCategoryBinding
    private lateinit var categoryAdapter: CategoryAdapter

    private val viewModel: CategoryViewModel by lazy {
        val factory = CategoryFactoryViewModel(
            CategoryRepoImp.getInstance(CategoryRemoteSource())!!
        )
        ViewModelProvider(this, factory)[CategoryViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCategoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        NetworkConnectivityObserver.initNetworkConnectivity(requireContext())

        NetworkConnectivityObserver.observeNetworkConnection().onEach {
            if (it == MyNetworkStatus.Available) {
                binding.noInternetContainer.visibility = View.GONE
                binding.categoryRecyclerView.visibility = View.VISIBLE
                binding.progressBar.visibility = View.VISIBLE
                viewModel.getCategories()
            } else {
                binding.categoryRecyclerView.visibility = View.GONE
                binding.noInternetContainer.visibility = View.VISIBLE
                binding.progressBar.visibility = View.GONE
            }
        }.launchIn(lifecycleScope)

        //adapter and recyclerview
        //viewModel.getCategories()
        categoryAdapter = CategoryAdapter(ArrayList(), requireActivity(), this)
        val layoutManager = LinearLayoutManager(requireContext())
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        binding.categoryRecyclerView.layoutManager = layoutManager

        lifecycleScope.launch {
            viewModel.categoryState.collect {
                when (it) {
                    is CategoryState.Loading -> {
                        binding.progressBar.visibility = View.VISIBLE
                    }
                    is CategoryState.Success -> {
                        binding.progressBar.visibility = View.GONE
                        categoryAdapter.setCategoryList(it.categoryList)
                        Log.i("Counttttt", "count =  ${it.categoryList.size}")
                        binding.categoryRecyclerView.adapter = categoryAdapter
                    }
                    else -> {
                        Log.i("TAG", "Errrrorrrr: $it")
                    }
                }
            }
        }

    }

    override fun onCategoryClick(categoryID: Long) {
        if (isConnected(requireContext())) {
            val action = CategoryFragmentDirections.actionCategoryFragmentToSubCategoryFragment(
                categoryID.toString()
            )
            binding.root.findNavController().navigate(action)
        }else{
            Snackbar.make(binding.root, R.string.noInternetConnection, Snackbar.LENGTH_LONG)
                .show()
        }
    }

}

