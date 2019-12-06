package io.github.snumcaa.ui.videofeed

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.*

import io.github.snumcaa.domain.entities.YouTubeVideo
import io.github.snumcaa.domain.repositories.YouTubeVideoRepository
import io.github.snumcaa.networking.BasicAuthClient
import kotlinx.coroutines.Dispatchers

class VideoFeedViewModel(context: Context): ViewModel() {
    private val youTubeVideoRepository: YouTubeVideoRepository =
            BasicAuthClient<YouTubeVideoRepository>().createAuth(YouTubeVideoRepository::class.java, context)

    fun getVideos(): LiveData<List<YouTubeVideo>> {
        return liveData(Dispatchers.IO) {
            val videos = youTubeVideoRepository.getVideoRecommendations()
            emit(videos.map {
                YouTubeVideo(
                        it.videoId,
                        it.posterUsername,
                        it.text
                )
            })
        }
    }

    override fun onCleared() {
        super.onCleared()
        Log.d("VideoFeedViewModel", "onCleared\n")
    }
}

class VideoFeedViewModelFactory(val context: Context?): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        val viewModel = when (context) {
            is Context -> VideoFeedViewModel(context)
            else -> null
        }

        return viewModel as T
    }
}
