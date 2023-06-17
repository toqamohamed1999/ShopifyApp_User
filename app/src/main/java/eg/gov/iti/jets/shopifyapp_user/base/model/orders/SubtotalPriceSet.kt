package eg.gov.iti.jets.shopifyapp_user.base.model.orders

data class SubtotalPriceSet(
    val presentment_money: PresentmentMoney,
    val shop_money: ShopMoney
)