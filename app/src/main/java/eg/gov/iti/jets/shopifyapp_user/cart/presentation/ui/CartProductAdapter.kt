package eg.gov.iti.jets.shopifyapp_user.cart.presentation.ui

import android.content.Context
import android.content.DialogInterface
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.squareup.picasso.Picasso
import eg.gov.iti.jets.shopifyapp_user.R
import eg.gov.iti.jets.shopifyapp_user.cart.data.model.LineItem
import eg.gov.iti.jets.shopifyapp_user.databinding.CartItemBinding
import eg.gov.iti.jets.shopifyapp_user.settings.data.local.UserSettings
import eg.gov.iti.jets.shopifyapp_user.util.Dialogs

class CartProductAdapter(private val listener: CartItemListener): ListAdapter<LineItem,CartProductAdapter.CartItemViewHolder>(CartDiffUtil()) {

    lateinit var binding: CartItemBinding

    inner class CartItemViewHolder(
        var binding:CartItemBinding
        ): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartItemViewHolder {
        val inflater =
            parent.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        binding = CartItemBinding.inflate(inflater, parent, false)
        return CartItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CartItemViewHolder, position: Int) {
        val product:LineItem? = getItem(position)
        //item Actions
        holder.binding.cartImageViewDecreaseProduct.setOnClickListener {
            listener.increaseProductInCart(getItem(holder.adapterPosition))
        }
        holder.binding.cartImageViewIncreaseProduct.setOnClickListener {
            listener.decreaseProductInCart(getItem(holder.adapterPosition))
        }
        holder.binding.cartImageViewDeleteProduct.setOnClickListener {
            Dialogs.alertDialogBuilder(holder.binding.root.context,"Alert","Delete This Item ?")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton("Yes") { _: DialogInterface?, _: Int -> listener.removerProduct(getItem(holder.adapterPosition)) }
                .setNegativeButton("No") { i: DialogInterface?, _: Int ->
                    i?.dismiss()
                }.create().show()
        }
        val imageUrl=product?.applied_discount?.description
        // item info
        Glide.with(holder.binding.root.context)
            .load(imageUrl)
            .error(R.drawable.noimage)
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(holder.binding.cartImageViewProduct)
        holder.binding.cartItemTitle.text = product?.title?.split("|")?.last()
        holder.binding.cartTextViewAmount.text = product?.quantity?.toString()
        if(product!=null) {
            holder.binding.cartItemPrice.text =
                "Price : " + UserSettings.getPrice(product.price.toDouble())
                    .toString() + UserSettings.getPriceSymbol()
            holder.binding.cartItemTotalPrice.text =
                "Total  : " + (UserSettings.getPrice(product.price.toDouble()) * product.quantity).toString()
        }
    }
}