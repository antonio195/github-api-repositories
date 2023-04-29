package com.antoniocostadossantos.githubapirepositories.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.antoniocostadossantos.githubapirepositories.model.commit.Commit
import com.antoniocostadossantos.githubapirepositories.model.issues.Issues
import com.antoniocostadossantos.githubapirepositories.model.license.LicenseRequest
import com.antoniocostadossantos.githubapirepositories.model.repo.BaseRequest
import com.antoniocostadossantos.githubapirepositories.model.repo.Item
import com.antoniocostadossantos.githubapirepositories.repository.MainRepository
import com.antoniocostadossantos.githubapirepositories.util.StateResource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response

class MainViewModel(private val mainRepository: MainRepository) : ViewModel() {

    private val _repositories = MutableLiveData<StateResource<BaseRequest>>()
    val repositories: LiveData<StateResource<BaseRequest>> = _repositories

    private val _userRepository = MutableLiveData<StateResource<Item>>()
    val userRepository: LiveData<StateResource<Item>> = _userRepository

    private val _repoCommits = MutableLiveData<StateResource<Commit>>()
    val repoCommits: LiveData<StateResource<Commit>> = _repoCommits

    private val _repoIssues = MutableLiveData<StateResource<Issues>>()
    val repoIssues: LiveData<StateResource<Issues>> = _repoIssues

    private val _repoLicense = MutableLiveData<StateResource<LicenseRequest>>()
    val repoLicense: LiveData<StateResource<LicenseRequest>> = _repoLicense

    fun getAllRepositories(
        language: String,
        page: Int
    ) = viewModelScope.launch(Dispatchers.IO) {
        val response = mainRepository.getRepositories(
            language = language,
            page = page
        )
        _repositories.postValue(handleResponse(response))
    }

    fun getUserRepository(
        owner: String,
        repositoryName: String
    ) = viewModelScope.launch(Dispatchers.IO) {
        val response = mainRepository.getRepository(
            owner = owner,
            repositoryName = repositoryName
        )
        _userRepository.postValue(handleResponse(response))
    }

    fun getRepoCommits(
        owner: String,
        repositoryName: String
    ) = viewModelScope.launch(Dispatchers.IO) {
        val response = mainRepository.getCommits(
            owner = owner,
            repositoryName = repositoryName
        )
        _repoCommits.postValue(handleResponse(response))
    }

    fun getRepoIssues(
        owner: String,
        repositoryName: String
    ) = viewModelScope.launch(Dispatchers.IO) {
        val response = mainRepository.getIssues(
            owner = owner,
            repositoryName = repositoryName
        )
        _repoIssues.postValue(handleResponse(response))
    }

    fun getLicense(
        owner: String,
        repositoryName: String
    ) = viewModelScope.launch(Dispatchers.IO) {
        val response = mainRepository.getLicense(
            owner = owner,
            repositoryName = repositoryName
        )
        _repoLicense.postValue(handleResponse(response))
    }

    private fun <T> handleResponse(response: Response<T>): StateResource<T> {
        if (response.isSuccessful) {
            response.body()?.let { values ->
                return StateResource.Success(values)
            }
        }
        return StateResource.Error(response.message())
    }


}