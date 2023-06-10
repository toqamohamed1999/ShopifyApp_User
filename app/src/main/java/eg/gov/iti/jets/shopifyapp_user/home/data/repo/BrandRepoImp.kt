package eg.gov.iti.jets.shopifyapp_user.home.data.repo

import eg.gov.iti.jets.shopifyapp_user.home.domain.model.BrandModel
import eg.gov.iti.jets.shopifyapp_user.home.domain.remote.BrandRemoteSourceInterface
import eg.gov.iti.jets.shopifyapp_user.home.domain.repo.BrandRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class BrandRepoImp(var remoteSource: BrandRemoteSourceInterface) : BrandRepo{

    companion object {
        private var instance: BrandRepoImp? = null
        fun getInstance(
            remoteSource: BrandRemoteSourceInterface
        ): BrandRepoImp? {
            return instance ?: synchronized(this) {
                instance = BrandRepoImp(remoteSource)
                instance
            }
        }
    }

    override suspend fun getAllBrands(): Flow<BrandModel> {
        return  flowOf(remoteSource.getAllBrands())
    }
}