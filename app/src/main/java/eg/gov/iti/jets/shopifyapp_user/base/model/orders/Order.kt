package eg.gov.iti.jets.shopifyapp_user.base.model.orders

import eg.gov.iti.jets.shopifyapp_user.cart.domain.model.LineItem
import eg.gov.iti.jets.shopifyapp_user.home.domain.model.addsmodels.DiscountCode

data class Order(
    val current_total_discounts: String,
    val current_total_price: String,
    val discount_codes: List<DiscountCode>,
    val fulfillment_status: String,
    val gateway: String,
    val line_items: List<LineItem>,
    val payment_details: PaymentDetails,
    val shipping_address: ShippingAddress,
    val test: Boolean,
    val transactions: List<Transaction>,
    val user_id: Int
)