package eg.gov.iti.jets.shopifyapp_user.base.domain.repo

import eg.gov.iti.jets.shopifyapp_user.base.model.FavRoomPojo
import kotlinx.coroutines.flow.Flow

interface FavOpRepoInterface {
    suspend fun insertFavProduct(favRoomPojo: FavRoomPojo)
    suspend fun deleteFavProductWithId(productId: Long)
    fun getAllFavProducts(): Flow<List<FavRoomPojo>>
    fun getFavProductWithId(productId: Long): Flow<FavRoomPojo >
}