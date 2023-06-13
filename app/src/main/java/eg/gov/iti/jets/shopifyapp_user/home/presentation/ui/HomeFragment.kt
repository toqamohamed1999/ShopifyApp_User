package eg.gov.iti.jets.shopifyapp_user.home.presentation.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import eg.gov.iti.jets.shopifyapp_user.R
import eg.gov.iti.jets.shopifyapp_user.base.remote.AppRetrofit
import eg.gov.iti.jets.shopifyapp_user.databinding.FragmentHomeBinding
import eg.gov.iti.jets.shopifyapp_user.home.data.model.BrandResultState
import eg.gov.iti.jets.shopifyapp_user.home.data.remote.AddsRemoteSourceImpl
import eg.gov.iti.jets.shopifyapp_user.home.data.remote.BrandRemoteSource
import eg.gov.iti.jets.shopifyapp_user.home.data.repo.AddsRepoImpl
import eg.gov.iti.jets.shopifyapp_user.home.data.repo.BrandRepoImp
import eg.gov.iti.jets.shopifyapp_user.home.domain.model.addsmodels.DiscountCode
import eg.gov.iti.jets.shopifyapp_user.home.domain.remote.AddsAPIServices
import eg.gov.iti.jets.shopifyapp_user.home.presentation.viewmodel.HomeFactoryViewModel
import eg.gov.iti.jets.shopifyapp_user.home.presentation.viewmodel.HomeViewModel
import eg.gov.iti.jets.shopifyapp_user.settings.data.local.UserSettings
import eg.gov.iti.jets.shopifyapp_user.util.Dialogs
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class HomeFragment : Fragment(), CouponClickListener, OnClickBrand {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var dotsLayout: LinearLayout
    private lateinit var brandAdapter: BrandAdapter
    private lateinit var  sliderPagerAdapter:CouponAdapter
    private val viewModel: HomeViewModel by lazy {
        val factory = HomeFactoryViewModel(
            BrandRepoImp.getInstance(BrandRemoteSource())!!,
            AddsRepoImpl(AddsRemoteSourceImpl(AppRetrofit.retrofit.create(AddsAPIServices::class.java)))
        )
        ViewModelProvider(this, factory)[HomeViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sliderPagerAdapter = CouponAdapter(listOf(), this)
        dotsLayout = view.findViewById(R.id.dotsLayout)
        dotsLayout.removeAllViews()
        binding.couponsViewPager.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                updateDots(position)
            }
        })
        //adds
        viewModel.getAdds()
        binding.couponsViewPager.adapter = sliderPagerAdapter
        lifecycleScope.launch {
            viewModel.adds.collectLatest {
                sliderPagerAdapter.discounts = it
                //createDots(it.size)
                //updateDots(0)
                //dotsLayout.refreshDrawableState()

            }
        }
        //adapter and recyclerview
        viewModel.getBrands()
        brandAdapter = BrandAdapter(ArrayList(), requireActivity(), this)
        val layoutManager = GridLayoutManager(requireContext(), 2)
        binding.brandsRecyclerview.layoutManager = layoutManager

        lifecycleScope.launch {
            viewModel.brandState.collect {
                when (it) {
                    is BrandResultState.Loading -> {
                    }
                    is BrandResultState.Success -> {
                        brandAdapter.setBrandList(it.brandList)
                        Log.i("Counttttt", "count =  ${it.brandList.size}")
                        binding.brandsRecyclerview.adapter = brandAdapter
                    }
                    else -> {
                        Log.i("TAG", "Errrrorrrr: $it")
                    }
                }
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
            dotsLayout.addView(dot)

        }
    }

    private fun updateDots(currentPosition: Int) {
        val numDots = dotsLayout.childCount
        for (i in 0 until numDots) {
            val dot = dotsLayout.getChildAt(i)
            val drawable = if (i == currentPosition) {
                ContextCompat.getDrawable(requireContext(), R.drawable.dot_selected)
            } else {
                ContextCompat.getDrawable(requireContext(), R.drawable.dot_unsetected)
            }
            dot.background = drawable
        }
    }

    override fun onImageClick(discountCode: DiscountCode) {
        UserSettings.userCurrentDiscountCopy = discountCode
        Dialogs.SnakeToast(requireView(),"Code Copied please paste with in checkout process")
    }

    override fun onBrandClick(brandName: String?) {
        val action = HomeFragmentDirections.actionHomeFragmentToProductsFragment(brandName)
        binding.root.findNavController().navigate(action)
    }
}