package eg.gov.iti.jets.shopifyapp_user.cart.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import eg.gov.iti.jets.shopifyapp_user.base.model.Variants
import eg.gov.iti.jets.shopifyapp_user.cart.data.remote.DraftOrderAPIState
import eg.gov.iti.jets.shopifyapp_user.cart.data.model.DraftOrderResponse
import eg.gov.iti.jets.shopifyapp_user.cart.data.model.LineItem
import eg.gov.iti.jets.shopifyapp_user.cart.domain.repo.CartRepository
import eg.gov.iti.jets.shopifyapp_user.settings.data.local.UserSettings
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class CartViewModel(private val repo:CartRepository):ViewModel() {

    private val _cartOrder:MutableStateFlow<DraftOrderAPIState> = MutableStateFlow(DraftOrderAPIState.Loading())
     val cartOrder:StateFlow<DraftOrderAPIState> = _cartOrder
     private var cartDraftOrder: DraftOrderResponse? = null
     private var isGetVaiants=false
     var isAvailable:MutableLiveData<Pair<Int,Int>> = MutableLiveData(Pair(-1,0))
    private val variants :MutableList<Variants?> = mutableListOf()
     fun removeProductFromCart(product: LineItem){
                removeProductFromList(product)
     }
    private fun removeProductFromList(product: LineItem){
        val mlist:MutableList<LineItem> = mutableListOf()
        cartDraftOrder?.draft_order?.line_items?.forEach {
            if(it.id!=product.id)
            {
                mlist.add(it)
            }
        }
        cartDraftOrder?.draft_order?.line_items = mlist
        viewModelScope.launch {
            cartDraftOrder?.let {
                repo.updateProductsInCart(
                    UserSettings.cartDraftOrderId,
                    it
                )
            }
            _cartOrder.value = DraftOrderAPIState.Success(cartDraftOrder)
        }
    }
    fun updateProduct(type:Int,product: LineItem,index:Int){

        if(type==1)
        {
           if(checkQuantity(product)) {
               increaseProductQuantity(product)
               _cartOrder.value = DraftOrderAPIState.Success(cartDraftOrder)
           }else{
               noEnoughProducts(product,index)
           }
        }else if (type==-1){
            decreaseProductQuantity(product,index)
            _cartOrder.value = DraftOrderAPIState.Success(cartDraftOrder)
        }

    }

    private fun noEnoughProducts(product: LineItem,index:Int) {
        isAvailable.value = Pair(2,index) // no more available items after increase
    }

    private fun checkQuantity(product: LineItem): Boolean {
        var flag=false
        val id = product.applied_discount.description?.split(")")?.get(0)
        variants.forEach {
            if(it?.id==id?.toLong())
            {
                if((it?.inventoryQuantity?:0)>product.quantity){
                    flag=true
                }
            }
        }
        return flag
    }

    private fun increaseProductQuantity(product: LineItem){
        cartDraftOrder?.draft_order?.line_items?.forEach {
            if(it.id==product.id)
            {
                it.quantity+=1
            }
        }
        viewModelScope.launch {
            repo.updateProductsInCart(
                UserSettings.cartDraftOrderId,
                cartDraftOrder ?: DraftOrderResponse(null)
            )
        }
    }
    private fun decreaseProductQuantity(product: LineItem,index:Int){
        if(product.quantity==1){
            removeProductFromCart(product)
        }else {
            cartDraftOrder?.draft_order?.line_items?.forEach {
                if (it.id == product.id) {
                    it.quantity -= 1
                    checkAfterDecrease(product,index)
                }
            }
            viewModelScope.launch {
                repo.updateProductsInCart(
                    UserSettings.cartDraftOrderId,
                    cartDraftOrder ?: DraftOrderResponse(null)
                )
                _cartOrder.value = DraftOrderAPIState.Success(cartDraftOrder)
            }
        }
    }
    private fun checkAfterDecrease(product: LineItem,index:Int) {
      if(checkQuantity(product)){
          isAvailable.value =Pair(1,index) // now is available after decrease
      }
    }
    fun getCartProducts() {
         viewModelScope.launch {
             repo.getCartProducts(UserSettings.cartDraftOrderId).collect {
                if(it is DraftOrderAPIState.Success)
                {
                    if(!isGetVaiants) {
                        cartDraftOrder = it.order
                        getVaiants()
                        isGetVaiants=true
                    }else{
                        cartDraftOrder = it.order
                        _cartOrder.value = it
                    }
                }
             }
         }
     }

    private fun getVaiants() {
        getList( cartDraftOrder?.draft_order?.line_items).forEach {
                val variantId=it?.applied_discount?.description?.split(")")?.get(0)
                getVaiant(variantId)
            }
        _cartOrder.value = DraftOrderAPIState.Success(cartDraftOrder)
    }
fun getList(list: List<LineItem>?):List<LineItem>{
    var mlist:MutableList<LineItem> = mutableListOf()
    if(!list.isNullOrEmpty())
    if(list.size>1)
    {
        for(i in 1 until list.size){
            mlist.add(list[i])
        }
    }
    return mlist
}
    private fun getVaiant(variantId: String?) {
        viewModelScope.launch {
            repo.getVariantBYId(variantId?.toLong()?:0).collect{
                if(it!=null) {
                    variants.add(it.variant)
                }
            }
        }
    }
fun crearIsAvailable(){
    isAvailable.value = Pair(-1,0)
}
    fun clearOrder() {
        cartDraftOrder=null
    }
    fun checkAllAvailability(product:LineItem,index:Int){
        if(!checkQuantity(product)){
            isAvailable.value = Pair(3,index) // check Availability of item
        }
    }
}