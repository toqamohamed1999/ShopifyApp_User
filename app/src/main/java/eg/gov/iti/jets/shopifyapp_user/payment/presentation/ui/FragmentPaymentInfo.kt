package eg.gov.iti.jets.shopifyapp_user.payment.presentation.ui

import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import eg.gov.iti.jets.shopifyapp_user.R
import eg.gov.iti.jets.shopifyapp_user.cart.presentation.ui.CartPaymentDataCollector
import eg.gov.iti.jets.shopifyapp_user.databinding.FragmentPaymentInfoBinding
import eg.gov.iti.jets.shopifyapp_user.util.Dialogs

class FragmentPaymentInfo: Fragment() {
    private var binding: FragmentPaymentInfoBinding? = null
    var completeInfo: CartPaymentDataCollector?=null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPaymentInfoBinding.inflate(inflater,container,false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.shippingInfoButtonContinuePayment?.setOnClickListener {
               val address = binding?.shippingInfoEditTextAddress?.text.toString()
            val phone = binding?.shippingInfoEditTextPhone?.text.toString()
            if(validatePhone(phone)&& address.isNotEmpty())
            {
                completeInfo?.getOrderPaymentDetails(address,phone,false)
                showMethodChooser()

            }else{
                Dialogs.SnakeToast(requireView(),"Please Enter Valid Data")
            }
        }
        binding?.shippingInfoImageButtonMapAddress?.setOnClickListener {
            //show map to select address
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
            completeInfo?.getOrderPaymentMethod(0)
            alertDialog.dismiss()
        }
        btnCashOnDelivery?.setOnClickListener {
            completeInfo?.getOrderPaymentMethod(1)
            alertDialog.dismiss()
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