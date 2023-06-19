package eg.gov.iti.jets.shopifyapp_user.profile.presentation.ui

import eg.gov.iti.jets.shopifyapp_user.base.model.orders.Order

interface OnClickOrder {

    fun onClickOrder(order: Order)
}