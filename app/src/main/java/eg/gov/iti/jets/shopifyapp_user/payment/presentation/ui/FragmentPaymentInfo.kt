package eg.gov.iti.jets.shopifyapp_user.payment.presentation.ui

import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.braintreepayments.api.*
import com.google.android.gms.wallet.TransactionInfo
import com.google.android.gms.wallet.WalletConstants
import eg.gov.iti.jets.shopifyapp_user.R
import eg.gov.iti.jets.shopifyapp_user.base.remote.AppRetrofit
import eg.gov.iti.jets.shopifyapp_user.cart.data.remote.DraftOrderRemoteSourceImpl
import eg.gov.iti.jets.shopifyapp_user.cart.data.repo.CartRepositoryImpl
import eg.gov.iti.jets.shopifyapp_user.cart.domain.remote.DraftOrderNetworkServices
import eg.gov.iti.jets.shopifyapp_user.databinding.FragmentPaymentInfoBinding
import eg.gov.iti.jets.shopifyapp_user.home.data.remote.AddsRemoteSourceImpl
import eg.gov.iti.jets.shopifyapp_user.home.data.repo.AddsRepoImpl
import eg.gov.iti.jets.shopifyapp_user.payment.data.remote.PaymentRemoteSourceImpl
import eg.gov.iti.jets.shopifyapp_user.payment.data.repo.PaymentRepoImpl
import eg.gov.iti.jets.shopifyapp_user.payment.presentation.viewmodel.PaymentViewModel
import eg.gov.iti.jets.shopifyapp_user.payment.presentation.viewmodel.PaymentViewModelFactory
import eg.gov.iti.jets.shopifyapp_user.payment.util.PaymentConstants
import eg.gov.iti.jets.shopifyapp_user.settings.data.local.UserSettings
import eg.gov.iti.jets.shopifyapp_user.settings.data.local.UserSettings.currencyCode
import eg.gov.iti.jets.shopifyapp_user.settings.data.local.UserSettings.currentCurrencyValue
import eg.gov.iti.jets.shopifyapp_user.settings.data.local.UserSettings.toAddressBody
import eg.gov.iti.jets.shopifyapp_user.settings.presentation.ui.AddressesFragmentDialog
import eg.gov.iti.jets.shopifyapp_user.settings.presentation.ui.SettingListener
import eg.gov.iti.jets.shopifyapp_user.util.Dialogs
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class FragmentPaymentInfo: Fragment(),GooglePayListener, SettingListener {
    private lateinit var addressesDialog: AddressesFragmentDialog
    private var binding: FragmentPaymentInfoBinding? = null
    private lateinit var braintreeClient: BraintreeClient
    private lateinit var googlePayClient:GooglePayClient
    private lateinit var methodDialog:AlertDialog
    private var isReadyButton = false
    private var totalPrice = 0
    private val viewModel by viewModels<PaymentViewModel> {
        PaymentViewModelFactory(
            CartRepositoryImpl(DraftOrderRemoteSourceImpl(
                AppRetrofit.retrofit.create(DraftOrderNetworkServices::class.java))),
            PaymentRepoImpl(PaymentRemoteSourceImpl()),AddsRepoImpl(AddsRemoteSourceImpl()))
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        braintreeClient = BraintreeClient(requireContext(), PaymentConstants.Tokenization_Key)
        binding = FragmentPaymentInfoBinding.inflate(inflater,container,false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        googlePayClient = GooglePayClient(this, braintreeClient)
        googlePayClient.setListener(this)
        googlePayClient.isReadyToPay(requireActivity()) { isReadyToPay, error ->
            if (isReadyToPay) {
                isReadyButton = true
            }
        }
        viewModel.getCartDraftOrder()
        setUpDialogs()
        showInfo()
        setUpActions()
        observeData()
    }

    private fun observeData() {
        lifecycleScope.launch {
            viewModel.mode.collectLatest {
                when(it){
                    1->{
                        binding?.editTextCoupon?.setText("")
                        Toast.makeText(requireContext(),"UnValid Discount Code",Toast.LENGTH_SHORT).show()
                        viewModel.resetMode()
                    }
                    -1->{
                        Toast.makeText(requireContext(),"Valid Discount Code",Toast.LENGTH_SHORT).show()
                        binding?.editTextCoupon?.setText("")
                        viewModel.resetMode()
                    }
                    2->{
                        // Done Deleting Cart You must show Bill
                        viewModel.resetMode()
                    }
                    else->{

                    }
                }
            }
        }
    }

    private fun setUpDialogs() {
        addressesDialog = AddressesFragmentDialog()
        addressesDialog.owner = this
        addressesDialog.settingListener = this
         val inflater= this.layoutInflater
         val builder = AlertDialog.Builder(requireContext())
         val view = inflater.inflate(R.layout.fragment_buy_with_x_method,null)
         builder.setView(view)
        val btnGooglePay =  view.findViewById<ImageView>(R.id.method_pay_google_pay)
        val btnCashOnDelivery = view.findViewById<ImageView>(R.id.method_pay_cach_on_delivery)
        btnCashOnDelivery.setOnClickListener {
            confirmOrder()
        }
        btnGooglePay.setOnClickListener {
            val googlePayRequest = GooglePayRequest()
            googlePayRequest.transactionInfo = TransactionInfo.newBuilder()
                .setTotalPrice("1.00")//setTotalPrice
                .setTotalPriceStatus(WalletConstants.TOTAL_PRICE_STATUS_FINAL)
                .setCurrencyCode(UserSettings.currencyCode)
                .build()
            googlePayRequest.isBillingAddressRequired = true
            googlePayClient.requestPayment(requireActivity(), googlePayRequest)
            methodDialog.dismiss()
        }
        methodDialog = builder.create()

    }

    private fun confirmOrder() {
        Toast.makeText(requireContext(),"Confirming Order",Toast.LENGTH_SHORT).show()
        viewModel.confirmOrder()
    }

    private fun setUpActions() {
        //address
        binding?.btnChangeAddress?.setOnClickListener{
            binding?.root?.findNavController()?.navigate(R.id.action_fragmentPaymentInfo_to_fragmentLocationDetector)
        }
        binding?.imageViewAddresses?.setOnClickListener {
            childFragmentManager.beginTransaction().add(addressesDialog,null).commit()
        }
        //coupon
        binding?.buttonValidateCoupon?.setOnClickListener {
            viewModel.validateDiscount(binding?.editTextCoupon?.text.toString())
        }

        //placeOrder
        binding?.placeOrderBtn?.setOnClickListener {
            if(validatePhone(binding?.phoneText?.text.toString()))
            {
                  showMethodDialog()
            }else{
                Dialogs.SnakeToast(requireView(),"Please Enter Valid Data")
            }
        }

    }

    private fun showMethodDialog() {
        methodDialog.show()
    }

    override fun onResume() {
        super.onResume()
        if(UserSettings.isSelected)
        {
            binding?.tvAddres?.text = UserSettings.selectedAddress?.toAddressBody()?.address?.address1
            UserSettings.isSelected = false
        }
    }

    private fun showInfo() {
        binding?.phoneText?.setText(UserSettings.phoneNumber)
        binding?.tvAddres?.text = UserSettings.shippingAddress
        //setSubtotal,//setTotal,setShippingFees
        binding?.textViewShippingFees?.text = "${(30* currentCurrencyValue)} $currencyCode"
        binding?.editTextCoupon?.setText(UserSettings.userCurrentDiscountCopy?.code)
    }

    private fun validatePhone(phone: String): Boolean {
        return  Patterns.PHONE.matcher(phone).matches()
    }
    override fun onGooglePaySuccess(paymentMethodNonce: PaymentMethodNonce) {
        confirmOrder()
        Toast.makeText(requireContext(),"Payment Success",Toast.LENGTH_SHORT).show()
    }

    override fun onGooglePayFailure(error: Exception) {
        if (error is UserCanceledException) {
            Toast.makeText(requireContext(),"Payment Canceled",Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(requireContext(),"Payment Error$error", Toast.LENGTH_SHORT).show()
        }
    }

    override fun selectCurrency(currency: String) {

    }

    override fun selectAddress(address: String) {
        binding?.tvAddres?.text = address
        addressesDialog.dismiss()
    }

}