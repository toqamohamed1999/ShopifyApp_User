package eg.gov.iti.jets.shopifyapp_user.cart.remote_test

import eg.gov.iti.jets.shopifyapp_user.base.remote.AppRetrofit
import eg.gov.iti.jets.shopifyapp_user.cart.data.remote.DraftOrderAiState
import eg.gov.iti.jets.shopifyapp_user.cart.data.remote.DraftOrderRemoteSourceImpl
import eg.gov.iti.jets.shopifyapp_user.cart.domain.model.DraftOrderResponse
import eg.gov.iti.jets.shopifyapp_user.cart.domain.remote.DraftOrderNetworkServices
import eg.gov.iti.jets.shopifyapp_user.cart.domain.remote.DraftOrderRemoteSource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
@OptIn(ExperimentalCoroutinesApi::class)
class RemoteSourceTest{
    private var remoteSource:DraftOrderRemoteSource? = null
    @Before
    fun setUpTest(){
        remoteSource = DraftOrderRemoteSourceImpl(AppRetrofit.retrofit.create(DraftOrderNetworkServices::class.java))
    }
    @After
    fun tearDown(){
        remoteSource = null
    }
    @Test
      fun testCreateNewDraftOrder_TakesDraftOrder_Return_StateFlow() {

    }
    @Test
      fun testGetDraftOrder_TakesOrderId_Return_StateFlow() {

    }
    @Test
      fun testDeleteDraftOrder_TakesOrderId_Return_StateFlow() {

    }
    @Test
    fun testUpdateDraftOrder_RemoveProduct_Takes_OrderIdAndNewOrder_ReturnStateFlow() {

    }
    @Test
    fun testUpdateDraftOrder_ADDProduct_Takes_OrderIdAndNewOrder_ReturnStateFlow() {

    }

}