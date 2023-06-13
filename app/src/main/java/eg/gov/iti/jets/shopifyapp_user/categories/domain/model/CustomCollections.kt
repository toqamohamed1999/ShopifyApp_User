package eg.gov.iti.jets.shopifyapp_user.categories.domain.model

import com.google.gson.annotations.SerializedName

data class CustomCollections(
    @SerializedName("id") var id : Long,
    @SerializedName("handle") var handle : String,
    @SerializedName("title") var title : String,
    @SerializedName("updated_at") var updatedAt : String,
    @SerializedName("body_html") var bodyHtml : String,
    @SerializedName("published_at") var publishedAt : String,
    @SerializedName("sort_order") var sortOrder : String,
    @SerializedName("template_suffix") var templateSuffix : String,
    @SerializedName("published_scope") var publishedScope : String,
    @SerializedName("admin_graphql_api_id") var adminGraphqlApiId : String,
    @SerializedName("image") var image : ImageCategory
)
