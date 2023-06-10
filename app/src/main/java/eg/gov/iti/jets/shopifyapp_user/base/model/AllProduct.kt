package eg.gov.iti.jets.shopifyapp_user.base.model

import com.google.gson.annotations.SerializedName

data class AllProduct(
    @SerializedName("products") var products : List<Product>
)
