package eg.gov.iti.jets.shopifyapp_user.base.local.database.favorite

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import eg.gov.iti.jets.shopifyapp_user.base.local.database.ShopifyDatabase
import eg.gov.iti.jets.shopifyapp_user.base.model.FavRoomPojo
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@SmallTest
class FavDAOTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    val rule = InstantTaskExecutorRule()

    private lateinit var shopifyDatabase: ShopifyDatabase
    private lateinit var favDao:FavoriteDao

    @Before
    fun setUp() {
        shopifyDatabase = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            ShopifyDatabase::class.java
        ).allowMainThreadQueries().build()
        favDao = shopifyDatabase.getFavoriteDAO()
    }
    @After
    fun closeDatabase() = shopifyDatabase.close()

    @Test
    fun insertFavProduct_returnProductId() = runBlockingTest {
        val favProduct = FavRoomPojo(123, "t-shirt", "image", "250", "description")
        favDao.insertFavProduct(favProduct)
        val result = favDao.getAllFavProducts().first()
        //Then
        MatcherAssert.assertThat(result[0].productId, CoreMatchers.`is`(123))
    }
    @Test
    fun getFavProductWithId()= runBlockingTest {
        val favProduct = FavRoomPojo(123, "t-shirt", "image", "250", "description")
        favDao.insertFavProduct(favProduct)
        val result = favDao.getFavProductWithId(123).first()
        //Then
        MatcherAssert.assertThat(result.productId, CoreMatchers.`is`(123))
    }
    @Test
    fun deleteFavProductWithId() =runBlockingTest {
        val favProduct = FavRoomPojo(123, "t-shirt", "image", "250", "description")
        favDao.insertFavProduct(favProduct)
        favDao.deleteFavProductWithId(123)
        val result = favDao.getFavProductWithId(123).first()
        //Then
        MatcherAssert.assertThat(result, CoreMatchers.nullValue())
    }

    @Test
    fun deleteAllFavoriteProducts() = runBlockingTest {
        val favProduct = FavRoomPojo(123, "t-shirt", "image", "250", "description")
        favDao.insertFavProduct(favProduct)
        favDao.deleteAllFavoriteProducts()
        val result = favDao.getAllFavProducts().first()
        //Then
        MatcherAssert.assertThat(result, CoreMatchers.`is`(emptyList()))
    }
}