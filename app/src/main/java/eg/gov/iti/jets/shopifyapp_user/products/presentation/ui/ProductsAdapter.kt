package eg.gov.iti.jets.shopifyapp_user.products.presentation.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import eg.gov.iti.jets.shopifyapp_user.databinding.ProductRowBinding
import eg.gov.iti.jets.shopifyapp_user.base.model.Product
import eg.gov.iti.jets.shopifyapp_user.util.getTitleOfProduct

class ProductsAdapter(private var productList: List<Product>, val context: Context, var myListener: OnClickProduct) :
    RecyclerView.Adapter<ProductsAdapter.ViewHolder>() {

    private lateinit var binding: ProductRowBinding

    fun setProductList(values: List<Product?>?) {
        this.productList = values as List<Product>
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater: LayoutInflater =
            parent.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        binding = ProductRowBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentProduct = productList[position]
        holder.binding.productTitleTextView.text = getTitleOfProduct(currentProduct.title)
        holder.binding.productPriceTextView.text = currentProduct.variants[0].price
        Glide.with(context)
            .load(currentProduct.image.src)
            .into(holder.binding.productImageView)
        holder.binding.productCardView.setOnClickListener {
            myListener.onClickProductCard(currentProduct)
        }
    }

    override fun getItemCount() = productList.size

    inner class ViewHolder(var binding: ProductRowBinding) : RecyclerView.ViewHolder(binding.root)
}