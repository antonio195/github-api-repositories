package com.antoniocostadossantos.githubapirepositories.validation

class ModelValidation {

    fun checkLanguage(language: String): Boolean {
        return language.isEmpty()
    }

    fun checkUser(userName: String): Boolean {
        return userName.isEmpty()
    }

}