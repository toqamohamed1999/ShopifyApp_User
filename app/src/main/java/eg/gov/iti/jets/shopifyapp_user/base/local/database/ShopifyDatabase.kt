package eg.gov.iti.jets.shopifyapp_user.base.local.database

import android.content.Context
import androidx.room.*
import eg.gov.iti.jets.shopifyapp_user.base.local.database.favorite.FavoriteDao
import eg.gov.iti.jets.shopifyapp_user.base.model.Product
import eg.gov.iti.jets.shopifyapp_user.base.model.ShopifyTypeConverter
import eg.gov.iti.jets.shopifyapp_user.cart.domain.model.LineItem

@Database(entities = [LineItem::class], version = 2)
@TypeConverters(ShopifyTypeConverter::class)
abstract class ShopifyDatabase: RoomDatabase() {
    abstract fun getFavoriteDAO():FavoriteDao

    companion object {
        lateinit var context: Context
        @Volatile
        private var INSTANCE: ShopifyDatabase? = null
        fun getInstance(): ShopifyDatabase {
            val instance = Room.databaseBuilder(
                context.applicationContext,
                ShopifyDatabase::class.java,
                "ShopifyDatabase"
            )
                .fallbackToDestructiveMigration()
                .build()
            INSTANCE = instance
            return instance
        }
    }
}