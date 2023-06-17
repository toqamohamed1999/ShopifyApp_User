package eg.gov.iti.jets.shopifyapp_user.products.presentation.ui

import eg.gov.iti.jets.shopifyapp_user.base.model.Product

interface OnClickProduct{
    fun onClickFavIcon(product_Id : Long)
    fun onClickProductCard(product_Id : Long)

}