package eg.gov.iti.jets.shopifyapp_user.auth.signUp.data.model.remote

import eg.gov.iti.jets.shopifyapp_user.auth.data.remote.Result
import eg.gov.iti.jets.shopifyapp_user.auth.domain.repo.FirebaseRepoInterface
import eg.gov.iti.jets.shopifyapp_user.auth.signUp.data.model.SignupUser

class AuthRepo (private val signUpUseCase: FirebaseRepoInterface) {
    suspend fun signUpUser(user: SignupUser): Result<String> {
        return signUpUseCase.signUpUser(user)
    }
}