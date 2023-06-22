package eg.gov.iti.jets.shopifyapp_user.profile.presentation.ui

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import eg.gov.iti.jets.shopifyapp_user.base.model.FavRoomPojo
import eg.gov.iti.jets.shopifyapp_user.databinding.WishlistItemBinding
import eg.gov.iti.jets.shopifyapp_user.profile.domain.local.OnWishListClick
import eg.gov.iti.jets.shopifyapp_user.settings.data.local.UserSettings
import eg.gov.iti.jets.shopifyapp_user.util.formatDecimal

class WishListAdapter(
    private var productList: List<FavRoomPojo>,
    val context: Context,
    var myListener: OnWishListClick
) :
    RecyclerView.Adapter<WishListAdapter.ViewHolder>() {


    private lateinit var binding: WishlistItemBinding

    @SuppressLint("NotifyDataSetChanged")
    fun setProductList(values: List<FavRoomPojo?>?) {
        this.productList = ArrayList()
        this.productList = values as List<FavRoomPojo>
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WishListAdapter.ViewHolder {
        val inflater: LayoutInflater =
            parent.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        binding = WishlistItemBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val current = productList[position]
        holder.binding.txtProductTitle.text = current.title
        holder.binding.txtPrice.text =
            formatDecimal(current.price!!.toDouble() * UserSettings.currentCurrencyValue) + " ${UserSettings.currencyCode}"
        Glide.with(context)
            .load(current.imageSrc)
            .into(holder.binding.productImage)
        holder.binding.wishListCard.setOnClickListener {
            myListener.onClickWishItem(current.productId!!)
        }
    }

    override fun getItemCount() = productList.size
    inner class ViewHolder(var binding: WishlistItemBinding) : RecyclerView.ViewHolder(binding.root)
}