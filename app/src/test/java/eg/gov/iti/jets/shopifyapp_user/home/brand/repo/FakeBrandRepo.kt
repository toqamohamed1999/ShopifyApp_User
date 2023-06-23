package eg.gov.iti.jets.shopifyapp_user.home.brand.repo

import eg.gov.iti.jets.shopifyapp_user.home.domain.model.BrandModel
import eg.gov.iti.jets.shopifyapp_user.home.domain.remote.BrandRemoteSourceInterface
import eg.gov.iti.jets.shopifyapp_user.home.domain.repo.BrandRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class FakeBrandRepo(private val remoteSource: BrandRemoteSourceInterface) : BrandRepo {

    override suspend fun getAllBrands(): Flow<BrandModel> {
        return flowOf(remoteSource.getAllBrands())
    }
}