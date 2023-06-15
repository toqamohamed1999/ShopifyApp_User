package eg.gov.iti.jets.shopifyapp_user.base.repo

import eg.gov.iti.jets.shopifyapp_user.base.domain.repo.FavOpRepoInterface
import eg.gov.iti.jets.shopifyapp_user.base.local.database.favorite.FavLocalSourceImpl
import eg.gov.iti.jets.shopifyapp_user.base.local.database.favorite.FavLocalSourceInterface
import eg.gov.iti.jets.shopifyapp_user.cart.domain.model.LineItem
import kotlinx.coroutines.flow.Flow

class FavOpRepoImpl(private val favLocalSource: FavLocalSourceInterface = FavLocalSourceImpl()) :
    FavOpRepoInterface {
    override suspend fun insertFavProduct(lineItem: LineItem) {
        favLocalSource.insertFavProduct(lineItem)
    }

    override suspend fun deleteFavProductWithId(lineItemId: Long) {
       favLocalSource.deleteFavProductWithId(lineItemId)
    }

    override fun getAllFavProducts(): Flow<List<LineItem>> {
        return favLocalSource.getAllFavProducts()
    }

    override fun getFavProductWithId(lineItemId: Long): Flow<LineItem> {
        return favLocalSource.getFavProductWithId(lineItemId)
    }
}