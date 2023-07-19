package eg.gov.iti.jets.shopifyapp_user.base.domain.data.remote

import eg.gov.iti.jets.shopifyapp_user.base.domain.remote.DraftOrderAPIService
import eg.gov.iti.jets.shopifyapp_user.base.domain.remote.FavDraftOrderRemoteSource
import eg.gov.iti.jets.shopifyapp_user.base.model.FavDraftOrderResponse
import eg.gov.iti.jets.shopifyapp_user.base.remote.AppRetrofit
import eg.gov.iti.jets.shopifyapp_user.cart.data.remote.DraftOrderAPIState
import eg.gov.iti.jets.shopifyapp_user.productdetails.domain.remote.ProductService

class FavDraftOrderRemoteSourceImpl : FavDraftOrderRemoteSource {
    private val favDraftOrderService: DraftOrderAPIService by lazy {
        AppRetrofit.retrofit.create(DraftOrderAPIService::class.java)
    }

    override suspend fun createFavDraftOrder(draftOrder: FavDraftOrderResponse): FavDraftOrderResponse {
       return favDraftOrderService.createFavDraftOrder(draftOrder)
    }

    override suspend fun getFavDraftOrder(draftOrderId: Long): FavDraftOrderResponse {
        return favDraftOrderService.getFavDraftOrder(draftOrderId)
    }

    override suspend fun deleteFavDraftOrder(draftOrderId: Long) {
        favDraftOrderService.deleteFavDraftOrder(draftOrderId)
    }

    override suspend fun updateFavDraftOrder(
        draftOrderId: Long,
        draftOrder: FavDraftOrderResponse
    ): FavDraftOrderResponse {
        return favDraftOrderService.updateFavDraftOrder(draftOrderId,draftOrder)
    }
}