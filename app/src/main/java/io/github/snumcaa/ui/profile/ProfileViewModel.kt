package io.github.snumcaa.ui.profile

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers

import io.github.snumcaa.domain.entities.Profile
import io.github.snumcaa.domain.repositories.ProfileUpdate
import io.github.snumcaa.domain.repositories.UserRepository
import io.github.snumcaa.networking.BasicAuthClient

class ProfileViewModel(context: Context): ViewModel() {
    private val userRepository: UserRepository =
            BasicAuthClient<UserRepository>().createAuth(UserRepository::class.java, context)

    fun getProfile(): LiveData<Profile?> {
        return liveData(Dispatchers.IO) {
            try {
                Log.i("ProfileViewModel", "Fetching profile.")
                emit(Profile(userRepository.getProfile().profile))
            } catch(e: Exception) {
                Log.i("ProfileViewModel", e.message)
                Log.i("ProfileViewModel", "Failed to fetch profile.")
                emit(null)
            }
        }
    }

    fun updateProfile(profile: String): LiveData<Profile?> {
        return liveData(Dispatchers.IO) {
            try {
                emit(Profile(userRepository.updateProfile(ProfileUpdate(profile)).profile))
            } catch(e: Exception) {
                emit(null)
            }
        }
    }
}

class ProfileViewModelFactory(val context: Context?): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        val viewModel = when (context) {
            is Context -> ProfileViewModel(context)
            else -> null
        }

        return viewModel as T
    }
}
