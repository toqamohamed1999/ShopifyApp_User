package eg.gov.iti.jets.shopifyapp_user.auth.domain.repo

import com.google.firebase.auth.FirebaseAuth
import eg.gov.iti.jets.shopifyapp_user.auth.data.remote.Result
import eg.gov.iti.jets.shopifyapp_user.auth.signUp.data.model.SignupUser
import kotlinx.coroutines.tasks.await

class FirebaseRepoImplementation(private val auth: FirebaseAuth) : FirebaseRepoInterface {
    override suspend fun signUpUser(user: SignupUser): Result<String> {
        return try {
            auth.createUserWithEmailAndPassword(user.email, user.password).await()
            // Send verification email
            auth.currentUser?.sendEmailVerification()?.await()
            Result.Success(auth.currentUser?.uid)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }
}