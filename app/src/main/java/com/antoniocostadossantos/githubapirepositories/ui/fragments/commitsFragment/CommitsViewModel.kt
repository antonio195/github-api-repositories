package com.antoniocostadossantos.githubapirepositories.ui.fragments.commitsFragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.antoniocostadossantos.githubapirepositories.model.commit.CommitItemResponse
import com.antoniocostadossantos.githubapirepositories.repository.MainRepository
import com.antoniocostadossantos.githubapirepositories.util.StateResource
import com.antoniocostadossantos.githubapirepositories.util.handleRespons
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CommitsViewModel(
    private val mainRepository: MainRepository
) : ViewModel() {

    private val _repoCommits = MutableLiveData<StateResource<List<CommitItemResponse>>>()
    val repoCommits: LiveData<StateResource<List<CommitItemResponse>>> = _repoCommits

    fun getRepoCommits(
        owner: String,
        repositoryName: String,
        page: Int
    ) = viewModelScope.launch(Dispatchers.IO) {
        _repoCommits.postValue(StateResource.Loading())
        val response = mainRepository.getCommits(
            owner = owner,
            repositoryName = repositoryName,
            page = page
        )
        _repoCommits.postValue(handleRespons(response))
    }

}