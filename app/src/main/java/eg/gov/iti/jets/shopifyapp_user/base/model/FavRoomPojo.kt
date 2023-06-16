package eg.gov.iti.jets.shopifyapp_user.base.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import eg.gov.iti.jets.shopifyapp_user.cart.data.model.LineItem

@Entity(tableName = "FavoriteProducts")
data class FavRoomPojo(
    @PrimaryKey
    val productId: Long?,
    val title: String?,
    val imageSrc: String?,
    val price: String?,
    val bodyHtml:String?
)
