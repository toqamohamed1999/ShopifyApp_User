package eg.gov.iti.jets.shopifyapp_user.payment.domain.model

data class TotalUnsettledSet(
    val presentment_money: PresentmentMoney,
    val shop_money: ShopMoney
)