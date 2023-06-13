package eg.gov.iti.jets.shopifyapp_user.home.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import eg.gov.iti.jets.shopifyapp_user.home.domain.repo.AddsRepo
import eg.gov.iti.jets.shopifyapp_user.home.domain.repo.BrandRepo

class HomeFactoryViewModel (private val repo: BrandRepo,private val addsRepo: AddsRepo) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        return if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            HomeViewModel(repo,addsRepo) as T
        } else {
            throw java.lang.IllegalArgumentException("HomeViewModel class not found")
        }
    }
}