package eg.gov.iti.jets.shopifyapp_user.cart.presentation.ui

import android.content.Context
import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import eg.gov.iti.jets.shopifyapp_user.cart.domain.model.LineItem
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
        val product = getItem(position)
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
        // item info
        Picasso.get().load(product.properties[0]).into(holder.binding.cartImageViewProduct)
        holder.binding.cartItemTitle.text = product.title
        holder.binding.cartTextViewAmount.text = product.quantity.toString()
        holder.binding.cartItemPrice.text = UserSettings.getPrice(product.price).toString()
        holder.binding.cartItemTotalPrice.text = (UserSettings.getPrice(product.price)*product.quantity).toString()
    }
}