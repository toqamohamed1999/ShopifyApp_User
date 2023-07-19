package eg.gov.iti.jets.shopifyapp_user.categories.domain.model

import com.google.gson.annotations.SerializedName

data class AllCategories(
    @SerializedName("custom_collections") var customCollections : List<CustomCollections>
)
