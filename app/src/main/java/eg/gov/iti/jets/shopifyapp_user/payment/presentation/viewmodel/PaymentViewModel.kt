package eg.gov.iti.jets.shopifyapp_user.payment.presentation.viewmodel


import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import eg.gov.iti.jets.shopifyapp_user.base.model.orders.*
import eg.gov.iti.jets.shopifyapp_user.cart.data.model.DraftOrderResponse
import eg.gov.iti.jets.shopifyapp_user.cart.data.remote.DraftOrderAPIState
import eg.gov.iti.jets.shopifyapp_user.cart.domain.repo.CartRepository
import eg.gov.iti.jets.shopifyapp_user.home.domain.model.addsmodels.DiscountCode
import eg.gov.iti.jets.shopifyapp_user.home.domain.repo.AddsRepo
import eg.gov.iti.jets.shopifyapp_user.payment.domain.repo.PaymentRepo
import eg.gov.iti.jets.shopifyapp_user.settings.data.local.UserSettings
import eg.gov.iti.jets.shopifyapp_user.settings.data.local.UserSettings.userAPI_Id
import eg.gov.iti.jets.shopifyapp_user.settings.data.local.UserSettings.userCurrentDiscountCopy
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class PaymentViewModel(private val cartRepo:CartRepository,
                       private val repo: PaymentRepo,
                       private val addsRepo: AddsRepo
                       ):ViewModel() {
    private var _mode:MutableStateFlow<Int> = MutableStateFlow(0)
    var mode: StateFlow<Int> = _mode
    private var order: Order?= Order(line_items = listOf<LineItemsOrder>())
    private var draftOrder:DraftOrderResponse?=null

    fun resetMode(){
        _mode.value = 0
    }
    fun getCartDraftOrder() {
        viewModelScope.launch {
            cartRepo.getCartProducts(UserSettings.cartDraftOrderId).collectLatest {
                if( it is DraftOrderAPIState.Success){
                 draftOrder = it.order
                }
            }
        }
    }
 fun setDiscount(discountCode: DiscountCode?,totalPrice:Double)
 {
     order?.total_discounts =userCurrentDiscountCopy?.created_at.toString()
     UserSettings.userCurrentDiscountCopy =discountCode
     order?.total_price = totalPrice.toString()
 }
fun setAddress(){
   // order?.shipping_address = UserSettings.shippingAddress

    order?.currency = UserSettings.currencyCode
    //order?.client_details = UserSettings.userName + ", " +  UserSettings.userEmail + ", " + UserSettings.phoneNumber
    order?.email=UserSettings.userEmail
    order?.send_receipt=true
    order?.fulfillment_status ="fulfilled"
    order?.merchant_of_record_app_id = "Shopify App Merchants"
    order?.current_subtotal_price = draftOrder?.draft_order?.subtotal_price
    order?.customer = CustomerOrder(id=UserSettings.userAPI_Id.toLong())
    Log.e("",".....................$userAPI_Id.................")
    order?.current_total_price =draftOrder?.draft_order?.total_price
    order?.total_price = draftOrder?.draft_order?.total_price
    order?.confirmed = true

}
    fun confirmOrder() {
        order?.line_items = draftOrder?.draft_order?.line_items?.takeLast((draftOrder?.draft_order?.line_items?.size?:1)-1)?.map {
            it.toLineItemOrder()
        }
       viewModelScope.launch {
           launch {
               draftOrder?.draft_order?.line_items = draftOrder?.draft_order?.line_items?.take(1)!!
               cartRepo.updateProductsInCart(draftOrder?.draft_order?.id.toString(), draftOrder!!)
               UserSettings.cartQuantity = 0
               UserSettings.saveSettings()
           }.join()
           launch {
              Log.e("", Gson().toJson(Order.OrderBody(order),Order.OrderBody::class.java).toString())
               repo.postOrder(Order.OrderBody(order)).collect{
                   Log.e("",(it.order.toString()))
               }
           }
        }
    }
    fun validateDiscount() {
        var flag = false
    }
}