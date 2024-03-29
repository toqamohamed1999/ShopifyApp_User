package eg.gov.iti.jets.shopifyapp_user.favorite.presentation.ui

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import eg.gov.iti.jets.shopifyapp_user.R
import eg.gov.iti.jets.shopifyapp_user.auth.data.remote.ResponseState
import eg.gov.iti.jets.shopifyapp_user.base.model.FavDraftOrderResponse
import eg.gov.iti.jets.shopifyapp_user.base.model.FavRoomPojo
import eg.gov.iti.jets.shopifyapp_user.databinding.FragmentFavoriteBinding
import eg.gov.iti.jets.shopifyapp_user.favorite.presentation.viewmodel.FavViewModel
import eg.gov.iti.jets.shopifyapp_user.products.presentation.ui.OnClickProduct
import eg.gov.iti.jets.shopifyapp_user.settings.data.local.UserSettings
import eg.gov.iti.jets.shopifyapp_user.util.Dialogs
import eg.gov.iti.jets.shopifyapp_user.util.isConnected
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


class FavoriteFragment : Fragment(), OnClickProduct {

    private lateinit var binding: FragmentFavoriteBinding
    private lateinit var viewModel: FavViewModel
    private lateinit var favAdapter:FavProductAdapter
    private var favList:List<FavRoomPojo> = emptyList()
    private var favDraftOrderResponse : FavDraftOrderResponse = FavDraftOrderResponse()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this)[FavViewModel::class.java]
         favAdapter = FavProductAdapter(ArrayList(), requireActivity() , this)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        if (!UserSettings.userAPI_Id.isNullOrEmpty()) {
            binding.loggedOutContainer.visibility = View.GONE
            binding.favRecycler.visibility = View.VISIBLE
            binding.txtSearch.visibility=View.VISIBLE
            binding.favRecycler.adapter = favAdapter
            binding.animationEmptyWish.visibility = View.GONE
            binding.txtnowishlist.visibility = View.GONE

            val textWatcher = object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                    // This method is called before the text is changed.
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    val filteredList = filteredMyListWithSequence(s.toString())
                    showNoMatchingResultIfFilteredListIsEmpty(filteredList)
                    if (filteredList != null) {
                        favAdapter.setProductList(filteredList)
                    }
                }

                override fun afterTextChanged(s: Editable?) {
                    // This method is called after the text has been changed.
                }
            }

            binding.searchEditText.addTextChangedListener(textWatcher)
            lifecycleScope.launch {

                viewModel.favProducts.collectLatest {
                    when (it) {
                        is ResponseState.Loading -> {

                        }
                        is ResponseState.Success -> {
                            favDraftOrderResponse=it.data!!
                        }
                        is ResponseState.Error -> {
                            println("Draft order Error ${it.exception}")
                        }
                    }
                }
            }

            lifecycleScope.launch {

                    viewModel.favorites.collect {
                        viewModel.list = it
                        favList = it
                        if (it.isEmpty()) {
                            binding.animationEmptyWish.visibility = View.VISIBLE
                            binding.txtnowishlist.visibility = View.VISIBLE
                            binding.favRecycler.visibility = View.GONE
                            binding.txtSearch.visibility = View.GONE

                        } else {
                            binding.animationEmptyWish.visibility = View.GONE
                            binding.txtnowishlist.visibility = View.GONE
                            binding.favRecycler.visibility = View.VISIBLE
                            binding.txtSearch.visibility = View.VISIBLE

                        }
                        favAdapter.setProductList(it)
                    }

            }

        }
        else{
            binding.loggedOutContainer.visibility = View.VISIBLE
            binding.favRecycler.visibility = View.GONE
            binding.txtSearch.visibility=View.GONE
            binding.txtnowishlist.visibility=View.GONE
            binding.btnlogin.setOnClickListener {
               Navigation.findNavController(requireView()).navigate(R.id.action_favoriteFragment_to_loginFragment)
            }

        }

    }

    private fun filteredMyListWithSequence(s: String): List<FavRoomPojo>? {

        return favList?.filter { it.title!!.lowercase().contains(s.lowercase()) }
    }
    private fun showNoMatchingResultIfFilteredListIsEmpty(filteredList: List<FavRoomPojo>?) {
        if (filteredList.isNullOrEmpty()) {
            binding.txtNoResults.visibility = View.VISIBLE
            binding.favRecycler.visibility = View.GONE
        } else {

            binding.txtNoResults.visibility = View.GONE
            binding.favRecycler.visibility = View.VISIBLE
        }
    }

    override fun onClickFavIcon(product_Id: Long) {
        if (isConnected(requireContext())) {
            viewModel.getFavRemoteProducts(UserSettings.favoriteDraftOrderId.toLong())

            val alertDialog = AlertDialog.Builder(context)

            alertDialog.apply {
                setIcon(R.drawable.baseline_delete_24)
                setTitle("Delete")
                setMessage("Are you sure you want to delete the Product from favorite?")
                setPositiveButton("Yes") { _: DialogInterface?, _: Int ->
                    favDraftOrderResponse.draft_order?.lineItems?.removeIf {e->e.productId==product_Id }
                    viewModel.updateFavDraftOrder(UserSettings.favoriteDraftOrderId.toLong(),favDraftOrderResponse)
                    viewModel.deleteFavProductWithId(product_Id!!)

                    Dialogs.SnakeToast(
                        requireView(),
                        resources.getString( R.string.delete_MSG_from_favorites)
                    )
                }
                setNegativeButton("Cancel") { _, _ ->

                }
            }.create().show()


        } else {
            Dialogs.SnakeToast(
                requireView(),
                resources.getString(R.string.noInternetConnection)
            )
        }

    }

    override fun onClickProductCard(product_Id: Long) {
        val action =
            FavoriteFragmentDirections.actionFavoriteFragmentToProductDetailsFragment(product_Id)
        binding.root.findNavController().navigate(action)
    }

    override fun onResume() {
        super.onResume()
        binding.txtNoResults.visibility=View.GONE
    }
}