package eg.gov.iti.jets.shopifyapp_user.base.local.database.favorite

import eg.gov.iti.jets.shopifyapp_user.base.model.Product
import eg.gov.iti.jets.shopifyapp_user.cart.domain.model.LineItem
import kotlinx.coroutines.flow.Flow

interface FavLocalSourceInterface {
    suspend fun insertFavProduct(lineItem: LineItem)
    fun getAllFavProducts(): Flow<List<LineItem >>
    fun getFavProductWithId(lineItemId: Long): Flow<LineItem >
    suspend fun deleteFavProductWithId(lineItemId: Long)
    fun deleteAllFavoriteProducts()
}