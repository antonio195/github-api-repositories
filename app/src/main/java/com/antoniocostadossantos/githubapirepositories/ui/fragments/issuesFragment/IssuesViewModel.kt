package com.antoniocostadossantos.githubapirepositories.ui.fragments.issuesFragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.antoniocostadossantos.githubapirepositories.model.issues.IssuesItemResponse
import com.antoniocostadossantos.githubapirepositories.repository.MainRepository
import com.antoniocostadossantos.githubapirepositories.util.StateResource
import com.antoniocostadossantos.githubapirepositories.util.handleRespons
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class IssuesViewModel(
    private val mainRepository: MainRepository
) : ViewModel() {

    private val _repoIssues = MutableLiveData<StateResource<List<IssuesItemResponse>>>()
    val repoIssues: LiveData<StateResource<List<IssuesItemResponse>>> = _repoIssues

    fun getRepoIssues(
        owner: String,
        repositoryName: String,
        page: Int
    ) = viewModelScope.launch(Dispatchers.IO) {
        _repoIssues.postValue(StateResource.Loading())
        val response = mainRepository.getIssues(
            owner = owner,
            repositoryName = repositoryName,
            page = page
        )
        _repoIssues.postValue(handleRespons(response))
    }
}