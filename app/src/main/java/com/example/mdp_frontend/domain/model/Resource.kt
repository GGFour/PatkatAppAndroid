package com.example.mdp_frontend.domain.model

sealed class Resource<out R> {
    data class Success<out R>(val data: R) : Resource<R>()
    data class Error(val exception: Exception) : Resource<Nothing>()
    object Loading : Resource<Nothing>()
}