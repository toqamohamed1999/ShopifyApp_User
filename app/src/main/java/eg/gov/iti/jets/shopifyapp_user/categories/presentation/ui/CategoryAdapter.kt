package eg.gov.iti.jets.shopifyapp_user.categories.presentation.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import eg.gov.iti.jets.shopifyapp_user.categories.domain.model.CustomCollections
import eg.gov.iti.jets.shopifyapp_user.databinding.BrandRowBinding
import eg.gov.iti.jets.shopifyapp_user.databinding.MaincategoryRowBinding

class CategoryAdapter (private var categoryList: List<CustomCollections>, val context: Context, var myListener: OnClickCategory) :
    RecyclerView.Adapter<CategoryAdapter.ViewHolder>(){

    private lateinit var binding: MaincategoryRowBinding

    fun setCategoryList(values: List<CustomCollections?>?) {
        this.categoryList = values as List<CustomCollections>
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater: LayoutInflater =
            parent.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        binding = MaincategoryRowBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentCategory = categoryList[position]
        holder.binding.categoryTitleTextView.text = currentCategory.title
        holder.binding.categoryImageView.clipToOutline = true
        Glide.with(context)
            .load(currentCategory.image?.src)
            .into(holder.binding.categoryImageView)
        holder.binding.categoryCardView.setOnClickListener {
            myListener.onCategoryClick(currentCategory.id)
        }
    }

    override fun getItemCount() = categoryList.size

    inner class ViewHolder(var binding: MaincategoryRowBinding) : RecyclerView.ViewHolder(binding.root)
}