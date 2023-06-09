package eg.gov.iti.jets.shopifyapp_user.auth.domain.model

import com.google.gson.annotations.SerializedName

class CustomerModel (

    @SerializedName("first_name"        ) var firstName       : String?              = null,
    @SerializedName("last_name"         ) var lastName        : String?              = null,
    @SerializedName("email"             ) var email           : String?              = null,
    @SerializedName("phone"             ) var phone           : String?              = null,
    @SerializedName("verified_email"    ) var verifiedEmail   : Boolean?             = null,
    @SerializedName("addresses"         ) var addresses       : ArrayList<Addresses> = arrayListOf(),
    @SerializedName("send_email_invite" ) var sendEmailInvite : Boolean?             = null

)

data class Addresses (

    @SerializedName("address1"   ) var address1  : String? = null,
    @SerializedName("city"       ) var city      : String? = null,
    @SerializedName("province"   ) var province  : String? = null,
    @SerializedName("phone"      ) var phone     : String? = null,
    @SerializedName("zip"        ) var zip       : String? = null,
    @SerializedName("last_name"  ) var lastName  : String? = null,
    @SerializedName("first_name" ) var firstName : String? = null,
    @SerializedName("country"    ) var country   : String? = null

)