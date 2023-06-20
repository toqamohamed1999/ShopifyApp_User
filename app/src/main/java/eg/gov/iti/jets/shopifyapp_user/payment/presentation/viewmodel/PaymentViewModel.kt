package eg.gov.iti.jets.shopifyapp_user.payment.presentation.viewmodel

import eg.gov.iti.jets.shopifyapp_user.base.model.orders.Order



import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import eg.gov.iti.jets.shopifyapp_user.base.model.orders.LineItemsOrder
import eg.gov.iti.jets.shopifyapp_user.base.model.orders.ShippingAddress
import eg.gov.iti.jets.shopifyapp_user.cart.data.model.DraftOrderResponse
import eg.gov.iti.jets.shopifyapp_user.cart.data.remote.DraftOrderAPIState
import eg.gov.iti.jets.shopifyapp_user.cart.domain.repo.CartRepository
import eg.gov.iti.jets.shopifyapp_user.home.domain.repo.AddsRepo
import eg.gov.iti.jets.shopifyapp_user.payment.domain.repo.PaymentRepo
import eg.gov.iti.jets.shopifyapp_user.settings.data.local.UserSettings
import eg.gov.iti.jets.shopifyapp_user.settings.data.local.UserSettings.toAddressBody
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
    private var validatorFlag=0
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
 fun setDiscount()
 {
     order?.total_discounts =userCurrentDiscountCopy?.created_at.toString()

 }
fun setAddress(){
    order?.shipping_address = UserSettings.shippingAddress
    order?.customer?.default_address=
        ShippingAddress(UserSettings.selectedAddress?.getAddressLine(0),
        UserSettings.selectedAddress?.countryName.toString(),
        UserSettings.selectedAddress?.adminArea?:"",
        country_code = UserSettings.currencyCode
    )
    order?.currency = UserSettings.currencyCode
    order?.client_details = UserSettings.userName + ", " +  UserSettings.userEmail + ", " + UserSettings.phoneNumber
    order?.merchant_of_record_app_id = "Shopify App Merchants"
    order?.current_subtotal_price = draftOrder?.draft_order?.subtotal_price

    order?.current_total_price =draftOrder?.draft_order?.total_price
    order?.total_price = draftOrder?.draft_order?.total_price
    order?.confirmed = true

}
    fun confirmOrder() {
        order?.line_items = draftOrder?.draft_order?.line_items?.map {
            val arr = it.applied_discount.description?.split(")")
            var url:String? = ""
            if((arr?.size?:0)>1)
            {
                url = arr?.get(1)
            }
            LineItemsOrder(it.price,it.quantity, title ="${it.title})${arr?.get(0)}" )

        }
        viewModelScope.launch {
            cartRepo.removeDraftOrder(draftOrder?.draft_order?.id.toString()).collectLatest {
                if(it is DraftOrderAPIState.Success){
                    _mode.value = 2
                }
            }
        }
        //here to save the order
    }
    fun validateDiscount(discountCode:String){
       viewModelScope.launch {
           launch {
               addsRepo.getAllPriceRules().collectLatest {
                   it?.price_rules?.forEach { priceRule ->
                       addsRepo.getAllDiscountsForPriceRule(priceRule.id.toString())
                           .collectLatest { disounts ->
                               disounts?.discount_codes?.forEach {
                                   if (it.code == discountCode) {
                                       validatorFlag = 1
                                       setDiscount()
                                   }
                               }

                           }
                   }
               }
           }.join()
           launch {
               if(validatorFlag!=1&&validatorFlag!=0)validatorFlag = -1
               _mode.value = validatorFlag
               validatorFlag = 0
           }.join()
       }
    }
}