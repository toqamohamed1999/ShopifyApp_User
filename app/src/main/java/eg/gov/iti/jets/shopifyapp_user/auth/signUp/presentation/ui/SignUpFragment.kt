package eg.gov.iti.jets.shopifyapp_user.auth.signUp.presentation.ui

import android.opengl.Visibility
import android.os.Bundle
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import eg.gov.iti.jets.shopifyapp_user.R
import eg.gov.iti.jets.shopifyapp_user.auth.data.remote.AuthRemoteSourceImp
import eg.gov.iti.jets.shopifyapp_user.auth.data.remote.ResponseState
import eg.gov.iti.jets.shopifyapp_user.auth.data.repo.APIRepoImplementation
import eg.gov.iti.jets.shopifyapp_user.auth.domain.model.Customer
import eg.gov.iti.jets.shopifyapp_user.auth.domain.model.SignupModel
import eg.gov.iti.jets.shopifyapp_user.auth.domain.model.SignupRequest
import eg.gov.iti.jets.shopifyapp_user.auth.domain.repo.FirebaseRepoImplementation
import eg.gov.iti.jets.shopifyapp_user.auth.signUp.data.model.SignupUser
import eg.gov.iti.jets.shopifyapp_user.auth.signUp.data.remote.AuthRepo
import eg.gov.iti.jets.shopifyapp_user.auth.signUp.presentation.viewModel.SignUpViewModel
import eg.gov.iti.jets.shopifyapp_user.auth.signUp.presentation.viewModel.SignUpViewModelFactory
import eg.gov.iti.jets.shopifyapp_user.base.model.DraftOrderFav
import eg.gov.iti.jets.shopifyapp_user.base.model.FavDraftOrderResponse
import eg.gov.iti.jets.shopifyapp_user.base.model.LineItems
import eg.gov.iti.jets.shopifyapp_user.databinding.FragmentSignUpBinding
import eg.gov.iti.jets.shopifyapp_user.util.isConnected
import kotlinx.coroutines.delay
import kotlin.io.path.createTempDirectory


