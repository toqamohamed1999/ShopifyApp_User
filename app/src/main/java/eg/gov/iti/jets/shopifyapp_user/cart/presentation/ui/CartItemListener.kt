package eg.gov.iti.jets.shopifyapp_user.cart.presentation.ui

import eg.gov.iti.jets.shopifyapp_user.cart.domain.model.LineItem

interface CartItemListener {
    fun increaseProductInCart(product:LineItem)
    fun  decreaseProductInCart(product: LineItem)
    fun removerProduct(product: LineItem)
}