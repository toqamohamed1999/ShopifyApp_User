package eg.gov.iti.jets.shopifyapp_user.settings.presentation.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import eg.gov.iti.jets.shopifyapp_user.databinding.AddressItemBinding
import eg.gov.iti.jets.shopifyapp_user.databinding.ReviewItemBinding
import eg.gov.iti.jets.shopifyapp_user.settings.domain.model.Addresse

class AdressesAdapter(private var addresses:List<Addresse>, var removeAddress:(address:Addresse)->Unit):
    RecyclerView.Adapter<AdressesAdapter.AddressViewHolder>() {
    private lateinit var binding:AddressItemBinding
   inner class AddressViewHolder(var binding:AddressItemBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddressViewHolder {
        val inflater: LayoutInflater =
            parent.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        binding =AddressItemBinding.inflate(inflater, parent, false)
        return AddressViewHolder(binding)
    }
    fun setAddresss(address:List<Addresse>){
        addresses = address
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return addresses.size
    }

    override fun onBindViewHolder(holder: AddressViewHolder, position: Int) {
       val address = addresses[position]
        holder.binding.deteteAddressButton.setOnClickListener {
           removeAddress(address)
       }
        holder.binding.textViewAddress.text = "${address.country} ${address.city}\n ${address.address1}"
    }

}