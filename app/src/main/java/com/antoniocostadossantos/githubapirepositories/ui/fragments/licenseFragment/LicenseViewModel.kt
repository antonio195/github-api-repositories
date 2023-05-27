package com.antoniocostadossantos.githubapirepositories.ui.fragments.licenseFragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.antoniocostadossantos.githubapirepositories.model.license.LicenseResponse
import com.antoniocostadossantos.githubapirepositories.repository.MainRepository
import com.antoniocostadossantos.githubapirepositories.util.StateResource
import com.antoniocostadossantos.githubapirepositories.util.handleRespons
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LicenseViewModel(
    private val mainRepository: MainRepository
) : ViewModel() {

    private val _repoLicense = MutableLiveData<StateResource<LicenseResponse>>()
    val repoLicense: LiveData<StateResource<LicenseResponse>> = _repoLicense

    fun getLicense(
        owner: String,
        repositoryName: String
    ) = viewModelScope.launch(Dispatchers.IO) {
        _repoLicense.postValue(StateResource.Loading())
        val response = mainRepository.getLicense(
            owner = owner,
            repositoryName = repositoryName
        )
        _repoLicense.postValue(handleRespons(response))
    }
}