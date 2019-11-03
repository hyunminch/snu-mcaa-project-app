package io.github.snumcaa.ui.videofeed

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import io.github.snumcaa.R

class VideoFeedFragment: Fragment() {
    private lateinit var viewModel: VideoFeedViewModel

    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_video_feed, container, false)

        recyclerView = view.findViewById(R.id.video_feed_recycler_view)
        viewModel = ViewModelProviders.of(this).get(VideoFeedViewModel::class.java)

        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = VideoFeedAdapter(context, viewModel)

        return view
    }
}
