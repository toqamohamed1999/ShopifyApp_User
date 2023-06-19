package eg.gov.iti.jets.shopifyapp_user.productdetails.presentation.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import eg.gov.iti.jets.shopifyapp_user.R
import eg.gov.iti.jets.shopifyapp_user.base.model.Image
import eg.gov.iti.jets.shopifyapp_user.databinding.CouponImageBinding

lateinit var binding: CouponImageBinding

class ProductImageViewPagerAdapter(private val images: List<Image>) :
    RecyclerView.Adapter<ProductImageViewPagerAdapter.ViewHolder>() {

    inner class ViewHolder(var binding: CouponImageBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater: LayoutInflater = parent.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
       binding = CouponImageBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }
    fun setImage(image: Image) {

        Glide.with(binding.root.context)
            .load(image.src)
            .error(R.drawable.noimage)
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(binding.couponImageView)
    }
    override fun getItemCount() = images.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val image = images[position]
        setImage(image)

    }
}