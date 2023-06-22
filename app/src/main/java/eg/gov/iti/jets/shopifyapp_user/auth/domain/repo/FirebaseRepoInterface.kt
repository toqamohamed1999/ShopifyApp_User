package eg.gov.iti.jets.shopifyapp_user.auth.domain.repo

import com.google.firebase.auth.FirebaseUser
import eg.gov.iti.jets.shopifyapp_user.auth.data.remote.ResponseState
import eg.gov.iti.jets.shopifyapp_user.auth.signUp.data.model.SignupUser

interface FirebaseRepoInterface  {
    suspend fun signUpUser(user: SignupUser): ResponseState<String>
    suspend fun loginUser(email: String,pass:String): ResponseState<Boolean>
}