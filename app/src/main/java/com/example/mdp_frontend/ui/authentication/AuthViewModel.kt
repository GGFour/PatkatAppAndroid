package com.example.mdp_frontend.ui.authentication

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mdp_frontend.domain.model.Resource
import com.example.mdp_frontend.domain.use_case.user.UserUseCases
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val useCases: UserUseCases,
) : ViewModel() {

    private val _loginFlow = MutableStateFlow<Resource<FirebaseUser>?>(null)
    val loginFlow: StateFlow<Resource<FirebaseUser>?> = _loginFlow

    private val _registerFlow = MutableStateFlow<Resource<FirebaseUser>?>(null)
    val registerFlow: StateFlow<Resource<FirebaseUser>?> = _registerFlow

    val currentUser: FirebaseUser?
        get() = useCases.currentUser()

    init {
        if (useCases.currentUser() != null) {
            _loginFlow.value = Resource.Success(useCases.currentUser()!!)
        }
    }

    fun login(email: String, password: String) = viewModelScope.launch {
        _loginFlow.value = Resource.Loading
        val result = useCases.login(email, password)
        _loginFlow.value = result

    }

    fun register(name: String, email: String, password: String) = viewModelScope.launch {
        _registerFlow.value = Resource.Loading
        val result = useCases.register(name, email, password)
        _registerFlow.value = result
    }

    fun logout() {
        useCases.logout()
        _loginFlow.value = null
        _registerFlow.value = null
    }
}