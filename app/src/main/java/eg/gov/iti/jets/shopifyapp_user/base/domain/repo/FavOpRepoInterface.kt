package eg.gov.iti.jets.shopifyapp_user.base.domain.repo

import eg.gov.iti.jets.shopifyapp_user.cart.domain.model.LineItem
import kotlinx.coroutines.flow.Flow

interface FavOpRepoInterface {
    suspend fun insertFavProduct(lineItem: LineItem)
    suspend fun deleteFavProductWithId(lineItemId: Long)
    fun getAllFavProducts(): Flow<List<LineItem>>
    fun getFavProductWithId(lineItemId: Long): Flow<LineItem>
}