package eg.gov.iti.jets.shopifyapp_user.auth.signUp.data.remote

import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import eg.gov.iti.jets.shopifyapp_user.auth.data.remote.ResponseState
import eg.gov.iti.jets.shopifyapp_user.auth.domain.repo.FirebaseRepoImplementation
import eg.gov.iti.jets.shopifyapp_user.auth.domain.repo.FirebaseRepoInterface
import eg.gov.iti.jets.shopifyapp_user.auth.signUp.data.model.SignupUser

class AuthRepo (private val signUpUseCase: FirebaseRepoInterface = FirebaseRepoImplementation(Firebase.auth)) {
    suspend fun signUpUser(user: SignupUser): ResponseState<String> {
        return signUpUseCase.signUpUser(user)
    }
    suspend fun checkVerification(email:String,pass:String):ResponseState<Boolean>{
        return signUpUseCase.loginUser(email, pass)
    }
}