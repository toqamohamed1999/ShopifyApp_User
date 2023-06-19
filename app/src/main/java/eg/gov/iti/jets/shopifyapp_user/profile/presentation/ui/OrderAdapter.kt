package eg.gov.iti.jets.shopifyapp_user.profile.presentation.ui

import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import eg.gov.iti.jets.shopifyapp_user.R
import eg.gov.iti.jets.shopifyapp_user.base.model.orders.Order
import eg.gov.iti.jets.shopifyapp_user.databinding.OrderRowBinding
import eg.gov.iti.jets.shopifyapp_user.util.convertDateTimeFormat

class OrderAdapter(
    private var orderList: List<Order>,
    val context: Context,
    var myListener: OnClickOrder
) :
    RecyclerView.Adapter<OrderAdapter.ViewHolder>() {

    private lateinit var binding: OrderRowBinding

    fun setOrderList(values: List<Order?>?) {
        this.orderList = values as List<Order>
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater: LayoutInflater =
            parent.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        binding = OrderRowBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentOrder = orderList[position]
        holder.binding.orderNumValue.text = currentOrder.order_number.toString()
        holder.binding.dateValue.text = convertDateTimeFormat(currentOrder.processed_at!!)
        holder.binding.priceValue.text = currentOrder.total_price
        holder.binding.orderCardView.startAnimation(
            AnimationUtils.loadAnimation(
                holder.itemView.context,
                R.anim.anim_recyclerview
            )
        )
        holder.binding.orderCardView.setOnClickListener {
            myListener.onClickOrder(currentOrder)
        }
    }

    override fun getItemCount() = orderList.size

    inner class ViewHolder(var binding: OrderRowBinding) : RecyclerView.ViewHolder(binding.root)
}