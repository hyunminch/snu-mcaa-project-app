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

    fun signUp(username: String, password: String): LiveData<SignUpResult> {
        return liveData(Dispatchers.IO) {
            try {
                userRepository.signUp(SignUp(username, password))
                emit(SignUpResult(false))
            } catch (e: Exception) {
                Log.i("AuthnzViewModel", e.message ?: "")
                emit(SignUpResult(true))
            }
        }
    }

    fun signIn(context: Context): LiveData<SignInResult> {
        val authRepository = BasicAuthClient<UserRepository>().createAuth(UserRepository::class.java, context)

        return liveData(Dispatchers.IO) {
            try {
                authRepository.signIn()
                emit(SignInResult(false))
            } catch (e: Exception) {
                emit(SignInResult(true))
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        Log.d("AuthnzViewModel", "onCleared\n")
    }
}