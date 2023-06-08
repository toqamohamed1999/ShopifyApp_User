package eg.gov.iti.jets.shopifyapp_user.home.domain.repo

import eg.gov.iti.jets.shopifyapp_user.home.domain.model.BrandModel
import kotlinx.coroutines.flow.Flow

interface BrandRepo {
    suspend fun getAllBrands(): Flow<BrandModel>
}