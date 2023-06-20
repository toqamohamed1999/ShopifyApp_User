package eg.gov.iti.jets.shopifyapp_user.settings.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import eg.gov.iti.jets.shopifyapp_user.R
import eg.gov.iti.jets.shopifyapp_user.databinding.FragmentSettingBinding
import eg.gov.iti.jets.shopifyapp_user.settings.data.local.UserSettings
import eg.gov.iti.jets.shopifyapp_user.settings.data.local.UserSettings.toAddressBody
import eg.gov.iti.jets.shopifyapp_user.settings.data.remote.SettingsRemoteSourceImpl
import eg.gov.iti.jets.shopifyapp_user.settings.data.repo.SettingsRepoImpl
import eg.gov.iti.jets.shopifyapp_user.settings.presentation.viewmodel.SettingViewModelFactory
import eg.gov.iti.jets.shopifyapp_user.settings.presentation.viewmodel.SettingsViewModel
import eg.gov.iti.jets.shopifyapp_user.util.Dialogs

class SettingsFragment:Fragment(),SettingListener{
    private var isValid: Boolean = false
    private var binding: FragmentSettingBinding? = null
    private lateinit var currenciesDialog: CurrencyFragmentDialog
    private  lateinit var addressesDialog:AddressesFragmentDialog

    private val viewModel by viewModels<SettingsViewModel> {
        SettingViewModelFactory(SettingsRepoImpl(SettingsRemoteSourceImpl()))
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSettingBinding.inflate(inflater,container,false)
        return binding?.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showSettigs()
        setUpActions()
    }

    private fun setUpActions() {
        setUpDialogs()

        binding?.imageView?.setOnClickListener {
            childFragmentManager.beginTransaction().add(addressesDialog,null).commit()

        }
        binding?.btnChangeCurrncy?.setOnClickListener {
            childFragmentManager.beginTransaction().add(currenciesDialog,null).commit()
        }
        binding?.btnLogout?.setOnClickListener {
            UserSettings.clearSettings()
            binding?.root?.findNavController()?.navigate(R.id.loginFragment)
        }

        binding?.btnChangeAddress?.setOnClickListener {
            binding?.root?.findNavController()?.navigate(R.id.action_settingsFragment_to_fragmentLocationDetector)
        }
    }

    private fun setUpDialogs() {
        currenciesDialog = CurrencyFragmentDialog()
        currenciesDialog.owner = this
        currenciesDialog.currencyListener=this
        addressesDialog = AddressesFragmentDialog()
        addressesDialog.owner = this
        addressesDialog.settingListener = this
    }

    private fun showSettigs() {
        binding?.tvCurrncy?.text=UserSettings.currencyCode
    }

    override fun onResume() {
        super.onResume()
        if(UserSettings.isSelected)
        {
            viewModel.addNewAddressForUser(UserSettings.userAPI_Id,UserSettings.selectedAddress?.toAddressBody())
            Dialogs.SnakeToast(requireView(),"Done Loading New Address")
            UserSettings.isSelected = false
        }
    }
    override fun selectAddress(address:String){
        UserSettings.saveNewAddress(address)
        binding?.tvAddres?.text = address
        Dialogs.SnakeToast(requireView(),"Done Selecting Default Address ")
    }
    override fun selectCurrency(currency: String) {
        viewModel.changeCurrency(currency)
        UserSettings.currencyCode = currency
        UserSettings.saveSettings()
        currenciesDialog.dismiss()
        binding?.tvCurrncy?.text = currency
    }
}