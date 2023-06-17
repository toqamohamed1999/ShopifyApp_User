package eg.gov.iti.jets.shopifyapp_user.base.model.orders

data class TotalLineItemsPriceSet(
    val presentment_money: PresentmentMoney,
    val shop_money: ShopMoney
)