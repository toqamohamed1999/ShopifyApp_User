package eg.gov.iti.jets.shopifyapp_user.categories.data.repo

import eg.gov.iti.jets.shopifyapp_user.categories.domain.model.AllCategories
import eg.gov.iti.jets.shopifyapp_user.categories.domain.remote.CategoryRemoteSourceInterface
import eg.gov.iti.jets.shopifyapp_user.categories.domain.repo.CategoryRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class CategoryRepoImp(var remoteSource: CategoryRemoteSourceInterface) : CategoryRepo {

    companion object {
        private var instance: CategoryRepoImp? = null
        fun getInstance(
            remoteSource: CategoryRemoteSourceInterface
        ): CategoryRepoImp? {
            return instance ?: synchronized(this) {
                instance = CategoryRepoImp(remoteSource)
                instance
            }
        }
    }

    override suspend fun getAllCategories(): Flow<AllCategories> {
        return flowOf(remoteSource.getCategories())
    }
}