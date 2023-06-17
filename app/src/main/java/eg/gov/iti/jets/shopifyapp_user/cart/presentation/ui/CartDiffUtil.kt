package eg.gov.iti.jets.shopifyapp_user.cart.presentation.ui

import androidx.recyclerview.widget.DiffUtil
import eg.gov.iti.jets.shopifyapp_user.cart.data.model.LineItem

class CartDiffUtil : DiffUtil.ItemCallback<LineItem>() {

    override fun areItemsTheSame(oldItem: LineItem, newItem: LineItem): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: LineItem, newItem: LineItem): Boolean {
        return oldItem.product_id.toString() == newItem.product_id.toString()
    }
}