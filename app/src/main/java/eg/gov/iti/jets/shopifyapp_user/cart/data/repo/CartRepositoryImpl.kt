package eg.gov.iti.jets.shopifyapp_user.cart.data.repo

import eg.gov.iti.jets.shopifyapp_user.cart.data.local.CartSharedPrefsOperations
import eg.gov.iti.jets.shopifyapp_user.cart.data.remote.DraftOrderAiState
import eg.gov.iti.jets.shopifyapp_user.cart.domain.model.DraftOrderResponse
import eg.gov.iti.jets.shopifyapp_user.cart.domain.model.LineItem
import eg.gov.iti.jets.shopifyapp_user.cart.domain.remote.DraftOrderRemoteSource
import eg.gov.iti.jets.shopifyapp_user.cart.domain.repo.CartRepository
import kotlinx.coroutines.flow.MutableStateFlow

class CartRepositoryImpl(
    private  val draftRemoteSource:DraftOrderRemoteSource,
    private val cartSharedPrefs:CartSharedPrefsOperations): CartRepository
{
    override fun saveDraftOrderId(draftId: String) {
        cartSharedPrefs.setCartDraftOrderId(draftId)
    }

    override fun getDraftOrderId(): String? {
        return cartSharedPrefs.getCartDraftOrderId()
    }

    override suspend fun createNewDraftOrder(order: DraftOrderResponse): MutableStateFlow<DraftOrderAiState> {
        return draftRemoteSource.createNewDraftOrder(order)
    }

    override suspend fun removeDraftOrder(oriderId: String): MutableStateFlow<DraftOrderAiState> {
        return draftRemoteSource.deleteDraftOrder(oriderId)
    }

    override suspend fun addProductToCart(product: LineItem) {
        getCartProducts().collect{
            if(it is DraftOrderAiState.Success) {
                val mlist: MutableList<LineItem> = mutableListOf()
                mlist.addAll(it.order?.draft_order?.line_items?: listOf())
                mlist.add(product)
                val draftOrder = it.order
                draftOrder?.draft_order?.line_items = mlist
                draftRemoteSource.updateDraftOrder(getDraftOrderId()?:"",draftOrder!!)
            }
        }
    }


    override suspend fun removeProductFromCart(product: LineItem){
        getCartProducts().collect{
            if(it is DraftOrderAiState.Success) {
                val mlist: MutableList<LineItem> = mutableListOf()
                it.order?.draft_order?.line_items?.forEach {item->
                    if(item.product_id!=product.product_id){
                        mlist.add(item)
                    }
                }
                val draftOrder = it.order
                draftOrder?.draft_order?.line_items = mlist
                draftRemoteSource.updateDraftOrder(getDraftOrderId()?:"",draftOrder!!)
            }
        }
    }

    override suspend fun updateProductInCart(product: LineItem) {
        getCartProducts().collect{
            if(it is DraftOrderAiState.Success) {
                val mlist: MutableList<LineItem> = mutableListOf()
                it.order?.draft_order?.line_items?.forEach {item->
                    if(item.product_id==product.product_id){
                        mlist.add(product)
                    }else{
                        mlist.add(item)
                    }
                }
                val draftOrder = it.order
                draftOrder?.draft_order?.line_items = mlist
                draftRemoteSource.updateDraftOrder(getDraftOrderId()?:"",draftOrder!!)
            }
        }
    }

    override suspend fun getCartProducts(): MutableStateFlow<DraftOrderAiState> {
        val id = getDraftOrderId()
        return try{
            draftRemoteSource.getDraftOrder(id?:"")}
        catch(error:java.lang.Exception) {
            MutableStateFlow(DraftOrderAiState.Error(error.message.toString()))
        }
    }
}