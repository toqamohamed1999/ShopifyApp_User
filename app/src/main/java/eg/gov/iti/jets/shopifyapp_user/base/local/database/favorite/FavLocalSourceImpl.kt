package eg.gov.iti.jets.shopifyapp_user.base.local.database.favorite

import eg.gov.iti.jets.shopifyapp_user.base.local.database.ShopifyDatabase
import eg.gov.iti.jets.shopifyapp_user.base.model.Product
import eg.gov.iti.jets.shopifyapp_user.cart.data.model.LineItem
import kotlinx.coroutines.flow.Flow

class FavLocalSourceImpl(private val favDAO: FavoriteDao =ShopifyDatabase.getInstance().getFavoriteDAO()):FavLocalSourceInterface {
    override suspend fun insertFavProduct(lineItem: LineItem) {
       favDAO.insertFavProduct(lineItem)
    }

    override fun getAllFavProducts(): Flow<List<LineItem>> {
        return favDAO.getAllFavProducts()
    }

    override fun getFavProductWithId(lineItemId: Long): Flow<LineItem> {
       return favDAO.getFavProductWithId(lineItemId)
    }

    override suspend fun deleteFavProductWithId(lineItemId: Long) {
       favDAO.deleteFavProductWithId(lineItemId)
    }

    override fun deleteAllFavoriteProducts() {
       favDAO.deleteAllFavoriteProducts()
    }

}