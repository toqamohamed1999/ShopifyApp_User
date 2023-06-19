package eg.gov.iti.jets.shopifyapp_user.home.presentation.ui

import androidx.recyclerview.widget.DiffUtil
import eg.gov.iti.jets.shopifyapp_user.home.domain.model.addsmodels.DiscountCode

class AddsDiffUtils : DiffUtil.ItemCallback<DiscountCode>() {

    override fun areItemsTheSame(oldItem: DiscountCode, newItem: DiscountCode): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: DiscountCode, newItem: DiscountCode): Boolean {
        return oldItem.code == newItem.code
    }
}