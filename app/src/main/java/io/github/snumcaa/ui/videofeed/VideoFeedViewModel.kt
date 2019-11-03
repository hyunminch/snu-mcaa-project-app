package io.github.snumcaa.ui.videofeed

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

import io.github.snumcaa.domain.entities.YouTubeVideo

class VideoFeedViewModel: ViewModel() {
    val text: LiveData<String> by lazy {
        val mutableLiveData = MutableLiveData<String>()
        mutableLiveData.value = "Alcatraz"
        mutableLiveData
    }

    val videos: LiveData<List<YouTubeVideo>> by lazy {
        val mutableLiveData = MutableLiveData<List<YouTubeVideo>>()
        mutableLiveData.value = listOf()
        mutableLiveData
    }

    override fun onCleared() {
        super.onCleared()
        Log.d("VideoFeedViewModel", "onCleared\n")
    }
}
