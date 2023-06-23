package eg.gov.iti.jets.shopifyapp_user.productdetails.presentation.ui

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import eg.gov.iti.jets.shopifyapp_user.R
import eg.gov.iti.jets.shopifyapp_user.base.model.Variants
import eg.gov.iti.jets.shopifyapp_user.databinding.VariantItemBinding
import eg.gov.iti.jets.shopifyapp_user.productdetails.domain.local.VariantClick

class VariantsAdapter(
    val context: Context,
    private var variantList: List<Variants>,
    var myListener: VariantClick

) : RecyclerView.Adapter<VariantsAdapter.ViewHolder>() {
    private lateinit var binding: VariantItemBinding
    private var selectedPosition: Int =0
    inner class ViewHolder(var binding: VariantItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater: LayoutInflater =
            parent.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        binding = VariantItemBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    @SuppressLint("ResourceAsColor", "NotifyDataSetChanged")
    override fun onBindViewHolder(holder: ViewHolder, @SuppressLint("RecyclerView") position: Int) {
        val current = variantList[position]
        if (position==selectedPosition){
            holder.binding.rowVariant.strokeColor= R.color.secondary_color
            holder.binding.rowVariant.strokeWidth=4
        }
        else{
            holder.binding.rowVariant.strokeColor= R.color.white
            holder.binding.rowVariant.strokeWidth=0
        }

            holder.binding.txtSize.text = current.option1
            if (current.sku?.contains("-") == true) {
                val list = current.sku?.split("-")
                holder.binding.txtColor.text = list?.get(2) ?: ""
            } else {
                holder.binding.txtColor.text = current.sku
            }
        holder.binding.rowVariant.setOnClickListener {
            myListener.onClickVariant(current)
            selectedPosition=position
            notifyDataSetChanged();
        }


    }

    override fun getItemCount() = variantList.count()
}


