package eg.gov.iti.jets.shopifyapp_user.auth.domain.model

import com.google.gson.annotations.SerializedName
data class Customer (

    @SerializedName("id"                           ) var id                        : Int?                 = null,
    @SerializedName("email"                        ) var email                     : String?              = null,
    @SerializedName("accepts_marketing"            ) var acceptsMarketing          : Boolean?             = null,
    @SerializedName("created_at"                   ) var createdAt                 : String?              = null,
    @SerializedName("updated_at"                   ) var updatedAt                 : String?              = null,
    @SerializedName("first_name"                   ) var firstName                 : String?              = null,
    @SerializedName("last_name"                    ) var lastName                  : String?              = null,
    @SerializedName("orders_count"                 ) var ordersCount               : Int?                 = null,
    @SerializedName("state"                        ) var state                     : String?              = null,
    @SerializedName("total_spent"                  ) var totalSpent                : String?              = null,
    @SerializedName("last_order_id"                ) var lastOrderId               : String?              = null,
    @SerializedName("note"                         ) var note                      : String?              = null,
    @SerializedName("verified_email"               ) var verifiedEmail             : Boolean?             = null,
    @SerializedName("multipass_identifier"         ) var multipassIdentifier       : String?              = null,
    @SerializedName("tax_exempt"                   ) var taxExempt                 : Boolean?             = null,
    @SerializedName("phone"                        ) var phone                     : String?              = null,
    @SerializedName("tags"                         ) var tags                      : String?              = null,
    @SerializedName("last_order_name"              ) var lastOrderName             : String?              = null,
    @SerializedName("currency"                     ) var currency                  : String?              = null,
    @SerializedName("addresses"                    ) var addresses                 : ArrayList<Addresses> = arrayListOf(),
    @SerializedName("accepts_marketing_updated_at" ) var acceptsMarketingUpdatedAt : String?              = null,
    @SerializedName("marketing_opt_in_level"       ) var marketingOptInLevel       : String?              = null,
    @SerializedName("tax_exemptions"               ) var taxExemptions             : ArrayList<String>    = arrayListOf(),
    @SerializedName("admin_graphql_api_id"         ) var adminGraphqlApiId         : String?              = null,
    @SerializedName("default_address"              ) var defaultAddress            : Addresses?      = Addresses()

)

data class Addresses (

    @SerializedName("id"            ) var id           : Int?     = null,
    @SerializedName("customer_id"   ) var customerId   : Int?     = null,
    @SerializedName("first_name"    ) var firstName    : String?  = null,
    @SerializedName("last_name"     ) var lastName     : String?  = null,
    @SerializedName("company"       ) var company      : String?  = null,
    @SerializedName("address1"      ) var address1     : String?  = null,
    @SerializedName("address2"      ) var address2     : String?  = null,
    @SerializedName("city"          ) var city         : String?  = null,
    @SerializedName("province"      ) var province     : String?  = null,
    @SerializedName("country"       ) var country      : String?  = null,
    @SerializedName("zip"           ) var zip          : String?  = null,
    @SerializedName("phone"         ) var phone        : String?  = null,
    @SerializedName("name"          ) var name         : String?  = null,
    @SerializedName("province_code" ) var provinceCode : String?  = null,
    @SerializedName("country_code"  ) var countryCode  : String?  = null,
    @SerializedName("country_name"  ) var countryName  : String?  = null,
    @SerializedName("default"       ) var default      : Boolean? = null

)