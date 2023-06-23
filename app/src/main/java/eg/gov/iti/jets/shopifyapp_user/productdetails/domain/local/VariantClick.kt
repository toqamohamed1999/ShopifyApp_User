package eg.gov.iti.jets.shopifyapp_user.productdetails.domain.local

import eg.gov.iti.jets.shopifyapp_user.base.model.Variants

interface VariantClick {
    fun onClickVariant(variant:Variants)
}