class SignUpFragment : Fragment() {
    private val viewModel: SignUpViewModel by lazy {
        val factory = SignUpViewModelFactory(
            AuthRepo(FirebaseRepoImplementation(Firebase.auth)),
            APIRepoImplementation.getInstance(AuthRemoteSourceImp())!!
        )
        ViewModelProvider(this, factory)[SignUpViewModel::class.java]
    }
    private var dummyLineItemList: ArrayList<LineItems> = arrayListOf()
    private var favDraftOrderId: String = ""
    private var orderDraftOrderId: String = ""
    lateinit var binding: FragmentSignUpBinding
    var uid: String = ""
    private var email = ""
    private var pass = ""
    private var confirmPass = ""
    private var fName = ""
    private var lName = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSignUpBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity).findViewById<AppBarLayout>(R.id.custom_toolBar)?.visibility =
            View.GONE
        binding.btnSignUp.setOnClickListener {
            if (isConnected(requireContext().applicationContext)) {
                email = binding.editEmailSignup.text.toString().trim()
                pass = binding.editPassSignup.text.toString().trim()
                confirmPass = binding.editConfirmPasswordSignUp.text.toString().trim()
                fName = binding.editFNameSignup.text.toString().trim()
                lName = binding.editLastNameSignup.text.toString().trim()
                clearErrors()

                if (fName.isEmpty()) {
                    binding.inputFNameSignup.error = "First name is required"
                    return@setOnClickListener
                }
                if (lName.isEmpty()) {
                    binding.inputLastNameSignup.error = "Last name is required"
                    return@setOnClickListener
                }
                if (email.isEmpty()) {
                    binding.inputEmailSignup.error = "Email is required"
                    return@setOnClickListener
                }
                if (!email.isValidEmail()) {
                    binding.inputEmailSignup.error = "Invalid email format"
                    return@setOnClickListener
                }
                if (pass.isEmpty()) {
                    binding.inputPassSignup.error = "Password is required"
                    return@setOnClickListener
                }
                if (!pass.isValidPassword()) {
                    binding.inputPassSignup.error =
                        "Password should contain at least 8 characters, including one uppercase letter, one lowercase letter, one number, and one special character."
                    return@setOnClickListener
                }
                if (confirmPass.isEmpty()) {
                    binding.inputConfirmPassSignUp.error = "Confirm Password is required"
                    return@setOnClickListener
                }
                if (pass != confirmPass) {
                    binding.inputConfirmPassSignUp.error = "Passwords do not match"
                    return@setOnClickListener
                } else {
                    binding.progressBar.visibility=View.VISIBLE
                    val signupUser = SignupUser(
                        fName,
                        lName,
                        email,
                        pass
                    )
                    viewModel.signUpUser(signupUser)

                }

            } else {
                binding.progressBar.visibility=View.GONE
                Snackbar.make(
                    binding.root,
                    resources.getString(R.string.noInternetConnection),
                    Snackbar.LENGTH_LONG
                )
                    .show()
            }

        }
        val firebaseSignUp = lifecycleScope.launchWhenStarted {
            viewModel.signUpResult.collect { result ->
                when (result) {
                    is ResponseState.Loading -> {
                        println("/////////////Loading//////////////////////")
                    }
                    is ResponseState.Success -> {
                        uid = result.data.toString()
                        dummyLineItemList?.add(
                            LineItems(
                                title = "dummy",
                                quantity = 1,
                                price = "50"
                            )
                        )
                        viewModel.createFavDraftOrder(
                            FavDraftOrderResponse(
                                DraftOrderFav(
                                    note = "fav_draftOrder",
                                    lineItems = dummyLineItemList!!

                                )
                            )
                        )
                        delay(3000)
                        dummyLineItemList?.add(
                            LineItems(
                                title = "dummy",
                                quantity = 1,
                                price = "50"
                            )
                        )
                        viewModel.createFavDraftOrder(
                            FavDraftOrderResponse(
                                DraftOrderFav(
                                    note = "cart_draftOrder",
                                    lineItems = dummyLineItemList!!

                                )
                            )
                        )

                    }
                    is ResponseState.Error -> {
                        println("/////////////Error ${result.exception}//////////////////////")
                        binding.editEmailSignup.error = "Email is already Exist"
                    }
                }
            }
        }
        lifecycleScope.launchWhenStarted {
            viewModel.draftOrder.collect {
                when (it) {
                    is ResponseState.Loading -> {
                        println("/////////////Loading//////////////////////")
                    }
                    is ResponseState.Success -> {
                        if (favDraftOrderId.isEmpty()) {
                            favDraftOrderId = it.data?.draft_order?.id.toString()!!
                        } else {
                            orderDraftOrderId = it.data?.draft_order?.id.toString()!!
                        }
                        if (favDraftOrderId.isNotEmpty() && orderDraftOrderId.isNotEmpty()) {
                            val customer = Customer(
                                email = email,
                                first_name = fName,
                                last_name = lName,
                                tags = "${pass},${uid}",
                                note = "${favDraftOrderId},${orderDraftOrderId}",
                                verified_email = false
                            )
                            viewModel.createCustomerAccount(SignupRequest(customer))
                        }
                    }
                    is ResponseState.Error -> {
                        println("/////////////Error ${it.exception.toString()}//////////////////////")
                    }

                }
            }
        }
        lifecycleScope.launchWhenStarted {
            viewModel.apisignUpResult.collect {
                when (it) {
                    is ResponseState.Loading -> {
                        binding.progressBar.visibility=View.VISIBLE
                    }
                    is ResponseState.Success -> {
                       binding.progressBar.visibility=View.GONE
                    }
                    is ResponseState.Error -> {
                        println("/////////////Error ${it.exception.toString()}//////////////////////")
                    }
                }
            }
        }


    }

    private fun clearErrors() {
        binding.inputPassSignup.error = null
        binding.inputFNameSignup.error = null
        binding.inputLastNameSignup.error = null
        binding.inputConfirmPassSignUp.error = null
        binding.inputEmailSignup.error = null

    }
}

fun String.isValidPassword(): Boolean {
    // Password should contain at least 8 characters, including one uppercase letter, one lowercase letter, one number, and one special character.
    val passwordRegex =
        Regex("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@\$!%*?&])[A-Za-z\\d@\$!%*?&]{8,}$")
    return passwordRegex.matches(this)
}

fun String.isValidEmail(): Boolean {
    return Patterns.EMAIL_ADDRESS.matcher(this).matches()
}
