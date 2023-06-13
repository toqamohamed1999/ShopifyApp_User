package eg.gov.iti.jets.shopifyapp_user.home.presentation.ui

import eg.gov.iti.jets.shopifyapp_user.home.domain.model.addsmodels.DiscountCode

interface CouponClickListener {
    fun onImageClick(discountCode: DiscountCode)
}