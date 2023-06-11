package eg.gov.iti.jets.shopifyapp_user.payment.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import eg.gov.iti.jets.shopifyapp_user.cart.presentation.ui.CartPaymentDataCollector
import eg.gov.iti.jets.shopifyapp_user.databinding.FragmentBuyWithXMethodBinding


class FragmentPaymentMethod : DialogFragment() {
    private var binding:FragmentBuyWithXMethodBinding? = null
    var completeChoosingMethod:CartPaymentDataCollector?=null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBuyWithXMethodBinding.inflate(inflater,container,false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.methodPayCachOnDelivery?.setOnClickListener {

            completeChoosingMethod?.getOrderPaymentMethod(0)
            dismiss()
        }
        binding?.methodPayGooglePay?.setOnClickListener {

            completeChoosingMethod?.getOrderPaymentMethod(1)
            dismiss()
        }
    }

}