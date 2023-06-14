package eg.gov.iti.jets.shopifyapp_user.home.presentation.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import eg.gov.iti.jets.shopifyapp_user.R
import eg.gov.iti.jets.shopifyapp_user.databinding.CouponImageBinding
import eg.gov.iti.jets.shopifyapp_user.databinding.DicountItemBinding
import eg.gov.iti.jets.shopifyapp_user.home.domain.model.addsmodels.DiscountCode



class CouponAdapter( var discounts: List<DiscountCode> , var myListener: CouponClickListener) :
    RecyclerView.Adapter<CouponAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater: LayoutInflater = parent.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
       val  binding = DicountItemBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val discount = discounts[position]
        holder.binding.discountTitleTextView.text = discount.code
        holder.binding.discountValueTextView.text = "${discount.created_at} %"
        holder.binding.discountImageView.setImageDrawable(ResourcesCompat.getDrawable(holder.binding.root.resources,R.drawable.discount,null))
        holder.binding.discountCardView.setOnClickListener {
            myListener.onImageClick(discount)
        }
    }

    override fun getItemCount() = discounts.size

    inner class ViewHolder(var binding: DicountItemBinding) : RecyclerView.ViewHolder(binding.root)
}
