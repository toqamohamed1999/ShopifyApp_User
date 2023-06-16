package eg.gov.iti.jets.shopifyapp_user.base.local.database.favorite

import eg.gov.iti.jets.shopifyapp_user.base.local.database.ShopifyDatabase
import eg.gov.iti.jets.shopifyapp_user.base.model.FavRoomPojo
import eg.gov.iti.jets.shopifyapp_user.base.model.Product
import eg.gov.iti.jets.shopifyapp_user.cart.domain.model.LineItem
import kotlinx.coroutines.flow.Flow

class FavLocalSourceImpl(private val favDAO: FavoriteDao =ShopifyDatabase.getInstance().getFavoriteDAO()):FavLocalSourceInterface {
    override suspend fun insertFavProduct(favRoomPojo: FavRoomPojo) {
        favDAO.insertFavProduct(favRoomPojo)
    }

    override fun getAllFavProducts(): Flow<List<FavRoomPojo>> {
       return favDAO.getAllFavProducts()
    }

    override fun getFavProductWithId(productId: Long): Flow<FavRoomPojo> {
      return favDAO.getFavProductWithId(productId)
    }

    override suspend fun deleteFavProductWithId(productId: Long) {
       favDAO.deleteFavProductWithId(productId)
    }

    override fun deleteAllFavoriteProducts() {
       favDAO.deleteAllFavoriteProducts()
    }


}