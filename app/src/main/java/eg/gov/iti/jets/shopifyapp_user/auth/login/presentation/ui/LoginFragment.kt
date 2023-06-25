package eg.gov.iti.jets.shopifyapp_user.auth.login.presentation.ui


import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import eg.gov.iti.jets.shopifyapp_user.R
import eg.gov.iti.jets.shopifyapp_user.auth.data.remote.ResponseState
import eg.gov.iti.jets.shopifyapp_user.auth.domain.model.Customer
import eg.gov.iti.jets.shopifyapp_user.auth.login.presentation.viewModel.LoginViewModel
import eg.gov.iti.jets.shopifyapp_user.base.model.FavDraftOrderResponse
import eg.gov.iti.jets.shopifyapp_user.base.model.LineItems
import eg.gov.iti.jets.shopifyapp_user.base.model.toFavRoomPojo
import eg.gov.iti.jets.shopifyapp_user.databinding.FragmentLoginBinding
import eg.gov.iti.jets.shopifyapp_user.settings.data.local.UserSettings
import eg.gov.iti.jets.shopifyapp_user.util.Dialogs
import eg.gov.iti.jets.shopifyapp_user.util.createAlertDialog
import eg.gov.iti.jets.shopifyapp_user.util.isConnected
import eg.gov.iti.jets.shopifyapp_user.util.isValidEmail
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


class LoginFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding
    private var email = ""
    private var pass = ""
    private lateinit var viewModel: LoginViewModel
    private var favDraftOrderResponse: FavDraftOrderResponse = FavDraftOrderResponse()
    private var customer: Customer = Customer()
    private var favLineItems: ArrayList<LineItems> = arrayListOf()
    private val alertDialog: AlertDialog by lazy {
        createAlertDialog(requireContext(), "")
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.txtSignUp.setOnClickListener {
            Navigation.findNavController(requireView())
                .navigate(R.id.action_loginFragment_to_signUpFragment)
        }
        binding.txtSkip.setOnClickListener {
            UserSettings.currencyCode="EGP"
            UserSettings.currentCurrencyValue = 1.0
            binding.root.findNavController()
                .navigate(R.id.homeFragment)
        }
        viewModel = ViewModelProvider(this)[LoginViewModel::class.java]
        binding.btnLogIn.setOnClickListener {
            if (isConnected(requireContext().applicationContext)) {

                email = binding.emailEditLogin.text.toString().trim()
                pass = binding.passwordEditLogin.text.toString().trim()
                binding.emailLayoutLogin.error = null
                binding.passwordLayoutLogin.error = null
                if (email.isEmpty()) {
                    binding.emailLayoutLogin.error = "Email is required"
                    return@setOnClickListener
                }
                if (!email.isValidEmail()) {
                    binding.emailLayoutLogin.error = "Invalid email format"
                    return@setOnClickListener
                }
                if (pass.isEmpty()) {
                    binding.passwordLayoutLogin.error = "Password is required"
                    return@setOnClickListener
                } else {
                    alertDialog.show()
                    viewModel.getCustomerByEmail(email)

                }

            } else {
                alertDialog.dismiss()
                Dialogs.SnakeToast(
                    requireView(),
                    resources.getString(R.string.noInternetConnection)
                )
            }


        }
        lifecycleScope.launchWhenStarted {
            viewModel.returnCustomer.collect {
                when (it) {
                    is ResponseState.Loading -> {

                    }
                    is ResponseState.Success -> {
                        if (it.data?.customers?.isNotEmpty() == true) {
                            customer = it.data.customers?.get(0)!!
                        }
                        if (it.data?.customers?.isEmpty() == true||customer.tags!!.split("#")[0] != pass) {
                            binding.passwordLayoutLogin.error = null
                            binding.emailLayoutLogin.error = null
                            alertDialog.dismiss()
                            val alertDialog = AlertDialog.Builder(context)
                            alertDialog.apply {
                                setIcon(R.drawable.baseline_info_24)
                                setTitle("warning")
                                setMessage(resources.getString(R.string.invalid_email_or_pass))
                                setPositiveButton("OK") { _: DialogInterface?, _: Int ->
                                }
                            }.create().show()
                            viewModel.resetFlow()

                        } else {
                            binding.emailLayoutLogin.error = null
                            binding.passwordLayoutLogin.error = null
                            viewModel.checkVerification(email, pass)
                            viewModel.resetFlow()
                        }

                    }
                    is ResponseState.Error -> {
                        alertDialog.dismiss()
                        println("/////////////Error ${it.exception.toString()}//////////////////////")
                        viewModel.resetFlow()
                    }
                }
            }
        }
        lifecycleScope.launch {
            viewModel.isVerified.collect {
                when (it) {
                    is ResponseState.Loading -> {

                    }
                    is ResponseState.Success -> {
                        if (it.data == true) {
                            saveCustomerInSharedPref()
                            loadFavToRoom()
                            alertDialog.dismiss()
                            Navigation.findNavController(requireView())
                                .navigate(R.id.action_loginFragment_to_homeFragment)
                        } else {
                            alertDialog.dismiss()
                            viewModel.checkVerification(email, pass)
                            val alertDialog = AlertDialog.Builder(context)
                            alertDialog.apply {
                                setIcon(R.drawable.baseline_info_24)
                                setTitle("Info")
                                setMessage(resources.getString(R.string.go_to_verify_email_from_login))
                                setPositiveButton("OK") { _: DialogInterface?, _: Int ->
                                }
                            }.create().show()
                            viewModel.resetVerificationFlow()
                        }
                    }
                    is ResponseState.Error -> {
                        alertDialog.dismiss()
                        println("////////////////Firebase Error $it/////////////")
                        val alertDialog = AlertDialog.Builder(context)
                        alertDialog.apply {
                            setIcon(R.drawable.baseline_info_24)
                            setTitle("warning")
                            setMessage(resources.getString(R.string.wait_to_sent_anotherMail))
                            setPositiveButton("OK") { _: DialogInterface?, _: Int ->
                            }
                        }.create().show()
                        viewModel.resetVerificationFlow()


                    }
                }
            }
        }

        lifecycleScope.launchWhenStarted {
            viewModel.favProducts.collectLatest {
                when (it) {
                    is ResponseState.Loading -> {

                    }
                    is ResponseState.Success -> {
                        favDraftOrderResponse = it.data!!
                        favLineItems = favDraftOrderResponse.draft_order!!.lineItems
                    }
                    is ResponseState.Error -> {
                        println("Draft order Error ${it.exception}")
                    }
                }
            }
        }

    }

    private suspend fun loadFavToRoom() {
        viewModel.getFavRemoteProducts(
            customer.note!!.split(
                "#"
            )[0].toLong()
        )
        delay(3000)
        favLineItems.removeFirst()
        for (fav in favLineItems) {
            viewModel.insertFavProduct(fav.toFavRoomPojo())
        }
    }

    private fun saveCustomerInSharedPref() {
        UserSettings.userAPI_Id = customer.id.toString()
        UserSettings.userName = customer.first_name.toString()
        UserSettings.userEmail = customer.email.toString()
        UserSettings.favoriteDraftOrderId = customer.note!!.split("#")[0]
        UserSettings.cartDraftOrderId = customer.note!!.split("#")[1]
        UserSettings.userPassword = pass
        try {
            UserSettings.currencyCode = customer.note?.split("#")?.get(2) ?: "EGP"
            UserSettings.currentCurrencyValue = customer.note?.split("#")?.get(3)?.toDouble() ?: 1.0
        }catch(e:java.lang.Exception){
            print(e.message)
        }
        UserSettings.saveSettings()
    }
}