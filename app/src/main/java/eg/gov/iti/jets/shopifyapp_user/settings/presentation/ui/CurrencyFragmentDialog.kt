package eg.gov.iti.jets.shopifyapp_user.settings.presentation.ui

import android.graphics.Point
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import eg.gov.iti.jets.shopifyapp_user.R
import eg.gov.iti.jets.shopifyapp_user.base.remote.AppRetrofit
import eg.gov.iti.jets.shopifyapp_user.databinding.CurrenciesLauoutBinding
import eg.gov.iti.jets.shopifyapp_user.databinding.UserAddressesLayoutBinding
import eg.gov.iti.jets.shopifyapp_user.settings.data.remote.SettingsRemoteSourceImpl
import eg.gov.iti.jets.shopifyapp_user.settings.data.repo.SettingsRepoImpl
import eg.gov.iti.jets.shopifyapp_user.settings.domain.remote.SettingsAPIServices
import eg.gov.iti.jets.shopifyapp_user.settings.presentation.viewmodel.SettingViewModelFactory
import eg.gov.iti.jets.shopifyapp_user.settings.presentation.viewmodel.SettingsViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class CurrencyFragmentDialog():DialogFragment() {
    private lateinit var adapter:CurrencyAdapter
    var binding:CurrenciesLauoutBinding?=null
    lateinit var owner: ViewModelStoreOwner
    private lateinit var viewModel: SettingsViewModel
    var currencyListener:CurrencyListener?=null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = CurrenciesLauoutBinding.inflate(inflater,container,false)
        return binding?.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(owner, SettingViewModelFactory(
            SettingsRepoImpl(
                SettingsRemoteSourceImpl(AppRetrofit.retrofit.create(SettingsAPIServices::class.java))
            )
        )
        )[SettingsViewModel::class.java]

        adapter = CurrencyAdapter(listOf(),currencyListener!!)
        binding?.currenciesRecycleView?.layoutManager = LinearLayoutManager(requireContext())
        binding?.currenciesRecycleView?.adapter = adapter
        lifecycleScope.launch{
            viewModel.currencies.collectLatest {
               val c = mutableListOf<String>()
                it?.currencies?.forEach {ii->
                    c.add(ii.currency)
                }
                adapter.setCurrencies(c)
            }
        }
        viewModel.getAllCurrencyCodesAvailable()
    }

    override fun onResume() {
        val window = dialog?.window
        val size =  Point()
        val display = window?.windowManager?.defaultDisplay
        display?.getSize(size)
        window?.setLayout((size.x*0.6).toInt(),(size.y*0.5).toInt());
        window?.setGravity(Gravity.CENTER)
        window?.setBackgroundDrawableResource(R.drawable.addresses_back)
        super.onResume()
    }
}
