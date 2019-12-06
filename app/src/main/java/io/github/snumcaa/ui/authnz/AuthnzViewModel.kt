package io.github.snumcaa.ui.authnz

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import io.github.snumcaa.domain.repositories.SignUp

import io.github.snumcaa.domain.repositories.UserRepository
import io.github.snumcaa.networking.BasicAuthClient
import kotlinx.coroutines.Dispatchers

class AuthnzViewModel: ViewModel() {
    private val userRepository: UserRepository =
            BasicAuthClient<UserRepository>().createNoAuth(UserRepository::class.java)

    fun signUp(username: String, password: String): LiveData<Unit> {
        return liveData(Dispatchers.IO) { emit(userRepository.signUp(SignUp(username, password))) }
    }

    fun signIn(context: Context): LiveData<Unit> {
        val authRepository = BasicAuthClient<UserRepository>().createAuth(UserRepository::class.java, context)
        return liveData(Dispatchers.IO) { emit(authRepository.signIn()) }
    }

    override fun onCleared() {
        super.onCleared()
        Log.d("AuthnzViewModel", "onCleared\n")
    }
}