package eg.gov.iti.jets.shopifyapp_user.cart.domain.remote

import eg.gov.iti.jets.shopifyapp_user.cart.data.model.InventoryLevelResponse
import eg.gov.iti.jets.shopifyapp_user.cart.data.model.UpdateQuantityBody
import eg.gov.iti.jets.shopifyapp_user.cart.data.model.VariantRoot
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import retrofit2.http.*

interface VariantsRemoteSource {

    suspend fun getVariantBYId(
        VariantId : Long
    ) : MutableStateFlow<VariantRoot?>

    suspend fun updateProductQuantity(
         body: UpdateQuantityBody
    ) : MutableStateFlow<InventoryLevelResponse?>
}