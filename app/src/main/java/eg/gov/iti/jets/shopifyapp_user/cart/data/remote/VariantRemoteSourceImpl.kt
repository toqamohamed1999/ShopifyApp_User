package eg.gov.iti.jets.shopifyapp_user.cart.data.remote

import eg.gov.iti.jets.shopifyapp_user.base.remote.AppRetrofit
import eg.gov.iti.jets.shopifyapp_user.cart.data.model.InventoryLevelResponse
import eg.gov.iti.jets.shopifyapp_user.cart.data.model.UpdateQuantityBody
import eg.gov.iti.jets.shopifyapp_user.cart.data.model.VariantRoot
import eg.gov.iti.jets.shopifyapp_user.cart.domain.remote.VariantServices
import eg.gov.iti.jets.shopifyapp_user.cart.domain.remote.VariantsRemoteSource
import kotlinx.coroutines.flow.MutableStateFlow

class VariantRemoteSourceImpl:VariantsRemoteSource {
    private val service = AppRetrofit.retrofit.create(VariantServices::class.java)
    override suspend fun getVariantBYId(variantId: Long): MutableStateFlow<VariantRoot?> {
     return try{
         MutableStateFlow(service.getVariantBYId(variantId))
     }catch (e:java.lang.Exception){
         MutableStateFlow(null)
     }
    }

    override suspend fun updateProductQuantity(body: UpdateQuantityBody): MutableStateFlow<InventoryLevelResponse?> {
        return try{
            MutableStateFlow(service.updateProductQuantity(body))
        }catch (e:java.lang.Exception){
            MutableStateFlow(null)
        }
    }
}