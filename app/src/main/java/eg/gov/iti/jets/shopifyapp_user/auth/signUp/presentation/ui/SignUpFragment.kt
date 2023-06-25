package eg.gov.iti.jets.shopifyapp_user.auth.signUp.presentation.ui

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import com.google.android.material.appbar.AppBarLayout
import eg.gov.iti.jets.shopifyapp_user.R
import eg.gov.iti.jets.shopifyapp_user.auth.data.remote.ResponseState
import eg.gov.iti.jets.shopifyapp_user.auth.domain.model.Customer
import eg.gov.iti.jets.shopifyapp_user.auth.domain.model.SignupRequest
import eg.gov.iti.jets.shopifyapp_user.auth.signUp.data.model.SignupUser
import eg.gov.iti.jets.shopifyapp_user.auth.signUp.presentation.viewModel.SignUpViewModel
import eg.gov.iti.jets.shopifyapp_user.base.model.DraftOrderFav
import eg.gov.iti.jets.shopifyapp_user.base.model.FavDraftOrderResponse
import eg.gov.iti.jets.shopifyapp_user.base.model.LineItems
import eg.gov.iti.jets.shopifyapp_user.databinding.FragmentSignUpBinding
import eg.gov.iti.jets.shopifyapp_user.util.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class SignUpFragment : Fragment() {
    private lateinit var viewModel: SignUpViewModel
    private var dummyLineItemList: ArrayList<LineItems> = arrayListOf()
    private var favDraftOrderId: String = ""
    private var cartDraftOrderId: String = ""
    lateinit var binding: FragmentSignUpBinding
    private var uid: String = ""
    private var email = ""
    private var pass = ""
    private var confirmPass = ""
    private var fName = ""
    private var lName = ""
    private val alertDialog: AlertDialog by lazy {
        createAlertDialog(requireContext(), "")
    }

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
        viewModel = ViewModelProvider(this)[SignUpViewModel::class.java]
        binding.textViewHaveAcount.setOnClickListener {
            Navigation.findNavController(requireView())
                .navigate(R.id.action_signUpFragment_to_loginFragment)
        }
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
                    alertDialog.show()
                    val signupUser = SignupUser(
                        fName,
                        lName,
                        email,
                        pass
                    )
                    viewModel.signUpUser(signupUser)

                }

            } else {
                alertDialog.dismiss()
                Dialogs.SnakeToast(
                    requireView(),
                    resources.getString(R.string.noInternetConnection)
                )
            }

        }
        viewModel.signUpResult.observe(viewLifecycleOwner) { result ->
            when (result) {
                is ResponseState.Loading -> {
                    println("/////////////Loading//////////////////////")
                }
                is ResponseState.Success -> {
                    uid = result.data.toString()
                    dummyLineItemList.add(
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
                                lineItems = dummyLineItemList

                            )
                        )
                    )
                    lifecycleScope.launch { delay(3000) }
                    /////////////////////////////////////////////////////
                    dummyLineItemList = ArrayList(0)
                    dummyLineItemList.add(
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
                                lineItems = dummyLineItemList

                            )
                        )
                    )


                }
                is ResponseState.Error -> {
                    binding.editEmailSignup.error = "Email is already Exist"
                    println("/////////////Error ${result.exception}//////////////////////")
                    alertDialog.dismiss()
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
                            favDraftOrderId = it.data?.draft_order?.id.toString()
                        } else {
                            cartDraftOrderId = it.data?.draft_order?.id.toString()
                        }
                        if (favDraftOrderId.isNotEmpty() && cartDraftOrderId.isNotEmpty()) {
                            val customer = Customer(
                                email = email,
                                first_name = fName,
                                last_name = lName,
                                tags = "${pass}#${uid}#false",//password,fireBaserUserId,emailVerification
                                note = "${favDraftOrderId}#${cartDraftOrderId}#EGP#1.0"
                            )
                            viewModel.createCustomerAccount(SignupRequest(customer))
                        }
                    }
                    is ResponseState.Error -> {
                        binding.editEmailSignup.error = "Email is already Exist"
                        alertDialog.dismiss()
                        println("/////////////Error ${it.exception}//////////////////////")
                    }

                }
            }
        }
        viewModel.apisignUpResult.observe(viewLifecycleOwner) {
            when (it) {
                is ResponseState.Loading -> {

                }
                is ResponseState.Success -> {
                    alertDialog.dismiss()
                    val alertDialog = AlertDialog.Builder(context)

                    alertDialog.apply {
                        setIcon(R.drawable.baseline_info_24)
                        setTitle("Info")
                        setMessage(resources.getString(R.string.registration_completed))
                        setPositiveButton("OK") { _: DialogInterface?, _: Int ->
                            Navigation.findNavController(requireView())
                                .navigate(R.id.action_signUpFragment_to_loginFragment)
                        }
                    }.create().show()


                }
                is ResponseState.Error -> {
                    println("/////////////Error ${it.exception}//////////////////////")
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


