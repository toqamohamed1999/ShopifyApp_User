package eg.gov.iti.jets.shopifyapp_user.payment.presentation.ui

import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
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
                childFragmentManager.popBackStack()
            }else{
                Dialogs.SnakeToast(requireView(),"Please Enter Valid Data")
            }
        }
        binding?.shippingInfoImageButtonMapAddress?.setOnClickListener {
            //show map to select address
        }
    }

    private fun validatePhone(phone: String): Boolean {
        return  Patterns.PHONE.matcher(phone).matches()
    }

}