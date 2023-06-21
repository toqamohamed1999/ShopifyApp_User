package eg.gov.iti.jets.shopifyapp_user.home.presentation.ui

import android.content.Context
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

        if(discount.updated_at=="percentage") {
            holder.binding.imageViewdiscountImage.visibility=View.GONE
            holder.binding.textViewvalueFixed.visibility=View.GONE
            holder.binding.discountTitleTextView.text = discount.code
            holder.binding.discountValueTextView.text = "${discount.created_at.replace("-", "")} %"
            val img = Random.nextInt(5)
            when (img) {
                1 -> {
                    holder.binding.discountImageView.setImageDrawable(
                        ResourcesCompat.getDrawable(
                            holder.binding.root.resources,
                            R.drawable.x1,
                            null
                        )
                    )
                }
                2 -> {
                    holder.binding.discountImageView.setImageDrawable(
                        ResourcesCompat.getDrawable(
                            holder.binding.root.resources,
                            R.drawable.x2,
                            null
                        )
                    )
                }
                3 -> {
                    holder.binding.discountImageView.setImageDrawable(
                        ResourcesCompat.getDrawable(
                            holder.binding.root.resources,
                            R.drawable.x3,
                            null
                        )
                    )
                }
                4 -> {
                    holder.binding.discountImageView.setImageDrawable(
                        ResourcesCompat.getDrawable(
                            holder.binding.root.resources,
                            R.drawable.x4,
                            null
                        )
                    )

                }
                else -> {
                    holder.binding.discountImageView.setImageDrawable(
                        ResourcesCompat.getDrawable(
                            holder.binding.root.resources,
                            R.drawable.x5,
                            null
                        )
                    )
                }
            }
        }else if(discount.updated_at=="fixed_amount")
        {
            holder.binding.discountTitleTextView.visibility=View.GONE
            holder.binding.discountValueTextView.visibility=View.GONE
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