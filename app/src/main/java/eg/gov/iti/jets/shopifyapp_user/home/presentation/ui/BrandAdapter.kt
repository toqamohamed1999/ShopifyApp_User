package eg.gov.iti.jets.shopifyapp_user.home.presentation.ui

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import eg.gov.iti.jets.shopifyapp_user.databinding.BrandRowBinding
import eg.gov.iti.jets.shopifyapp_user.home.domain.model.SmartCollection
import eg.gov.iti.jets.shopifyapp_user.R

class BrandAdapter(private var brandList: List<SmartCollection>, val context: Context, var myListener: OnClickBrand) :
    RecyclerView.Adapter<BrandAdapter.ViewHolder>() {

    private lateinit var binding: BrandRowBinding

    @SuppressLint("NotifyDataSetChanged")
    fun setBrandList(values: List<SmartCollection?>?) {
        this.brandList = values as List<SmartCollection>
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater: LayoutInflater =
            parent.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        binding = BrandRowBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentBrand = brandList[position]
        holder.binding.brandTitleTextView.text = currentBrand.title
//        holder.binding.brandCardView.startAnimation(
//            AnimationUtils.loadAnimation(
//                holder.itemView.context,
//                R.anim.anim_recyclerview
//            )
//        )
        Glide.with(context)
            .load(currentBrand.image?.src)
            .into(holder.binding.brandImageView)
        holder.binding.brandCardView.setOnClickListener {
            myListener.onBrandClick(currentBrand.title)
        }
    }

    override fun getItemCount() = brandList.size

    inner class ViewHolder(var binding: BrandRowBinding) : RecyclerView.ViewHolder(binding.root)
}