package eg.gov.iti.jets.shopifyapp_user.base.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.Ignore
import com.google.gson.annotations.SerializedName
import eg.gov.iti.jets.shopifyapp_user.cart.domain.model.AppliedDiscount
import eg.gov.iti.jets.shopifyapp_user.cart.domain.model.LineItem
import eg.gov.iti.jets.shopifyapp_user.cart.domain.model.TaxLineX
import kotlinx.parcelize.Parcelize

@Parcelize
data class Product (
    @SerializedName("id") var id : Long?=0,
    @SerializedName("title") var title : String?="",
    @SerializedName("body_html") var bodyHtml : String?="",
    @SerializedName("vendor") var vendor : String?="",
    @SerializedName("product_type") var productType : String?="",
    @SerializedName("created_at") var createdAt : String?="",
    @SerializedName("handle") var handle : String?="",
    @SerializedName("updated_at") var updatedAt : String?="",
    @SerializedName("published_at") var publishedAt : String?="",
    @SerializedName("template_suffix") var templateSuffix : String?="",
    @SerializedName("status") var status : String?="",
    @SerializedName("published_scope") var publishedScope : String?="",
    @SerializedName("tags") var tags : String?="",
    @SerializedName("admin_graphql_api_id") var adminGraphqlApiId : String?="",
    @SerializedName("variants") var variants : List<Variants>,
    @SerializedName("options") var options : List<Options>,
    @SerializedName("images") var images : List<Image>,
    @SerializedName("image") var image : Image
):Parcelable
fun Product.toLineItem():LineItem{
    return LineItem(
        id = id,
    variant_id = 0,
    product_id = id,
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
    ))
    ,
    applied_discount = AppliedDiscount(
            description =image.src,//line item /  image
            value ="10.0",
            title = "Custom",
            amount = "20.00",
        value_type = bodyHtml.toString() // line item product description
    ),
    name = title,
    properties = listOf(),
    custom = true,
    price =variants[0].price.toString(),
    admin_graphql_api_id = "gid://shopify/DraftOrderLineItem/58260103233817"
    )
}
fun Product.toFavRoomPojo():FavRoomPojo{
    return FavRoomPojo(
        productId = id,
        title=title,
        imageSrc = images[0].src,
        price=variants[0].price,
        bodyHtml=bodyHtml
    )
}
