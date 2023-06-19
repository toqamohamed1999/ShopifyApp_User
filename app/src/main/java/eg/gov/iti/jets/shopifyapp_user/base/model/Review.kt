package eg.gov.iti.jets.shopifyapp_user.base.model

data class Review(
    var reviewerName: String,
    var rate: Double,
    var reviewerImg: String,
    var review: String
)