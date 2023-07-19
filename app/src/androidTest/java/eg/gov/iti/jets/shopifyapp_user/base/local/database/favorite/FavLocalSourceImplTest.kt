package eg.gov.iti.jets.shopifyapp_user.base.local.database.favorite

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import eg.gov.iti.jets.shopifyapp_user.MainRule
import eg.gov.iti.jets.shopifyapp_user.base.local.database.ShopifyDatabase
import eg.gov.iti.jets.shopifyapp_user.base.model.FavRoomPojo
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.After
import org.junit.Assert.*

import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@MediumTest
class FavLocalSourceImplTest {
    @ExperimentalCoroutinesApi
    @get:Rule
    val mainRule = MainRule()

    @ExperimentalCoroutinesApi
    @get:Rule
    val rule = InstantTaskExecutorRule()

    var context: Context = ApplicationProvider.getApplicationContext()
    private lateinit var shopifyDatabase: ShopifyDatabase
    lateinit var localDataSource: FavLocalSourceImpl

    @Before
    fun setUp() {
        ShopifyDatabase.context = context
        shopifyDatabase = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            ShopifyDatabase::class.java
        ).allowMainThreadQueries().build()
        localDataSource = FavLocalSourceImpl()
    }

    @After
    fun closeDatabase() = shopifyDatabase.close()

    @Test
    fun insertFavProduct() = mainRule.runBlockingTest {
        val favProduct = FavRoomPojo(123, "t-shirt", "image", "250", "description")
        localDataSource.insertFavProduct(favProduct)
        val result = localDataSource.getAllFavProducts().first()
        //Then
        MatcherAssert.assertThat(result[0].productId, CoreMatchers.`is`(123))
    }

    @Test
     fun getFavProductWithId()= mainRule.runBlockingTest {
        val favProduct = FavRoomPojo(123, "t-shirt", "image", "250", "description")
        localDataSource.insertFavProduct(favProduct)
        val result = localDataSource.getFavProductWithId(123).first()
        //Then
        MatcherAssert.assertThat(result.productId, CoreMatchers.`is`(123))
    }

    @Test
    fun deleteFavProductWithId()= mainRule.runBlockingTest {
        val favProduct = FavRoomPojo(123, "t-shirt", "image", "250", "description")
        localDataSource.insertFavProduct(favProduct)
        localDataSource.deleteFavProductWithId(123)
        val result = localDataSource.getFavProductWithId(123).first()
        //Then
        MatcherAssert.assertThat(result, CoreMatchers.nullValue())
    }

    @Test
    fun deleteAllFavoriteProducts() = mainRule.runBlockingTest {
        val favProduct = FavRoomPojo(123, "t-shirt", "image", "250", "description")
        localDataSource.insertFavProduct(favProduct)
        localDataSource.deleteAllFavoriteProducts()
        val result = localDataSource.getAllFavProducts().first()
        //Then
        MatcherAssert.assertThat(result, CoreMatchers.`is`(emptyList()))
    }
}