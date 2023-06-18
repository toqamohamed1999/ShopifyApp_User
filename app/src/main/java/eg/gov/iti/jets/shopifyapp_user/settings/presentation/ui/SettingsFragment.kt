package eg.gov.iti.jets.shopifyapp_user.settings.presentation.ui

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import eg.gov.iti.jets.shopifyapp_user.R
import eg.gov.iti.jets.shopifyapp_user.base.remote.AppRetrofit
import eg.gov.iti.jets.shopifyapp_user.databinding.FragmentSettingBinding
import eg.gov.iti.jets.shopifyapp_user.settings.data.local.UserSettings
import eg.gov.iti.jets.shopifyapp_user.settings.data.local.UserSettings.toAddressBody
import eg.gov.iti.jets.shopifyapp_user.settings.data.remote.SettingsRemoteSourceImpl
import eg.gov.iti.jets.shopifyapp_user.settings.data.repo.SettingsRepoImpl
import eg.gov.iti.jets.shopifyapp_user.settings.domain.remote.SettingsAPIServices
import eg.gov.iti.jets.shopifyapp_user.settings.presentation.viewmodel.SettingViewModelFactory
import eg.gov.iti.jets.shopifyapp_user.settings.presentation.viewmodel.SettingsViewModel
import eg.gov.iti.jets.shopifyapp_user.util.Dialogs

class SettingsFragment:Fragment(),CurrencyListener{
    private var isValid: Boolean = false
    private var binding:FragmentSettingBinding? = null
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

        binding?.buttonShowAdresses?.setOnClickListener {
            childFragmentManager.beginTransaction().add(addressesDialog,null).commit()

        }
        binding?.textViewCurrancyList?.setOnClickListener {
            childFragmentManager.beginTransaction().add(currenciesDialog,null).commit()
        }
        binding?.buttonSavePhone?.setOnClickListener {
            validateData()
            if(isValid) {
                UserSettings.phoneNumber = binding?.editTextPhone?.text.toString()
                UserSettings.saveSettings()
                binding?.buttonSavePhone?.visibility = View.INVISIBLE
                Dialogs.SnakeToast(requireView(), "Done saving new Phone Number")
            }else{
                Dialogs.SnakeToast(requireView(), "Please Enter Valid Data")
            }
        }
        binding?.editTextPhone?.addTextChangedListener(object:TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(s.isNullOrEmpty())
                {
                    binding?.buttonSavePhone?.visibility = View.INVISIBLE
                }
            }

            override fun afterTextChanged(s: Editable?) {
                binding?.buttonSavePhone?.visibility = View.VISIBLE
            }

        })
        binding?.button3?.setOnClickListener {
            UserSettings.clearSettings()
            binding?.root?.findNavController()?.navigate(R.id.homeFragment)
        }

        binding?.buttonAddNewAdress?.setOnClickListener {
            binding?.root?.findNavController()?.navigate(R.id.action_settingsFragment_to_fragmentLocationDetector)
        }
        binding?.imageButtonbackButton?.setOnClickListener {
            binding?.root?.findNavController()?.popBackStack()
        }
    }

    private fun setUpDialogs() {
        currenciesDialog = CurrencyFragmentDialog()
        currenciesDialog.owner = this
        currenciesDialog.currencyListener=this
        addressesDialog = AddressesFragmentDialog()
        addressesDialog.owner = this
    }

    private fun validateData() {
        isValid = (Patterns.PHONE.matcher(binding?.editTextPhone?.text.toString()).matches())
    }

    private fun showSettigs() {
        binding?.buttonSavePhone?.visibility = View.INVISIBLE
        binding?.editTextPhone?.setText(UserSettings.phoneNumber)
        binding?.textViewCurrancyList?.text=UserSettings.currencyCode
    }

    override fun onResume() {
        super.onResume()
        if(UserSettings.isSelected)
        {
            viewModel.addNewAddressForUser(UserSettings.userAPI_Id,UserSettings.selectedAddress?.toAddressBody())
            Dialogs.SnakeToast(requireView(),"Done Loading New Address")
            UserSettings.saveNewAddress()
        }
    }
    override fun selectCurrency(currency: String) {
        viewModel.changeCurrency(UserSettings.currencyCode,currency)
        UserSettings.currencyCode = currency
        UserSettings.saveSettings()
        currenciesDialog.dismiss()
        binding?.textViewCurrancyList?.text = currency

    }

}