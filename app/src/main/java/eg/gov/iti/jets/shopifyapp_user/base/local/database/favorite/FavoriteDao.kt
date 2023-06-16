package eg.gov.iti.jets.shopifyapp_user.base.local.database.favorite

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import eg.gov.iti.jets.shopifyapp_user.base.model.FavRoomPojo
import eg.gov.iti.jets.shopifyapp_user.base.model.Product
import eg.gov.iti.jets.shopifyapp_user.cart.domain.model.LineItem
import kotlinx.coroutines.flow.Flow
@Dao
interface FavoriteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavProduct(favRoomPojo: FavRoomPojo)
    @Query("Select * from FavoriteProducts")
    fun getAllFavProducts(): Flow<List<FavRoomPojo>>
    @Query("Select * from FavoriteProducts where productId = :productId ")
    fun getFavProductWithId(productId: Long): Flow<FavRoomPojo >
    @Query("Delete from FavoriteProducts where productId = :productId")
    suspend fun deleteFavProductWithId(productId: Long)
    @Query("DELETE from FavoriteProducts")
    fun deleteAllFavoriteProducts()
}