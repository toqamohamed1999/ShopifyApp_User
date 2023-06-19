package eg.gov.iti.jets.shopifyapp_user.profile.presentation.ui

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import eg.gov.iti.jets.shopifyapp_user.base.model.orders.Order
import eg.gov.iti.jets.shopifyapp_user.databinding.FragmentOrderDetailsBinding
import eg.gov.iti.jets.shopifyapp_user.util.convertDateTimeFormat

class OrderDetailsFragment : Fragment() {

    private lateinit var binding: FragmentOrderDetailsBinding
    private lateinit var itemOrderAdapter: ItemOrderAdapter
    val args: OrderDetailsFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentOrderDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val order = args.order

        binding.orderNumValue.text = order.order_number.toString()
        binding.dateValue.text = convertDateTimeFormat(order.processed_at!!)
        binding.priceValue.text = order.current_subtotal_price
        binding.taxPriceValue.text = order.total_tax

        itemOrderAdapter = ItemOrderAdapter(ArrayList(), requireActivity())
        val layoutManager = LinearLayoutManager(requireContext())
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        binding.itemsRecyclerView.layoutManager = layoutManager

        itemOrderAdapter.setOrderList(order.line_items)
        binding.itemsRecyclerView.adapter = itemOrderAdapter
    }

}