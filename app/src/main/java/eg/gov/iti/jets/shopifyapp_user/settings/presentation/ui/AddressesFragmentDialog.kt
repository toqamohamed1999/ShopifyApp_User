package eg.gov.iti.jets.shopifyapp_user.settings.presentation.ui

import android.graphics.Point
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import eg.gov.iti.jets.shopifyapp_user.R
import eg.gov.iti.jets.shopifyapp_user.base.remote.AppRetrofit
import eg.gov.iti.jets.shopifyapp_user.databinding.UserAddressesLayoutBinding
import eg.gov.iti.jets.shopifyapp_user.settings.data.local.UserSettings
import eg.gov.iti.jets.shopifyapp_user.settings.data.remote.SettingsRemoteSourceImpl
import eg.gov.iti.jets.shopifyapp_user.settings.data.repo.SettingsRepoImpl
import eg.gov.iti.jets.shopifyapp_user.settings.domain.remote.SettingsAPIServices
import eg.gov.iti.jets.shopifyapp_user.settings.presentation.viewmodel.SettingViewModelFactory
import eg.gov.iti.jets.shopifyapp_user.settings.presentation.viewmodel.SettingsViewModel
import kotlinx.coroutines.launch

class AddressesFragmentDialog():DialogFragment() {
   private lateinit var adapter:AdressesAdapter
   lateinit var owner:ViewModelStoreOwner
   lateinit var settingListener: SettingListener
   private lateinit var viewModel:SettingsViewModel
    var binding:UserAddressesLayoutBinding?=null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = UserAddressesLayoutBinding.inflate(inflater,container,false)
        return binding?.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(owner,SettingViewModelFactory(
            SettingsRepoImpl(
                SettingsRemoteSourceImpl()
            )
        )
        )[SettingsViewModel::class.java]

        adapter = AdressesAdapter(listOf(), {
            viewModel.removeAddress(it.id.toString(),UserSettings.userAPI_Id)
            viewModel.getAllAddressesForUser(UserSettings.userAPI_Id)
        },settingListener)
        binding?.rescleViewAddressesContainer?.layoutManager = LinearLayoutManager(requireContext())
        binding?.rescleViewAddressesContainer?.adapter = adapter

        lifecycleScope.launch {
            viewModel.addresses.collect {
                adapter.setAddresss(it?.addresses?: listOf())
            }
        }
        viewModel.getAllAddressesForUser(UserSettings.userAPI_Id)
    }

    override fun onResume() {
        val window = dialog?.window
        val size =  Point()
        val display = window?.windowManager?.defaultDisplay
        display?.getSize(size)
        window?.setLayout((size.x*0.71).toInt(),(size.y*0.6).toInt());
        window?.setGravity(Gravity.CENTER)
        window?.setBackgroundDrawableResource(R.drawable.addresses_back)
        super.onResume()
    }
}