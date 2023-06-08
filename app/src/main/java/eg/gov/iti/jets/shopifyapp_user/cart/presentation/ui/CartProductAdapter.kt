package eg.gov.iti.jets.shopifyapp_user.cart.presentation.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import eg.gov.iti.jets.shopifyapp_user.databinding.CartItemBinding
/*
class CartProductAdapter: ListAdapter<Product,CartProductAdapter.CartItemViewHolder>(CartDiffUtil()) {

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

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: CartItemViewHolder, position: Int) {
        TODO("Not yet implemented")
    }
}*/