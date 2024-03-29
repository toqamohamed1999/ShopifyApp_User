package eg.gov.iti.jets.shopifyapp_user.base.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import eg.gov.iti.jets.shopifyapp_user.cart.data.model.AppliedDiscount
import eg.gov.iti.jets.shopifyapp_user.cart.data.model.LineItem
import eg.gov.iti.jets.shopifyapp_user.cart.data.model.TaxLineX
import kotlinx.parcelize.Parcelize

@Parcelize
data class Product(
    @SerializedName("id") var id: Long? = 0,
    @SerializedName("title") var title: String? = "",
    @SerializedName("body_html") var bodyHtml: String? = "",
    @SerializedName("vendor") var vendor: String? = "",
    @SerializedName("product_type") var productType: String? = "",
    @SerializedName("created_at") var createdAt: String? = "",
    @SerializedName("handle") var handle: String? = "",
    @SerializedName("updated_at") var updatedAt: String? = "",
    @SerializedName("published_at") var publishedAt: String? = "",
    @SerializedName("template_suffix") var templateSuffix: String? = "",
    @SerializedName("status") var status: String? = "",
    @SerializedName("published_scope") var publishedScope: String? = "",
    @SerializedName("tags") var tags: String? = "",
    @SerializedName("admin_graphql_api_id") var adminGraphqlApiId: String? = "",
    @SerializedName("variants") var variants: List<Variants>,
    @SerializedName("options") var options: List<Options>,
    @SerializedName("images") var images: List<Image>,
    @SerializedName("image") var image: Image
) : Parcelable

fun Product.toLineItem(id:Int): LineItem {
    val variant = this.variants[id]
    return  LineItem(
        id = variant.id,
        product_id = variant.id,
        title = title,
        sku = null,
        quantity = 1,
        requires_shipping = false,
        taxable = false,
        gift_card = false,
        fulfillment_service = "manual",
        grams = 0,
        tax_lines = listOf(
            TaxLineX(
                rate = 0.14,
                title = "GST",
                price = "22.68"
            )
        ),
        applied_discount = AppliedDiscount(
            description = "${variant.id})${image.src}",//line item /  image
            value = "10.0",
            title = "Custom",
            amount = "20.00",
            value_type = "percentage"
        ),
        name = title,
        properties = listOf(Property("${this.id}","${variant.inventoryItemId}")),
        custom = true,
        price = variants[0].price.toString(),
        variant_id = null, variant_title = bodyHtml.toString() // Product Description
        , vendor = null
    )
}

fun Product.toFavRoomPojo(): FavRoomPojo {
    return FavRoomPojo(
        productId = id,
        title = title,
        imageSrc = images[0].src,
        price = variants[0].price,
        bodyHtml = bodyHtml
    )
}

fun Product.toLineItems(): LineItems {
    return LineItems(

        productId = id,
        title = title,
        sku = null,
        quantity = 1,
        requiresShipping = false,
        taxable = true,
        giftCard = false,
        fulfillmentService = "manual",
        grams = 0,
        taxLines = arrayListOf(),
        appliedDiscount = eg.gov.iti.jets.shopifyapp_user.base.model.AppliedDiscount(
            description = image.src,//line item /  image
            value = "10.0",
            title = "Custom",
            amount = "20.00",
            valueType = "percentage"
        ),
        name = title,
        properties = arrayListOf(),
        custom = true,
        price = variants[0].price.toString(),
        variantId = variants[0].id, variantTitle = bodyHtml.toString() // Product Description
        , vendor = null
    )

}
