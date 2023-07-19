package eg.gov.iti.jets.shopifyapp_user.favorite.data.repo

import eg.gov.iti.jets.shopifyapp_user.base.local.database.favorite.FavLocalSourceImpl
import eg.gov.iti.jets.shopifyapp_user.base.local.database.favorite.FavLocalSourceInterface
import eg.gov.iti.jets.shopifyapp_user.base.model.FavRoomPojo
import eg.gov.iti.jets.shopifyapp_user.favorite.domain.repo.FavLocalRepoInterface
import kotlinx.coroutines.flow.Flow

class FavLocalRepoImpl(private val favLocalSource: FavLocalSourceInterface = FavLocalSourceImpl()) :
    FavLocalRepoInterface {
    override suspend fun deleteFavProductWithId(productId: Long) {
        favLocalSource.deleteFavProductWithId(productId)
    }

    override fun getAllFavProducts(): Flow<List<FavRoomPojo>> {
       return favLocalSource.getAllFavProducts()
    }

    override suspend fun deleteAllFavoriteProducts() {
        favLocalSource.deleteAllFavoriteProducts()
    }

}