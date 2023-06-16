package eg.gov.iti.jets.shopifyapp_user.favorite.data.repo

import eg.gov.iti.jets.shopifyapp_user.base.local.database.favorite.FavLocalSourceImpl
import eg.gov.iti.jets.shopifyapp_user.base.local.database.favorite.FavLocalSourceInterface
import eg.gov.iti.jets.shopifyapp_user.cart.data.model.LineItem
import eg.gov.iti.jets.shopifyapp_user.favorite.domain.repo.FavLocalRepoInterface
import kotlinx.coroutines.flow.Flow

class FavLocalRepoImpl(private val favLocalSource: FavLocalSourceInterface = FavLocalSourceImpl()) :
    FavLocalRepoInterface {
    override suspend fun deleteFavProductWithId(lineItemId: Long) {
        favLocalSource.deleteFavProductWithId(lineItemId)
    }

    override fun getAllFavProducts(): Flow<List<LineItem>> {
        return favLocalSource.getAllFavProducts()
    }
}