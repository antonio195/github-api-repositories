package com.antoniocostadossantos.githubapirepositories.ui.fragments.searchUserFragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.antoniocostadossantos.githubapirepositories.model.user.UserResponse
import com.antoniocostadossantos.githubapirepositories.repository.MainRepository
import com.antoniocostadossantos.githubapirepositories.util.StateResource
import com.antoniocostadossantos.githubapirepositories.util.handleRespons
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SearchUserViewModel(
    private val mainRepository: MainRepository
) : ViewModel() {

    private val _users = MutableLiveData<StateResource<UserResponse>>()
    val users: LiveData<StateResource<UserResponse>> = _users

    fun getUser(
        owner: String,
    ) = viewModelScope.launch(Dispatchers.IO) {
        _users.postValue(StateResource.Loading())
        val response = mainRepository.getUser(
            owner = owner
        )
        _users.postValue(handleRespons(response))
    }

}