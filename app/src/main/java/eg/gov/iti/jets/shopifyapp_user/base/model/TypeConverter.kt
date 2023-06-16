package eg.gov.iti.jets.shopifyapp_user.base.model

import com.google.gson.Gson
import androidx.room.TypeConverter
import eg.gov.iti.jets.shopifyapp_user.cart.domain.model.AppliedDiscount
import eg.gov.iti.jets.shopifyapp_user.cart.domain.model.LineItem
import eg.gov.iti.jets.shopifyapp_user.cart.domain.model.TaxLineX

class ShopifyTypeConverter {
    @TypeConverter
    fun fromLineItemToString(lineItem: LineItem) = Gson().toJson(lineItem)

    @TypeConverter
    fun fromStringToLineItem(stringLineItem: String) =
        Gson().fromJson(stringLineItem, LineItem::class.java)

    @TypeConverter
    fun fromAppliedDiscountToString(appliedDiscount: AppliedDiscount) =
        Gson().toJson(appliedDiscount)

    @TypeConverter
    fun fromStringToAppliedDiscount(stringAppliedDiscount: String) =
        Gson().fromJson(stringAppliedDiscount, AppliedDiscount::class.java)

    @TypeConverter
    fun fromTaxLineXListToString(taxLineX: List<TaxLineX>) = Gson().toJson(taxLineX)

    @TypeConverter
    fun fromStringToTaxLineXList(stringTaxLineX: String) =
        Gson().fromJson(stringTaxLineX, Array<TaxLineX>::class.java).toList()

    @TypeConverter
    fun fromStringListToString(string: List<String>) = Gson().toJson(string)

    @TypeConverter
    fun fromStringToStringList(string: String) =
        Gson().fromJson(string, Array<String>::class.java).toList()

}