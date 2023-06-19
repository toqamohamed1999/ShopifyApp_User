package eg.gov.iti.jets.shopifyapp_user.base.model.orders

data class TotalShippingPriceSet(
    val presentment_money: PresentmentMoney,
    val shop_money: ShopMoney
)