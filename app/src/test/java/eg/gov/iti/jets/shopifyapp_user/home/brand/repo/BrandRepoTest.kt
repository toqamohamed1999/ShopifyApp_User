package eg.gov.iti.jets.shopifyapp_user.home.brand.repo

import eg.gov.iti.jets.shopifyapp_user.MainRule
import eg.gov.iti.jets.shopifyapp_user.home.brand.remote.BrandRemoteSourceTest
import eg.gov.iti.jets.shopifyapp_user.home.domain.model.BrandModel
import eg.gov.iti.jets.shopifyapp_user.home.domain.model.Image
import eg.gov.iti.jets.shopifyapp_user.home.domain.model.SmartCollection
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.core.IsEqual
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class BrandRepoTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    val mainRule = MainRule()

    private lateinit var repository: FakeBrandRepo
    private lateinit var fakeRemoteSource: BrandRemoteSourceTest

    @Before
    fun setup() {
        fakeRemoteSource = BrandRemoteSourceTest()
        repository = FakeBrandRepo(fakeRemoteSource)
    }

    @Test
    fun getSmartCollectionsBrand_remoteSmartCollectionsBrand() = mainRule.runBlockingTest {
        repository.getAllBrands().collect{
            val tasks =it
            // Then
            Assert.assertThat(tasks, IsEqual(BrandModel(mutableListOf())))
        }
    }
}