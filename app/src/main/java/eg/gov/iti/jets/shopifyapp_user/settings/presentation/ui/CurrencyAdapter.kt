package eg.gov.iti.jets.shopifyapp_user.settings.presentation.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import eg.gov.iti.jets.shopifyapp_user.databinding.CurrencyItemBinding

class CurrencyAdapter(private var currencies:MutableList<List<String>>, var currencySelected:CurrencyListener):
    RecyclerView.Adapter<CurrencyAdapter.CurrencyViewHolder>() {
    private lateinit var binding:CurrencyItemBinding
   inner class CurrencyViewHolder(var binding:CurrencyItemBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrencyViewHolder {
        val inflater: LayoutInflater =
            parent.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        binding =CurrencyItemBinding.inflate(inflater, parent, false)
        return CurrencyViewHolder(binding)
    }
    fun setCurrencies(currencies: MutableList<List<String>>){
        this.currencies = currencies
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return currencies.size
    }

    override fun onBindViewHolder(holder: CurrencyViewHolder, position: Int) {
       val currency = currencies[position]
        holder.binding.currencyTectView.text =currency?.get(0)+"/n"+currency?.get(1)
        holder.binding.currencyTectView.setOnClickListener {
            currencySelected.selectCurrency(currencies[holder.adapterPosition][0])
        }
    }

}