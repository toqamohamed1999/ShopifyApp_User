package eg.gov.iti.jets.shopifyapp_user.home.presentation.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import eg.gov.iti.jets.shopifyapp_user.databinding.CouponImageBinding

private lateinit var binding: CouponImageBinding

class CouponAdapter(private val images: List<Int> , var myListener: CouponClickListener) :
    RecyclerView.Adapter<CouponAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater: LayoutInflater = parent.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        binding = CouponImageBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val image = images[position]
        holder.binding.couponImageView.setImageResource(image)
        holder.binding.couponImageView.setOnClickListener {
            myListener.onImageClick(image)
        }
    }

    override fun getItemCount() = images.size

    inner class ViewHolder(var binding: CouponImageBinding) : RecyclerView.ViewHolder(binding.root)
}