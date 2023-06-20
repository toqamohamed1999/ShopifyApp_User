package eg.gov.iti.jets.shopifyapp_user.auth.login.presentation.ui


import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import com.google.android.material.snackbar.Snackbar
import eg.gov.iti.jets.shopifyapp_user.R
import eg.gov.iti.jets.shopifyapp_user.auth.data.remote.ResponseState
import eg.gov.iti.jets.shopifyapp_user.auth.login.presentation.viewModel.LoginViewModel
import eg.gov.iti.jets.shopifyapp_user.base.model.FavDraftOrderResponse
import eg.gov.iti.jets.shopifyapp_user.base.model.LineItems
import eg.gov.iti.jets.shopifyapp_user.base.model.toFavRoomPojo
import eg.gov.iti.jets.shopifyapp_user.databinding.FragmentLoginBinding
import eg.gov.iti.jets.shopifyapp_user.settings.data.local.UserSettings
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
    private var favDraftOrderResponse : FavDraftOrderResponse = FavDraftOrderResponse()
    private var favLineItems : ArrayList<LineItems> = arrayListOf()

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
                }
               else {
                    binding.progressBar.visibility = View.VISIBLE
                    viewModel.getCustomerByEmail(email)

                }

            } else {
                binding.progressBar.visibility = View.GONE
                Snackbar.make(
                    binding.root,
                    resources.getString(R.string.noInternetConnection),
                    Snackbar.LENGTH_LONG
                )
                    .show()
            }


        }
        lifecycleScope.launchWhenStarted {
            viewModel.returnCustomer.collect {
                when (it) {
                    is ResponseState.Loading -> {

                    }
                    is ResponseState.Success -> {
                        binding.progressBar.visibility = View.GONE
                         if (it.data?.customers?.size == 0) {
                            binding.emailLayoutLogin.error = "email is not found"
                            binding.passwordLayoutLogin.error=null

                         } else if (it.data?.customers?.get(0)?.tags!!.split("#")[0] != pass) {
                            println("///////////////////////pass${(it.data?.customers?.get(0)?.tags!!.split("#")[0])}//////")
                            binding.passwordLayoutLogin.error = "wrong password"
                            binding.emailLayoutLogin.error=null
                        }
                        else if (it.data?.customers?.get(0)?.tags!!.split("#")[2] =="true") {
                             println("///////////////////////verification ${(it.data?.customers?.get(0)?.tags!!.split("#")[2])}")
                            binding.emailLayoutLogin.error=null
                            binding.passwordLayoutLogin.error=null
                             UserSettings.userAPI_Id=it.data.customers[0].id.toString()
                             UserSettings.userName=it.data.customers[0].first_name.toString()
                             UserSettings.userEmail=it.data.customers[0].email.toString()
                             UserSettings.favoriteDraftOrderId=it.data?.customers?.get(0)?.note!!.split("#")[0]
                             UserSettings.cartDraftOrderId=it.data?.customers?.get(0)?.note!!.split("#")[1]
                             UserSettings.userPassword=pass
                             UserSettings.saveSettings()
                             viewModel.getFavRemoteProducts(it.data?.customers?.get(0)?.note!!.split("#")[0].toLong())
                            delay(3000)
                             loadFavToRoom()
                            Navigation.findNavController(requireView()).navigate(R.id.action_loginFragment_to_homeFragment)
                        }
                        else {
                        val alertDialog = AlertDialog.Builder(context)

                        alertDialog.apply {
                            setIcon(R.drawable.baseline_info_24)
                            setTitle("Info")
                            setMessage(resources.getString(R.string.go_to_verify_email_from_login))
                            setPositiveButton("OK") { _: DialogInterface?, _: Int ->
                            }
                        }.create().show()

                        }
                    }
                    is ResponseState.Error -> {
                        binding.progressBar.visibility = View.GONE
                        println("/////////////Error ${it.exception.toString()}//////////////////////")
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
                        favDraftOrderResponse=it.data!!
                        favLineItems=favDraftOrderResponse.draft_order!!.lineItems
                    }
                    is ResponseState.Error -> {
                        println("Draft order Error ${it.exception}")
                    }
                }
            }
        }

    }
    fun loadFavToRoom()
    {
        favLineItems.removeFirst()
        for (fav in favLineItems){
            viewModel.insertFavProduct(fav.toFavRoomPojo())
        }
    }
}