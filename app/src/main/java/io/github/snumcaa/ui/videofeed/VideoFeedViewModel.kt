package io.github.snumcaa.ui.videofeed

import android.util.Log
import androidx.lifecycle.*

import io.github.snumcaa.domain.entities.YouTubeVideo
import io.github.snumcaa.domain.repositories.YouTubeVideoRepository
import io.github.snumcaa.networking.BasicAuthClient
import kotlinx.coroutines.Dispatchers

class VideoFeedViewModel: ViewModel() {
    private val youTubeVideoRepository: YouTubeVideoRepository = BasicAuthClient<YouTubeVideoRepository>().create(YouTubeVideoRepository::class.java)

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
