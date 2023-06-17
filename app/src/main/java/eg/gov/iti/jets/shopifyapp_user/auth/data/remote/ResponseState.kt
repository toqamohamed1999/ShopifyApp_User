package eg.gov.iti.jets.shopifyapp_user.auth.data.remote

sealed class ResponseState<out T> {
    data class Success<out T>(val data: T?) : ResponseState<T>()
    data class Error(val exception: Exception) : ResponseState<Nothing>()
    object Loading : ResponseState<Nothing>()
}