package eg.gov.iti.jets.shopifyapp_user.favorite.presentation.ui

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import eg.gov.iti.jets.shopifyapp_user.R
import eg.gov.iti.jets.shopifyapp_user.base.model.FavRoomPojo
import eg.gov.iti.jets.shopifyapp_user.base.model.Product
import eg.gov.iti.jets.shopifyapp_user.databinding.ProductRowBinding
import eg.gov.iti.jets.shopifyapp_user.products.presentation.ui.OnClickProduct
import eg.gov.iti.jets.shopifyapp_user.settings.data.local.UserSettings
import eg.gov.iti.jets.shopifyapp_user.util.getTitleOfProduct


class FavProductAdapter(private var productList: List<FavRoomPojo>, val context: Context, var myListener: OnClickProduct) :
    RecyclerView.Adapter<FavProductAdapter.ViewHolder>() {

    private lateinit var binding: ProductRowBinding

    @SuppressLint("NotifyDataSetChanged")
    fun setProductList(values: List<FavRoomPojo?>?) {
        this.productList=ArrayList()
        this.productList = values as List<FavRoomPojo>
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater: LayoutInflater =
            parent.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        binding = ProductRowBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentProduct = productList[position]
        holder.binding.productTitleTextView.text = currentProduct?.title?.let { getTitleOfProduct(it) }
        holder.binding.productPriceTextView.text = (currentProduct.price!!.toDouble() * UserSettings.currentCurrencyValue).toString() + " ${UserSettings.currencyCode}"
        holder.binding.favIcon.setImageResource(R.drawable.favorite_icon)
        Glide.with(context)
            .load(currentProduct.imageSrc)
            .into(holder.binding.productImageView)
        holder.binding.productCardView.setOnClickListener {
            myListener.onClickProductCard(currentProduct.productId!!)
        }
        holder.binding.favIcon.setOnClickListener {
            myListener.onClickFavIcon(currentProduct.productId!!)
        }
    }

    override fun getItemCount() = productList.size

    inner class ViewHolder(var binding: ProductRowBinding) : RecyclerView.ViewHolder(binding.root)
}