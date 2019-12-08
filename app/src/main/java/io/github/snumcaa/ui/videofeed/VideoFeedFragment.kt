package io.github.snumcaa.ui.videofeed

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout

import io.github.snumcaa.R
import io.github.snumcaa.domain.entities.YouTubeVideo
import io.github.snumcaa.networking.Result

class VideoFeedFragment: Fragment(), SwipeRefreshLayout.OnRefreshListener, View.OnClickListener {
    private lateinit var viewModel: VideoFeedViewModel

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: VideoFeedAdapter
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout

    private lateinit var youTubeUrlEditText: EditText
    private lateinit var videoTextEditText: EditText
    private lateinit var recommendVideoButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProviders.of(this, VideoFeedViewModelFactory(context)).get(VideoFeedViewModel::class.java)
        adapter = VideoFeedAdapter(context, viewModel).apply {
            videoFeedItemListener = object : VideoFeedAdapter.VideoFeedItemListener {
                override fun onVideoShare(youTubeVideo: YouTubeVideo) {
                    shareVideo(youTubeVideo)
                }
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_video_feed, container, false)

        recyclerView = view.findViewById(R.id.video_feed_recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(context)

        swipeRefreshLayout = view.findViewById(R.id.swipe_refresh_layout)
        swipeRefreshLayout.setOnRefreshListener(this)

        recyclerView.adapter = adapter

        youTubeUrlEditText = view.findViewById(R.id.youtube_url_edit_text)
        videoTextEditText = view.findViewById(R.id.video_text_edit_text)
        recommendVideoButton = view.findViewById(R.id.recommend_video_button)

        recommendVideoButton.setOnClickListener(this)

        if (!adapter.isInitialized())
            onRefresh()

        return view
    }

    override fun onRefresh() {
        Log.i("VideoFeedFragment", "OnRefresh invoked.")

        viewModel.getVideos()
                .observe(this, Observer { result ->
                    when (result) {
                        is Result.Success -> {
                            adapter.setYouTubeVideos(result.data)
                        }
                        is Result.Error -> {
                            // TODO Error Handling
                        }
                    }
                })

        swipeRefreshLayout.isRefreshing = false
    }

    fun clearEditTexts() {
        youTubeUrlEditText.text.clear()
        videoTextEditText.text.clear()

        youTubeUrlEditText.clearFocus()
        videoTextEditText.clearFocus()

    }

    override fun onClick(v: View?) {
        val youTubeUrl = youTubeUrlEditText.text.toString()

        val text = {
            val rawText = videoTextEditText.text.toString()

            if (rawText.isEmpty())
                null
            else
                rawText
        }()

        viewModel
                .recommend(youTubeUrl, text)
                .observe(this, Observer { result ->
                    when (result) {
                        is Result.Success -> {
                            adapter.setYouTubeVideos(result.data)
                            clearEditTexts()
                        }
                        is Result.Error -> {
                            // TODO Error Handling
                        }
                    }
                })
    }

    private fun shareVideo(youTubeVideo: YouTubeVideo) {
        viewModel.share(youTubeVideo)
                .observe(this, Observer { result ->
                    when (result) {
                        is Result.Success -> {
                            adapter.updateItem(result.data)
                        }
                        is Result.Error -> {
                            // TODO Error Handling
                        }
                    }
                })
    }
}