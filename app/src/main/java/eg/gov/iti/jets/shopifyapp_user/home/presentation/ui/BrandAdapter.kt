package eg.gov.iti.jets.shopifyapp_user.home.presentation.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import eg.gov.iti.jets.shopifyapp_user.databinding.BrandRowBinding
import eg.gov.iti.jets.shopifyapp_user.home.domain.model.SmartCollection

class BrandAdapter(private var brandList: List<SmartCollection>, val context: Context) :
    RecyclerView.Adapter<BrandAdapter.ViewHolder>() {

    private lateinit var binding: BrandRowBinding

    fun setBrandList(values: List<SmartCollection?>?) {
        this.brandList = values as List<SmartCollection>
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
        Glide.with(context)
            .load(currentBrand.image?.src)
            .into(holder.binding.brandImageView)
    }

    override fun getItemCount() = brandList.size

    inner class ViewHolder(var binding: BrandRowBinding) : RecyclerView.ViewHolder(binding.root)
}