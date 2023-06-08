package eg.gov.iti.jets.shopifyapp_user.auth.signUp.presentation.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import eg.gov.iti.jets.shopifyapp_user.R
import eg.gov.iti.jets.shopifyapp_user.auth.data.remote.Result
import eg.gov.iti.jets.shopifyapp_user.auth.domain.repo.FirebaseRepoImplementation
import eg.gov.iti.jets.shopifyapp_user.auth.signUp.data.model.SignupUser
import eg.gov.iti.jets.shopifyapp_user.auth.signUp.data.model.remote.AuthRepo
import eg.gov.iti.jets.shopifyapp_user.auth.signUp.presentation.viewModel.SignUpViewModel
import eg.gov.iti.jets.shopifyapp_user.auth.signUp.presentation.viewModel.SignUpViewModelFactory
import eg.gov.iti.jets.shopifyapp_user.databinding.FragmentSignUpBinding

class SignUpFragment : Fragment() {
    private val viewModel: SignUpViewModel by viewModels {
        SignUpViewModelFactory(AuthRepo(FirebaseRepoImplementation(Firebase.auth)))
    }
    lateinit var binding: FragmentSignUpBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSignUpBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnSignUp.setOnClickListener {
            val signupUser = SignupUser(
                binding.editFNameSignup.toString(),
                binding.editLastNameSignup.toString(),
                binding.editEmailSignup.text.toString().trim(),
                binding.editPassSignup.text.toString().trim()
            )
            viewModel.signUpUser(signupUser)
        }
        lifecycleScope.launchWhenStarted {
            viewModel.signUpResult.collect { result ->
                when (result) {
                    is Result.Loading -> {
                        println("/////////////Loading//////////////////////")
                    }
                    is Result.Success -> {
                        val uid = result.data
                        println("/////////////Success ${uid}//////////////////////")
                    }
                    is Result.Error -> {
                        println("/////////////Error ${result.exception}//////////////////////")
                    }
                }
            }
        }
    }
}