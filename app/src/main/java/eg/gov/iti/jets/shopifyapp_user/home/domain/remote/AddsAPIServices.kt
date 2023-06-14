package eg.gov.iti.jets.shopifyapp_user.home.domain.remote

import eg.gov.iti.jets.shopifyapp_user.home.domain.model.addsmodels.DiscountCodeBody
import eg.gov.iti.jets.shopifyapp_user.home.domain.model.addsmodels.Discounts
import eg.gov.iti.jets.shopifyapp_user.home.domain.model.addsmodels.PriceRules
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.PUT
import retrofit2.http.Path

interface AddsAPIServices {
    @Headers(
        "Content-Type:application/json",
        "X-Shopify-Access-Token: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"
    )
    @GET("price_rules.json?")
    suspend fun getAllPriceRules(): PriceRules

    @Headers(
        "Content-Type:application/json",
        "X-Shopify-Access-Token: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"
    )
    @GET("price_rules/{ruleId}/discount_codes.json")
    suspend fun getAllDiscountsForPriceRule(@Path("ruleId")ruleId:String): Discounts
    @Headers(
        "Content-Type:application/json",
        "X-Shopify-Access-Token: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"
    )
    @PUT("price_rules/{ruleId}/discount_codes/{discount_code_id}.json")
    suspend fun updateUsageCountForDiscount(@Path(value ="ruleId")ruleId:String,
                                    @Path(value ="discount_code_id")discount_code_id:String,@Body discountCode: DiscountCodeBody
    ): DiscountCodeBody
}