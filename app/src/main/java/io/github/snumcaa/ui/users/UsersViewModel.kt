package io.github.snumcaa.ui.users

import android.content.Context
import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers

import io.github.snumcaa.domain.entities.User
import io.github.snumcaa.domain.repositories.Followed
import io.github.snumcaa.domain.repositories.UserRepository
import io.github.snumcaa.networking.BasicAuthClient

class UsersViewModel(context: Context): ViewModel() {
    private val userRepository: UserRepository =
            BasicAuthClient<UserRepository>().createAuth(UserRepository::class.java, context)

    fun getUsers(): LiveData<List<User>> {
        return liveData(Dispatchers.IO) {
            emit(
                userRepository.getUsers()
                .map { user ->
                    User(
                            user.id,
                            user.username,
                            user.following
                    )
                }
            )
        }
    }

    fun followUser(userId: String): LiveData<Boolean> {
        return liveData(Dispatchers.IO) {
            emit(
                try {
                    userRepository.follow(Followed(userId))
                    true
                } catch(e: Exception) {
                    false
                }
            )
        }
    }

    fun unfollowUser(userId: String): LiveData<Boolean> {
        return liveData(Dispatchers.IO) {
            emit(
                try {
                    userRepository.unfollow(Followed(userId))
                    true
                } catch(e: Exception) {
                    false
                }
            )
        }
    }
}

class UsersViewModelFactory(val context: Context?): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        val viewModel = when (context) {
            is Context -> UsersViewModel(context)
            else -> null
        }

        return viewModel as T
    }
}
