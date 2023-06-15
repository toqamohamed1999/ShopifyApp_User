package eg.gov.iti.jets.shopifyapp_user.base.local.database.favorite

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import eg.gov.iti.jets.shopifyapp_user.base.model.Product
import eg.gov.iti.jets.shopifyapp_user.cart.domain.model.LineItem
import kotlinx.coroutines.flow.Flow
@Dao
interface FavoriteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavProduct(lineItem: LineItem)
    @Query("Select * from FavoriteProducts")
    fun getAllFavProducts(): Flow<List<LineItem >>
    @Query("Select * from FavoriteProducts where id = :lineItemId ")
    fun getFavProductWithId(lineItemId: Long): Flow<LineItem >
    @Query("Delete from FavoriteProducts where id = :lineItemId")
    suspend fun deleteFavProductWithId(lineItemId: Long)
    @Query("DELETE from FavoriteProducts")
    fun deleteAllFavoriteProducts()
}