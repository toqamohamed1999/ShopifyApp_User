package eg.gov.iti.jets.shopifyapp_user.base.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import eg.gov.iti.jets.shopifyapp_user.cart.domain.model.AppliedDiscount
import eg.gov.iti.jets.shopifyapp_user.cart.domain.model.LineItem
import eg.gov.iti.jets.shopifyapp_user.cart.domain.model.TaxLineX

@Entity(tableName = "FavoriteProducts")
data class FavRoomPojo(
    @PrimaryKey
    val productId: Long?,
    val title: String?,
    val imageSrc: String?,
    val price: String?,
    val bodyHtml:String?
)
fun FavRoomPojo.toLineItem():LineItem{
    return LineItem(
        id = productId,
        variant_id = 0,
        product_id = productId,
        title =title,
        variant_title = null,
        sku =null,
        vendor = null,
        quantity = 1,
        requires_shipping = false,
        taxable = true,
        gift_card = false,
        fulfillment_service = "manual",
        grams = 0,
        tax_lines = listOf( TaxLineX(
            rate= 0.14,
            title="GST",
            price="22.68"
        )
        )
        ,
        applied_discount = AppliedDiscount(
            description =imageSrc,//line item /  image
            value ="10.0",
            title = "Custom",
            amount = "20.00",
            value_type = bodyHtml.toString() // line item product description
        ),
        name = title,
        properties = listOf(),
        custom = true,
        price =price.toString(),
        admin_graphql_api_id = "gid://shopify/DraftOrderLineItem/58260103233817"
    )
}