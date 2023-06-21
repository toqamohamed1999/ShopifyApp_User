package eg.gov.iti.jets.shopifyapp_user.products.presentation.ui

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import eg.gov.iti.jets.shopifyapp_user.R
import eg.gov.iti.jets.shopifyapp_user.base.model.FavRoomPojo
import eg.gov.iti.jets.shopifyapp_user.databinding.ProductRowBinding
import eg.gov.iti.jets.shopifyapp_user.base.model.Product
import eg.gov.iti.jets.shopifyapp_user.settings.data.local.UserSettings
import eg.gov.iti.jets.shopifyapp_user.util.getTitleOfProduct

class ProductsAdapter(
    private var productList: List<Product>,
    private var favList: List<FavRoomPojo>,
    val context: Context,
    var myListener: OnClickProduct
) :
    RecyclerView.Adapter<ProductsAdapter.ViewHolder>() {

    private lateinit var binding: ProductRowBinding
    private var isFav = false

    @SuppressLint("NotifyDataSetChanged")
    fun setProductList(values: List<Product?>?) {
        this.productList = ArrayList()
        this.productList = values as List<Product>
        notifyDataSetChanged()
    }

    fun setFavList(favList: List<FavRoomPojo>?) {
        if (favList != null) {
            this.favList = favList
        }
    }

    fun getIsFav(): Boolean {
        return isFav
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater: LayoutInflater =
            parent.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        binding = ProductRowBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentProduct = productList[position]
        if(UserSettings.userAPI_Id.isEmpty()){
            holder.binding.favIcon.visibility= View.GONE
        }
        holder.binding.productTitleTextView.text =
            currentProduct?.title?.let { getTitleOfProduct(it) }
        holder.binding.productPriceTextView.text =
            (currentProduct.variants[0].price!!.toDouble() * UserSettings.currentCurrencyValue).toString() + " ${UserSettings.currencyCode}"
        holder.binding.favIcon.setImageResource(setFavoriteIcon(currentProduct, favList))
        Glide.with(context)
            .load(currentProduct.image.src)
            .into(holder.binding.productImageView)
        holder.binding.productCardView.setOnClickListener {
            myListener.onClickProductCard(currentProduct.id!!)
        }
        holder.binding.favIcon.setOnClickListener {
            myListener.onClickFavIcon(currentProduct.id!!)
            if (isFav) {
                val alertDialog = AlertDialog.Builder(context)
                alertDialog.apply {
                    setIcon(R.drawable.baseline_delete_24)
                    setTitle("Delete")
                    setMessage("Are you sure you want to delete the Product from favorite?")
                    setPositiveButton("Yes") { _: DialogInterface?, _: Int ->
                        isFav = false
                        holder.binding.favIcon.setImageResource(R.drawable.favorite_border_icon)
                        Snackbar.make(
                            binding.root,
                            R.string.delete_MSG_from_favorites,
                            Snackbar.LENGTH_LONG
                        ).show()
                    }
                    setNegativeButton("Cancel") { _, _ ->
                    }
                }.create().show()
            } else {
                isFav = true
                holder.binding.favIcon.setImageResource(R.drawable.favorite_icon)
            }
        }
    }

    override fun getItemCount() = productList.size

    inner class ViewHolder(var binding: ProductRowBinding) : RecyclerView.ViewHolder(binding.root)

    fun setFavoriteIcon(product: Product, favProducts: List<FavRoomPojo>): Int {
        val isFavorite = favProducts.any { it.productId == product.id }
        return if (isFavorite) {
            isFav = true
            R.drawable.favorite_icon
        } else {
            isFav = false
            R.drawable.favorite_border_icon
        }
    }
}

