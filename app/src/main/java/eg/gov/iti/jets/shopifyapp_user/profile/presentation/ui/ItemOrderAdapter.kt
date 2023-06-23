package eg.gov.iti.jets.shopifyapp_user.profile.presentation.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import eg.gov.iti.jets.shopifyapp_user.R
import eg.gov.iti.jets.shopifyapp_user.base.model.orders.LineItemsOrder
import eg.gov.iti.jets.shopifyapp_user.cart.data.model.LineItem
import eg.gov.iti.jets.shopifyapp_user.databinding.ItemsOrderRowBinding
import eg.gov.iti.jets.shopifyapp_user.settings.data.local.UserSettings
import eg.gov.iti.jets.shopifyapp_user.util.formatDecimal
import eg.gov.iti.jets.shopifyapp_user.util.splitItemOrder

class ItemOrderAdapter(
    private var itemOrderList: List<LineItemsOrder>,
    val context: Context,
    var myListener: OnClickItemOrder
) : RecyclerView.Adapter<ItemOrderAdapter.ViewHolder>() {

    private lateinit var binding: ItemsOrderRowBinding

    fun setOrderList(values: List<LineItemsOrder?>?) {
        this.itemOrderList = values as List<LineItemsOrder>
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater: LayoutInflater =
            parent.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        binding = ItemsOrderRowBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItemOrder = itemOrderList[position]
        val result = splitItemOrder(currentItemOrder.title!!)
        val title = result.first
        val id = result.second
        holder.binding.orderTitleValue.text = title
        holder.binding.quantityValue.text = currentItemOrder.quantity.toString()
        holder.binding.priceValue.text =
            formatDecimal(currentItemOrder.price!!.toDouble() * UserSettings.currentCurrencyValue) + " ${UserSettings.currencyCode}"
        Glide.with(context)
            .load(currentItemOrder.properties[0].value)
            .into(holder.binding.itemImageView)
        holder.binding.itemOrderCardView.setOnClickListener {
            myListener.onClickItemOrder(currentItemOrder.properties[0].name.toLong())
        }
//        holder.binding.itemOrderCardView.startAnimation(
//            AnimationUtils.loadAnimation(
//                holder.itemView.context,
//                R.anim.anim_recyclerview
//            )
//        )
    }

    override fun getItemCount(): Int = itemOrderList.size

    inner class ViewHolder(var binding: ItemsOrderRowBinding) :
        RecyclerView.ViewHolder(binding.root)
}