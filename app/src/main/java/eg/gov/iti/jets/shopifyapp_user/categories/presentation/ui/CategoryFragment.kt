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
import eg.gov.iti.jets.shopifyapp_user.categories.data.model.CategoryState
import eg.gov.iti.jets.shopifyapp_user.categories.data.remote.CategoryRemoteSource
import eg.gov.iti.jets.shopifyapp_user.categories.data.repo.CategoryRepoImp
import eg.gov.iti.jets.shopifyapp_user.categories.presentation.viewmodel.CategoryFactoryViewModel
import eg.gov.iti.jets.shopifyapp_user.categories.presentation.viewmodel.CategoryViewModel
import eg.gov.iti.jets.shopifyapp_user.databinding.FragmentCategoryBinding
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

        //adapter and recyclerview
        viewModel.getCategories()
        categoryAdapter = CategoryAdapter(ArrayList(), requireActivity(), this)
//        val layoutManager = GridLayoutManager(requireContext(), 2)
//        binding.categoryRecyclerView.layoutManager = layoutManager
        val layoutManager = LinearLayoutManager(requireContext())
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        binding.categoryRecyclerView.layoutManager = layoutManager

        lifecycleScope.launch {
            viewModel.categoryState.collect {
                when (it) {
                    is CategoryState.Loading -> {
                    }
                    is CategoryState.Success -> {
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
        val action = CategoryFragmentDirections.actionCategoryFragmentToSubCategoryFragment(
            categoryID.toString()
        )
        binding.root.findNavController().navigate(action)
    }
}

