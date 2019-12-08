package io.github.snumcaa.ui.notifications

import android.content.Context
import androidx.lifecycle.*
import io.github.snumcaa.domain.entities.Notification
import io.github.snumcaa.domain.repositories.NotificationRepository
import io.github.snumcaa.domain.repositories.NotificationResponse
import io.github.snumcaa.networking.BasicAuthClient
import io.github.snumcaa.networking.Result
import kotlinx.coroutines.Dispatchers

class NotificationsViewModel(private val repository: NotificationRepository) : ViewModel() {
    fun loadNotifications(): LiveData<Result<List<Notification>>> {
        return liveData(Dispatchers.Main) {
            try {
                val notifications = repository.getNotifications()
                emit(Result.Success(notifications.map { it.toViewModel() }))
            } catch (e: Exception) {
                emit(Result.Error(e))
            }
        }
    }
}

class NotificationsViewModelFactory(val context: Context?): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        val viewModel = when (context) {
            is Context -> NotificationsViewModel(
                    BasicAuthClient<NotificationRepository>().createAuth(NotificationRepository::class.java, context)
            )
            else -> null
        }

        return viewModel as T
    }
}

fun NotificationResponse.toViewModel() = Notification(videoId, message, sharedCount)