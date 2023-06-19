package eg.gov.iti.jets.shopifyapp_user.categories.domain.remote

import eg.gov.iti.jets.shopifyapp_user.categories.domain.model.AllCategories

interface CategoryRemoteSourceInterface {
    suspend fun getCategories(): AllCategories
}