package eg.gov.iti.jets.shopifyapp_user.auth.domain.repo

import com.google.firebase.auth.FirebaseAuth
import eg.gov.iti.jets.shopifyapp_user.auth.data.remote.ResponseState
import eg.gov.iti.jets.shopifyapp_user.auth.signUp.data.model.SignupUser
import kotlinx.coroutines.tasks.await

class FirebaseRepoImplementation(private val auth: FirebaseAuth) : FirebaseRepoInterface {
    override suspend fun signUpUser(user: SignupUser): ResponseState<String> {
        return try {
            auth.createUserWithEmailAndPassword(user.email, user.password)?.await()
            // Send verification email
            auth.currentUser?.sendEmailVerification()?.await()
            ResponseState.Success(auth.currentUser?.uid)
        } catch (e: Exception) {
            ResponseState.Error(e)
        }
    }
}