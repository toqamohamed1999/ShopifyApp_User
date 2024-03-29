package eg.gov.iti.jets.shopifyapp_user.home.presentation.ui

import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import eg.gov.iti.jets.shopifyapp_user.R
import eg.gov.iti.jets.shopifyapp_user.databinding.CouponImageBinding
import eg.gov.iti.jets.shopifyapp_user.databinding.DicountItemBinding
import eg.gov.iti.jets.shopifyapp_user.home.domain.model.addsmodels.DiscountCode
import eg.gov.iti.jets.shopifyapp_user.productdetails.presentation.ui.binding
import eg.gov.iti.jets.shopifyapp_user.settings.data.local.UserSettings
import kotlin.math.roundToInt
import kotlin.random.Random


class CouponAdapter( var discounts: ArrayList<DiscountCode> , var myListener: CouponClickListener,var viewPager2: ViewPager2):RecyclerView.Adapter<CouponAdapter.ViewHolder>() {

    private val runnable = Runnable {
        discounts.addAll(discounts)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater: LayoutInflater = parent.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val  binding = DicountItemBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val discount = discounts[position]

        val img = Random.nextInt(6)
        when (img) {
            1 -> {
                holder.binding.imageViewdiscountImage.setImageDrawable(
                    ResourcesCompat.getDrawable(
                        holder.binding.root.resources,
                        R.drawable.fixed1,
                        null
                    )
                )
            }
            2 -> {
                holder.binding.imageViewdiscountImage.setImageDrawable(
                    ResourcesCompat.getDrawable(
                        holder.binding.root.resources,
                        R.drawable.fixed2,
                        null
                    )
                )
            }
            3 -> {
                holder.binding.imageViewdiscountImage.setImageDrawable(
                    ResourcesCompat.getDrawable(
                        holder.binding.root.resources,
                        R.drawable.fixed3,
                        null
                    )
                )
            }
            4 -> {
                holder.binding.imageViewdiscountImage.setImageDrawable(
                    ResourcesCompat.getDrawable(
                        holder.binding.root.resources,
                        R.drawable.fixed4,
                        null
                    )
                )

            }
            5 -> {
                holder.binding.imageViewdiscountImage.setImageDrawable(
                    ResourcesCompat.getDrawable(
                        holder.binding.root.resources,
                        R.drawable.fixed5,
                        null
                    )
                )

            }
            else -> {
                holder.binding.imageViewdiscountImage.setImageDrawable(
                    ResourcesCompat.getDrawable(
                        holder.binding.root.resources,
                        R.drawable.fixed6,
                        null
                    )
                )
            }
        }
        if(discount.updated_at=="percentage") {

            holder.binding.discountValueTextView.text = "${discount.created_at.replace("-", "")} %"

        }else if(discount.updated_at=="fixed_amount")
        {
            holder.binding.discountValueTextView.text = "${discount.created_at.replace("-", "")} ${UserSettings.currencyCode} OFF"
        }
        holder.binding.discountCardView.setOnClickListener {
            myListener.onImageClick(discount)
        }
        if (position == discounts.size-1){
            viewPager2.post(runnable)
        }
    }

    override fun getItemCount() = discounts.size

    inner class ViewHolder(var binding: DicountItemBinding) : RecyclerView.ViewHolder(binding.root)
}