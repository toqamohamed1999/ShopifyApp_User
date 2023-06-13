package eg.gov.iti.jets.shopifyapp_user.categories.domain.repo

import eg.gov.iti.jets.shopifyapp_user.categories.domain.model.AllCategories
import kotlinx.coroutines.flow.Flow

interface CategoryRepo {
    suspend fun getAllCategories(): Flow<AllCategories>
}