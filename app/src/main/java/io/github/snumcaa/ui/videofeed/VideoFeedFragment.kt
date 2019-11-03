package io.github.snumcaa.ui.videofeed

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout

import io.github.snumcaa.R
import io.github.snumcaa.domain.entities.YouTubeVideo

class VideoFeedFragment: Fragment(), SwipeRefreshLayout.OnRefreshListener {
    private lateinit var viewModel: VideoFeedViewModel

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: VideoFeedAdapter
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_video_feed, container, false)

        recyclerView = view.findViewById(R.id.video_feed_recycler_view)
        viewModel = ViewModelProviders.of(this).get(VideoFeedViewModel::class.java)

        recyclerView.layoutManager = LinearLayoutManager(context)

        swipeRefreshLayout = view.findViewById(R.id.swipe_refresh_layout)
        swipeRefreshLayout.setOnRefreshListener(this)

        adapter = VideoFeedAdapter(context, viewModel)
        recyclerView.adapter = adapter

        onRefresh()

        return view
    }

    override fun onRefresh() {
        viewModel.getVideos()
                .observe(this, Observer<List<YouTubeVideo>> { t ->
                    t?.let {
                        adapter.setYouTubeVideos(it)
                    }
                })

        swipeRefreshLayout.isRefreshing = false
    }
}

