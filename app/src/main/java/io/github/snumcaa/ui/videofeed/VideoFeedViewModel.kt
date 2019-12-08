package io.github.snumcaa.ui.videofeed

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import io.github.snumcaa.domain.entities.YouTubeVideo
import io.github.snumcaa.domain.repositories.PostVideoRecommendation
import io.github.snumcaa.domain.repositories.ShareVideoRecommendation
import io.github.snumcaa.domain.repositories.YouTubeVideoRecommendation
import io.github.snumcaa.domain.repositories.YouTubeVideoRepository
import io.github.snumcaa.networking.BasicAuthClient
import io.github.snumcaa.networking.Result
import kotlinx.coroutines.Dispatchers

class VideoFeedViewModel(private val youTubeVideoRepository: YouTubeVideoRepository): ViewModel() {

    fun getVideos(): LiveData<Result<List<YouTubeVideo>>> {
        return liveData(Dispatchers.IO) {
            try {
                val videos = youTubeVideoRepository.getVideoRecommendations()
                emit(Result.Success(videos.map { it.toViewModel() }))
            } catch (e: Exception) {
                emit(Result.Error(e))
            }
        }
    }

    fun recommend(youTubeUrl: String, text: String?): LiveData<Result<List<YouTubeVideo>>> {
        return liveData(Dispatchers.IO) {
            try {
                youTubeVideoRepository.postVideoRecommendation(PostVideoRecommendation(youTubeUrl, text))
                val videos = youTubeVideoRepository.getVideoRecommendations()

                emit(Result.Success(videos.map { it.toViewModel() }))
            } catch(e: Exception) {
                emit(Result.Error(e))
            }
        }
    }

    fun share(youTubeVideo: YouTubeVideo): LiveData<Result<YouTubeVideo>> {
        return liveData(Dispatchers.IO) {
            try {
                val video = youTubeVideoRepository.shareVideoRecommendation(ShareVideoRecommendation(youTubeVideo.id))
                emit(Result.Success(video.toViewModel()))
            } catch (e: Exception) {
                emit(Result.Error(e))
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
            is Context -> VideoFeedViewModel(
                    BasicAuthClient<YouTubeVideoRepository>().createAuth(YouTubeVideoRepository::class.java, context)
            )
            else -> null
        }

        return viewModel as T
    }
}

fun YouTubeVideoRecommendation.toViewModel() = YouTubeVideo(id, videoId, posterUsername, text, shared)