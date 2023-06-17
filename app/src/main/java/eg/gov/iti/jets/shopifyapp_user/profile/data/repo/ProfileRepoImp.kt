package eg.gov.iti.jets.shopifyapp_user.profile.data.repo

import eg.gov.iti.jets.shopifyapp_user.base.model.orders.OrderDetails
import eg.gov.iti.jets.shopifyapp_user.profile.domain.remote.ProfileRemoteSourceInterface
import eg.gov.iti.jets.shopifyapp_user.profile.domain.repo.ProfileRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class ProfileRepoImp(var remoteSource: ProfileRemoteSourceInterface) : ProfileRepo {

    companion object {
        private var instance: ProfileRepoImp? = null
        fun getInstance(
            remoteSource: ProfileRemoteSourceInterface
        ): ProfileRepoImp? {
            return instance ?: synchronized(this) {
                instance = ProfileRepoImp(remoteSource)
                instance
            }
        }
    }

    override suspend fun getOrdersUser(id: Long): Flow<OrderDetails> {
        return flowOf(remoteSource.getOrdersUser(id))
    }
}