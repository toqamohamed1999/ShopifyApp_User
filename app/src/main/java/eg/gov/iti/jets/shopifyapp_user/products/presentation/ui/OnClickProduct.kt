package eg.gov.iti.jets.shopifyapp_user.products.presentation.ui

import eg.gov.iti.jets.shopifyapp_user.base.model.Product

interface OnClickProduct{
    fun onClickFavIcon(product : Product)
    fun onClickProductCard(product_Id : Long)

}