package io.github.snumcaa.ui.videofeed

import android.content.Context
import android.util.Log
import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers

import io.github.snumcaa.domain.entities.YouTubeVideo
import io.github.snumcaa.domain.repositories.PostVideoRecommendation
import io.github.snumcaa.domain.repositories.YouTubeVideoRepository
import io.github.snumcaa.networking.BasicAuthClient

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

    fun recommend(youTubeUrl: String, text: String?): LiveData<List<YouTubeVideo>> {
        return liveData(Dispatchers.IO) {
            try {
                youTubeVideoRepository.postVideoRecommendation(PostVideoRecommendation(youTubeUrl, text))
                val videos = youTubeVideoRepository.getVideoRecommendations()

                emit(videos.map {
                    YouTubeVideo(
                            it.videoId,
                            it.posterUsername,
                            it.text
                    )
                })
            } catch(e: Exception) {
                // TODO: handle error
            }
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
