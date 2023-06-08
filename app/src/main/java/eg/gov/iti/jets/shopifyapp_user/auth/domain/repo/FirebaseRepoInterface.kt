package eg.gov.iti.jets.shopifyapp_user.auth.domain.repo

import com.google.firebase.auth.FirebaseUser
import eg.gov.iti.jets.shopifyapp_user.auth.data.remote.Result
import eg.gov.iti.jets.shopifyapp_user.auth.signUp.data.model.SignupUser

interface FirebaseRepoInterface {
    suspend fun signUpUser(user: SignupUser): Result<String>
}