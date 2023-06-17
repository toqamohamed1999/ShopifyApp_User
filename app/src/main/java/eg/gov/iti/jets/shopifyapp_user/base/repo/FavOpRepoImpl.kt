package eg.gov.iti.jets.shopifyapp_user.base.repo

import eg.gov.iti.jets.shopifyapp_user.base.domain.repo.FavOpRepoInterface
import eg.gov.iti.jets.shopifyapp_user.base.local.database.favorite.FavLocalSourceImpl
import eg.gov.iti.jets.shopifyapp_user.base.local.database.favorite.FavLocalSourceInterface
import eg.gov.iti.jets.shopifyapp_user.base.model.FavRoomPojo
import kotlinx.coroutines.flow.Flow

class FavOpRepoImpl(private val favLocalSource: FavLocalSourceInterface = FavLocalSourceImpl()) :
    FavOpRepoInterface {
    override suspend fun insertFavProduct(favRoomPojo: FavRoomPojo) {
        favLocalSource.insertFavProduct(favRoomPojo)
    }

    override suspend fun deleteFavProductWithId(productId: Long) {
        favLocalSource.deleteFavProductWithId(productId)
    }

    override fun getAllFavProducts(): Flow<List<FavRoomPojo>> {
        return favLocalSource.getAllFavProducts()
    }

    override fun getFavProductWithId(productId: Long): Flow<FavRoomPojo> {
        return favLocalSource.getFavProductWithId(productId)
    }

}