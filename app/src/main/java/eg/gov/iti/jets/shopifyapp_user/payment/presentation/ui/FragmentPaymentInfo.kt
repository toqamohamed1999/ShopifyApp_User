package eg.gov.iti.jets.shopifyapp_user.payment.presentation.ui

import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import eg.gov.iti.jets.shopifyapp_user.R
import eg.gov.iti.jets.shopifyapp_user.base.remote.AppRetrofit
import eg.gov.iti.jets.shopifyapp_user.databinding.FragmentPaymentInfoBinding
import eg.gov.iti.jets.shopifyapp_user.payment.data.remote.PaymentRemoteSourceImpl
import eg.gov.iti.jets.shopifyapp_user.payment.data.repo.PaymentRepoImpl
import eg.gov.iti.jets.shopifyapp_user.payment.domain.remote.PaymentAPIServices
import eg.gov.iti.jets.shopifyapp_user.payment.domain.remote.PaymentRemoteSource
import eg.gov.iti.jets.shopifyapp_user.payment.presentation.viewmodel.PaymentViewModel
import eg.gov.iti.jets.shopifyapp_user.payment.presentation.viewmodel.PaymentViewModelFactory
import eg.gov.iti.jets.shopifyapp_user.productdetails.presentation.ui.ProductDetailsFragmentArgs
import eg.gov.iti.jets.shopifyapp_user.settings.data.local.UserSettings
import eg.gov.iti.jets.shopifyapp_user.util.Dialogs
import retrofit2.create

class FragmentPaymentInfo: Fragment() {
    private var binding: FragmentPaymentInfoBinding? = null
    private val args: FragmentPaymentInfoArgs by navArgs()
    private val viewModel by viewModels<PaymentViewModel> {
        PaymentViewModelFactory(PaymentRepoImpl(PaymentRemoteSourceImpl(
            AppRetrofit.retrofit.create(PaymentAPIServices::class.java))))
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentPaymentInfoBinding.inflate(inflater,container,false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.setDraftOrder(args.draftOrder)
        showInfo()
       setUpActions()
    }

    private fun setUpActions() {
        binding?.shippingInfoButtonContinuePayment?.setOnClickListener {
            val address = binding?.shippingInfoEditTextAddress?.text.toString()
            val phone = binding?.shippingInfoEditTextPhone?.text.toString()
            if(validatePhone(phone)&& address.isNotEmpty())
            {
              /*  completeInfo?.getOrderPaymentDetails(address,phone,binding?.checkBox?.isSelected?:false)
                showMethodChooser()*/

            }else{
                Dialogs.SnakeToast(requireView(),"Please Enter Valid Data")
            }
        }
        binding?.shippingInfoImageButtonMapAddress?.setOnClickListener {
            binding?.root?.findNavController()?.navigate(R.id.action_fragmentPaymentInfo_to_fragmentLocationDetector)
        }
    }
    /* override fun getOrderPaymentDetails(shippingAddress: String, phone: String,saveForEveryTime:Boolean) {
            if(saveForEveryTime)
            {
                UserSettings.shippingAddress=shippingAddress
                UserSettings.phoneNumber =phone
                UserSettings.saveSettings()
            }
            orderPaymentDetails.paymentAddress = shippingAddress
            orderPaymentDetails.paymentPhone = phone
        }*/
    private fun showInfo() {

        binding?.shippingInfoEditTextAddress?.setText(UserSettings.shippingAddress)
        binding?.shippingInfoEditTextPhone?.setText(UserSettings.phoneNumber)
    }

    override fun onResume() {
        super.onResume()
        if(UserSettings.isSelected)
        {
            binding?.shippingInfoEditTextAddress?.setText(UserSettings.selectedAddress)
            UserSettings.isSelected=false
        }
    }
    private fun showMethodChooser() {
        val inflater = this.layoutInflater
        val builder =  AlertDialog.Builder(requireContext())
        val dialogView = inflater.inflate(R.layout.fragment_buy_with_x_method, null)
        builder.setView(dialogView)
        val btnCashOnDelivery = dialogView.findViewById<ImageView>(R.id.method_pay_cach_on_delivery)
        val btnBayGooglePay =  dialogView.findViewById<ImageView>(R.id.method_pay_google_pay)

        val alertDialog = builder.create()
        btnBayGooglePay?.setOnClickListener {
          /*  completeInfo?.getOrderPaymentMethod(0)
            alertDialog.dismiss()*/
        }
        btnCashOnDelivery?.setOnClickListener {
            /*completeInfo?.getOrderPaymentMethod(1)
            alertDialog.dismiss()*/
        }
        alertDialog.setOnDismissListener{
            binding?.root?.findNavController()?.popBackStack()
        }
        alertDialog.setCancelable(false)
        alertDialog.show()
    }

    private fun validatePhone(phone: String): Boolean {
        return  Patterns.PHONE.matcher(phone).matches()
    }

}