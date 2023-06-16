package eg.gov.iti.jets.shopifyapp_user.settings.presentation.ui

import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import eg.gov.iti.jets.shopifyapp_user.R
import eg.gov.iti.jets.shopifyapp_user.databinding.FragmentSettingBinding
import eg.gov.iti.jets.shopifyapp_user.settings.data.local.UserSettings
import eg.gov.iti.jets.shopifyapp_user.util.Dialogs

class SettingsFragment:Fragment(){
    private var isValid: Boolean = false
    private var binding:FragmentSettingBinding? = null
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
       binding?.radioGroup?.setOnCheckedChangeListener { group, checkedId ->
        binding?.button?.visibility = View.VISIBLE
        when(checkedId)
        {
            R.id.setting_currency_usd->{
                UserSettings.currencyCode = "USD"
            }
            R.id.setting_currency_eur->{
                UserSettings.currencyCode = "EUR"
            }
            else->{
                UserSettings.currencyCode = "EGP"
            }
        }
       }

        binding?.button?.setOnClickListener {
            validateData()
            if(isValid) {
                UserSettings.saveSettings()
                binding?.button?.visibility = View.INVISIBLE
                Dialogs.SnakeToast(requireView(), "Done saving new settings")
            }else{
                Dialogs.SnakeToast(requireView(), "Please Enter Valid Data")
            }
        }

        binding?.button3?.setOnClickListener {
            UserSettings.clearSettings()
            binding?.root?.findNavController()?.navigate(R.id.homeFragment)
        }

        binding?.imageButtonAddressGetter?.setOnClickListener {
            binding?.root?.findNavController()?.navigate(R.id.action_settingsFragment_to_fragmentLocationDetector)
        }
    }

    private fun validateData() {
        isValid = (Patterns.PHONE.matcher(binding?.editTextPhone?.text.toString()).matches()&&!binding?.editTextTextShippingAddress?.text.isNullOrEmpty())
    }

    private fun showSettigs() {
        binding?.button?.visibility = View.INVISIBLE
        binding?.editTextTextShippingAddress?.setText(UserSettings.shippingAddress)
        when(UserSettings.currencyCode) {
            "USD" -> {
                binding?.settingCurrencyUsd?.isSelected = true
            }
            "EUR" -> {
                binding?.settingCurrencyEur?.isSelected = true
            }
            else -> {
                binding?.settingCurrencyEgb?.isSelected = true
            }
        }
        binding?.editTextPhone?.setText(UserSettings.phoneNumber)
    }

    override fun onResume() {
        super.onResume()
        if(UserSettings.isSelected)
        {
            binding?.editTextTextShippingAddress?.setText(UserSettings.selectedAddress)
            Dialogs.SnakeToast(requireView(),"Done Loading New Address")
            UserSettings.saveNewAddress()
        }
    }

}