package eg.gov.iti.jets.shopifyapp_user.cart.presentation.ui

import androidx.recyclerview.widget.DiffUtil

class CartDiffUtil : DiffUtil.ItemCallback<Any>() {

    override fun areItemsTheSame(oldItem: Any, newItem: Any): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Any, newItem: Any): Boolean {
        return true
    }
}